keycloak
========

Creates a configuration directory. Installs Keycloak Web SSO standalone server, opens HTTP and HTTPS ports on firewall and configures HTTPS support.

Requirements
------------

Requires Ansible 2.3 or higher.
Requires ccp-commons-ansible 2018.1.0 or higher.

Role Variables
--------------

User-defined variables that should be defined on invocation

    # Password for the Java KeyStore that will be created for Keycloak's self-signed certificate
    kc_keystore_password
    kc_certificate_password

Default variables that can be overwritten

    # Seconds to wait for Keycloak server to start after a service restart.
    # For slower hosts the value can be increased.
    kc_start_wait_timeout:    60
    # Validity period of Keycloak certificate
    validity_days: 3650
    # Organization response during certificate generation
    responses_organization: "TEST"
    # Final response during certificate generation
    responses_correct: "yes"
  
Default variables that should not have to be overwritten

	# Preffer IPv4 over IPv6
    kc_preferred_ip_version: 4

    # CSF Keycloak standalone distribution. Other distributions are keycloak-standalone-ha and keycloak-domain
    kc_sa_package_name:       		"keycloak-standalone"
    kc_service_name:          		"keycloak"
    kc_fqdn:                  		"{{ inventory_hostname }}"
    kc_http_port:             		8665
    kc_https_port:            		8666
    kc_install_dir:           		"/opt/keycloak"
	kc_jboss_bind_address: 			"{{ (kc_preferred_ip_version == 4) | ternary('0.0.0.0','[::]') }}"
    kc_jboss_bind_address_private: 	"{{ (kc_preferred_ip_version == 4) | ternary('127.0.0.1','[::1]') }}"
    
    # Custom specific configuration
    kc_conf_dir:              "/opt/TEST/keycloak"
    kc_conf_dir_owner:        TEST
    kc_conf_dir_group:        TEST
    kc_conf_dir_mode:         0755
    
    # Standalone mode configuration directory
    kc_sa_conf_dir:           "{{ kc_install_dir }}/standalone/configuration"
    kc_csf_conf:              "/etc/keycloak.conf"
    
    # Vault
    kc_vault_name: "vault.jks"
    kc_vault_file: "{{ kc_install_dir }}/security/vault/{{ kc_vault_name }}"
    kc_vault_alias: "initseckey"
    kc_vault_password: "keystore" # TODO: support non-default pass
    kc_vault_salt: 33550336       # TODO: support non-default salt
    kc_vault_iterations: 99       # TODO: support non-default iteration count

    # Keystore and certificate
    kc_keystore_name: "keycloak-keystore.jks"
    kc_keystore_file: "{{ kc_install_dir }}/security/ssl/{{ kc_keystore_name }}"
    kc_keystore_password_attribute: "ks_pass"
    kc_certificate_name: "keycloak-certificate"
    kc_certificate_password_attribute: "cert_pass"


Dependencies
------------

check-variables
install-webapp

Example Playbook
----------------

    - hosts: idprovider
      roles:
        - role: keycloak
          kc_keystore_password: changeit

License
-------

Commercial

Author Information
------------------

TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
