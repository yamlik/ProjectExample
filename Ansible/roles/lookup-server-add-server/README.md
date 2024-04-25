Role Name
=========

Add lookup server to TEST


Requirements
------------

TEST and Lookup server RPM packages to be installed.

Role Variables
--------------

User defined variables, these should be defined on invocation by the user   
    lookup_server_name: "SmokeServer"  # Name of the lookup server
    lookup_server_port: 51260          # Port on which lookup server will run

These can be overridden
    lookup_server_host: "{{ inventory_hostname }}"            # host that will be displayed in UI
    lookup_server_ip:   "{{ ansible_default_ipv4.address }}"  # IP address of the host


Dependencies
------------

lookup-server RPM package
TEST-db RPM package
TEST-scripts RPM package

extra-vars.yml file with following variables:
user: TEST
group: TEST
install_dir: "/opt/TEST/TEST"      # Path where TEST is installed
TEST_home: "{{ install_dir }}/base"   # TEST base directory


Example Playbook
----------------

---

- hosts: processing-host1
  vars:
    - lookup_server_name: "SmokeServer"
    - lookup_server_port: 51260
  roles:
    - lookup-server-add-server
    

Example command to run the playbook
-----------------------------------

ansible-playbook add-lookup-server-playbook.yml -i inventory -e @extra-vars.yml


License
-------
Commercial

Author Information
------------------
TEST Corporation
http://www.TEST.com
