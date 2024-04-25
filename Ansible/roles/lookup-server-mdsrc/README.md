Role Name
=========

Configure RCFILE (.mds.rc) lookup-server section.

Requirements
------------

None

Role Variables
--------------

User defined variables, these should be defined on invocation by the user
    db_el_user:  "elink"                                                    # TEST Postgres user
    db_el_password: "pass"                                                  # TEST Postgres password

These can be overwritten:
    db_el_name: "TEST"                                                 # TEST database name
    db_el_port: 5432                                                        # TEST database port
    db_el_host: "dbhost"                                                    # PostgreSQL database server
    ls_directory: "/opt/TEST/TEST/lookup_server"                    # Lookup Server install directory
    ls_pid_directory: "/opt/TEST/TEST/lookup_server/tmp/"           # Location of the PID directory
    ls_shmid_directory: "/opt/TEST/TEST/lookup_server/tmp/"         # Location of the SHMID directory
    ls_log_directory: "/opt/TEST/TEST/lookup_server/log/"           # Location of the log directory
    ls_dump_directory: "/opt/TEST/TEST/lookup_server/bin/dump/"     # Location of the dump directory

    ls_diagnostic_level: 0                                                  # Lookup Server diagnostic level (0 is no diagnostics log, 99 is verbose)
    ls_log_roll_interval: 10                                                # Log file open duration (hours)
    ls_log_roll_count: 3                                                    # Amount of old log files kept (files)
    ls_max_tables: 20                                                       # Maximum number of tables stored in Lookup Server
    ls_separator_string: " "                                                # Separator string for data files
    ls_command_after_reload: ""                                             # Location of the reload script (path+filename)
    ls_load_data_from_files_flag: 0                                         # Load data from a temporary flat file, if one exists, rather than download first from DB
    ls_sql_fetch_size: 1000                                                 # The number of rows to fetch from sql at a time

    ls_agent_zk_url: "zookeeperhost:2181"                                   # Zookeeper host url for Lookup Server Agent
    ls_agent_namenodes: hdfs://hdfshost1:8020,hdfs://hdfshost2:8020         # HDFS host url for Lookup Server Agent
    ls_agent_jmx_port: 61112                                                # JMX port for Lookup Server Agent
    ls_agent_monitoring_interval: 5                                         # Monitoring interval for Lookup Server Agent
    ls_agent_shutdown_timeout: 60                                           # Lookup Server Agent timeout duration to shut down
    ls_agent_max_thread: 100                                                # Lookup Server Agent maximum thread

Dependencies
------------

None


Example Playbook
----------------

---
- hosts: processing
	roles:
		- lookup-server-mdsrc
		
	vars:
		db_el_user: "elink"
		db_el_password: "elpass"
	
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com
