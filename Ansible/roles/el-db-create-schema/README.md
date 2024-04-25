Role Name
=========

Create TEST schemas

Requirements
------------

PostgreSQL system RPM should be installed on the target host.
Python pip to be installed on target host.
Postgres and TEST database to be initialized and privileged user and password to be created on target host.


Role Variables
--------------

    db_el_tablespace: ctl               #TEST table space for tables
    db_el_tablespace_index: ctlidx      #TEST table space for indexes


Example Playbook
----------------

---
    - hosts: processing-hosts
        vars:
            - db_el_tablespace: ctl
            - db_el_tablespace_index: ctlidx
        roles:
            - el-db-create-schema

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

