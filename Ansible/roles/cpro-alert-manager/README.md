Role Name
=========

cpro-alert-manager: Ansible role to configure and start alertmanager service

Role Variables
--------------

These variables can be overridden in extra_vars_alarms.yml:

- webhook4fluentd_listening_ip: 127.0.0.1
- webhook4fluentd_listening_port: 5061
- alert_manager_http_port: 9093

Example Playbook
----------------

---
    - hosts: ui
      roles:
        - cpro-alert-manager

    ...

License
-------

Commercial

Author Information
------------------

TEST Corporation
https://www.TEST.com
