Role Name
=========

Install and configure DevTool UI package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

Available variables are listed below, along with default values (see `defaults/main.yml`):

    devtool_ui_http_port: "57080"                       # Tomcat http port.
    devtool_ui_ajp_port: "57009"                        # Tomcat ajp port.
    devtool_ui_shutdown_port: "57005"                   # Tomcat shutdown port.
    devtool_ui_redirect_port: "57081"                   # Tomcat redirect port.
    httpd_listening_port: "55000"                       # Port that apached httpd is listening.
    devtool_backend_http_port: "58080"                  # Tomcat http port for DevTool backend service.
    
    el_ui_fqdn: "{{ inventory_hostname }}"              # Fully qualified domain name of the TEST UI host.
    devtool_ui_fqdn: "{{ inventory_hostname }}"         # Fully qualified domain name of the DevTool UI host.
    devtool_backend_fqdn: "{{ inventory_hostname }}"    # Fully qualified domain name of the DevTool backend host.
    
    refreshingInterval: 60000                           # UI views refresh interval in miliseconds
    devtoolBackend: "/devtool/ext"                      # URL for DevTool backend service handled by DevTool UI filters
    TESTBackend: "/devtool/ext"                    # URL for TEST backend service handled by DevTool UI filters
    TESTApp: "/TEST"                          # URL for TEST UI service
    
    devtool_ui_service:         "{{ protocol | default('http') }}://{{ devtool_ui_fqdn }}:{{ devtool_ui_http_port }}"                       # DevTool UI service URL used in Apache proxy
    devtool_backend_service:    "{{ protocol | default('http') }}://{{ devtool_backend_fqdn }}:{{ devtool_backend_http_port }}"             # DevTool backend service URL used in Apache proxy
    devtool_ui_setting_service: "{{ protocol | default('http') }}://{{ devtool_ui_fqdn }}:{{ devtool_ui_http_port }}"                       # DevTool UI settings URL used in Apache proxy
    ui_config_service:          "{{ protocol | default('http') }}://{{ devtool_ui_fqdn }}:{{ devtool_ui_http_port }}"                       # UI configuration URL used in Apache proxy
    TEST_ui_service:       "{{ protocol | default('http') }}://{{ el_ui_fqdn }}:{{ el_ui_http_port }}"   # TEST UI service URL used in Apache proxy

Default variables related to KC with their values

    product_fqdn_devtool_ui: "{{ devtool_ui_fqdn }}"                                   # Devtool UI fqdn 
    client_local_conf_devtool_ui: "{{ ansible_dir }}/devtool-ui-keycloak.json"         # Location of KC json on the ansible host
    certificate_alias_devtool_ui: "devtool-ui"                                         # Alias for devtool kc certificate
    keystore_file_name:_devtool_ui "{{ certificate_alias }}.jks"                       # Keystore name for devtool kc certificate
    truststore_file_name_devtool_ui: "trusted-certs.jks"                               # Trustore for devtool kc certificate
    product_install_dir_devtool_ui: "/opt/TEST/devtool"                             # Installation directory of Devtool UI 
    product_kc_http_port_devtool_ui: "{{ devtool_ui_http_port }}"                      # Devtool UI httpd port 
    product_kc_origins_devtool_ui:                                                     # KC origins  
      - "{{ protocol | default('http') }}://{{ product_fqdn_devtool_ui }}:{{ devtool_ui_http_port }}"
      - "{{ protocol | default('http') }}://{{ product_fqdn_devtool_ui }}:{{ httpd_listening_port }}"
    product_redirect_uris_devtool_ui:                                                   # KC redirect uris
      - "{{ protocol | default('http') }}://{{ product_fqdn_devtool_ui }}:{{ devtool_ui_http_port }}/*"
      - "{{ protocol | default('http') }}://{{ product_fqdn_devtool_ui }}:{{ httpd_listening_port }}/*"
    product_kc_base_url_devtool_ui: "{{ protocol | default('http') }}://{{ product_fqdn }}:{{ devtool_ui_http_port }}/devtool"            # KC base url
    product_kc_admin_url_devtool_ui: "{{ protocol | default('http') }}://{{ product_fqdn }}:{{ devtool_ui_http_port }}/devtool"           # KC admin url
    kc_client_scope_mappings_devtool_ui:                                                # Client-level roles to be assigned to the client.
      - { client_id: "TEST_ui", role_name: "TEST.browse" } 



Dependencies
------------

Depends on solution-config and configure-webapp-with-keycloak roles. Checks the existence of the following variables:

    solution_name
    product_name
    copyright_text


Note: Check solution-config and configure-webapp-with-keycloak roles variables for more information.

Dependency TEST roles:
* update-link-solution-config
* configure-webapp-with-keycloak
* TEST-httpd

Example Playbook
----------------

Example playbook when DevTool UI is installed on same host as solution-config and TEST UI:

    ---
    - hosts: ui
      vars:
        - solution_name: "Devtool"
        - product_name: "Devtool"
        - copyright_text: "Copyright TEST 2017"
      roles:
        - solution-config
        - devtool-ui-configure

Example playbook when DevTool UI is installed on different host:
        
    - hosts: ui
      vars:
        - devtool_ui_fqdn: "devtool.TEST.com"
        - httpd_listening_port: 55000
        - solution_name: "Devtool"
        - product_name: "Devtool"
        - copyright_text: "Copyright TEST 2017"
      roles:
        - solution-config
        - role: update-link-solution-config
          component_name: "TEST Development Tool"
          menu_items:
             label: "Devtool"
              tooltip: "TEST Development Tool"
              url: "http://{{ devtool_ui_fqdn }}:{{ httpd_listening_port }}/devtool"
              permissions:
                - "devtool.read"
                - "TEST.browse"
              state: present
              openNewTab: "yes"
          add_solution_config_entry: true
          link_solution_config: false
    
    - hosts: devtool
      roles:
        - devtool-ui-configure
       

License
-------

Commercial

Author Information
------------------
TEST Corporation
http://www.TEST.com
