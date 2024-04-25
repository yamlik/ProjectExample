#!/usr/bin/python

ANSIBLE_METADATA = {
    'metadata_version': '1.0',
    'supported_by': 'TEST',
    'status': ['stableinterface']
}

DOCUMENTATION = '''
---
module: versioninfo
short_description: A RESTful client for sending host and component information to Version Info backend.
description:
    - Gathers names, versions and installtimes for all installed RPM packages developed by TEST.
    - Creates a JSON containing the hostname, installation path and installed RPM packages.
    - Sends the object to Version Info backend through Admin Tools REST interface.
    - Version info for host can be created with state "present", updated with state "updated" and removed
      with state "absent".
version_added: "2.2"
author: TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
options:
    apikey:
        description:
            - Admin Tools API key registered to User Management backend.
        required: true
        type: str
    endpoint:
        description:
            - Fully-qualified domain name of Admin Tools backend.
        required: true
        type: str
    hostname:
        description:
            - Fully-qualified domain name of the sending host.
            - Typically should be {{ inventory_hostname }}.
        required: true
        type: str
    protocol:
        description:
            - Network protocol used to connect to Admin Tools.
        type: str
        choices: ['http','https']
        default: 'http'
    timeout:
        description:
            - Seconds after which the request should time out.
        type: int
        default: 30
    state:
        description:
            - Desired state after the operation.
        type: str
        choices: ['present','updated','absent']
        default: 'present'
    port:
        description:
            - Port which Admin Tools backend is listening.
        type: int
        default: 45080
requirements: [md5, json, urllib, requests, subprocess, tzlocal, datetime]
notes:
    - Placeholder
'''

EXAMPLES = '''
- name: Send version information
  versioninfo:
    apikey: 1Ab23cD45EF67gh
    endpoint: admintools.TEST.com
    hostname: "{{ inventory hostname }}"
    
- name: Remove version info
  versioninfo:
    apikey: 1Ab23cD45EF67gh
    endpoint: admintools.TEST.com
    hostname: "{{ inventory_hostname }}"
    protocol: https
    port: 45081
    state: absent
    timeout: 60
'''

import md5
import json
import urllib
import requests
import subprocess
from tzlocal import get_localzone
from datetime import datetime

# Collects component version information from RPM and returns a python dictionary
class VersionInfoCollector:

    def __init__( self, host, path=None, date=None ):
        self.localzone = get_localzone()
        self.host = host
        self.path = path if path else "/opt/TEST"
        self.components = self._collect_components()

    def _collect_components( self ):
        try:
            out = subprocess.check_output(['rpm','-qa','--queryformat','%{name} | %{version} | %{installtime} | %{vendor}\n']).strip()
            
            lines = [ line.split(' | ') for line in out.splitlines() ] # Split each line to segments divided by '|'
            packages = [ pkg for pkg in lines if self._TEST_filter(pkg[3]) ] # Filter out non-TEST/TEST packages
            
            return [ { 'name': pkg[0], 'version': pkg[1], 'installIsoDate': self._time_to_iso(pkg[2]) } for pkg in packages ]
        except subprocess.CalledProcessError as cpe:
            raise RuntimeError("Could not gather version information from RPM: " + str(cpe))

    def _time_to_iso( self, time ):
        dt = datetime.fromtimestamp(int(time), self.localzone)
        '''
        Version Info backend only allows timestamps in format
            'YYYY-MM-DD hh:mm:ss.SSSXXX' i.e. ISO 8601 with milliseconds
        However, it does not allow timestamps with coarser or finer time resolution.
         * datetime returns timestamps with either second or microsecond time resolution.
         * installtime from RPM are always in seconds.
         
        TODO: Update Version Info backend to allow all ISO 8601 format timestamps
        Then the following line can be updated to:
            return dt.isoformat()
        '''
        # 0-18: timestamp, 19: timezone
        return '{}.000{}'.format(dt.isoformat(' ')[:19], dt.isoformat(' ')[19:])

    def _TEST_filter( self, string ):
        return string.decode('utf-8').lower() in ['TEST', 'TEST corporation', 'TEST', 'TEST corporation']

    def collect( self ):
        return { 'host': self.host, 'path': self.path, 'components': self.components }
        
