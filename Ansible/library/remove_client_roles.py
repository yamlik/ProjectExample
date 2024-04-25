#!/usr/bin/python


ANSIBLE_METADATA = {
    'metadata_version': '1.1',
    'status': ['stableinterface'],
    'supported_by': 'TEST'
}

DOCUMENTATION = '''
---
module: remove_client_roles
short_description: Removes client role(s) in Keycloak.
description:
    - "Remove existing client role in the Keycloak endpoint."
    - "The request is authenticated using Realm administrator credentials"
    - "Retrieves keycloak.json adapter configuration from the client installation endpoint and returns it along with effective ClientRepresentation."
version_added: "2.4"
author: "TEST Corporation"
options:
    server:
        description:
            - Keycloak server URL, e.g. U(https://localhost:8081/auth).
            - Must contain scheme, FQDN (or IP address) and context path.
        required: true
    realm:
        description:
            - Name of the realm where clients will be registered.
            - Should not be the master realm.
        required: true
    username:
        description:
            - Realm administrator username.
        required: true
    password:
        description:
            - Realm administrator password.
        required: true
    verify_ssl:
        description:
            - Whether server certificate should be verified.
            - If set to 'false', the certificate is not verified.
            - If set to 'true', verifies the certificate with trusted certificate authorities.
            - If instead a path to a file or directory is given, the certificates in the given file or directory are used.
        default: false
        type: str
    clientId:
        description:
            - Id of the Keycloak Client.
        required: true
        type: str
    client_roles:
        description:
            - A list of RoleRepresentation instances that will be removed for the client.
            - For available suboptions, see U(http://www.keycloak.org/docs-api/3.4/rest-api/index.html#_rolerepresentation).
            - Name attribute is required for each RoleRepresentation.
        required: true
        type: list
notes:
    - Keycloak server URL scheme MUST be HTTPS.
'''

EXAMPLES = '''
- name: Remove Awesomo client roles
  remove_client_roles:
    server: https://id.TEST.com:8081/auth
    realm: TEST
    username: realmadmin
    password: realmadmin
    verify_ssl: /etc/ssl/certs/ca-bundle.trust.crt
    clientId: awesomo
    client_roles:
      - name: awesomo.read
        description: Read permissions to Awesomo app
      - name: awesomo.create
        description: Create permissions to Awesomo app
'''

from ansible.module_utils.basic import AnsibleModule

import json
import requests
import base64

CONTENT_TYPE_FORM = 'application/x-www-form-urlencoded'
CONTENT_TYPE_JSON = 'application/json'

def parse_verify_ssl(verify_ssl):
    if verify_ssl in [ 'true', 'True', True ]:
        return True
    if verify_ssl in [ 'false', 'False', False ]:
        return False
    return verify_ssl

class RemoveClientRolesService:
    ADMIN_CLIENT_ID = 'admin-cli'
    
    def __endpoints( self ):
        base_path = "%s/realms/%s" % (self.server, self.realm)
        oidc_endpoints = '%s/protocol/openid-connect' % base_path
        
        self.token_endpoint = '%s/token' % oidc_endpoints
        self.logout_endpoint = '%s/logout' % oidc_endpoints
        
        admin_api = '%s/admin/realms/%s' % (self.server, self.realm)      
        reg_endpoint = '%s/clients-registrations' % base_path
        
        self.clients_endpoint = "%s/clients" % admin_api
        self.users_endpoint = "%s/users" % admin_api
        
        self.create_client_endpoint = '%s/default' % reg_endpoint
        self.adapter_conf_endpoint = '%s/install' % reg_endpoint     
        
    def __init__( self, server, realm, verify_ssl ):
        self.server     = server
        self.realm      = realm
        self.verify_ssl = parse_verify_ssl(verify_ssl)
        self.__endpoints()

    def authenticate( self, username, password ):
        payload = { "grant_type" : "password", "client_id" : self.ADMIN_CLIENT_ID, "username" : username, "password" : password }
        result = self.post(self.token_endpoint, data = payload, headers = self._headers(content_type=CONTENT_TYPE_FORM, authorize=False)).json()
        self.access_token, self.refresh_token = result['access_token'], result['refresh_token']
    
    def _headers( self, content_type=CONTENT_TYPE_JSON, authorize=True ):
        headers = { 'Content-Type': content_type }
        if authorize:
            headers['Authorization'] = 'Bearer ' + self.access_token
        return headers
    
    def delete( self, url, headers, data ):
        return requests.delete(url, data = data, headers = headers, verify=self.verify_ssl)
    
    def post( self, url, headers, data ):
        return requests.post(url, data = data, headers = headers, verify=self.verify_ssl)
        
    def get( self, url, headers ):
        headers['Authorization'] = 'Bearer ' + self.access_token
        response = requests.get(url=url, headers=headers, verify=self.verify_ssl)
        return response
        
    def get_client( self, client_id ):
        return self.get("%s/?clientId=%s" % (self.clients_endpoint, client_id), self._headers())
    
    def get_role( self, container_id, role_name ):
        return self.get("%s/%s/roles/%s" % (self.clients_endpoint, container_id, role_name), self._headers())
    
    def remove_role( self, container_id, role_name, role_representation ):
        return self.delete("%s/%s/roles/%s" % (self.clients_endpoint, container_id, role_name), data = json.dumps(role_representation), headers = self._headers())

    def logout( self ):
        form_data = { 'client_id': self.ADMIN_CLIENT_ID, 'refresh_token': self.refresh_token }
        self.post(self.logout_endpoint, headers=self._headers(content_type=CONTENT_TYPE_FORM), data=form_data)

    
def request_and_raise(module, func):
    response = None
    try:
        response = func()
        response.raise_for_status()
        return response
    except Exception as e:
        module.fail_json(msg='Error occurred: %s. Response contents: %s' % (e, response.text))
    
def main():
    module = AnsibleModule(
        argument_spec = dict(
            clientId                = dict(required=True, type='str'),
            client_roles            = dict(required=True, type='list'),
            server                  = dict(required=True, type='str'),
            realm                   = dict(required=True, type='str'),
            verify_ssl              = dict(default=False, type='str'),
            username                = dict(required=True, type='str'),
            password                = dict(required=True, type='str')
        )
    )

    server                  = module.params['server']
    realm                   = module.params['realm']
    verify_ssl              = module.params['verify_ssl']
    username                = module.params['username']
    password                = module.params['password']

    client_id               = module.params['clientId']
    client_roles            = module.params['client_roles']
    
    service = RemoveClientRolesService(server, realm, verify_ssl)
    
    try:
        result = service.authenticate(username, password)
        
        clients = service.get_client(client_id)
        clients.raise_for_status() # Raise error for 4XX and 5XX status codes

        if len(clients.json()) > 0:
            clients_json = clients.json()[0]
        else:
            raise Exception('clientId (%s) not found' % client_id)

        removed = False
        removedRoles = list()
        # The generated ID of the client
        container_id = clients_json['id']
        for role_representation in client_roles:
            role = service.get_role(container_id, role_representation["name"])
            if role.status_code == 200:
                roles_response = service.remove_role(container_id, role_representation["name"], role_representation)
                if roles_response.status_code == 204:
                    removed = True
                    removedRoles.append(role_representation["name"])
                
        service.logout()

        if removed:
            module.exit_json(clientId=client_id, removedRoles=removedRoles, changed=removed)
        else:
            module.exit_json(changed=removed)

    except Exception as e:
        module.fail_json(msg='Error occurred: %s' % e)

if __name__ == '__main__':
    main()
