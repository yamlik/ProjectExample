Role Name
=========

ganglia-3gpp-disable: Remove the cron job to generate the 3gpp xml files, remove sftp created user and disable the rrdcached service

Requirements
------------

Target server must have rrdtool and ganglia installed

Role Variables
--------------

It is not necessary to provide these variables:
    cluster_name: cluster1                                      # Cluster name of gmetad
    files_3gppxml_location: /appdata/ganglia/meas/              # Path to store the 3gpp xml file generated
    rrd_files_location: /var/lib/ganglia/rrds/                  # 3GPP xml file generated time interval(minute)
    file_3gppxml_expiry_interval_day: "1"                       # 3GPP xml file expiry interval(day)

Dependencies
------------

None

Example Playbook
----------------

---
- hosts: ui
  vars:
  roles:
    - ganglia-3gpptool-disable
...

License
-------
Commercial


Author Information
------------------
TEST Corporation