Role Name
=========

Create lookup company permissions for each of the company created in LOOKUP_COMPANIES database table.

Requirements
------------

None.

Role Variables
--------------

db_el_name
db_el_user
db_el_password
db_el_host
db_el_port

kc_https_url
kc_https_port
kc_realm_name
kc_ssl_certificate_file
kc_realm_admin_user
kc_realm_admin_password
kc_client_id_TEST_ui


Dependencies
------------

None.


Example Playbook
----------------

You can create a playbook with all the required variables,

    ---
    - hosts: ui
      roles:
        - role: create-lookup-company-permissions
          vars:
            db_el_name: "TEST"
            db_el_port: 5432
            kc_https_port: 8666
            kc_https_url: "https://{{ kc_fqdn }}:{{ kc_https_port }}/auth"
            kc_realm_name: "TEST"
            kc_client_id_TEST_ui: "TEST_ui"
            kc_certificate_name: "keycloak-certificate"
            kc_ssl_certificate_file: "/opt/TEST/ansible/{{ kc_certificate_name }}"


and run the playbook like the folloiwng,

    ansible-playbook -i /opt/TEST/ansible/inventory.ini \
      -e @/opt/TEST/ansible/extra_vars.yml \
      -e @/opt/TEST/ansible/vault.yml \
      example-playbook.yml


Alternatively, the following playbook example is the same as the above:

    ---
    - hosts: ui
      roles:
        - role: create-lookup-company-permissions

License
-------

Commercial

Author Information
------------------

TEST Corporation
http://www.TEST.com

