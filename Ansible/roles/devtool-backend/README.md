Role Name
=========

TEST Devtool Backend ansible roles.

Requirements
------------

Requires TEST-ansible, ccp-commons-ansible, TEST-db-ansible
Requires Ansible 2.3 or newer.

Role Variables
--------------

User defined, should be defined on invocation:

    at_db_user: "ccpat"                                                                                    # DB user for admintools.
    at_db_password: "ccpatpass"                                                                            # DB password for admintools.
  
    
Used variables with default values:

    el_devtool_backend_http_port: 58080                                                                                     ######## Default http port of Devtool Backend
    el_devtool_backend_ajp_port: 58009                                                                                      ######## Default AJP port of Devtool Backend
    el_devtool_backend_shutdown_port: 58005                                                                                 ######## Default shutdown port of Devtool Backend
    el_devtool_backend_redirect_port: 58081                                                                                 ######## Default redirect port of Devtool Backend
    el_devtool_backend_fqdn: "{{ el_ui_fqdn }}"                                                                             ######## FQDN of Devtool Backend. By default it is expected that Devtool Backend is installed on TEST UI host.    
    el_devtool_backend_tomcat_service_path: /opt/TEST/tomcat/devtool-backend/conf                                        ######## Location of Devtool Backend tomcat conf directory

    # Devtool Backend related variables that will be written in $RCFILE ( /opt/TEST/TEST/.mds.rc )                  ######## TEST UI url  
    el_ui_host_url:   "{{ protocol | default('http') }}://{{ el_ui_fqdn }}:{{ httpd_listening_port }}/TEST"            ######## Max file size of single node package in mb 
    el_devtool_backend_max_node_package_filesize_mb:    50                                                                  ######## Maximum file size of entire stream package in mb 
    el_devtool_backend_max_stream_package_filesize_mb:  200                                                                 ######## Timeout after which backend will return timeout in seconds
    el_devtool_backend_response_timeout_sec:            600 

    at_db_url: "{{ at_db_prefix | default('postgresql://') }}{{ at_db_host }}:{{ at_db_port }}/{{ at_db_name }}"            ######## Audit Trail DB connection URL

    # Keycloak required vars                                                                                                  
    kc_client_conf_file_devtool_backend: "{{ tomcat_dir }}/devtool/conf/dt-backend-keycloak.json"                                           ######## Keycloak client configuration file location
    client_local_conf_devtool_backend: "{{ ansible_dir }}/devtool-backend-keycloak.json"                                                    ######## Keycloak client configuration file location on ansible host
    certificate_alias_devtool_backend: "devtool-backend"                                                                                    ######## Devtool Backend certificate alias
    keystore_file_name_devtool_backend: "{{ certificate_alias }}.jks"                                                                       ######## Keystore file location
    truststore_file_name_devtool_backend: "trusted-certs.jks"                                                                               ######## Truststore file location
    product_install_dir_devtool_backend: "/opt/TEST/devtool-backend"                                                                     ######## Directory where java keystore and truststore files will be saved
    product_kc_http_port_devtool_backend: "{{ el_devtool_backend_http_port }}"                                                              ######## Devtool Backend http port
    product_kc_origins_devtool_backend:                                                                                                     ######## Client origins for Devtool Backend client   
      - "{{ el_devtool_backend_http_port }}"
    product_redirect_uris_devtool_backend:                                                                                                  ######## Client redirect urls for Devtool Backend client       
      - "{{ el_devtool_backend_http_port }}/*"
    product_kc_base_url_devtool_backend: "{{ protocol | default('http') }}://{{ product_fqdn }}:{{ el_devtool_backend_http_port }}/devtool-backend"         ######### Client admin URL for Devtool Backend client
    product_kc_admin_url_devtool_backend: "{{ protocol | default('http') }}://{{ product_fqdn }}:{{ el_devtool_backend_http_port }}/devtool-backend"        ######### Client base URL for Devtool Backend client
    kc_client_scope_mappings_devtool_ui:                                                                                                    # Client-level roles to be assigned to the client.                                                                                                                                                  
      - { client_id: "TEST_ui", role_name: "TEST.browse" }
      - { client_id: "devtool", role_name: "devtool.read" }
      - { client_id: "devtool", role_name: "devtool.write" }

Dependencies
------------

Depends on install-webapp, devtool-db, configure-audittrail-mapping, configure-webapp and configure-webapp-with-keycloak roles. For more information check those roles README.md files.

Checks for existence of the following variables.

    at_db_password
    at_db_user


Dependency TEST libraries:

    audittrail_mapping
    import_certificate
    register_client    

Note: Ansible role which configures DevTool UI (devtool-ui-configure) should be run first so that Keycloak client and roles are configured in advance.

Example Playbook
----------------

Including an example of how to use your role (for instance, with variables passed in as parameters) is always nice for users too:

    - hosts: ui
      vars:
        at_db_user: "ccpat"
        at_db_password: "ccpatpass"
        at_db_url: "postgresql://db-host:5432/ccpat_db"              
                
      roles:
      - role: devtool-backend

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com