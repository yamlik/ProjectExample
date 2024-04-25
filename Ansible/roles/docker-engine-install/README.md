Role Name
=========

docker-engine-install: to install and configure docker engine

Requirements
------------


Role Variables
--------------

    The following variables should be defined on invocation:

    docker_registry_port    #The port docker-distribution service is listening to

Example Playbook
----------------

    ---

    - hosts: processing-host
      roles:
        - docker-engine-install

    ...



License
-------
Commercial


Author Information
------------------
TEST Corporation
http://www.TEST.com

