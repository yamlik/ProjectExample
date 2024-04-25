Role Name
=========

cpro-server : Ansible role to configure and start prometheus-server service.

Requirements
------------

- At least RHEL 7.7 is required.
- Java 1.8

Role Variables
--------------

Dependencies
------------

cpro-server required these dependencies.
- prometheus-2.11.1

Example Playbook
----------------

---
- hosts: ui
  roles:
    - cpro-server
   
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
