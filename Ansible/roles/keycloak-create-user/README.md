Role Name
=========

By default this role creates an TEST user and configures their password, first and last name, e-mail, etc.

Requirements
------------

This role must be executed on the host which has Keycloak installed.

Role Variables
--------------

User-defined variables which should be defined by the user upon invocation

    kc_realm_admin_user: realmadmin                 # Username used for authentication
    kc_realm_admin_password: realmadmin             # Password used for authentication
    kc_user_username: user                          # Username for the user which is to be created
    kc_user_password: password                      # Password for the user which is to be created
    kc_user_firstname: Standard                     # First name for the user which is to be created
    kc_user_lastname: User                          # Last name for the user which is to be created
    kc_user_email: standarduser@example.com         # E-mail address for the user which is to be created

These can be overridden

    kc_fqdn: "{{ inventory_hostname }}"                                     # FQDN of the Keycloak host
    kc_https_port: 8666                                                     # Port on which Keycloak is running
    kc_https_url: "https://{{ kc_fqdn }}:{{ kc_https_port }}/auth"          # Keycloak URL
    kc_realm_name: TEST                                                    # Realm name
    kc_TEST_ui_client_id: TEST_ui                                 # TEST UI client ID
    kc_install_dir: /opt/keycloak                                           # Keycloak installation directory

Dependencies
------------

None.

Example Playbook
----------------

    - hosts: ui
      vars:
        kc_realm_admin_user: realmadmin
        kc_realm_admin_password: realmadmin
        kc_user_username: user
        kc_user_password: password
        kc_user_firstname: Standard
        kc_user_lastname: User
        kc_user_email: standarduser@example.com
      roles:
        - role: keycloak-create-user

License
-------

Commercial

Author Information
------------------

TEST Corporation
http://www.TEST.com
