install-webapp
==============

Installs the web application RPM, opens given ports on firewalld and restarts web application.
This role should be run via Role Dependencies.

Requirements
------------

Requires yum, firewalld and systemd. Requires Ansible 2.2 or higher.

Role Variables
--------------

The following variables should be defined on invocation:

    component_name      # Proper name of the component, e.g. "User Management" or "TEST UI"
    package_name        # RPM package name, e.g. "usermanagement" or "admintools"
    service_name        # Name of the systemd service, e.g. "tomcat@usermanagement" or "tomcat@admintools"
    ports               # List of ports the web application needs open, e.g. [ 46080, 46081 ]
    wait_for_port       # Port which to poll after web application deployment, typically http port (should be one of the opened ports)

Default variables:

    package_state: present                    # Desired state of the RPM package after yum operation
                                              # Available states are listed in
                                              # http://docs.ansible.com/ansible/latest/yum_module.html
    wait_for_fqdn: {{ inventory_hostname }}   # FQDN which to poll after web application deployment
    wait_for_timeout: 60                      # Polling timeout after web application deployment
    restart_webapp: true                      # Whether the web application should be restarted after it's installed

Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: servers
      roles:
         - {  role: install-webapp,
              component_name: "User Management",
              package_name: "usermanagement",
              service_name: "tomcat@usermanagent",
              ports: [ 46080, 46081 ],
              wait_for_port: 46080
           }
    
    # Update User Management to new version
    - hosts: servers
      roles:
        - { role: install-webapp,
            component_name: "User Management",
            package_name: "usermanagement",
            package_state: "latest",
            service_name: "tomcat@usermanagement",
            ports: [ 46080, 46081 ],
            wait_for_port: 46080
          }

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
