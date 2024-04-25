#!/usr/bin/python

ANSIBLE_METADATA = {
    'metadata_version': '1.0',
    'supported_by': 'TEST',
    'status': ['stableinterface']
}

DOCUMENTATION = '''
module: audittrail_mapping
short_description: Maintaing id-to-name mappings for Admin Tools auditing UI.
description:
    - Add, update or remove items from mappings.json - the id-to-name mapping
      configuration for Admin Tools AuditTrail UI.
    - A mapping changes the way how an applications name is shown in the
      AuditTrail UI.
version_added: "2.2"
author: TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
options:
    component_id:
        description:
            - ID of the component for AuditTrail.
        required: true
        type: str
    component_name:
        description:
            - Name to be shown in AuditTrail UI.
            - Required when I(state) is C(present) or C(updated)
        type: str
    state:
        description:
            - Desired state after the operation.
        type: str
        choices: ['present','updated','absent']
        default: 'present'
requirements: [json]
notes:
    - Placeholder
'''

EXAMPLES = '''
- name: "Add mapping"
  audittrail_mapping:
    component_id: sample
    component_name: "Sample Module"
    state: present

- name: "Present state doesn't modify component name"
  audittrail_mapping:
    component_id: sample
    component_name: "Sample Module v2"
    state: present

- name: "Updated state modifies component name"
  audittrail_mapping:
    component_id: sample
    component_name: "Sample Module v2"
    state: updated

- name: "Remove mapping"
  audittrail_mapping:
    component_id: sample
    state: absent

- name: "Removing mapping again doesn't affect mappings"
  audittrail_mapping:
    component_id: sample
    state: absent
'''

import json

class MappingModel:

    def _update_result( self, msg=None, changed=None, **kwargs ):
        if msg:
            self.result['msg'] = msg
        if changed:
            self.result['changed'] = changed
        
        for key, value in kwargs.iteritems():
            self.result[key] = value

    def __init__( self, filepath ):
        self.filepath = filepath
        self.model = self._load()
        self.result = {
            'failed': False,
            'changed': False,
            'msg': ''
        }

    def _get_item( self, item ):
        for an_item in self.model['items']:
            if an_item['id'] == item['id']:
                return an_item
        return None
        
    def _present( self, item ):
        found = self._get_item(item)
        if found:
            self._update_result(msg="Item was already present", existing_item=found)
            return self.result
        self.model['items'].append(item)
        self._update_result(msg="Item was successfully added", changed=True)
        return self.result

    def _updated( self, item ):
        found = self._get_item(item)
        if found:
            if found['componentName'] != item['componentName']:
                self._update_result(msg="Item was succesfully updated", changed = True, old_item=found.copy())
                found['componentName'] = item['componentName']
            else:
                self._update_result(msg="Item was already present")
        else:
            self.model['items'].append(item)
            self._update_result(msg="Item was succesfully added", changed=True)
        return self.result

    def _absent( self, item ):
        for an_item in self.model['items']:
            if an_item['id'] == item['id']:
                self._update_result(msg="Item was succesfully removed", changed=True, removed=an_item.copy())
                self.model['items'].remove(an_item)
                return self.result
        self._update_result(msg="Item was already absent")
        return self.result

    def handle( self, item, state ):
        status = self.dispatch[state](self, item)
        self._save()
        return status

    def _load( self ):
        with open(self.filepath) as mapping_file:
            return json.load(mapping_file)

    def _save( self ):
        with open(self.filepath, 'w') as mapping_file:
            json.dump(self.model, mapping_file, indent=4)

    dispatch = {
        'present': _present,
        'updated': _updated,
        'absent': _absent
    }

from ansible.module_utils.basic import AnsibleModule

def main():
    module = AnsibleModule(
        argument_spec = dict(
            component_id    = dict(required=True, type='str'),
            component_name  = dict(type='str'),
            state           = dict(default='present', choices=['present','updated','absent'])
        ),
        required_if = [
            ('state', 'present', ['component_name']),
            ('state', 'updated', ['component_name'])
        ]
    )

    component_id    = module.params['component_id']
    component_name  = module.params['component_name']
    state           = module.params['state']
    
    item = dict(id = component_id)
    if component_name:
        item['componentName'] = component_name
    
    mapping_file = "/opt/TEST/admintools/mapping.json"
    
    try:
        model = MappingModel(mapping_file)
        status = model.handle(item, state)
        module.exit_json(**status)
    except Exception as e:
        module.fail_json(msg="Failed to update mappings: " + str(e))

if __name__ == '__main__':
    main()
