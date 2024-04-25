Some overview on project I worked previously.
You might see some syntax error because I replace some sensitive words.
All of these are not complete code, they are just fraction of the project I worked on.

Ansible
========
This fraction of whole product demostrate how to use ansible concept.
It consist of usage of 
- Ansible library, common task and roles for high code reusability
- Ansible roles for specific task
- A master playbook with pre and post task, specific roles, variable template usage based on specific host group.

Major involve: Ansible, Shell script, Unix operation (volumes, services, firewall, rpms)


Java Selenium Serenity BDD Test Framework
==========================================
This fraction of whole test framework shows below:
The combination of java selenium and serenity bdd
The structure of common bdd framework that consist of 
- feature file
- steps file
- page file
- hooks 
- test import to xray
- default report by serenity
- pre and post hooks in test execution
- usage of serenity bdd context.

Major involve: Java, Selenium, Serenity, xray, HTTP Request


Robot framework in shell
=========================
This fraction of whole test frameworks shows below:
The execution test automation in shell environment using the SSH Library in Robotframework
The structure of robotframework  that consist of
- test suite / test case files
- keywords as library files

Major involve: SSH Library of Robot framework, Robot framework, Shell, Unix operation


Python behave BDD test framework
===============================
This fraction of whole test frameworks shows below:
This example shows how to use the python BDD framework to do test automation in openstack environment with 12 vm and above.
It show that python is good at perform multiple kind of operation task at OS level.
Demonstrate the usage of process in test execution across few vm  using ssh commands. Eg restart service, detach networks, check for volumes.
The structure of behave bdd framework  consist of 
- features file
- steps file

Major involve: python, shell, Unix operation
- Trigger rest end point start vm lifecycle management like heal, rebuild then poll for the result using python
- Perform shell command using python. Eg. update network config, check for volumes.
- Perform yaml/json/ini file update
- Main and subprocess execution.
- Minor Openstack overcloud knowledge


Jenkins
==========
Jenkins files that show how to perform test execution and some devops operation
Some jenkins file show the building of 
- Golden images from spinning up base image to openstack using terrafom 
- Follow by configuration on servers that spinned up
- Snapshot on servers and convert snapshot to different image of golden images for different platform like iso, qcow2(openstack), vmdk (vmware)
- Follow by uploading images to artifactory and cleanup.

Major involve: Jenkins pipeline, shell, Unix operation, terraform, openstack
