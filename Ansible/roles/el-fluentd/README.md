Role Name
=========

el-fluentd: Ansible role to configure and start td-agent service


Role Variables
--------------

These variables can be overridden in extra_vars_alarms.yml:

- amq_host: "dr-ui-host.TEST.com"
- amq_heartbeat_seconds: 180
- logrotate_rotate: 7
- logrotate_size: 100M

Variables to be defined in vault_alarms.yml 
- mq_username: user
- mq_password: password


Example Playbook
----------------

---
    - hosts: processing
      roles:
        - el-fluentd

...

 
License
-------
Commercial


Author Information
------------------
TEST Corporation
https://www.TEST.com

