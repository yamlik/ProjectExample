#!/usr/bin/python

ANSIBLE_METADATA = {
    'metadata_version': '1.0',
    'supported_by': 'TEST',
    'status': ['stableinterface']
}

DOCUMENTATION = '''
---
module: usermanagement
short_description: A RESTful client for User Management backend
description:
    - Add resources such as permissions, password profiles, roles or users to User Management server.
    - The main functions are resource creation and removal. There are some limitations to both.
    - A password profile can be removed once it's not referenced by any user. In the same way
      a permission can be removed once it's not referenced by any role, which can be removed
      once it's not referenced by any user.
    - Updates to resources are carried out only when forced with the state parameter value C(updated).
version_added: "2.2"
author: TEST Corporation
options:
    host:
        description:
            - Fully qualified domain name of User Management backend
        required: true
        type: str
    apikey_module_id:
        description:
            - ModuleID registered to User Management (typically 'ansible')
            - ModuleID along with APIKey allow non-interactive authentication
        required: true
        type: str
    apikey:
        description:
            - APIKey registered to ModuleID in User Management
        required: true
        type: str
    service:
        description:
            - Name of the User Management provided service to be accessed
            - Required together with I(resource)
        type: str
        choices: [ 'permissions', 'passwordprofiles', 'roles', 'users' ]
    resource:
        description:
            - A User Management resource object. Either a permission, password profile, role or user.
            - Required together with I(service).
            - Mutually exclusive with I(permission), I(passwordprofile), I(role) and I(user).
            - Check available suboptions from actual type (permission, passwordprofile, role or user).
        type: dict
    permission:
        description:
            - A permission object in User Management
            - Mutually exlusive with I(resource), I(passwordprofile), I(role) and I(user).
        type: dict
        suboptions:
            name:
                description:
                    - Name of the permission
                required: true
                default: null
                type: str
            description:
                description:
                    - Description of the permission
                required: true
                default: null
                type: str
            moduleId:
                description:
                    - ID of the module owning the permission
                required: true
                default: null
                type: str
            state:
                description:
                    - Permission state after operation
                required: false
                default: present
                choices: [ 'present', 'updated', 'absent' ]
    passwordprofile:
        description:
            - A password profile object in User Management
            - Mutually exclusive with I(resource), I(permission), I(role) and I(user).
        type: dict
        suboptions:
            name:
                description:
                    - Name of the password profile
                required: true
                default: null
                type: str
            description:
                description:
                    - Description of the password profile
                required: true
                default: null
                type: str
            soxRules:
                description:
                    - Rules that passwords must adhere to
                    - A comma-separated list of SOX rules implemented by User Management
                required: true
                default: null
                type: str or list
            attributes:
                description:
                    - Attributes for SOX rules
                    - A comma-separated list of 'key=value' pairs
                required: true
                default: null
                type: str or dict
            state:
                description:
                    - Password profile state after operation
                required: false
                default: present
                choices: [ 'present', 'updated', 'absent' ]
    role:
        description:
            - A role object in User Management
            - Mutually exclusive with I(resource), I(permission), I(passwordprofile) and I(user).
        type: dict
        suboptions:
            name:
                description:
                    - Name of the role
                required: true
                default: null
                type: str
            description:
                description:
                    - Description of the role
                required: true
                default: null
                type: str
            userPermissions:
                description:
                    - Name of the permissions tied to role
                    - A comma-separated list of permissions
                required: true
                default: null
                type: str or list
            state:
                description:
                    - Role state after operation
                required: false
                default: present
                choices: [ 'present', 'updated', 'absent' ]
    user:
        description:
            - A user object in User Management
            - Mutually exclusive with I(resource), I(permission), I(passwordprofile) and I(role).
        type: dict
        suboptions:
            userName:
                description:
                    - Username of the user
                required: true
                default: null
                type: str
            password:
                description:
                    - Password of the user
                required: true
                default: null
                type: str
            fullName:
                description:
                    - Full name of the user
                required: true
                default: null
                type: str
            userLocked:
                description:
                    - Whether the is locked from access
                required: true
                default: null
                type: bool
            email:
                description:
                    - Email of the user
                required: true
                default: null
                type: str
            userRoles:
                desctiption:
                    - Name of the roles the user has
                    - A comma-separated list of roles
                required: true
                default: null
                type: str or list
            passwordProfile:
                description:
                    - Name of the password profile that dictates
                      user's password validation method
                required: true
                default: null
                type: str
            state:
                description:
                    - User state after operation
                required: false
                default: present
                choices: [ 'present', 'updated', 'absent' ]
    protocol:
        description:
            - Protocol used to connect to User Management backend.
        type: str
        default: http
        choices: [ 'http', 'https' ]
    port:
        description:
            - The listen port of the User Management backend.
        type: int
    timeout:
        description:
            - Timeout in seconds used for REST requests.
        type: int
        default: 30
requirements: [ md5, json, urllib, requests ]
notes:
    - Required one of I(resource), I(permission), I(passwordprofile), I(role) or I(user).
    - For each resource the parameter I(state) value can be one of the following
      - C(present) - Checks that a resource exists. If it doesn't, it is created.
      - C(updated) - If the resource doesn't exist, it is created. If it does, it is updated.
      - C(absent)  - If the resource exists, it is removed.
    - For more usage examples, check tasks list from user-management-finalize role.
'''

