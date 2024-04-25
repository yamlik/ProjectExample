#!/usr/bin/python

ANSIBLE_METADATA = {
    'metadata_version': '1.0',
    'supported_by': 'TEST',
    'status': ['stableinterface']
}

DOCUMENTATION = '''
---
module: solutionconfig
short_description: Edits solutionwide UI configuration (solution config).
description:
    - Adds, edits and removes menu and system items and environment banner in solution-config.json file.
    - Sets solution name, product name and copyright text shown in multiple views across TEST web applications.
version_added: "2.2"
author: TEST Corporation
options:
    menuitem:
        description:
            - Main menu item to add, edit or remove
        type: dict
        suboptions:
            label:
                description:
                    - Application name shown in UI
                required: true
                default: null
                type: str
            tooltip:
                description:
                    - Text shown on mouse over
                required: true
                default: null
                type: str
            url:
                description:
                    - URI address or context path to a web application
                required: true
                default: null
                type: str
            permissions:
                description:
                    - One or more permissions that dictate whether the menu entry is visible.
                    - The menu entry is visible to users who have any one of the defined permissions.
                    - String in CSV format is automatically parsed to Python list.
                required: true
                default: null
                type: str or list
            state:
                description:
                    - Value C(present) adds new menu item or edits existing
                    - Value C(absent) removes existing menu item
                required: false
                default: present
                choices: [ 'present', 'absent' ]
    systemitem:
        description:
            - Main menu system item to add, edit or remove
            - Used to link between TEST Communications Platform web applications (User Management, Admin Tools and System Monitoring)
        type: dict
        suboptions:
            label:
                description:
                    - Application shown in UI
                required: true
                default: null
                type: str
            tooltip:
                description:
                    - Text shown on mouse over
                default: null
                type: str
            url:
                description:
                    - URI address or context path to a web application
                required: If I(state=present)
                default: null
                type: str
            permissions:
                description:
                    - One or more permissions that dictate whether the menu entry is visible.
                    - The menu entry is visible to users who have any one of the defined permissions.
                    - String in CSV format is automatically parsed to Python list.
                default: null
                type: str or list
            state:
                description:
                    - Value C(present) adds new system item or edits existing
                    - Value C(absent) removes existing system item
                default: present
                choices: [ 'present', 'absent' ]
    sticky_note_backend:
        description:
            - A REST interface to poll for sticky notes, shown in TEST main menu
        type: dict
        suboptions:
            module:
                description:
                    - Module name that provides the notes
                required: true
                default: null
                type: str
            restUrl:
                description:
                    - URL to the REST interface
                required: If I(state=present)
                default: null
                type: str
            state:
                description:
                    - Value C(present) adds new sticky note backend item or edits existing
                    - Value C(absent) removes existing sticky note backend item
                default: present
                choices: [ 'present', 'absent' ]
    envbanner:
        description:
            - Environment banner to add, edit or remove
        type: dict
        suboptions:
            name:
                description:
                    - Name or URL of the environment
                    - Shown on the left side of the banner
                required: If I(state=present)
                default: null
                type: str
            description:
                description:
                    - Description of the environment
                    - Shown on the right side of the banner
                required: If I(state=present)
                default: null
                type: str
            mode:
                description:
                    - Value C(thin) makes environment banner 30px in height
                    - Value C(thick) makes environment banner 50px in height
                default: 'thin'
                type: str
                choices: [ 'thin', 'thick' ]
            theme:
                description:
                    - Envinroment banner coloring theme
                default: 'grey-theme'
                type: str
                choices: [ 'red-theme', 'grey-theme', 'green-theme', 'blue-theme', 'orange-theme' ]
            state:
                description:
                    - Value C(present) enables a new environment banner or edits existing
                    - Value C(absent) removes environment banner
                default: present
                choices: [ 'present', 'absent' ]
    solution_name:
        description:
            - Solution name shown in UI
        type: str
    product_name:
        description:
            - Product name shown in UI
        type: str
    copyright_text:
        description:
            - Copyright text shown in UI
        type: str
requirements: [ json ]
notes:
    - At least one of options I(menuitem), I(systemitem), I(envbanner), I(solution_name), I(product_name) or I(copyright_text) is required.
    - The solution-config.json file provides centralized navigation and solution configuration across TEST web applications.
'''

