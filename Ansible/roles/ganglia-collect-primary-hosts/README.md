ganglia-collect-hosts
=====================

Collects the host names of the current host group and sets them to the 'ganglia_primary_hosts' variable

By default this role retrieves the list of primary hosts from the inventory host group with the name 'ganglia_primary_hosts'.

The primary host group can be defined explicitly by overriding the variable 'ganglia_primary_hosts_group'.

Requirements
------------

Requires Ansible 2.2 or higher.

Role Variables
------------

ganglia_primary_hosts_group:    Used to define the host group that is considered 
                                the primary host group of the Ganglia installation.
                                This is the group of hosts that listens on incoming UDP traffic
                                from the secondary gmond installations.

Dependencies
------------

No dependencies

Example Playbooks
----------------

Playbook using the default name for the primary host group:

    ---
    - hosts: ganglia_primary_hosts
      roles:
        - role: ganglia-collect-primary-hosts
	- hosts: ganglia_secondary_hosts
	  roles:
	    - role: ganglia-collect-primary-hosts

Playbook using a non-default name for the primary host group 
and specifying it in the ganglia_primary_hosts_group variable:
        
    ---
    - hosts: host_group
      roles:
        - role: ganglia-collect-primary-hosts
		  ganglia_primary_hosts_group: host_group
    - hosts: other_host_group
      roles:
        - role: ganglia-collect-primary-hosts
		  ganglia_primary_hosts_group: host_group
	
		  
License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>
