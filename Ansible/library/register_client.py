#!/usr/bin/python


ANSIBLE_METADATA = {
    'metadata_version': '1.1',
    'status': ['stableinterface'],
    'supported_by': 'TEST'
}

DOCUMENTATION = '''
---
module: register_client
short_description: Registers an OpenID Connect client in Keycloak and retrieves Keycloak Adapter configuration.
description:
    - "Registers a new or updates existing client in the Keycloak client registration endpoint."
    - "The request is authenticated using Realm administrator credentials"
    - "Retrieves keycloak.json adapter configuration from the client installation endpoint and returns it along with effective ClientRepresentation."
version_added: "2.4"
author: "TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>"
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
    client_representation:
        description:
            - An instance of ClientRepresentation.
            - For available suboptions, see U(http://www.keycloak.org/docs-api/3.4/rest-api/index.html#_clientrepresentation).
        required: true
        type: dict
    client_roles:
        description:
            - A list of RoleRepresentation instances that will be created or updated for the client.
            - For available suboptions, see U(http://www.keycloak.org/docs-api/3.4/rest-api/index.html#_rolerepresentation).
            - Name attribute is required for each RoleRepresentation.
        default: []
        type: list
    adapter_config_extras:
        description:
            - A dictionary of key - value pairs that will be merged to the adapter configuration.
        default: {}
        type: dict
notes:
    - Keycloak server URL scheme MUST be HTTPS.
'''

EXAMPLES = '''
# This configuration resembles closely to shiro-client configuration template
- name: Register Awesomo client
  register_client:
    server: https://id.TEST.com:8081/auth
    realm: TEST
    username: realmadmin
    password: realmadmin
    verify_ssl: /etc/ssl/certs/ca-bundle.trust.crt
    client_representation:
      adminUrl: http://ui.TEST.com:8080/awesomo/keycloak
      baseUrl: http://ui.TEST.com:8080/awesomo
      webOrigins:
        - http://ui.TEST.com:8080
      redirectUris:
        - http://ui.TEST.com:8080/awesomo/*
      clientId: awesomo
      name: "Awesomo Service"
      description: "A very awesome client"
      enabled: true
      fullScopeAllowed: true
      publicClient: false
      consentRequired: false
      protocol: openid-connect
      bearerOnly: false
      directAccessGrantsEnabled: false
      implicitFlowEnabled: false
      standardFlowEnabled: true
      serviceAccountsEnabled: true
    client_roles:
      - name: awesomo.read
        description: Read permissions to Awesomo app
      - name: awesomo.create
        description: Create permissions to Awesomo app

# Register a Shiro based client using shiro-client template
- name: Register TEST Standard Client
  register_client:
    server: https://id.TEST.com:8081/auth
    realm: TEST
    username: realmadmin
    password: realmadmin
    client_representation:
      adminUrl: http://ui.TEST.com:8080/elclient
      baseUrl: http://ui.TEST.com:8080/elclient
      webOrigins:
        - http://ui.TEST.com:8080
      redirectUris:
        - http://ui.TEST.com:8080/elclient/*
      clientId: elclient
      name: "TEST Standard Client"
      description: "API for Standard Stuff"
      enabled: true
      clientTemplate: shiro-client
      useTemplateConfig: true
      useTemplateMappers: true
    client_roles:
      - name: elclient.admin
        description: Administrative permission to TEST Standard Clent

# Rename the client from previous example and edit description
- name: Reconfigure TEST Standard Client
  register_client:
    server: https://id.TEST.com:8081/auth
    realm: TEST
    username: realmadmin
    password: realmadmin
    client_representation:
      clientId: elclient
      name: "TEST Standard Client"
      description: "A Standard TEST client for Standard Stuff"
'''

