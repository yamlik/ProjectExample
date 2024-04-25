Role Name
=========

Install TEST UI DB package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

These should  be defined by the user. 

    db_el_user: 'elink'                             # DB user for TEST System database.
    db_el_password: 'el_Pass'                         # DB password for  TEST System database.

These can be overridden
    install_directory: "/opt/TEST/TEST"               # Installation directory for TEST tomcat service.
    db_el_name: "TEST"                         # DB name of the TEST System database
    db_el_host: "db.service.consul"                 # DB host of the TEST System database.  By default is pointing to the consul service.
    db_el_port: "5432"                              # DB port of the TEST System database.
    db_el_tablespace: 'ctl'                             # DB tablespace for TEST System database.
    db_el_tablespace_index: 'ctlidx'                         # DB index tablespace for  TEST System database.
    install_directory: "/opt/TEST/TEST"              # Installation directory for Eventink UI.
    TEST_bin: "{{ install_directory }}/base/bin"        # Installation directory for Eventink scripts.

Dependencies
------------

Depends on el-db role and TEST-ui. Inherit the existence of the following variables:

    db_el_tablespace
    db_el_tablespace_index
    db_el_user
    db_el_password
    install_directory
    TEST_bin

Checks the existence of the following variables:
    db_el_user
    db_el_password

Example Playbook
----------------

---
    - hosts: ui-hosts
      vars:
            db_el_name: "TEST"
            db_el_host: "db.service.consul"
            db_el_port: "5432"
            db_el_user: "elink"
            db_el_password: "el_Pass"
            db_el_tablespace: "ctl"
            db_el_tablespace_index: "ctlidx"
            install_directory: "/opt/TEST/TEST"
            TEST_bin: "{{ install_directory }}/base/bin"
      roles:
            - TEST-ui-db

License
-------
Commercial

Author Information
------------------
TEST Corporation
http://www.TEST.com
