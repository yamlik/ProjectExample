Role Name
=========

Install and configure Apache httpd package with TEST specific configuration on CentOS/Red-Hat

Currently doesn't support configuration of SSL.

Requirements
------------

libselinux-python and libsemanage-python packages along with their dependencies to be available in yum repositories.

Role Variables
--------------

These can be overridden:

    httpd_listening_port: "55000"  # httpd listen port                                                        
    reports_service:           "http://{{ inventory_hostname }}:31080"  # Host on which runs the reports webapp
    sysmonui_service:          "http://{{ inventory_hostname }}:34080"  # Host on which runs the sysmonui webapp
    admintools_service:        "http://{{ inventory_hostname }}:45080"  # Host on which runs the admintools webapp
    TEST_ui_service:      "http://{{ inventory_hostname }}:55080"  # Host on which runs the TEST webapp
    system_monitoring_service: "http://{{ inventory_hostname }}:30080"  # Host on which runs the systemMonitoring webapp

Dependencies
------------

Depends on TEST-ui, admintools and other roles. Checks the existence of the following variables:

  el_ui_http_port
  # more variables should be added for other roles

Example Playbook
----------------

---
    - hosts: ui-hosts
      vars:
            - httpd_listening_port: "55000"
            - reports_service:           "http://{{ inventory_hostname }}:31080"
            - sysmonui_service:          "http://{{ inventory_hostname }}:34080"
            - admintools_service:        "http://{{ inventory_hostname }}:45080"
            - TEST_ui_service:      "http://{{ inventory_hostname }}:55080"
            - system_monitoring_service: "http://{{ inventory_hostname }}:30080"
      roles:
            - TEST-httpd

License
-------
Commercial

Author Information
------------------
TEST Corporation
http://www.TEST.com