RETURN = '''
client_representation:
    description: The effective ClientRepresentation, for a full list of suboptions, see U(http://www.keycloak.org/docs-api/3.4/rest-api/index.html#_clientrepresentation)
    returned: success
    type: complex
adapter_config:
    description: Keycloak Adapter configuration
    returned: success
    type: complex
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

class RegistrationService:
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

    def create_client( self, client_representation):
        return self.post(self.create_client_endpoint, data = json.dumps(client_representation), headers = self._headers())
    
    def update_client( self, client_id, client_representation ):
        return self.put("%s/%s" % (self.create_client_endpoint, client_id), data = json.dumps(client_representation), headers = self._headers())
    
    def _headers( self, content_type=CONTENT_TYPE_JSON, authorize=True ):
        headers = { 'Content-Type': content_type }
        if authorize:
            headers['Authorization'] = 'Bearer ' + self.access_token
        return headers
    
    def put( self, url, headers, data ):
        return requests.put(url, data = data, headers = headers, verify=self.verify_ssl)
    
    def post( self, url, headers, data ):
        return requests.post(url, data = data, headers = headers, verify=self.verify_ssl)
        
    def get( self, url, headers ):
        headers['Authorization'] = 'Bearer ' + self.access_token
        response = requests.get(url=url, headers=headers, verify=self.verify_ssl)
        return response
        
    def get_client( self, client_id ):
        return self.get("%s/?clientId=%s" % (self.clients_endpoint, client_id), self._headers())
        
    def get_client_secret( self ):
        return self.get(self.client_secret_endpoint, self._headers())
        
    def get_client_representation( self ):
        return self.get(self.client_rep_endpoint, self._headers())
        
    def get_roles( self, client_id ):
        return self.get("%s/%s/roles" % (self.clients_endpoint, client_id), self._headers())
    
    def get_role( self, container_id, role_name ):
        return self.get("%s/%s/roles/%s" % (self.clients_endpoint, container_id, role_name), self._headers())
        
    def create_role( self, container_id, role_representation ):
        return self.post("%s/%s/roles" % (self.clients_endpoint, container_id), data = json.dumps(role_representation), headers = self._headers())
    
    def update_role( self, container_id, role_name, role_representation ):
        return self.put("%s/%s/roles/%s" % (self.clients_endpoint, container_id, role_name), data = json.dumps(role_representation), headers = self._headers())
    
    def grant_user_client_roles( self, user_id, container_id, role_representations ):
        return self.post("%s/%s/role-mappings/clients/%s" % (self.users_endpoint, user_id, container_id), data = json.dumps(role_representations), headers = self._headers())
    
    def grant_service_account_roles( self, container_id, role_representations ):
        service_account_id = self.get_service_account_id(container_id)
        return self.grant_user_client_roles(service_account_id, container_id, role_representations)
    
    def get_service_account_id( self, container_id ):
        user_repr_response = self.get("%s/%s/service-account-user" % (self.clients_endpoint, container_id), self._headers())
        user_repr_response.raise_for_status()
        return user_repr_response.json()['id']
    
    def get_adapter_conf( self, client_id ):
        return self.get("%s/%s" % (self.adapter_conf_endpoint, client_id), self._headers())
        
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
            client_representation   = dict(required=True, type='dict'),
            client_roles            = dict(default=[], type='list'),
            adapter_config_extras   = dict(default={}, type='dict'),
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

    client_representation   = module.params['client_representation']
    client_roles            = module.params['client_roles']
    adapter_config_extras   = module.params['adapter_config_extras']
    
    service = RegistrationService(server, realm, verify_ssl)
    
    try:
        result = service.authenticate(username, password)

        client_id = client_representation['clientId']
        
        clients = service.get_client(client_id)
        clients.raise_for_status() # Raise error for 4XX and 5XX status codes
    
        if len(clients.json()) == 0:
            client_representation_response = service.create_client(client_representation)
        else:
            client_representation_response = service.update_client(client_id, client_representation)
        
        client_representation_response.raise_for_status()
        client_representation_json = client_representation_response.json()
        
        # The generated ID of the client
        container_id = client_representation_json['id']
        for role_representation in client_roles:
            role = service.get_role(container_id, role_representation["name"])
            if role.status_code == 404:
                roles_response = service.create_role(container_id, role_representation)
            else:
                roles_response = service.update_role(container_id, role_representation["name"], role_representation)
        
        if client_representation_json['serviceAccountsEnabled']:
            client_roles = service.get_roles(container_id)
            service.grant_service_account_roles(container_id, client_roles.json())
        
        adapter_config_response = service.get_adapter_conf(client_id)
        adapter_config_response.raise_for_status()
                
        service.logout()
        
        adapter_config = dict(adapter_config_response.json().items() + adapter_config_extras.items())

        module.exit_json(client_representation=client_representation_json, adapter_config=adapter_config, changed=True)
    except Exception as e:
        module.fail_json(msg='Error occurred: %s' % e)

if __name__ == '__main__':
    main()
