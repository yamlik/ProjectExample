ganglia-gmond
=============

Installs and configures Ganglia gmond process

Requirements
------------

	- Ansible 2.2 or higher.
	- ccp-commons-ansible

Role Variables
--------------

Default variables:

	gmond_fqdn: "{{ inventory_hostname }}"

	gmond_udp_recv_port: 8649
	gmond_tcp_accept_port: 8649
	
	gmond_udp_send_port: "{{ gmond_udp_recv_port }}" 

	gmond_start_timeout: 60
	
Optional variables

	gmond_override_hostname: <override_hostname>
	
	gmond_time_treshold: <time_treshold>
	
	gmond_polling_interval: <polling_interval>

	# Determines whether current host group is secondary or primary
	# If set to true, the gmond processes will be "deaf" to incoming UDP and TCP traffic
	secondary: <true|false>
	
	# By default the gmond Ansible role configures the send, receive and accept channels
	# according to the defined primary and secondary hosts and their default ports
	# It is possible to explicitly set the channels with the following set of parameters:
	gmond_udp_send_channels:
		- { host: <host>, port: <port> [, mcast_join: <mcast_join> ] }
		- ...
		
	gmond_udp_recv_channels:
		- { host: <host>, port: <port> [, mcast_join: <mcast_join> ] [, bind: <bind> ] [, buffer: <buffer> ] [, family: inet4|inet6 ] }
		- ...
		
	gmond_tcp_accept_channels:
		- { port: <port> [, family: inet4|inet6 ] [, gzip_output: <gzip_output> ] }
		- ...
	
Dependencies
------------

* install-package
* ganglia-collect-primary-hosts
* check-variables

Dependency roles:
- Installs ganglia-gmond RPM package, if its not present.
- Collects the names of the Ganglia primary hosts.
- Checks the 'ganglia_primary_hosts' variable is correctly set

Example Playbook
----------------
    
Playbook using default Ganglia primary hosts group name:
    
    ---
    - hosts: ganglia_primary_hosts
      roles:
        - role: ganglia-gmond
    - hosts: ganglia_secondary_hosts
      roles:
        - role: ganglia-gmond
          secondary: true
          
Playbook using a non-default primary hosts group and specifying
it in the ganglia_primary_hosts_group parameter:

    ---
    - hosts: host_group
      roles:
        - role: ganglia-gmond
          ganglia_primary_hosts_group: host_group
    - hosts: ganglia_secondary_hosts
      roles:
        - role: ganglia-gmond
          ganglia_primary_hosts_group: host_group
          secondary: true
          
Playbook that explicitly defines the UDP send and receive channels and TCP accept channels. Normally these
are automatically configured based on the primary and secondary hosts and default ports:

    ---
    - hosts: ganglia_primary_hosts
      roles:
        - role: ganglia-gmond
          gmond_udp_recv_channels:
            - { port: "8866" }
            - { port: "8867" }
          gmond_udp_send_channels:
            - { host: "localhost", port: "8866" }
          gmond_tcp_accept_channels:
            - { port: "8866" }
    - hosts: ganglia_secondary_hosts
      roles:
        - role: ganglia-gmond
          secondary: true
          gmond_udp_send_channels:
            - { host: "hrh7st202.TEST.com", port: "8866" }
            - { host: "hrh7st202.TEST.com", port: "8867" }
            - { host: "hrh6st61.TEST.com", port: "8866" }
            - { host: "hrh6st61.TEST.com", port: "8867" }

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