EXAMPLES = '''
- name: Add permissions for MyModule in User Management
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    service: permissions
    resource:
      name: mymodule.admin
      description: "Admin permission to MyModule"
      moduleId: mymodule
      state: present

- name: Update permissions for MyModule in User Management
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    permission:
      name: mymodule.admin
      description: "Administrative permission to MyModule"
      moduleId: mymodule
      state: updated

- name: Add MyModuleAdmin role to User Management
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    service: roles
    resource:
      name: MyModuleAdmin
      description: "Role with admin permissions to MyModule"
      userPermissions: mymodule.admin
      state: present

- name: Edit MyModuleAdmin role description and append permission
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    role:
      name: MyModuleAdmin
      description: "Role with SUPER permissions to MyModule and User Management"
      userPermissions: "mymodule.admin, usermanagement.admin, usermanagement.permissions"
      state: updated

- name: Add Matti Meikalainen user to User Management
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    user:
      userName: masa
      password: masa_Salakala
      fullName: 'Matti Meikalainen'
      userLocked: false
      email: masanaattori@TEST.com
      userRoles: MyModuleAdmin
      passwordProfile: 'Default profile'
      state: present

- name: Remove Matti Meikalainen for inappropriate behaviour
  usermanagement:
    host: umhost.TEST.com
    apikey: eb94afc5610b1624fac3ecc98dcca2
    apikey_module_id: ansible
    service: users
    resource:
      userName: masa
      password: masa_Salakala
      fullName: 'Matti Meikalainen'
      userLocked: false
      email: masanaattori@TEST.com
      userRoles: MyModuleAdmin
      passwordProfile: 'Default profile'
      state: absent
'''

RETURN = '''
resource:
    description: Filtered resource given as a user parameter
    returned: when resource is valid
    type: dictionary
    sample:
        - { 'name': 'usermanagement.admin', 'moduleId': 'usermanagement', 'description': 'User Management administrative permission (manage users, roles and permissions)' }
        - { 'attributes': { 'previousPasswordsNotAllowedCount': '1' }, 'description': 'Default password profile for Security Solutions', 'name': 'Default profile', 'soxRules': [ 'PASSWORD_PREVIOUS_PASSWORDS' ] }
        - { 'description': 'TEST Administrator role', 'name': 'ccpAdmin', 'userPermissions': [ 1, 2 ] }
        - { 'userName': 'masa', 'password': 'masa_Salakala', 'fullName': 'Matti Meikalainen', 'userLocked': False, 'email': 'masanaattori@TEST.com', userRoles: [ 4 ], 'passwordProfile': 3 }
response:
    description: Response from User Management server
    returned: success
    type: dictionary
    sample:
        - { 'id': 1, 'name': 'usermanagement.admin', 'moduleId': 'usermanagement', 'description': 'User Management administrative permission (manage users, roles and permissions)' }
        - { 'id': 3, 'attributes': { 'previousPasswordsNotAllowedCount': '1' }, 'description': 'Default password profile for Security Solutions', 'name': 'Default profile', 'soxRules': [ 'PASSWORD_PREVIOUS_PASSWORDS' ], 'userCount': 1 }
        - { 'id': 4, 'description': 'TEST Administrator role', 'name': 'ccpAdmin', 'userCount': 1, 'userPermissions': [ 2, 1 ], 'users': [ 5 ] }
        - { 'id': 5, 'userName': 'masa', 'password': '', 'fullName': 'Matti Meikalainen', 'userLocked', False, 'email': 'masanaattori@TEST.com', userRoles: [ 4 ], 'passwordProfile': 3, 'createdIsoDate': '2017-04-13 06:36:41.739Z', 'modifiedIsoDate': '2017-05-18 15:36:41.739Z', 'validFrom': null, 'validUntil': null }
'''

