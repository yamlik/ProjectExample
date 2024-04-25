Role Name
=========

el-nodes-install: to import node and stream packages into database

Requirements
------------

TEST-db rpm
el-system-tools rpm

Role Variables
--------------

    import_stream        # true: import stream package, false: not import stream package
    stream_host          # TEST instance name used when import stream package
    el_ui_http_port      # TEST UI httpd port
    um_crud_wait_timeout # Timeout connecting to TEST UI


Example Playbook
----------------

---

- hosts: processing-host
  roles:
    - el-nodes-install

  vars:
    import_stream: true
    stream_host: "processing-host.TEST.com"
    el_ui_http_port: "55080"
    um_crud_wait_timeout: 30

...



License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
