Role Name
=========

Drop TEST schemas

Requirements
------------

PostgreSQL system RPM should be installed on the target host.
Python pip to be installed on target host.
Postgres and TEST database to be initialized and privileged user and password to be created on target host.

Example Playbook
----------------

---
    - hosts: processing-hosts
        roles:
            - el-db-drop-schema

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

