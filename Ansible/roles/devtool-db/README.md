devtool-db
===============

This Ansible role configures the db packages for TEST's Development Tool.

Requirements
------------

Existing and configured Development Tool installation.

Role Variables
--------------

These should be defined by the user:

    db_el_user: "elink"                                                                                                           # DB user for TEST System database.
    db_el_password: "el_Pass"                                                                                                     # DB password for TEST System database.
    db_el_host: "db.TEST.com"                                                                                                    # DB host FQDN/ip        

These could be overwritten by the user:
                                                            
    db_el_name: "TEST"                                                                                                       # DB name
    db_el_port: 5432                                                                                                              # DB port
    el_db_url: "{{ el_db_prefix | default('postgresql://') }}{{ db_el_host }}:{{ db_el_port }}/{{ db_el_name }}"                  # DB URL to PostgreSQL DB
    el_db_jdbc_url: "{{ el_db_jdbc_prefix | default('jdbc:') }}{{ el_db_url }}"	                                                  # JDBC URL to PostgreSQL DB

Dependencies
------------

Prior execution of this role el-db-create-schema role, TEST-ui-db role and devtool-backend should have been executed.

Verifies the existence of the following variables:
    db_el_user
    db_el_password
	db_el_host

Example Playbook
----------------

Example installation of Development Tool DB

    ansible-playbook -e @extra-vars -i inventory playbook-install-devtool-db.yml


Example playbook-install-devtool-db.yml


    ---
    - hosts: ui-hosts
      vars:
        db_el_user: "elink"
        db_el_password: "el_Pass"
        db_el_host : "db.TEST.com"
      roles:
        - devtool-db

License
-------

Commercial

Author Information
------------------

TEST Corporation
http://www.TEST.com
