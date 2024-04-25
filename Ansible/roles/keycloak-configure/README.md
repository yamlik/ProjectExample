keycloak-configure
==================

Starts up Keycloak server and configures TEST realm and admin users. Creates client template and initial access token for client registration.

Requirements
------------

Requires Ansible 2.2 or higher.
Requires ccp-commons-ansible 2018.1.0 or higher.

Role Variables
--------------

User-defined variables that should be defined on invocation

    # Credentials for super administrator
    kc_admin_user
    kc_admin_password
    # Credentials for realm administrator
    kc_realm_admin_user
    kc_realm_admin_password

Default variables that should not have to be overwritten

    kc_realm_template:                      TEST-realm.json.j2
    kc_realm_file:                          "{{ kc_conf_dir }}/TEST-realm.json"
    kc_realm_name:                          TEST
    kc_realm_login_theme:                   TEST-csf
    kc_realm_enabled:                       true

    kc_client_template_template:            shiro-client-template.json.j2
    kc_client_template_file:                "{{ kc_conf_dir }}/shiro-client-template.json"
    kc_client_template_name:                shiro-client
    kc_client_template_protocol:            openid-connect
    kc_client_template_description:         "Client template for clients utilizing Shiro-based authorization"
    kc_client_template_groups_claim_name:   groups

    # Reconfigures client registration policies
    kc_allowed_client_templates_template:   allowed-client-templates.json.j2
    kc_allowed_client_templates_file:       "{{ kc_conf_dir }}/allowed-client-templates.json"

    # Initial access token config
    kc_client_initial_access_count:         10
    kc_client_initial_access_expiration:    300

    # Variables to control initial access token file on local machine
    kc_client_initial_access_local_file:        "/opt/TEST/ansible/initial-access-token"
    kc_client_initial_access_local_file_owner:  TEST
    kc_client_initial_access_local_file_group:  TEST
    kc_client_initial_access_local_file_mode:   0400

Variables used from Role Dependencies

    # From role keycloak
    kc_service_name
    kc_http_port
    kc_conf_dir
    kc_install_dir
    kc_sa_conf_dir
    kc_fqdn
    kc_start_wait_timeout

Dependencies
------------

keycloak
configure-webapp

Example Playbook
----------------

    - hosts: idprovider
      roles:
        - role: keycloak-configure
          kc_admin_user: admin
          kc_admin_password: admin
          kc_realm_admin_user: realmadmin
          kc_realm_admin_password: realmadmin

License
-------

Commercial

Author Information
------------------

TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
