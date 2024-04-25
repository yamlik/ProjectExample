keycloak-configure-ldap
=======================

Configures an LDAP user storage provider and it's group mapper in Keycloak

Requirements
------------

Requires Ansible 2.4 or newer.

Role Variables
--------------

User-defined variables, need to be defined on invocation

    ldap_storage_name         # Name for the LDAP storage
    ldap_users_dn             # Distinguished name of users tree
    ldap_bind_dn              # Distinguished name of login user
    ldap_bind_credentials     # Password of login user
    ldap_connection_url       # LDAP url

Default variables. Override when necessary.

    ldap_configuration_template:    "ldap-component.json.j2"
    ldap_configuration_file:        "{{ kc_conf_dir }}/ldap-component.json"

    ldap_group_mapping_template:    "group-mapping-component.json.j2"
    ldap_group_mapping_file:        "{{ kc_conf_dir }}/group-mapping-component.json"

LDAP configuration variables. Override when necessary

    ldap_username_ldap_attribute:   cn
    ldap_uuid_ldap_attribute:       objectGUID
    ldap_user_object_classes:       "person, organizationalPerson, user"
    ldap_group_object_classes:      "group"
    ldap_vendor:                    ad
    
    ldap_rdn_ldap_attribute:                        "{{ ldap_username_ldap_attribute }}"
    ldap_connection_pooling:                        true
    ldap_pagination:                                true
    ldap_cache_policy:                              "NO_CACHE"
    ldap_allow_kerberos_authentication:             false
    ldap_use_kerberos_for_password_authentication:  false
    ldap_import_enabled:                            true
    ldap_sync_registrations:                        false
    ldap_full_sync_period:                          -1
    ldap_changed_sync_period:                       -1
    ldap_auth_type:                                 "simple"
    ldap_debug:                                     false
    ldap_search_scope:                              1
    ldap_use_truststore_spi:                        "ldapsOnly"
    ldap_priority:                                  0
    ldap_edit_mode:                                 "READ_ONLY"
    ldap_batch_size:                                1000

LDAP Group mapping configuration variables. Override when necessary

    ldap_groups_dn:                             "{{ ldap_users_dn }}"
    ldap_group_membership_attribute_type:       "DN"
    ldap_group_name_ldap_attribute:             "cn"
    ldap_membership_user_ldap_attribute:        "cn"
    ldap_user_roles_retrieve_strategy:          "LOAD_GROUPS_BY_MEMBER_ATTRIBUTE"
    ldap_membership_ldap_attribute:             "member"
    ldap_memberof_ldap_attribute:               "memberOf"
    ldap_preserve_group_inheritance:            false
    ldap_ignore_missing_groups:                 false
    ldap_drop_non_existing_groups_during_sync:  false

Variables inherited from keycloak-configure

    kc_conf_dir
    kc_https_url
    kc_realm_name
    kc_realm_admin_user
    kc_realm_admin_password
    kc_keystore_file
    kc_keystore_password

Dependencies
------------

check-variables
keycloak-configure

Example Playbook
----------------

    - hosts: servers
      roles:
        - role: keycloak-configure-ldap
          ldap_storage_name: "Organization"
          ldap_users_dn: "cn=users,dc=TEST,dc=com"
          ldap_bind_dn: "cn=Administrator,cn=admins,dc=TEST,dc=com"
          ldap_bind_credentials: "guiadmin"
          ldap_connection_url: "ldap://users.TEST.com:389"

License
-------

Commercial

Author Information
------------------

TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
