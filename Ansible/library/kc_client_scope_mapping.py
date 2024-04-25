#!/usr/bin/python


ANSIBLE_METADATA = {
    'metadata_version': '1.1',
    'status': ['stableinterface'],
    'supported_by': 'TEST'
}

DOCUMENTATION = '''
---
module: kc_client_scope_mapping
short_description: Update OpenID Connect client scope mappings in Keycloak and assign roles to service account.
description:
    - "Add client-level roles to the client's scope."
    - "Assigns these roles to client's service account user."
    - "The request is authenticated using Realm administrator credentials."
    - "For more information review U(https://www.keycloak.org/docs-api/2.5/rest-api/index.html)."
    - "This module supports check_mode."
version_added: "2.4"
author: "TEST A&A, Team Musala <TeamMusala@groups.TEST.com>"
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
    client_id:
        description:
            - Client ID which will be the target client for scope manipulation.
        required: true
        type: str
    scope_mappings:
        description:
            - A list of role name and client IDs.
            - role_name attribute is required for each line.
            - client_id attribute is required for each line.
            - assign_to_service_account attribute is set to True in case role needs to be assigned to client's service account.
            - assign_to_service_account attribute is optional. 
        default: []
        type: list

notes:
    - Keycloak server URL scheme MUST be HTTPS.
'''

EXAMPLES = '''
# Add client-level roles manage-clients and module.settings to TEST_ui client scope
# Only manage-clients role will be assigned to TEST_ui client's service account

- name: "Create scope mapping for TEST_ui client"
  kc_client_scope_mapping:
    server: "https://do166.TEST.com:8666/auth"
    realm: "TEST"
    username: "realmadmin"
    password: "realmadmin"
    verify_ssl: "/tmp/keycloak-certificate.tmp"
    client_id: "TEST_ui"
    scope_mappings:
      - { client_id: "admintools", role_name: "module.settings" }
      - { client_id: "realm-management", role_name: "view-clients", assign_to_service_account: True }
'''

from ansible.module_utils.basic import AnsibleModule

import json
import requests

CONTENT_TYPE_FORM = 'application/x-www-form-urlencoded'
CONTENT_TYPE_JSON = 'application/json'

ERRORS = dict(
    RESOURCE_VALIDATION_ERROR= "Required field missing: %s. Keys are case-sensitive. Check spelling.",
    NO_CLIENT='Module error: Client with client ID %s is not registered. Please use register_client Ansible module to register one first.',
    ROLE_NOT_FOUND='Role with name: %s not found available. Source client_id: %s',
    MAPPING_ASSIGNMENT_ERROR='Error occurred during roles mapping assignment. target_client_id: %s, source_client_id: %s, role_name: %s, response_status_code: %s, response_text: %s',
    ROLE_ASSIGNMENT_ERROR='Error occurred during roles assignment to user. user_id: %s, source_client_id: %s, role_name: %s, response_status_code: %s, response_text: %s',
    GENERIC_RESPONSE_ERROR='Error occurred: %s. Response contents: %s',
    CONNECTION_TIMEOUT='Connection timed out: %s',
    CONNECTION_ERROR='Could not connect to Keycloak service: %s',
    HTTP_ERROR='An error occurred with request: %s. Check server logs for more information.',
    GENERIC_ERROR='Error occurred: %s'
)

ARGUMENT_SPEC = dict(
    server                  = dict(required=True, type='str'),
    realm                   = dict(required=True, type='str'),
    verify_ssl              = dict(default=False, type='str'),
    username                = dict(required=True, type='str'),
    password                = dict(required=True, type='str'),
    client_id               = dict(required=True, type='str'),
    scope_mappings          = dict(default=[], type='list')
)


def parse_verify_ssl(verify_ssl):
    if verify_ssl in [ 'true', 'True', True ]:
        return True
    if verify_ssl in [ 'false', 'False', False ]:
        return False
    return verify_ssl


