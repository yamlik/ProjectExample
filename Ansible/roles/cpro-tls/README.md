Role Name
=========

cpro-cert : Ansible role to generate, configure el-node-export & prometheus-server service. Services will be restarted end of execution role. 

Requirements
------------

- At least RHEL 7.7 is required.


Role Variables
--------------
node_exporter_port: 9100   # this port has to be same with port set in el-node-exporter


Dependencies
------------

cpro-cert required these dependencies.
- prometheus-2.11.1
- node_exporter-1.0.1

Example Playbook
----------------

---
- hosts: ui
  roles:
    - cpro-cert
   
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
