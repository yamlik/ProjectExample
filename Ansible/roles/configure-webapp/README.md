configure-webapp
================

Copies web application configuration to target host and restarts the application.

Requirements
------------

Requires systemd. Requires Ansible 2.2 or newer.

Role Variables
--------------

The following variables should be defined on invocation:

    component_name        # Proper name of the component, e.g. "User Management" or "TEST UI"
    service_name          # Name of the systemd service, e.g. "tomcat@usermanagement" or "tomcat@admintools"
    configuration_files   # A list of configuration files in the format: [ {src: "example-file.xml", dest: "/opt/TEST/app/example-file"}, {src: "another-example", dest: "/opt/TEST/tomcat/app/lib/another-example"} ]
    wait_for_port         # Port which to poll after web application restart, typically http port, e.g. 46080

Default variables:

    wait_for_fqdn: {{ inventory_hostname }}   # FQDN which to poll after web application restart
    wait_for_timeout: 60                      # Polling timeout after web application restart
    restart_webapp: true                      # Whether the web application should be restarted after applying configuration

Optional variables:
    plain_db_password       #Tomcat database password 

Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: servers
      roles:
         - {  role: configure-webapp,
              component_name: "User Management",
              service_name: "tomcat@usermanagement",
              plain_db_password: "tomcatPassword",
              configuration_files: [  {src: "user-management-context.xml.j2", dest: "/opt/TEST/tomcat/usermanagement/webapps/usermanagement/META-INF/context.xml"},
                                      {src: "openam-realm-configuration.xml.j2", dest: "/opt/TEST/usermanagement/openam-realm-configuration.xml"},
                                      {src: "tomcat@usermanagement.j2", dest: "/etc/sysconfig/tomcat@usermanagement", become: "true"} ],
              wait_for_port: 46080
           }

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