###############################
# Helper classes and functions
###############################

import md5
import json
import urllib
import requests

class UserManagementRESTClient:
    TIMEOUT_WAIT = 30

    def __init__( self, url, apikey, apikey_module_id ):
        self.url                = url
        self.apikey             = apikey
        self.apikey_module_id   = apikey_module_id

    # Creates signature by appending the entire request (including header and URL)
    # and hashing it with md5 (as expected by User Management backend)
    def create_apikey_signature( self, request_method, request_url, request_params=None, request_body=None ):
        method = request_method.upper()
        url = "%s?%s" % (request_url, urllib.urlencode(request_params)) if request_params else "%s" % request_url
        body = request_body if request_body else ""

        return md5.new(self.apikey + self.apikey_module_id + method + url + body).hexdigest()

    def create_headers( self, apikey_signature ):
        return { 'Content-Type': 'application/json', 'apikey-module-id': self.apikey_module_id, 'apikey-signature': apikey_signature }

    def send_request( self, request_method, request_url, request_params=None, request_body=None ):
        if isinstance(request_body, dict):
            request_body = json.dumps(request_body)

        apikey_signature = self.create_apikey_signature(request_method, request_url, request_params, request_body)
        headers = self.create_headers(apikey_signature)

        response = requests.request(method=request_method, url=request_url, params=request_params, data=request_body, headers=headers, timeout=self.TIMEOUT_WAIT)
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
        return self.send_request(request_method="GET", request_url=self.url, request_params=q_params)

    def update( self, resource ):
        url = self.url + "/" + str(resource['id'])
        return self.send_request(request_method="PUT", request_url=url, request_body=json.dumps(resource))

    def delete( self, resource ):
        url = self.url + "/" + str(resource['id'])
        return self.send_request(request_method="DELETE", request_url=url, request_body=json.dumps(resource))

    def create( self, resource ):
        return self.send_request(request_method="POST", request_url=self.url, request_body=json.dumps(resource))

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

class UserManagementService:
    # Query filters for name/username in UM backend for different services
    # * UM backend expects 'username' query param filter for user
    query_param_names = {
        'permissions': 'name',
        'passwordprofiles': 'name',
        'roles': 'name',
        'users': 'username'
    }
    # name/username key names for UM resources
    # * UM backend expects 'userName' field for user json
    resource_param_names = {
        'permissions': 'name',
        'passwordprofiles': 'name',
        'roles': 'name',
        'users': 'userName'
    }

    default_ports = {
        'http': 46080,
        'https': 46081
    }

    def __init__( self, host, apikey, apikey_module_id, service, protocol=None, port=None, timeout=None ):
        self.service = service
        protocol = protocol if protocol else "http"
        port = port if port else self.default_ports[protocol]
        rest_api_url = complete_padding(host, protocol + "://", ":" + str(port) + "/usermanagement/api/" + service)
        self.api = UserManagementRESTClient(url=rest_api_url, apikey=apikey, apikey_module_id=apikey_module_id)
        if timeout and timeout > 0:
            self.api.TIMEOUT_WAIT = timeout

    def create_name_query( self, resource ):
        q_key = self.query_param_names[self.service]
        q_val = resource[self.resource_param_names[self.service]]

        return { q_key: q_val }

    def query_by_name( self, value ):
        q_key = self.query_param_names[self.service]
        q_val = value

        return self.api.read({q_key: q_val})

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

