Role Name
=========

cpro-gen3gppxml : Ansible role to configure and start gen3gppxml service.

Requirements
------------

- At least RHEL 7.7 is required.
- Java 1.8


Role Variables
--------------

These variables can be overridden in extra_vars.yml:

- audit_3gppxml_enabled: false
- gen3gppxml_log_level: INFO
- gen3gppxml_report_threadpool_size: 20
  
Variables to be defined in vault.yml

Authenticate using password or key
- gen3gppxml_sftp_user:
- gen3gppxml_sftp_password:

 
Dependencies
------------

cpro-gen3gppxml required these dependencies.
- gen3gppxml-1.4.7
- Java 1.8
- python34

Example Playbook
----------------

---
- hosts: ui
  roles:
    - cpro-gen3gppxml
   
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
