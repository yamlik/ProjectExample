@runCustomAnsible
Feature: Run custom Ansible
 
  Scenario: hello_world.tgz package is available on active OAM
    Given Instantiation status is finished
    When I log in to "OAM" node
    Then I can see that "httpd" is running
    And "hello_world.tgz" is located in "/var/www/html/ansible-scripts/" directory

  Scenario Outline: hello_world.tgz package is available on VMs
    Given Instantiation status is finished
    When I log in to "<vm_type>" VM
    And I download custom Ansible scripts package from "oam_vip" path "/hello_world.tgz"
    Then "hello_world.tgz" "file" is located in "/home/TEST/" directory

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 
      | ui            | 
      | db            | 

  Scenario: Execute "Run Custom Ansible" LCM on all VMs
    Given Instantiation status is finished
    When I launch "Run Custom Ansible" LCM action with params:
      | key                       | value                                           |
      | run_on_all                | hello_world                                     |
      | run_on_online_processing  |                                                 |
      | run_on_offline_processing |                                                 |
      | run_on_ui                 |                                                 |
      | run_on_db                 |                                                 |
      | run_on_oam                |                                                 |
      | ansible_extra_vars        | message='Hello World!', filename=helloWorld.txt |
      | serial_run                | 5                                               |   
    Then "Run Custom Ansible" LCM action finishes successfully

  Scenario Outline: Verify hello_world.tgz Ansible has run successfully on all VMs
    Given "Run Custom Ansible" LCM action finishes successfully
    When I log in to "<vm_type>" VM
    Then "helloWorld.txt" "file" is located in "/home/TEST/" directory
    And "/home/TEST/helloWorld.txt" has content of "Hello World!"

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 
      | ui            | 
      | db            | 
      | oam           | 

  Scenario Outline: Remove helloWorld.txt files from hosts
    Given "Run Custom Ansible" LCM action finishes successfully
    When I log in to "<vm_type>" VM
    Then "helloWorld.txt" "file" is located in "/home/TEST/" directory
    and "file" "helloWorld.txt" is deleted from "/home/TEST/" directory

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 
      | ui            | 
      | db            | 
      | oam           | 

  Scenario: Execute "Run Custom Ansible" LCM on selected VMs
    Given Instantiation status is finished
    When I launch "Run Custom Ansible" LCM action with params:
      | key                       | value                                             |
      | run_on_all                |                                                   |
      | run_on_online_processing  | hello_world                                       |
      | run_on_offline_processing | hello_world                                       |
      | run_on_ui                 |                                                   |
      | run_on_db                 |                                                   |
      | run_on_oam                |                                                   |
      | ansible_extra_vars        | message='Hello World2!', filename=helloWorld2.txt |
      | serial_run                | 5                                                 |
    Then "Run Custom Ansible" LCM action finishes successfully

  Scenario Outline: Verify hello_world.tgz Ansible has run successfully on selected VMs
    Given "Run Custom Ansible" LCM action finishes successfully
    When I log in to "<vm_type>" VM
    Then "helloWorld.txt" "file" is located in "/home/TEST/" directory
    And "/home/TEST/helloWorld.txt" has content of "Hello World!"

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 

  Scenario Outline: Verify hello_world.tgz Ansible has not run on undefined VMs
    Given "Run Custom Ansible" LCM action finishes successfully
    When I log in to "<vm_type>" VM
    Then "helloWorld.txt" "file" should not be located in "/home/TEST/" directory

    Examples:
      | vm_type       |
      | ui            | 
      | db            | 
      | oam           | 

