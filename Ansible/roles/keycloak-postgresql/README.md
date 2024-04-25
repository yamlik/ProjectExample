keycloak-database
=================

Creates a PostgreSQL user and database for Keycloak, installs PostgreSQL JDBC driver WildFly module and configures Keycloak CSF configuration with new database information.

Requirements
------------

Requires Ansible 2.2 or higher.

Role Variables
--------------

User-defined variables that should be defined on invocation

    # Initial credentials that have permission to create new users
    db_admin_user
    db_admin_password
    
    # PostgreSQL user for Keycloak, owner of Keycloak's database
    kc_db_user
    kc_db_password

Default variables that should not have to be overwritten

    kc_db_host:               db.service.consul
    kc_db_port:               5432
    kc_db_name:               keycloak
    kc_db_password_attribute: ds_pass
    kc_db_info:               "postgresql:{{ kc_db_name }}:{{ kc_db_host }}:{{ kc_db_port }}:{{ kc_db_user }}:{{ kc_db_password_attribute }}"
    kc_db_user_role_attrs:    CREATEDB,LOGIN
    kc_db_encoding:           ""
    kc_db_lc_collate:         ""
    kc_db_lc_ctype:           ""
    kc_db_tablespace:         ""

Variables used from Role Dependencies

    # From role keycloak
    kc_install_dir
    kc_csf_conf
    kc_vault_name
    kc_vault_alias
    kc_vault_password
    kc_vault_salt
    kc_vault_iterations

Dependencies
------------

keycloak

Example Playbook
----------------

    - hosts: idprovider
      roles:
        - role: keycloak-database
          kc_keystore_password: changeit
          db_admin_user: admin
          db_admin_password: adminpassword
          kc_db_user: keycloak
          kc_db_password keycloakpassword

License
-------

Commercial

Author Information
------------------

TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
