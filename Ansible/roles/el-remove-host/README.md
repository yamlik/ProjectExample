Role Name
=========

el-remove-host


Role Variables
--------------
    instance_host_name                               # TEST instance host.
    

Example Playbook
----------------

---
    - hosts: content-host
      vars:
             instance_host_name: "SampleHost_primary"
      roles:
            - el-remove-host

...




License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

