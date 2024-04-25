Role Name
=========

Create and configure TEST TEST database on CentOS/Red-Hat

Requirements
------------

PostgreSQL system RPM should be installed on the target host.
Python pip to be installed on target host.
Postgres database to be initialized and privileged user and password to be created on target host.

Role Variables
--------------

User defined variables, these should be defined on invocation by the user
    db_login_user: "postgres"                                                                  # Priviledged Postgres user
    db_login_password: "pass"                                                                   # Priviledged Postgres user's password

These can be overridden

    postgresql_hba_entries:                                                                     # Default Postgres authentication rules (dictionary)
        - { type: local, database: all, user: postgres, auth_method: peer }
	hba_network_address: ""                                                                     # Trusted authentication network address
	hba_netmask: 24                                                                             # Trusted authentication netmask, default value 24.

    db_login_host: "db.TEST.com"                                                             # Postgres database host
    db_login_name: "postgres"                                                                   # Postgres database name
    db_login_port: 5432                                                                         # Postgres database port
    db_el_encoding: "DEFAULT"                                                                   # TEST database encoding
    db_el_lc_collate: "DEFAULT"                                                                 # TEST database collate
    db_el_lc_ctype: "DEFAULT"                                                                   # TEST database ctype
    db_el_name: "TEST"                                                                     # TEST database name
    db_el_user: "elink"                                                                         # TEST database user
    db_el_password: "elpass"                                                                    # TEST database user's password
    db_el_user_role_attrs: CREATEDB,LOGIN                                                       # TEST database user's database privileges

Dependencies
------------

Dependency TEST libraries:
    - create-postgresql-database
------------

Example Playbook
----------------

---
    - hosts: db-hosts
        vars:
            - db_login_host: "db.TEST.com"
            - db_login_user:  "postgres"
            - db_login_password: "pass"
            - db_el_name: "TEST"
            - db_el_user: "elink"
            - db_el_password: "elpass"
        roles:
            - el-db-create-database

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
