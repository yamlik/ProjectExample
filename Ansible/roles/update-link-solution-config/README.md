update-link-solution-config
===========================

Updates solution-config.json using the solutionconfig.py Ansible module.
Links the solution-config directory inside the webapps directory of a given Tomcat instance.

Requirements
------------

Requires Ansible 2.2 or higher.

Role Variables
--------------

User defined. These need to be defined on invocation:

    component_name    # Name of the calling component, e.g. "User Management"
    webapps_dir       # Tomcat deployment directory, where to link the solution-config, e.g. "/opt/TEST/tomcat/usermanagement/webapps"
    menu_items        # A list of solution-config menu entries (OPTIONAL: if left empty, no menu entries are added)
    system_items      # A list of solution-config systemitem entries (OPTIONAL: if left empty, no system item entries are added)
                      # These represent the icons for UM, AdminTools and SysMon at the top right corner.

Default values:

    solution_config_dir: /opt/TEST/solution-config   # Directory where solution-config.json resides
    add_solution_config_entry: false                    # Whether to add a Solution Config entry to solution-config.json
    link_solution_config: false                         # Whether to link the solution-config directory under 'webapps_dir'

Dependencies
------------

No dependencies

Example Playbook
----------------

    - hosts: ui
      roles:
        - role: update-link-solution-config
          component_name: "User Management"
          webapps_dir: "/opt/TEST/tomcat/usermanagement/webapps"
          solution_items:
            - menuitem:
              label: UserManagement
              tooltip: "User Management"
              url: ui-host.TEST.com:46080/TEST
              permissions:
                - usermanagement.admin
                - usermanagement.permissions
              state: present
          add_solution_config_entry: true
          link_solution_config: true

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
