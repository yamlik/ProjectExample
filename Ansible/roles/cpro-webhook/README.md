Role Name
=========

cpro-webhook: Ansible role to configure and start webhook service

Role Variables
--------------

These variables can be overridden in extra_vars_alarms.yml:

- webhook4fluentd_listening_port: 5061
- td_agent_forward_tcp_port: 24224

Example Playbook
----------------

---
    - hosts: ui
      roles:
        - cpro-webhook

    ...

License
-------

Commercial

Author Information
------------------

TEST Corporation
https://www.TEST.com