class ClientScopeRoleMappingService:
    ADMIN_CLIENT_ID = 'admin-cli'

    def __endpoints( self ):
        base_path = "%s/realms/%s" % (self.server, self.realm)
        oidc_endpoints = '%s/protocol/openid-connect' % base_path

        self.token_endpoint = '%s/token' % oidc_endpoints
        self.logout_endpoint = '%s/logout' % oidc_endpoints

        admin_api = '%s/admin/realms/%s' % (self.server, self.realm)

        self.clients_endpoint = "%s/clients" % admin_api

        self.scope_mappings_endpoint = "%s/%s/scope-mappings/clients/%s"
        self.scope_mappings_composite_endpoint = "%s/%s/scope-mappings/clients/%s/composite"
        self.scope_mappings_available_endpoint = "%s/%s/scope-mappings/clients/%s/available"

        self.users_endpoint = "%s/users" % admin_api
        self.users_role_mappings_composite_endpoint = "%s/%s/role-mappings/clients/%s/composite"
        self.users_role_mappings_available_endpoint = "%s/%s/role-mappings/clients/%s/available"

    def __init__( self, server, realm, verify_ssl ):
        self.server = server
        self.realm = realm
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

    def get_client_effective_scope_mappings( self, client_container_id_a, client_container_id_b):
        return self.get(self.scope_mappings_composite_endpoint % (self.clients_endpoint, client_container_id_a, client_container_id_b), self._headers())

    def get_client_available_scope_mappings( self, client_container_id_a, client_container_id_b):
        return self.get(self.scope_mappings_available_endpoint % (self.clients_endpoint, client_container_id_a, client_container_id_b), self._headers())

    def assign_scope_mappings(self, client_container_id_a, client_container_id_b, role_representations):
        return self.post(self.scope_mappings_endpoint % (self.clients_endpoint, client_container_id_a, client_container_id_b), data = json.dumps(role_representations), headers = self._headers())

    def grant_user_client_roles( self, user_id, container_id, role_representations ):
        return self.post("%s/%s/role-mappings/clients/%s" % (self.users_endpoint, user_id, container_id), data = json.dumps(role_representations), headers = self._headers())

    def get_user_effective_role_mappings( self, user_id, container_id):
        return self.get(self.users_role_mappings_composite_endpoint % (self.users_endpoint, user_id, container_id), self._headers())

    def get_user_available_role_mappings( self, user_id, container_id):
        return self.get(self.users_role_mappings_available_endpoint % (self.users_endpoint, user_id, container_id), self._headers())

    def get_client_container_id( self, client_id ):
        clients_reponse = self.get_client(client_id)
        clients_reponse.raise_for_status()
        clients = clients_reponse.json()
        if len(clients) == 0:
            raise Exception(ERRORS['NO_CLIENT'] % client_id)
        return clients[0]['id'], clients[0]

    def grant_service_account_roles( self, service_account_id, client_container_id, role_representations ):
        return self.grant_user_client_roles(service_account_id, client_container_id, role_representations)

    def get_service_account_id( self, container_id ):
        user_repr_response = self.get("%s/%s/service-account-user" % (self.clients_endpoint, container_id), self._headers())
        user_repr_response.raise_for_status()
        return user_repr_response.json()['id']

    def logout( self ):
        form_data = { 'client_id': self.ADMIN_CLIENT_ID, 'refresh_token': self.refresh_token }
        self.post(self.logout_endpoint, headers=self._headers(content_type=CONTENT_TYPE_FORM), data=form_data)


def setup_module_object():
    module = AnsibleModule(
        argument_spec = ARGUMENT_SPEC,
        supports_check_mode=True
    )
    return module


def setup_client_service(server, realm, verify_ssl):
    service = ClientScopeRoleMappingService(server, realm, verify_ssl)
    return service

def request_and_raise(module, result, func):
    response = None
    try:
        response = func()
        response.raise_for_status()
        return response
    except Exception as e:
        module.fail_json(msg=ERRORS['GENERIC_RESPONSE_ERROR'] % (e, str(response)), **result)


def find_role_in_list(roles, role_name):
    return next((role for role in roles if role['name'] == role_name), None)


def get_role_by_clients(role_name, target_client_container_id, source_client_container_id, service, module, result):
    source_client_available_roles_response = request_and_raise(
        module,
        result,
        lambda: service.get_client_available_scope_mappings(
            target_client_container_id,
            source_client_container_id
        )
    )

    source_client_available_roles = source_client_available_roles_response.json()

    source_client_effective_roles_response = request_and_raise(
        module,
        result,
        lambda: service.get_client_effective_scope_mappings(
            target_client_container_id,
            source_client_container_id
        )
    )

    source_client_effective_roles = source_client_effective_roles_response.json()

    found_available = find_role_in_list(source_client_available_roles, role_name)
    found_effective = find_role_in_list(source_client_effective_roles, role_name)

    return found_available, found_effective


def get_role_by_account(role_name, account_id, client_container_id, service, module, result):
    account_available_roles_response = request_and_raise(
        module,
        result,
        lambda: service.get_user_available_role_mappings(
            account_id,
            client_container_id
        )
    )

    account_available_roles = account_available_roles_response.json()

    account_effective_roles_response = request_and_raise(
        module,
        result,
        lambda: service.get_user_effective_role_mappings(
            account_id,
            client_container_id
        )
    )

    account_effective_roles = account_effective_roles_response.json()

    found_available = find_role_in_list(account_available_roles, role_name)
    found_effective = find_role_in_list(account_effective_roles, role_name)

    return found_available, found_effective


def perform_action_for_role(found_available, found_effective, action, on_error):
    assign_response = None

    if found_available is not None and found_effective is None:
        # Role not found in effective roles and is available
        assign_response = action()

    elif found_effective is not None:
        # Role is already effective
        print "Role %s is already assigned" % found_effective['name']
    elif found_available is None:
        # Role is not found
        on_error()

    return assign_response


def verify_role_assignment_response(response, on_success, on_fail):
    # Keycloak REST will return 204 No Content, if the assignment is made successfully
    if response is not None:
        if response.status_code == 204:
            on_success()
        else:
            on_fail()


