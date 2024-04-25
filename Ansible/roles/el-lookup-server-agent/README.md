Role Name
=========

el-lookup-server-agent: Configure agent to upload lookup files to HDFS and update status at Zookeeper

Requirements
------------


Role Variables
--------------

    ls_agent_zk_url                 # zookeeper url
    ls_agent_namenodes              # hfds namenodes
    ls_agent_jmx_port               # agent jmx port
    ls_agent_monitoring_interval    # interval of monitoring lookup files
    ls_agent_shutdown_timeout       # agent shutdown timeout
    ls_agent_max_thread             # max thread use to upload to hdfs

Example Playbook
----------------

---

- hosts: processing-host
  roles:
    - el-lookup-server-agent
  vars:
    ls_agent_zk_url: "zk-host.TEST.com:2181"
    ls_agent_namenodes: "hadoop1.TEST.com:8020,hadoop2.TEST.com:8020"
    ls_agent_jmx_port: 61112
    ls_agent_monitoring_interval: 5
    ls_agent_shutdown_timeout: 60
    ls_agent_max_thread: 100
...



License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