def parse_verify_ssl(verify_ssl):
    if verify_ssl in [ 'true', 'True', True ]:
        return True
    if verify_ssl in [ 'false', 'False', False ]:
        return False
    return verify_ssl
        
class VersionInfoRESTClient:
    DEFAULT_TIMEOUT = 60

    def __init__( self, url, verify_ssl=False, timeout=None):
        self.url                = url
        self.verify_ssl         = parse_verify_ssl(verify_ssl)
        self.timeout            = timeout if timeout and timeout > 0 else self.DEFAULT_TIMEOUT

    def access_token( self, access_token ):
        self.access_token = access_token

    def _send_request( self, method, params=None, data=None, url=None):
        headers = { 'Authorization' : 'Bearer ' + self.access_token, 'Content-Type' : 'application/json'  }
        
        response = method(url if url else self.url, params = params, data = data, headers = headers, verify = self.verify_ssl)
        response.raise_for_status()

        if response.text:
            resource = response.json()
            if not resource:
                return None
            elif isinstance(resource, dict):
                return resource
            elif isinstance(resource, list):
                return resource[0]
        return None

    def read( self, q_params ):
        return self._send_request(requests.get, params = q_params)

    def update( self, resource ):
        return self._send_request(requests.update, data = json.dumps(resource))

    def delete( self, resource ):
        url = "%s/%s" % (self.url, str(resource['id']))
        return self._send_request(requests.delete, data = json.dumps(resource), url = url)

    def create( self, resource ):
        return self._send_request(requests.post, data = json.dumps(resource))

# Concatenates substrings of 'starts_with' with 'string' until
# string actually starts with 'starts_with'
def complete_start( string, starts_with, end_index=0 ):
    concat = starts_with[:end_index] + string
    return concat if concat.startswith(starts_with) else complete_start(string, starts_with, end_index+1)

# Concatenates 'string' with substrings of 'ends_with' until
# string actually ends with 'ends_with'
def complete_end( string, ends_with, start_index=0 ):
    concat = string + ends_with[start_index:] if start_index < 0 else string
    return concat if concat.endswith(ends_with) else complete_end(string, ends_with, start_index-1)

# Adds desired padding to string, if not already present
def complete_padding( string, starts_with, ends_with ):
    string = complete_start(string, starts_with)
    return complete_end(string, ends_with)

# Compares two dict types with each other.
# Goes through the keys of 'expected' and expects 'actual' to
#   1 ) have the key defined and
#   2 ) have the same value in key
def differs( expected, actual ):
    if not expected and not actual:
        return False

    if not actual:
        return True

    if not isinstance(expected, dict) or not isinstance(actual, dict):
        return True

    # Backend does not return password, so it cannot be compared.
    ex = expected.copy()
    ex.pop('password', None)

    for key in ex.keys():
        if actual[key] is None or ex[key] != actual[key]:
            return True

    return False