EXAMPLES = '''
- name: "Add MyApplication menuitem"
  solutionconfig:
    menuitem:
      label: "MyApp"
      tooltip: "My Application"
      url: "http://host.TEST.com:40080/myapp"
      permissions: "myapp.admin, myapp.user"
      state: present

- name: "Add User Management system item"
  solutionconfig:
    systemitem:
      label: "UserManagement"
      tooltip: "User Management"
      url: "http//host.TEST.com:46080/usermanagement"
      permissions: [ "usermanagement.admin" ]
      state: present

- name: "Configure solution parameters"
  solutionconfig:
    solution_name: "TEST Convergent Mediation"
    product_name: "TEST 8"
    copyright_text: "All rights reserved 2017"
    envbanner:
      name: "vSphere"
      description: "VMWare cloud environment"
      mode: thin
      theme: blue-theme
      state: present

- name: "Remove environment banner and User Management system item"
  solutionconfig:
    envbanner:
      state: absent
    systemitem:
      label: "UserManagement"
      state: absent

- name: "Add TEST Sticky Note Backend"
  solutionconfig:
    sticky_note_backend:
      module: "TEST"
      restUrl: "https://host.TEST.com:55000/TEST/api/sticky-notes"
'''

RETURN = '''
menuitem:
    description: Parsed and applied main menu item entry containing it's state (present or absent)
    returned: when menuitem is defined
    type: dictionary
    sample: { 'label': 'MyApp', 'tooltip': 'My Application', 'url': '/myapp', 'permissions': [ 'myapp.admin', 'myapp.user'], 'state': 'present' }
systemitem:
    description: Parsed and applied main menu system item entry containing it's state (present or absent)
    returned: when systemitem is defined
    type: dictionary
    sample: { 'label': 'UserManagement', 'tooltip': 'User Management', 'url': '/usermanagement', 'permissions': [ 'usermanagement.admin' ], 'state': 'absent' }
envbanner:
    description: Applied environment banner containing it's state (present or absent)
    returned: when envbanner is defined
    type: dictionary
    sample: { 'name': 'vSphere', 'description': 'VMWare cloud environment', 'mode': 'thin', 'theme': 'blue-theme', 'state': 'present' }
solution_name:
    description: Applied solution name
    returned: when solution_name is defined
    type: string
    sample: 'TEST Convergent Mediation'
product_name:
    description: Applied product name
    returned: when product_name is defined
    type: string
    sample: 'TEST 8'
copyright_text:
    description: Applied copyright text
    returned: when copyright_text is defined
    type: string
    sample: 'All rights reserved 2017'
'''

#########################################
# Accessor class to solution-config.json
#########################################

import json

