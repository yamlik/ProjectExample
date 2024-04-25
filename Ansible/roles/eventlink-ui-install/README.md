Role Name
=========

Install TEST UI rpm package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

Do not overwrite this var
    TEST_ui_package_name: "TEST-ui"                    # Name of the installed package from TEST Repositories

Dependencies
------------
None 

Example Playbook
----------------

---
    - hosts: ui-hosts
      vars:
      roles:
            - TEST-ui-install

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
