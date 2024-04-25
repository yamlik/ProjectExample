Role Name
=========

Install Devtool UI rpm package on CentOS/Red-Hat

Requirements
------------

None.

Role Variables
--------------

Normally user shouldn't overwrite next variables:
    el_devtool_ui_package_name: "el-devtool-ui"                    # Name of the installed package from TEST Repositories

Dependencies
------------
None 

Example Playbook
----------------

---
    - hosts: ui-hosts
      vars:
      roles:
            - el-devtool-ui-install

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