def changed(result, msg):
    result['changed'] = True,
    result['message'] += msg


def validate_resource( resource, keys ):
    for key in keys:
        if key not in resource:
            raise KeyError(ERRORS['RESOURCE_VALIDATION_ERROR'] % key)
    return resource


def main():
    module = setup_module_object()

    server                  = module.params['server']
    realm                   = module.params['realm']
    verify_ssl              = module.params['verify_ssl']
    username                = module.params['username']
    password                = module.params['password']

    client_id               = module.params['client_id']
    scope_mappings          = module.params['scope_mappings']

    result = dict(
        changed=False,
        original_message='',
        message=''
    )

    # Perform additional validation for scope_mappings
    for scope_mapping in scope_mappings:
        validate_resource(scope_mapping, ['role_name', 'client_id'])

    service = setup_client_service(server, realm, verify_ssl)

    try:
        service.authenticate(username, password)

        target_client_container_id, target_client = service.get_client_container_id(client_id)
        service_account_id = service.get_service_account_id(target_client_container_id)

        for scope_mapping in scope_mappings:

            source_client_container_id, source_client = service.get_client_container_id(scope_mapping['client_id'])

            # Process client role assignment

            found_available_for_client, found_effective_for_client = get_role_by_clients(
                scope_mapping['role_name'],
                target_client_container_id,
                source_client_container_id,
                service,
                module,
                result
            )

            if module.check_mode:
                result['message'] += "Client-level role %s from client %s will be added to client %s scope.\n" \
                                % (scope_mapping['role_name'], scope_mapping['client_id'], client_id)
            else:
                assign_role_to_client_response = perform_action_for_role(
                    found_available_for_client,
                    found_effective_for_client,
                    lambda: service.assign_scope_mappings(
                        target_client_container_id,
                        source_client_container_id,
                        [found_available_for_client]
                    ),
                    lambda: module.fail_json(
                        msg=ERRORS['ROLE_NOT_FOUND'] % (scope_mapping['role_name'], scope_mapping['client_id']),
                        **result
                    )
                )

                verify_role_assignment_response(
                    assign_role_to_client_response,
                    lambda: changed(
                            result,
                            "Client-level role %s from client %s added to client %s scope.\n" % (scope_mapping['role_name'], scope_mapping['client_id'], client_id)
                    ),
                    lambda: module.fail_json(
                        msg=ERRORS['MAPPING_ASSIGNMENT_ERROR'] % (
                            client_id,
                            scope_mapping['client_id'],
                            scope_mapping['role_name'],
                            assign_role_to_client_response.status_code,
                            assign_role_to_client_response.text
                        ),
                        **result
                    )
                )

            # Process service account role assignment
            if target_client['serviceAccountsEnabled'] and \
                    'assign_to_service_account' in scope_mapping and \
                    scope_mapping['assign_to_service_account'] is True:
                # Assign the role to service account in case serviceAccount parameter is enabled

                found_available_for_account, found_effective_for_account = get_role_by_account(
                    scope_mapping['role_name'],
                    service_account_id,
                    source_client_container_id,
                    service,
                    module,
                    result
                )

                if module.check_mode:
                    result['message'] += "Role %s will be assigned to service account of client %s.\n" \
                                     % (scope_mapping['role_name'], client_id)
                else:
                    assign_role_to_account_response = perform_action_for_role(
                        found_available_for_account,
                        found_effective_for_account,
                        lambda: service.grant_service_account_roles(
                            service_account_id,
                            source_client_container_id,
                            [found_available_for_account]
                        ),
                        lambda: module.fail_json(
                            msg=ERRORS['ROLE_NOT_FOUND'] % (scope_mapping['role_name'], scope_mapping['client_id']),
                            **result
                        )
                    )

                    verify_role_assignment_response(
                        assign_role_to_account_response,
                        lambda: changed(
                            result,
                            "Role %s assigned to service account of client %s.\n" % (scope_mapping['role_name'], client_id)
                        ),
                        lambda: module.fail_json(
                            msg=ERRORS['ROLE_ASSIGNMENT_ERROR'] % (
                                service_account_id,
                                scope_mapping['client_id'],
                                scope_mapping['role_name'],
                                assign_role_to_account_response.status_code,
                                assign_role_to_client_response.text
                            ),
                            **result
                        )
                    )

        service.logout()
        module.exit_json(**result)

    except requests.exceptions.Timeout as t:
        module.fail_json(msg=ERRORS['CONNECTION_TIMEOUT'] % str(t), **result)
    except requests.exceptions.ConnectionError as e:
        module.fail_json(msg=ERRORS['CONNECTION_ERROR'] % str(e), **result)
    except requests.exceptions.HTTPError as e:
        module.fail_json(msg=ERRORS['HTTP_ERROR']% str(e), **result)
    except Exception as e:
        module.fail_json(msg=ERRORS['GENERIC_ERROR'] % str(e), **result)


if __name__ == '__main__':
    main()