class SolutionConfigModel:

    def _load( self ):
        with open(self.filename) as solcfg_file:
            return json.load(solcfg_file)

    def _save( self ):
        with open(self.filename, 'w') as solcfg_file:
            json.dump(self.model, solcfg_file, indent=4)

    def _update_result( self, changed, msg ):
        self._update_changed(changed)
        self._update_msg(msg)

    def _update_changed( self, changed=None ):
        if changed is None:
            return
        # Update "changed" only if it's False
        if not self.result["changed"]:
            self.result["changed"] = changed

    def _update_msg( self, msg=None ):
        if msg is None:
            return
        # If message is empty, overwrite. Otherwise append.
        if not self.result["msg"]:
            self.result["msg"] = str(msg)
        else:
            self.result["msg"] += " " + str(msg)

    def __init__( self, filename ):
        self.result = dict(changed=False, failed=False, msg="")
        self.filename = filename

        model = self._load()
        if not isinstance(model, dict):
            raise TypeError("Solution config cannot be parsed!")
        self.model = model

    def update( self, menuitem=None, systemitem=None, sticky_note_backend=None, envbanner=None, solution_name=None, product_name=None, copyright_text=None ):
        # Validate and parse first to prevent unnecessary modifications
        for (name, param) in [("menuitem", menuitem), ("systemitem", systemitem), ("sticky_note_backend", sticky_note_backend), ("envbanner", envbanner), ("solution_name", solution_name), ("product_name", product_name), ("copyright_text", copyright_text)]:
            if param:
                state = self._pop_state(param)
                self._validate_param(state, name, param)
                self._parse_param(state, name, param)
                self._push_state(state, param)
        # Apply changes after all validations are done
        for (name, param) in [("menuitem", menuitem), ("systemitem", systemitem), ("sticky_note_backend", sticky_note_backend), ("envbanner", envbanner), ("solution_name", solution_name), ("product_name", product_name), ("copyright_text", copyright_text)]:
            if param:
                state = self._pop_state(param)
                self._handle_param(state, name, self._copy_param(param))
                self._push_state(state, param)
                self.result[name] = param
        self._save()
        return self.result

    def fail( self, msg=None ):
        self._update_msg(msg)
        self.result["failed"] = True

    def _copy_param( self, param ):
        if isinstance(param, dict):
            return param.copy()
        else:
            return param

    def _pop_state( self, param ):
        if isinstance(param, dict):
            return param.pop("state", "present")
        else:
            return "present"

    def _push_state( self, state, param ):
        if isinstance(param, dict):
            param["state"] = state

    def _validate_param( self, state, name, param ):
        if not isinstance(param, dict):
            return
        # Check required keys are present
        for key in self._params_metadata[state][name]["req_keys"]:
            if key not in param:
                raise KeyError("Error in parameter: " + name + ". Required field missing: " + key + ". Keys are case-sensitive. Check spelling.")
        # Check all keys are accounted for
        for key in param.keys():
            if key not in self._params_metadata[state][name]["req_keys"] and key not in self._params_metadata[state][name]["opt_keys"]:
                raise KeyError("Error in parameter: " + name + ". Unrecognized field: " + key + ". Keys are case-sensitive. Check spelling.")

    def _parse_param( self, state, name, param ):
        # Parse with the correct parser
        self._params_metadata[state][name]["parser"](self, state, param)

    def _handle_param( self, state, name, param ):
        self._params_metadata[state][name]["handler"](self, param)

    def _update_menuitem( self, menuitem ):
        self._update_item(array=self.model["menu"]["items"], item=menuitem, item_id="label", log_string="Menu item")

    def _remove_menuitem( self, menuitem ):
        self._remove_item(array=self.model["menu"]["items"], item=menuitem, item_id="label", log_string="Menu item")

    def _update_systemitem( self, systemitem ):
        self._update_item(array=self.model["menu"]["systemItems"], item=systemitem, item_id="label", log_string="System item")

    def _remove_systemitem( self, systemitem ):
        self._remove_item(array=self.model["menu"]["systemItems"], item=systemitem, item_id="label", log_string="System item")

    def _update_sticky_note_backend( self, sticky_note_backend ):
        self._update_item(array=self.model["stickyNoteBackends"], item=sticky_note_backend, item_id="module", log_string="Sticky note backend")

    def _remove_sticky_note_backend( self, sticky_note_backend ):
        self._remove_item(array=self.model["stickyNoteBackends"], item=sticky_note_backend, item_id="module", log_string="Sticky note backend")

    def _update_item( self, array, item, item_id, log_string ):
        for idx, element in enumerate(array):
            if element[item_id] == item[item_id]:
                if element == item:
                    self._update_result(False, log_string + " was already present.")
                else:
                    self._update_result(True, log_string + " was updated.")
                array[idx] = item
                return

        self._update_result(True, "Menu item was added.")
        array.append(item)

    def _remove_item( self, array, item, item_id, log_string ):
        for element in array:
            if element[item_id] == item[item_id]:
               array.remove(element)
               self._update_result(True, log_string + " was removed.")
               return

        self._update_result(False, log_string + " was already absent.")

    def _update_envbanner( self, envbanner ):
        envbanner["showBanner"] = "true"
        # Env banner is not empty and matches given argument
        if "environment" in self.model["menu"] and self.model["menu"]["environment"] == envbanner:
            self._update_result(False, "Environment banner was already present.")
        # Env banner is empty or not defined
        elif "environment" not in self.model["menu"] or not self.model["menu"]["environment"]:
            self._update_result(True, "Environment banner was added.")
        # Env banner was not empty and was inequal to given argument
        else:
            self._update_result(True, "Environment banner was updated.")
        self.model["menu"]["environment"] = envbanner

    def _remove_envbanner( self, envbanner ):
        if "environment" in self.model["menu"]:
            self._update_result(True, "Environment banner was removed.")
        else:
            self._update_result(False, "Environment banner was already absent.")
        self.model["menu"]["environment"] = ""

    def _update_solution_name( self, solution_name ):
        self._update_value(domain="solution", key="solutionName", value=solution_name, log_string="Solution name")

    def _update_product_name( self, product_name ):
        self._update_value(domain="product", key="productName", value=product_name, log_string="Product name")

    def _update_copyright_text( self, copyright_text):
        self._update_value(domain="product", key="copyright", value=copyright_text, log_string="Copyright text")

    def _update_value( self, domain, key, value, log_string ):
        if self.model[domain][key] == value:
            self._update_result(False, log_string + " '" + value + "' was already set.")
        else:
            self._update_result(True, log_string + " updated to '" + value + "'.")
        self.model[domain][key] = value

    def _add_default_values( self, state, envbanner ):
        if state == "absent":
            return
        if "mode" not in envbanner:
            envbanner["mode"] = 'thin'
        if "theme" not in envbanner:
            envbanner["theme"] = 'grey-theme'

    def _parse_menu_entry( self, state, entry ):
        if state == "absent":
            return
        if isinstance(entry["permissions"], str):
            entry["permissions"] = map((lambda s: s.strip()), entry["permissions"].split(','))

    def _no_op( self, *args ):
        return

    _params_metadata = {
        "present": {
            "menuitem": {
                "req_keys": ['label', 'url', 'tooltip', 'permissions'],
                "opt_keys": ['state', 'openNewTab'],
                "parser": _parse_menu_entry,
                "handler": _update_menuitem
            },
            "systemitem": {
                "req_keys": ['label', 'url'],
                "opt_keys": ['state', 'permissions', 'tooltip'],
                "parser": _parse_menu_entry,
                "handler": _update_systemitem
            },
            "sticky_note_backend": {
                "req_keys": ['module', 'restUrl'],
                "opt_keys": ['state'],
                "parser": _no_op,
                "handler": _update_sticky_note_backend
            },
            "envbanner": {
                "req_keys": ['name', 'description'],
                "opt_keys": ['mode', 'theme', 'state'],
                "parser": _add_default_values,
                "handler": _update_envbanner
            },
            "solution_name": {
                "parser": _no_op,
                "handler": _update_solution_name
            },
            "product_name": {
                "parser": _no_op,
                "handler": _update_product_name
            },
            "copyright_text": {
                "parser": _no_op,
                "handler": _update_copyright_text
            }
        },
        "absent": {
            "menuitem": {
                "req_keys": ['label'],
                "opt_keys": ['state', 'url', 'tooltip', 'permissions', 'openNewTab'],
                "parser": _parse_menu_entry,
                "handler": _remove_menuitem
            },
            "systemitem": {
                "req_keys": ['label'],
                "opt_keys": ['state', 'url', 'tooltip', 'permissions'],
                "parser": _parse_menu_entry,
                "handler": _remove_systemitem
            },
            "sticky_note_backend": {
                "req_keys": ['module'],
                "opt_keys": ['state', 'restUrl'],
                "parser": _no_op,
                "handler": _remove_sticky_note_backend
            },
            "envbanner": {
                "req_keys": [],
                "opt_keys": ['name', 'description', 'mode', 'theme', 'state'],
                "parser": _add_default_values,
                "handler": _remove_envbanner
            }
        }

    }