#####################
# AnsibleModule main
#####################

from ansible.module_utils.basic import AnsibleModule

def parse_to_list( string ):
    return map((lambda s: s.strip()), string.split(','))

def parse_to_dict( string ):
    return dict((key, value) for (key, value) in map((lambda s: s.strip().split('=')), string.split(',')))

def validate_resource( resource, keys ):
    for key in keys:
        if key not in resource:
            raise KeyError("Required field missing: " + key + ". Keys are case-sensitive. Check spelling.")

    return resource

# NO-OP
def filter_permission( permission, *args ):
    return permission

def filter_passwordprofile( profile, *args ):
    sox_rules = profile['soxRules']
    if isinstance(sox_rules, str):
        profile['soxRules'] = parse_to_list(sox_rules)
    elif not isinstance(sox_rules, list):
        raise ValueError("Could not parse soxRules " + str(soxRules) + ". Must be a comma-separated list of string, or a list.\nex. soxRules: 'PASSWORD_DEFAULT_PASSWORD,PASSWORD_LENGTH'\nex. soxRules: ['PASSWORD_DEFAULT_PASSWORD', 'PASSWORD_LENGTH']")

    attributes = profile['attributes']
    if isinstance(attributes, dict):
        for key in attributes.keys():
            attributes[key] = str(attributes[key])
    elif isinstance(attributes, str):
        profile['attributes'] = parse_to_dict(attributes)
    else:
        raise ValueError("Could not parse attributes " + str(attributes) + ". Must be a comma-separated list of string mappings delimited by '=' sign, or a dict.\nex. attributes: 'previousPasswordsNotAllowedCount=3,pwdMinLength=9'\nex. attributes: { 'previousPasswordsNotAllowedCount': 3, 'pwdMinLength': 9}")

    return profile

def filter_role( role, host, apikey, apikey_module_id, protocol=None, port=None ):
    user_permissions = role['userPermissions']
    if isinstance(user_permissions, str):
        user_permissions = parse_to_list(user_permissions)
    elif not isinstance(user_permissions, list):
        raise ValueError("Could not parse userPermissions " + str(user_permissions) + ". Must be a comma-separated list of strings, or a list.\nex. userPermissions: 'mymodule.admin, mymodule.view'\nex. userPermissions: ['mymodule.admin', 'mymodule.view']")

    user_permission_ids = []
    client = UserManagementService(host=host, apikey=apikey, apikey_module_id=apikey_module_id, service="permissions", protocol=protocol, port=port)
    for permission in user_permissions:
        try:
            user_permission_ids.append(client.query_by_name(permission)['id'])
        except:
            raise ValueError("Could not find permission by name '" + permission + "'")

    role['userPermissions'] = user_permission_ids
    return role

def filter_user( user, host, apikey, apikey_module_id, protocol=None, port=None ):
    password_profile = user['passwordProfile']
    if isinstance(password_profile, str):
        client = UserManagementService(host=host, apikey=apikey, apikey_module_id=apikey_module_id, service="passwordprofiles", protocol=protocol, port=port)
        try:
            user['passwordProfile'] = client.query_by_name(password_profile)['id']
        except:
            raise ValueError("Could not find passwordProfile by name '" + password_profile + "'")
    else:
        raise ValueError("Could not parse passwordProfile " + str(password_profile) + ". Must be string name, string id or int id.\nex. passwordProfile: 'My password profile'\nex. passwordProfile: 1\nex. passwordProfile: '1'")

    user_roles = user['userRoles']
    if isinstance(user_roles, str):
        user_roles = parse_to_list(user_roles)
    elif not isinstance(user_roles, list):
        raise ValueError("Could not parse userRoles " + str(user_roles) + ". Must be a comma-separated list of strings, or a list.\nex. userRoles: 'ccpAdmin, myModuleAdmin'\nex. userRoles: ['ccpAdmin', 'myModuleAdmin']")

    user_role_ids = []
    client = UserManagementService(host=host, apikey=apikey, apikey_module_id=apikey_module_id, service="roles")
    for role in user_roles:
        try:
            user_role_ids.append(client.query_by_name(role)['id'])
        except:
            raise ValueError("Could not find role by name '" + role + "'")

    user['userRoles'] = user_role_ids
    return user

