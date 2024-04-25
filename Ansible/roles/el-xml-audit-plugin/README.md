Role Name
=========

el-xml-audit-plugin: to install auditxml.plugin and create spool directory

Requirements
------------


Role Variables
--------------

    xml_audit_spool_dir         # XML audit flume spool directory


Example Playbook
----------------

---

- hosts: processing-host
  roles:
    - el-xml-audit-plugin

  vars:
    xml_audit_spool_dir: "/opt/TEST/TEST/flume/spool"

...



License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

