install-package
==============

Installs the given RPM package.

This role should be run via Role Dependencies.

Requirements
------------

Requires yum.

Requires Ansible 2.2 or higher.

Role Variables
--------------

The following variables should be defined on invocation:

    package_name        # RPM package name, e.g. "usermanagement" or "admintools"

Default variables:

    package_state: present                    # Desired state of the RPM package after yum operation
                                              # Available states are listed in
                                              # http://docs.ansible.com/ansible/latest/yum_module.html

Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: servers
      roles:
         - {  role: install-package,
              package_name: "ganglia-gmond",
           }

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