def main():
    service_keys_and_filters = {
        'permissions': (
            ['moduleId', 'name', 'description'],
            filter_permission
        ),
        'passwordprofiles': (
            ['name', 'description', 'soxRules', 'attributes'],
            filter_passwordprofile
        ),
        'roles': (
            ['name', 'description', 'userPermissions'],
            filter_role
        ),
        'users': (
            ['userName', 'password', 'fullName', 'userLocked', 'email', 'userRoles', 'passwordProfile'],
            filter_user
        )
    }

    module = AnsibleModule(
        argument_spec = dict(
            apikey_module_id    = dict(required=True, type='str'),
            apikey              = dict(required=True, type='str'),
            host                = dict(required=True, type='str'),
            service             = dict(required=False, type='str', choices=service_keys_and_filters.keys()),
            resource            = dict(required=False, type='dict'),
            permission          = dict(required=False, type='dict'),
            passwordprofile     = dict(required=False, type='dict'),
            role                = dict(required=False, type='dict'),
            user                = dict(required=False, type='dict'),
            protocol            = dict(required=False, type='str', choices=['http','https'], default='http'),
            port                = dict(required=False, type='int'),
            timeout             = dict(required=False, type='int', default=30)
        ),
        required_together=[
            ['service', 'resource']
        ],
        required_one_of=[
            ['resource', 'permission', 'passwordprofile', 'role', 'user']
        ],
        mutually_exclusive=[
            ['resource', 'permission', 'passwordprofile', 'role', 'user']
        ]
    )

    apikey_module_id    = module.params['apikey_module_id']
    apikey              = module.params['apikey']
    host                = module.params['host']
    service             = module.params['service']
    resource            = module.params['resource']
    permission          = module.params['permission']
    passwordprofile     = module.params['passwordprofile']
    role                = module.params['role']
    user                = module.params['user']

    protocol            = module.params['protocol']
    port                = module.params['port']
    timeout             = module.params['timeout']

    for (serv, res) in [('permissions', permission), ('passwordprofiles', passwordprofile), ('roles', role), ('users', user)]:
        if res:
            service = serv
            resource = res

    try:
        resource = validate_resource(resource, service_keys_and_filters[service][0])
        resource = service_keys_and_filters[service][1](resource, host, apikey, apikey_module_id, protocol, port)
    except KeyError as k:
        module.fail_json(changed=False, msg="Resource validation failed: " + str(k))
    except ValueError as v:
        module.fail_json(changed=False, msg="Resource parsing failed: " + str(v))

    client = UserManagementService(host=host, apikey=apikey, apikey_module_id=apikey_module_id, service=service, protocol=protocol, port=port, timeout=timeout)
    try:
        expected_state = resource.pop('state', 'present')
        result, response = client.handle(expected_state, resource)
        if result['failed']:
            module.fail_json(msg=result['msg'], response=response, resource=resource)
        else:
            module.exit_json(msg=result['msg'], changed=result['changed'], response=response, resource=resource)
    except requests.exceptions.Timeout as t:
        module.fail_json(msg="Connection timed out: " + str(t), resource=resource)
    except requests.exceptions.ConnectionError as e:
        module.fail_json(msg="Could not connect to User Management: " + str(e), resource=resource)
    except requests.exceptions.HTTPError as e:
        module.fail_json(msg="An error occurred with request: " + str(e) + ". Check server logs for more information", resource=resource)

    module.exit_json(changed=False, msg="No resource given. Doing nothing.")


if __name__ == '__main__':
    main()
