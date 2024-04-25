Role Name
=========

crmq : Ansible role to configure CRMQ rabbitmq-server.

Requirements
------------

At least RHEL 7.7 is required. 

Role Variables
--------------

These variables can be overridden in extra_vars_alarms.yml:

- crmq_log_level: info
- crmq_heartbeat_seconds: 180

Dependencies
------------

Rabbitmq required these dependencies.
- rabbitmq-server-3.8.2
- erlang-22.2.7

Example Playbook
----------------

---
- hosts: ui
  roles:
    - crmq
    
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