#######################
# SolutionConfig Main
#######################

from ansible.module_utils.basic import AnsibleModule

def main():
    module = AnsibleModule(
        argument_spec= {
            'menuitem'              : { 'required': False, 'type': 'dict'},
            'systemitem'            : { 'required': False, 'type': 'dict'},
            'sticky_note_backend'   : { 'required': False, 'type': 'dict'},
            'envbanner'             : { 'required': False, 'type': 'dict'},
            'solution_name'         : { 'required': False, 'type': 'str'},
            'product_name'          : { 'required': False, 'type': 'str'},
            'copyright_text'        : { 'required': False, 'type': 'str'}
        },
        required_one_of=[
            ['menuitem', 'systemitem', 'sticky_note_backend', 'envbanner', 'solution_name', 'product_name', 'copyright_text']
        ]
    )

    menuitem                = module.params["menuitem"]
    systemitem              = module.params["systemitem"]
    sticky_note_backend     = module.params["sticky_note_backend"]
    envbanner               = module.params["envbanner"]
    solution_name           = module.params["solution_name"]
    product_name            = module.params["product_name"]
    copyright_text          = module.params["copyright_text"]

    solution_config_file='/opt/TEST/solution-config/solution-config.json'

    try:
        model = SolutionConfigModel(solution_config_file)
        model.update(menuitem=menuitem, systemitem=systemitem, sticky_note_backend=sticky_note_backend, envbanner=envbanner, solution_name=solution_name, product_name=product_name, copyright_text=copyright_text)
        module.exit_json(**model.result)
    except TypeError as t:
        model.fail("TypeError: " + str(t))
        module.fail_json(**model.result)
    except KeyError as k:
        model.fail("KeyError: " + str(k))
        module.fail_json(**model.result)
    except Exception as e:
        model.fail("Something went wrong: " + str(e))
        module.fail_json(**model.result)

if __name__ == '__main__':
    main()
