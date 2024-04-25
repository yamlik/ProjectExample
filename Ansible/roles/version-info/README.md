version-info
============

Installs Python packages tzlocal and requests. Sends host and component information to Admin Tools backend.

Requirements
------------

Requires pip. Requires Ansible 2.2 or higher.

Role Variables
--------------

Default variables:

    vi_crud_wait_timeout: 30              # The value can be set higher for really slow hosts.
    host_fqdn: "{{ inventory_hostname }}" # The FQDN of the sending party, overwrite this if inventory contains other than actual hostnames

Variables used from admin-tools-configure:
(NOTE: if admin-tools-configure is being run in the same play, these need not be redefined)

    admintools_fqdn                       # Fully-Qualified Domain Name of host running Admin Tools
    admintools_client_local_conf          # Admin Tools' adapter configuration file on Ansible controller host.
    
Variables used from keycloak-ansible (optional):
(NOTE: if keycloak-ansible is run in the same play, these need not be redefined)

    kc_ssl_certificate_file               # The trusted SSL certificate for Keycloak that is copied to master host after it is generated
                                          # If this variable is not defined, SSL verification for the Keycloak authentication will be disabled
    
Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: all
      vars:
        # This can be the fqdn of 
        # * any UI host (where Admin Tools is installed
        # * a Consul service name (e.g. ui.service.consul)
        # * a load balancer (which will route the request to appropriate Admin Tools backend)
        - admintools_fqdn: <FQDN>
        - admintools_client_local_conf: "{{ lookup('file', '/opt/TEST/ansible/admintools-keycloak.json') }}"
        # Custom timeout (2min) for some slow hosts
        - vi_crud_wait_timeout: 120
        # (Optional) trusted SSL certificate
        - kc_ssl_certificate_file: /opt/TEST/ansible/keycloak-certificate
      roles:
        - version-info

License
-------

Commercial

Author Information
------------------

TEST A&A, Team NPC <I_AA_DO_RD_SO_NPC@internal.TEST.com>
