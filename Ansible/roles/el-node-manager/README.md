Role Name
=========

el-node-manager


Role Variables
--------------

    use_alarm                               # 0: no alarm 1: use alarm
    alarm_host                              # alarm host
    alarm_port:                             # alarm port
    alarm_send_warning                      # 0: no send 1: send warning
    nm_tracelog_maxsize                     # max tracelog size in bytes
    nm_tracelog_append                      # false: not append true: append tracelog
    nm_node_heartbeat                       # heartbeat in second
    nm_node_heartbeattolerance              # heartbeat tolerance
    nm_node_heartbeat_warning_interval      # heartbeat warning interval
    nm_node_zerothresholdshift:             # zero thresholdshift
    nm_node_debug_next                      # false: turn off true: turn on debug next
    nm_node_kafka_session_timeout           # kafka session timeout
    nm_node_kafka_connection_timeout        # kafka connection timeout
    audit_plugins                           # audit plugins
    audit_thread_assignment                 # audit thread assignment
    audit_thread_count                      # audit thread count
    audit_message_aggregation_threshold     # audit message aggregation threshold
    audit_message_aggregation_timelimit     # audit message aggregation timelimit
    audit_3gppxml_enabled                   # true: enable audit 3gpp PM integration, false: disable the feature
    audit_3gppxml_location                  # audit 3gpp xml directory, directory must exist if audit 3gpp feature is enabled
    audit_3gppxml_location_separate_host_dir# Whether to automatic create sub-directory for each nm host, and save XMLs onto that directory
    audit_3gppxml_cleanup_interval_minute   # audit 3gpp xml cleanup interval in minutes
    audit_3gppxml_interval_hour             # audit 3gpp xml interval in hours
    nm_maxConcurrentCollectorStartup        # max Concurrent Collector Startup
    nm_crontabfile                          # crontab file that run by node manager 
    node_zero_threshold                     # node zero threshold
    record_dir                              # record buffer directory
    control_dir                             # control directory 
    offline_dir                             # offline files directory
    storage_dir                             # storage directory
    record_dir_threshold                    # record directory threshold
    control_dir_threshold                   # control directory threshold
    storage_dir_threshold                   # storage directory threshold
    max_diagnostic_messages                 # max diag message in a file
    max_diagnostic_files                    # max diag log before recycle
    exec_log_days                           # exec log days
    trs_warning_threshold                   # trs warning threshold
    portserver_port                         # port server port
    portserver_range_min                    # port server min port 
    portserver_range_max                    # port server max port
    oriserver_port                          # ori server port
    jmx_port                                # JMX port
    timezone                                # time zone

Example Playbook
----------------

---
    - hosts: all-processing
      vars:
             use_alarm: 0
             alarm_host:
             alarm_port:
             alarm_send_warning:
             nm_tracelog_maxsize: 10000
             nm_tracelog_append: false
             nm_node_heartbeat: 60
             nm_node_heartbeattolerance: 10000
             nm_node_heartbeat_warning_interval: 600000
             nm_node_zerothresholdshift: 10
             nm_node_debug_next: true
             nm_node_kafka_session_timeout: 15000
             nm_node_kafka_connection_timeout: 10000
             audit_plugins: reporting,statistics,schedules
             audit_thread_assignment:
             audit_thread_count: 1
             audit_message_aggregation_threshold: 0
             audit_message_aggregation_timelimit: 0
             audit_3gppxml_enabled: true
             audit_3gppxml_location: "/appdata/TEST"
             audit_3gppxml_location_separate_host_dir: true
             audit_3gppxml_cleanup_interval_minute: 10080
             audit_3gppxml_interval_hour: 1
             nm_maxConcurrentCollectorStartup: 4
             nm_crontabfile:
             node_zero_threshold: 10
             record_dir: "/opt/TEST/TEST/base/buffer"
             control_dir: "/opt/TEST/TEST/base/control"
             offline_dir: "/opt/TEST/TEST/base/internal/offline"
             storage_dir: "/opt/TEST/TEST/base/internal/nodes"
             record_dir_threshold: 500
             control_dir_threshold: 30
             storage_dir_threshold: 200
             max_diagnostic_messages: 50000
             max_diagnostic_files: 10
             exec_log_days: 10
             trs_warning_threshold: 20
             portserver_port: 62400
             portserver_range_min: 62500
             portserver_range_max: 62550
             oriserver_port: 62404
             jmx_port: 62405
             timezone: "Asia/Kuala_Lumpur"
      roles:
            - el-node-manager

...




License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

