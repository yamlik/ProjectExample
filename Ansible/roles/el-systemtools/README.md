Role Name
=========

Install and configure TEST el-system-tools package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

User defined variables are listed below, along with example values:

    el_ui_URL: "http://el-ui-host.TEST.com:55080/TEST"

TEST UI URL which will be used by SystemTools.


Available variables are listed below, along with default values (see `defaults/main.yml` and `vars/main.yml`):

    kc_fqdn: keycloak.TEST.com
    
Server FQDN where Keycloak service is running.

    kc_https_port: 8666
    
Keycloak service HTTPS port.

    kc_token_life_span: 100000
    
Keycloak access token lifespan. When an OIDC access token is created, this value affects the expiration.

    kc_realm_name: "TEST"

Realm name to which the Keycloak client will be registered.

    client_local_conf_TEST_ui: "{{ lookup('file', '/opt/TEST/ansible/TEST-ui-keycloak.json') }}"

TEST UI adapter configuration file on Ansible controller host.
This file will be created by TEST_ui Ansible role.

    kc_TEST_ui_client_id: "TEST_ui"
    
OpenID Connect client ID in Keycloak for TEST UI resource. 
    
    kc_ssl_certificate_file: "/opt/TEST/ansible/keycloak-certificate"

The trusted SSL certificate for Keycloak that is copied to master host after it is generated.
In case SSL is enabled on Keycloak service.

    package_state: "present"
    
SystemTools RPM package state. In case upgrade of RPM package is needed this variable should be set to "latest".

    install_directory: '/opt/TEST/TEST'

TEST installation directory.

    rc_file_location: '{{ install_directory w}}/.mds.rc'

TEST RC file location.

    db_properties_location: '{{ install_directory }}/etc/db/db.properties'

DB properties file which holds information for TEST system database connectivity location.

    TEST_home: '{{ install_directory }}/base'

TEST home directory location.

    TEST_bin: "{{ TEST_home }}/bin"

EvenLink bin directory location.

Dependencies
------------

- keycloak Ansible role should be executed first. (Keycloak SSL certificate will be copied on Ansible control node)
- TEST_ui Ansible role should be executed first. (Keycloak adapter config json will be copied on Ansible control node)
- jmespath Python module should be available on Ansible target host. (pip install jmespath)
- import_certificate Ansible library provided by ccp-commons-ansible rpm

Example Playbook
----------------

    - hosts: ui-hosts
      vars:
        - kc_fqdn: "keycloak.TEST.com"
        - el_ui_URL: "http://el-ui-host.TEST.com:55080/TEST"
      roles:
        - el-systemtools

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
