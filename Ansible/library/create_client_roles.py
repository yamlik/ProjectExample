#!/usr/bin/python

ANSIBLE_METADATA = {
    "metadata_version": "1.0",
    "status": ["stableinterface"],
    "supported_by": "TEST"
}

DOCUMENTATION = '''
module: create_client_roles
short_description: Create one or more roles for a client
options:
  server:
    description:
        - Keycloak server URL, e.g. U(https://localhost:8081/auth)
  realm:
    description:
        - Name of the realm where the client's role will be created
  verify_ssl:
    description:
        - Path of the Keycloak's certificate
  username:
    description:
        - Username of the realm administrator
  password:
    description:
        - Password of the realm administrator
  client:
    description:
        - Client where the role is to be created
  client_roles:
    description:
        - List of roles to be created
'''

EXAMPLES = '''
- name: "Create YOURROLE for TEST_ui client"
  create_client_roles:
    server: "https://{{ kc_fqdn }}:8666/auth"
    realm: "TEST"
    verify_ssl: "/opt/TEST/ansible/keycloak-certificate"
    username: "realmadmin"
    password: "realmadmin"
    client: "TEST_ui"
    client_roles:
      - name: "YOURROLE"
        description: "Example of YOURROLE"
'''

RETURN = '''
'''

from ansible.module_utils.basic import AnsibleModule

import json
import requests
import base64

CONTENT_TYPE_FORM = "application/x-www-form-urlencoded"
CONTENT_TYPE_JSON = "application/json"

class RegistrationService:

    ADMIN_CLIENT_ID = "admin-cli"

    def __endpoints(self):
        base_path = "%s/realms/%s" % (self.server, self.realm)
        oidc_endpoints = "%s/protocol/openid-connect" % base_path
        self.token_endpoint = "%s/token" % oidc_endpoints
        self.logout_endpoint = "%s/logout" % oidc_endpoints
        admin_api = "%s/admin/realms/%s" % (self.server, self.realm)
        self.clients_endpoint = "%s/clients" % admin_api

    def __init__(self, server, realm, verify_ssl):
        self.server     = server
        self.realm      = realm
        self.verify_ssl = verify_ssl
        self.__endpoints()

    def authenticate(self, username, password):
        payload = { "grant_type" : "password", "client_id" : self.ADMIN_CLIENT_ID, "username" : username, "password" : password }
        result = self.post(self.token_endpoint, data = payload, headers = self._headers(content_type=CONTENT_TYPE_FORM, authorize=False)).json()
        self.access_token, self.refresh_token = result["access_token"], result["refresh_token"]

    def _headers(self, content_type = CONTENT_TYPE_JSON, authorize = True):
        headers = { "Content-Type": content_type }
        if authorize:
            headers["Authorization"] = "Bearer " + self.access_token
        return headers

    def post(self, url, headers, data):
        return requests.post(url, data = data, headers = headers, verify = self.verify_ssl)

    def get(self, url, headers):
        headers["Authorization"] = "Bearer " + self.access_token
        response = requests.get(url = url, headers = headers, verify = self.verify_ssl)
        return response

    def get_client(self, client_id):
        return self.get("%s/?clientId=%s" % (self.clients_endpoint, client_id), self._headers())

    def get_client_container_id(self, client_id):
        clients_reponse = self.get_client(client_id)
        clients_reponse.raise_for_status()
        clients = clients_reponse.json()
        if len(clients) == 0:
            raise Exception(ERRORS["NO_CLIENT"] % client_id)
        return clients[0]["id"]

    def get_role(self, container_id, role_name):
        return self.get("%s/%s/roles/%s" % (self.clients_endpoint, container_id, role_name), self._headers())

    def create_role(self, container_id, role_representation):
        return self.post("%s/%s/roles" % (self.clients_endpoint, container_id), data = json.dumps(role_representation), headers = self._headers())

    def logout(self):
        form_data = { "client_id": self.ADMIN_CLIENT_ID, "refresh_token": self.refresh_token }
        self.post(self.logout_endpoint, headers = self._headers(content_type = CONTENT_TYPE_FORM), data = form_data)

def main():

    module_args = dict(
        server        = dict(required = True, type = "str"),
        realm         = dict(required = True, type = "str"),
        verify_ssl    = dict(required = True, type = "str"),
        username      = dict(required = True, type = "str"),
        password      = dict(required = True, type = "str"),
        client        = dict(required = True, type = "str"),
        client_roles  = dict(default = [], type = "list")
    )

    module = AnsibleModule(
        argument_spec = module_args
    )

    server       = module.params["server"]
    realm        = module.params["realm"]
    verify_ssl   = module.params["verify_ssl"]
    username     = module.params["username"]
    password     = module.params["password"]
    client       = module.params["client"]
    client_roles = module.params["client_roles"]

    service = RegistrationService(server, realm, verify_ssl)

    try:
        service.authenticate(username, password)
        container_id = service.get_client_container_id(client)
        roles_created = []
        roles_ignored = []
        for role in client_roles:
            resp = service.get_role(container_id, role["name"])
            if resp.status_code == 404:
                resp = service.create_role(container_id, {
                    "name": role["name"],
                    "description": role["description"]
                })
                if resp.status_code == 201:
                    roles_created.append(role["name"])
            else:
                roles_ignored.append(role["name"])


        service.logout()

        result = dict(
            changed = bool(roles_created),
            roles_created = roles_created,
            roles_ignored = roles_ignored
        )
        module.exit_json(**result)

    except Exception as e:
        module.fail_json(msg = "Error occurred: %s" % e)

if __name__ == "__main__":
    main()