class VersionInfoService:
    def _endpoints( self ):
        oidc_endpoints          = '%srealms/%s/protocol/openid-connect' % (self.server, self.realm)
        self.token_endpoint     = '%s/token' % oidc_endpoints
    
    def __init__( self, server, realm, rest_api_url, verify_ssl, timeout=None ):
        self.server         = server
        self.realm          = realm
        self.verify_ssl     = parse_verify_ssl(verify_ssl)
        
        self._endpoints()
        
        self.api = VersionInfoRESTClient(url=rest_api_url, verify_ssl=self.verify_ssl, timeout=timeout)

    def _credentials( self ):
        return base64.b64encode("%s:%s" % (self.client_id, self.client_secret))
        
    def _headers( self ):
        return { 'Authorization': 'Basic ' + self._credentials(), 'Content-Type': 'application/x-www-form-urlencoded' }
        
    def authenticate( self, client_id, client_secret ):
        self.client_id = client_id
        self.client_secret = client_secret
    
        payload = { 'grant_type' : 'client_credentials' }
        result = requests.post(self.token_endpoint, data = payload, headers = self._headers(), verify=self.verify_ssl)
        result.raise_for_status()
        
        result_json = result.json()
        self.access_token = result_json['access_token']
        
        self.api.access_token(self.access_token)

        
    def create_name_query( self, resource ):
        query_key = "host"
        query_value = resource[query_key]

        return { query_key: query_value }

    def create( self, result, resource ):
        created = self.api.create(resource)
        result['msg'] = "Resource was successfully created"
        result['changed'] = True
        return result, created

    def update( self, result, resource ):
        updated = self.api.update(resource)
        result['msg'] = "Resource was successfully updated"
        if differs(resource, updated):
            result['changed'] = True
        else:
            result['changed'] = False
        return result, updated

    def delete( self, result, resource ):
        deleted = self.api.delete(resource)
        resource = deleted
        result['msg'] = "Resource was successfully deleted"
        result['changed'] = True
        return result, deleted

    def absent( self, result, resource ):
        result = { 'changed': False, 'failed': False, 'msg': "" }
        found = self.api.read(self.create_name_query(resource))
        if found:
            return self.delete(result, found)
        result['msg'] = "Resource was already absent"
        return result, resource

    def present( self, result, resource ):
        found = self.api.read(self.create_name_query(resource))
        if found:
            result['msg'] = "Resource was already present"
            result['changed'] = False
            return result, found
        return self.create(result, resource)

    def updated( self, result, resource ):
        found = self.api.read(self.create_name_query(resource))
        if found:
            resource['id'] = found['id']
            return self.update(result, resource)
        return self.create(result, resource)

    def handle( self, state, resource ):
        result = dict(changed=False, failed=False, msg="")
        copy = resource.copy()
        return self.handler_functions[state](self, result, copy)

    handler_functions = {
        'present': present,
        'updated': updated,
        'absent': absent
    }

from ansible.module_utils.basic import AnsibleModule
import base64

DEFAULT_PORTS = {
    'http': 45080,
    'https': 45081
}
def get_rest_api_url(host, protocol="https", port=None):
    port = port if port else DEFAULT_PORTS[protocol]
    return complete_padding(host, protocol + "://", ":" + str(port) + "/admintools/api/components")

def main():
    module = AnsibleModule(
        argument_spec = dict(
            adapter_config          = dict(required=True, type='dict'),
                    
            rest_api_protocol       = dict(default='http', type='str'),
            rest_api_endpoint       = dict(required=True, type='str'),
            rest_api_port           = dict(default=None, type='int'),
            verify_ssl              = dict(default=False, type='str'),    
            
            hostname        = dict(required=True, type='str'),
            timeout         = dict(default=30, type='int'),
            state           = dict(default='present', choices=['absent','present','updated'])
        )
    )

    # Keycloak
    adapter_config   = module.params['adapter_config']
    
    # REST API
    rest_api_protocol        = module.params['rest_api_protocol']
    rest_api_endpoint        = module.params['rest_api_endpoint']
    rest_api_port            = module.params['rest_api_port']
    verify_ssl               = module.params['verify_ssl']
    
    # General
    hostname        = module.params['hostname']
    timeout         = module.params['timeout']
    state           = module.params['state']
    
    version_info = VersionInfoCollector(hostname).collect()

    client_id, client_secret = adapter_config['resource'], adapter_config['credentials']['secret']
    realm, server = adapter_config['realm'], adapter_config['auth-server-url']
       
    rest_api_url = get_rest_api_url(protocol=rest_api_protocol, host=rest_api_endpoint, port=rest_api_port)
    client = VersionInfoService(server, realm, rest_api_url, verify_ssl, timeout)
    try:
        client.authenticate(client_id, client_secret)
        result, response = client.handle(state, version_info)
        if result['failed']:
            module.fail_json(msg=result['msg'], response=response, version_info=version_info)
        else:
            module.exit_json(msg=result['msg'], changed=result['changed'], response=response, version_info=version_info)
    except requests.exceptions.Timeout as t:
        module.fail_json(msg="Connection timed out: " + str(t), version_info=version_info)
    except requests.exceptions.ConnectionError as e:
        module.fail_json(msg="Connection failed: " + str(e), version_info=version_info)
    except requests.exceptions.HTTPError as e:
        module.fail_json(msg="An error occurred with request: " + str(e) + ". Check server logs for more information", version_info=version_info)

if __name__ == '__main__':
    main()

