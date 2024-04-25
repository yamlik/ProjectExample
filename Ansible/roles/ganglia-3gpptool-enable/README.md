Role Name
=========

ganglia-3gpp-enable: Enable rrdcached service, create sftp user and add a cronjob to generate 3gpp xml files

Requirements
------------

Target server must have rrdtool and ganglia installed

Role Variables
--------------

These can be overridden:
    rrd_timeout: "60"                                       # Interval duration to write data
    rrd_delay: "60"                                         # Delay duration to write data to spread load
    cluster_name: cluster1                                  # Cluster name of gmetad
    files_3gppxml_location: /appdata/ganglia/meas/          # Path to store the 3gpp xml file generated
    rrd_files_location: /var/lib/ganglia/rrds/              # Path of the rrd files being stored
    file_3gppxml_time_interval_min: "15"                    # 3GPP xml file generated time interval(minute)
    file_3gppxml_expiry_interval_day: "1"                   # 3GPP xml file expiry interval (day)

Dependencies
------------

These dependencies are not the dependencies of the installation of this ansible role, but instead, it is dependencies that are needed
by the 3GPP tool in order to generate 3GPP xml files.
- ganglia
- ganglia-setup
- ganglia-gmond
- libconfuse
- rrdtool


Example Playbook
----------------

---
- hosts: ui
  vars:
    - rrd_timeout: "60"
    - rrd_delay: "60"
    - cluster_name: cluster1
    - files_3gppxml_location: /appdata/ganglia/meas/
    - rrd_files_location: /var/lib/ganglia/rrds/
    - file_3gppxml_time_interval_min: 15
    - file_3gppxml_expiry_interval_day: 1
  roles:
    - ganglia-3gpptool-enable
...

License
-------
Commercial


Author Information
------------------
TEST Corporation
