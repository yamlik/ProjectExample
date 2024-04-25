Role Name
=========

cpro-grafana : Ansible role to configure and start grafana-server service.

Requirements
------------

At least RHEL 7.7 is required.


Role Variables
--------------

These variables can be overridden in extra_vars.yml:
- grafana_server_http_port: 3000
 

Dependencies
------------

cpro-grafana required these dependencies.
- grafana-6.2.2

Example Playbook
----------------

---
- hosts: ui
  roles:
    - cpro-grafana
   
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
