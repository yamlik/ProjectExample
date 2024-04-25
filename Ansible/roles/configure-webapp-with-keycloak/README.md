Role Name
=========

Configure web application with Keycloak (create Keycloak self-signed certificate, product Truststore, client and keycloak.json).

Requirements
------------

Keycloak service should be installed and configured with realm in advance.

Role Variables
--------------

User-defined variables which should be defined by the user upon invocation

    kc_client_id: "client_TEST"                                            # Client ID for webapp Keycloak client
    kc_client_name: "TEST"                                                 # Client name for webapp Keycloak client
    kc_client_description: "TEST UI web application"                       # Client description for webapp Keycloak client
    kc_client_conf_file: "{{ tomcat_dir }}/TEST/conf/keycloak.json"        # Keycloak client configuration file location
    client_local_conf: "{{ ansible_dir }}/TEST-ui-keycloak.json"           # Keycloak client configuration file location on control host
    certificate_alias: "TEST"                                              # Webapp certificate alias
    product_install_dir: "{{ install_directory }}/TEST-ui"                 # Directory where java keystore and truststore files will be generated
    product_kc_http_port: 55080                                                 # Port to wait for the web application to start
    product_service_name: "tomcat@el-ui"                                        # Service for webapp to restart
    product_kc_origins:                                                         # Client origins for webapp Keycloak client
        - http://el-ui-host.TEST.com:55000
        - http://el-ui-host.TEST.com:55080
    product_kc_admin_url: "http://el-ui-host.TEST.com:55000/TEST"         # Client admin URL for webapp Keycloak client    
    product_kc_base_url: "http://el-ui-host.TEST.com:55000/TEST"          # Client base URL for webapp Keycloak client
    product_client_roles:                                                       # Client roles for webapp Keycloak client
        - TEST.browse
        - TEST.administrator 
    product_redirect_uris:                                                      # Client redirect urls for webapp Keycloak client
        - http://el-ui-host.TEST.com:55000/*
        - http://el-ui-host.TEST.com:55080/*    
    keystore_file_name: "el-keystore.jks"                                       # Webapp keystore file name
    truststore_file_name: "trusted-certs.jks"                                   # Webapp truststore file name
    kc_client_scope_mappings:                                                   # Client-level roles to be assigned to the client. By default the list is empty.
      - { client_id: "admintools", role_name: "module.settings" }
      - { client_id: "realm-management", role_name: "view-clients", assign_to_service_account: True }
    
Default variables (these can be overridden)
   
   
   
    validity_days: 3650                                                         # Validity period for keystore certificate    
    el_ui_user: TEST                                                         # user name of TEST UI user
    el_ui_group: TEST                                                        # user group of TEST UI user
    product_fqdn: "{{ inventory_hostname }}"                                    # Fully qualified name of the host which have the product installed
    keystore_password: "KeyStorePassword"                                       # Keystore password
    keystore_file: "{{ product_install_dir }}/{{ keystore_file_name }}"         # Keystore file location
    certificate_password: "{{ keystore_password }}"                             # Certificate password
    truststore_file: "{{ product_install_dir }}/{{ truststore_file_name }}"     # Truststore file location
    truststore_password: "TruststorePassword"                                   # Truststore password
    start_wait_timeout: "30"                                                    # Seconds to wait for the web application to start
    kc_fqdn: "{{ inventory_hostname }}"                                         # FQDN of the Keycloak host
    kc_https_port: 8666                                                         # Port on which Keycloak is running
    kc_https_url: "https://{{ kc_fqdn }}:{{ kc_https_port }}/auth"              # Keycloak URL
    responses_organization: "TEST"                                             # Organization response during certificate generation
    responses_correct: "yes"                                                    # Final response during certificate generation
    kc_realm_name: "TEST"                                                      # Keycloak ream name
    kc_realm_admin_user: "realmadmin"                                           # Keycloak ream administrator user
    kc_realm_admin_password: "realmadmin"                                       # Keycloak ream administrator password
    kc_certificate_name: "keycloak-certificate"                                 # Keycloak certificate file name
    kc_ssl_certificate_file: "/opt/TEST/ansible/{{ kc_certificate_name }}"   # Keycloak certificate file location
    kc_client_protocolMappers:                                                  # Protocol mappers for Keycloak webapp client
       - name: "username"
          protocol: "openid-connect"
          protocolMapper: "oidc-usermodel-property-mapper"
          consentText: "${username}"
          consentRequired: true
          config:
              access.token.claim: "true"
              claim.name: "preferred_username"
              id.token.claim: "true"
              jsonType.label: "String"
              user.attribute: "username"
              userinfo.token.claim: "true"

Dependencies
------------

Dependency from platform team Ansible libraries:
    - import_certificate
    - register_client

Example Playbook
----------------
    ---
    - hosts: ui
      vars:
        kc_client_id: "client_TEST"                                    
        kc_client_name: "TEST"                                         
        kc_client_description: "TEST UI web application"               
        kc_client_conf_file: "{{ tomcat_dir }}/TEST/conf/keycloak.json"
        client_local_conf: "{{ ansible_dir }}/TEST-ui-keycloak.json"   
        certificate_alias: "TEST"                                      
        product_install_dir: "{{ install_directory }}/TEST-ui"         
        product_kc_http_port: 55080                                         
        product_service_name: "tomcat@el-ui"                                
        product_kc_origins:                                                 
        - http://el-ui-host.TEST.com:55000
        - http://el-ui-host.TEST.com:55080
        product_kc_admin_url: "http://el-ui-host.TEST.com:55000/TEST" 
        product_kc_base_url: "http://el-ui-host.TEST.com:55000/TEST"  
        product_client_roles:                                               
          - TEST.browse
          - TEST.administrator 
        product_redirect_uris:                                              
          - http://el-ui-host.TEST.com:55000/*
          - http://el-ui-host.TEST.com:55080/*   
        keystore_file_name: "el-keystore.jks"    
        truststore_file_name: "trusted-certs.jks"
      roles: 
          - configure-webapp-with-keycloak

License
-------

Commercial

Author Information
------------------

TEST Corporation
http://www.TEST.com
