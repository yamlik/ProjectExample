Role Name
=========

Install and configure TEST UI package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

User defined variables, these should be defined on invocation by the user

    db_el_user: "elink"                                                                                    # DB user for TEST System database.
    db_el_password: "elink"                                                                                # DB password for  TEST System database.
    el_ui_cloud_cluster_base_url: "http://kubernetes.TEST.com:8081"                                     # Kubernetes cluster base URL
    at_db_user: "ccpat"                                                                                    # DB user for admintools.
    at_db_password: "ccpatpass"                                                                            # DB password for admintools.
    admintools_fqdn: "example.TEST.com"                                                                 # Admintools fqdn.
    

These can be overridden

    tomcat_dir: "/opt/TEST/tomcat"                      # Installation directory for TEST tomcat service.
    install_directory: "/opt/TEST/TEST"            # Installation directory for Eventink UI.
    el_ui_user: "TEST"                                  # TEST user.
    el_ui_group: "TEST"                                 # TEST user group.
    el_ui_oriserver_response_timeout: "1800"               # This value defines how long (in seconds), the UI waits a reply from the Node manager ORI Server. This parameter affects all ORIServer based services such as rejected data services.
    
    el_ui_cloud_cluster_response_timeout: 5                # Timeout in seconds for communicatino with Kubernetes
    el_ui_flink_max_number_of_task_managers: 24            # Max number of task managers per node
    el_ui_flink_max_memory_per_task_manager: 65536         # Max memory per task manager
    el_ui_flink_max_slots_per_task_manager: 32             # Max number of slots per task manager
    el_ui_http_port: "55080"                               # Tomcat http port.
    el_ui_redirect_port: "55081"                           # Tomcat shutdown redirect port.
    el_ui_ajp_port: "55009"                                # Tomcat ajp port.
    el_ui_shutdown_port: "55005"                           # Tomcat shutdown port.
    db_el_name: "TEST"                                # DB name of the TEST System database
    db_el_host: "eldb.TEST.com"                         # DB host of the TEST System database.  By default is pointing to the consul service.
    db_el_port: "5432"                                     # DB port of the TEST System database.

    solution_config_dir: '/opt/TEST/solution-config'    # Location of solution-config directory for solution-config that is used systemwide.
    httpd_listening_port: '55000'                          # Port that apached httpd is listening.
    el_set_banner : false                                  # When set environment banner will appear on gui. All el_banner* are dependant on this variable.
    el_banner_name: "{{ el_ui_fqdn }}"                     # Name or URL of the environment. Shown on the left side of the banner
    el_banner_desc: 'TEST TEST/PROD environment'      # Description of the environment. Shown on the right side of the banner.
    el_banner_mode: 'thin'                                 # List of choices [ 'thin', 'thick' ]. Value C(thin) makes environment banner 30px in height. Value C(thick) makes environment banner 50px in height.
    el_banner_theme: 'grey-theme'                          # Envinroment banner coloring theme. List of choices [ 'red-theme', 'grey-theme', 'green-theme', 'blue-theme', 'orange-theme' ] 

    vi_crud_wait_timeout: "120"                                                    # Timeout for version info communication timeout.
    at_db_jdbc_url: "{{ at_db_jdbc_prefix | default('jdbc:') }}{{ at_db_url }}"    # DB jdbc for admintools.
    audittrail_datasource_name: "jdbc/audittrail"                                  # Data source for integration with admintools.
    audittrail_config_file: "/etc/audit.conf"                                      # Config file for admintools.
    
    product_install_dir_TEST_ui: "{{ install_directory }}/TEST-ui"            # Directory where java keystore and truststore files will be generated
    product_service_name_TEST_ui: "tomcat@el-ui"                                   # Service for TEST-ui
    certificate_alias_TEST_ui: "TEST"                                         # TEST UI certificate alias
    product_kc_http_origin_TEST_ui: "{{ protocol | default('http') }}://{{ el_ui_fqdn }}:{{ el_ui_http_port }}"      # Client origin for TEST UI (Tomcat) Keycloak client
    product_kc_apache_origin_TEST_ui: "{{ protocol | default('http') }}://{{ el_ui_fqdn }}:{{ httpd_listening_port }}"                 # Client origin for TEST UI (Apache proxy) Keycloak client
    keystore_file_name_TEST_ui: "el-keystore.jks"                                  # TEST UI keystore file name
    keystore_password_TEST_ui: "TEST_pass"                                    # TEST UI keystore file password
    truststore_password_TEST_ui: "TEST_pass"                                  # TEST UI trustore file password
    client_local_conf_TEST_ui: "{{ ansible_dir }}/TEST-ui-keycloak.json"      # Keycloak client configuration file location on control host
    client_roles_TEST_ui: "{{ permission_dictionary }}"                            # Client roles for TEST UI Keycloak client
    truststore_file_name_TEST_ui: "trusted-certs.jks"                              # TEST UI truststore file name
    kc_client_id_TEST_ui: "TEST_ui"                                           # Client ID for TEST UI Keycloak client
    kc_client_name_TEST_ui: "TEST"                                            # Client name for TEST UI Keycloak client
    kc_client_description_TEST_ui: "TEST UI web application"                  # Client description for TEST UI Keycloak client
    kc_client_conf_file_TEST_ui: "{{ tomcat_dir }}/TEST/conf/keycloak.json"   # Keycloak client configuration file location
    kc_client_scope_mappings:                                                           # Client-level roles to be assigned to the client. By default the list is empty.
      - { client_id: "admintools", role_name: "module.settings" }
      - { client_id: "realm-management", role_name: "view-clients", assign_to_service_account: True }
      - { client_id: "realm-management", role_name: "manage-clients", assign_to_service_account: True }
    
Dependencies
------------

Depends on el-db, audittrail and version-info roles. Checks the existence of the following variables:

    db_el_name
    db_el_host 
    db_el_port
    db_el_user
    db_el_password
    at_db_password
    at_db_user
    at_db_url

Dependency TEST libraries:
    - audittrail_mapping
    - versioninfo
    - solutionconfig

Dependent TEST UI roles:
    - TEST-ui-install
    - TEST-ui-db
    - configure-webapp-with-keycloak

------------

Example Playbook
----------------

---
    - hosts: ui-hosts
      vars:
          - el_ui_user: "TEST"
          - el_ui_group: "TEST"
          - el_ui_oriserver_response_timeout: "1800"
          - el_ui_http_port: "55080"
          - el_ui_redirect_port: "55081"
          - el_ui_ajp_port: "55009"
          - el_ui_shutdown_port: "55005"
          - install_directory: "/opt/TEST/TEST"
          - db_el_name: "TEST"
          - db_el_host: "eldb.TEST.com"
          - db_el_port: "5432"
          - db_el_user: "elink"
          - db_el_password: "elink"
          - audittrail_datasource_name: "jdbc/audittrail"
          - audittrail_config_file: "/etc/audit.conf"
          - at_db_user: "ccpat"
          - at_db_password: "ccpatpass"
          - at_db_jdbc_url: "jdbc:postgresql://atdb.TEST.com:5432/ccpat_db"
          - admintools_fqdn: "example.TEST.com"
          - el_ui_fqdn: "example.TEST.com"
          - vi_crud_wait_timeout: "120"
      roles:
        - audittrail
        - solution-config
        - el-db
        - TEST-ui
            


License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
