docker-registry-install
==============

Install the docker-distribution, opens given ports on firewalld and restarts docker-distribution service.
This role should be run via Role Dependencies.

Requirements
------------

Requires yum, firewalld and systemd. Requires Ansible 2.2 or higher.

Role Variables
--------------

The following variables should be defined on invocation:

    docker_registry_ip        # The IP interface for docker-distribution service should listen, default is empty which listen to all interfaces, e.g.: 192.168.1.100
    docker_registry_port      # The port for docker-distribution service should listen, use default is 5000, e.g. 5000
    docker_registry_path      # The path to store the docker images, e.g. /var/lib/registry

Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: ui-hosts
      vars:
        - docker_registry_ip: #can be empty so it listen to all interfaces
        - docker_registry_port: 5000
        - docker_registry_path: /var/lib/registry
      roles:
        - docker-registry-install

License
-------

Commercial

Author Information
------------------
TEST Corporation
http://www.TEST.com

