@instantiate
Feature: Instantiation

  Scenario: Check vnf_path directory structure
    Given vnfInfo exists
    And it contains vnf_path
    When directory in vnf_path is checked
    Then The directory contains appropriate directories

  Scenario: Check for correct CBIS usage
    Given vnf_path is correct
    And TOSCA_metadata exists

  Scenario: Create ApiAction object
    Given cbamApiClass exists
    When variables are loaded
    And authentication is received
    Then ApiAction object has auth_header and client_id

  Scenario: Input vnfdId is loaded from environmental variables and is inputted into vnfd
    Given an input vnfdId is given as an environment variable
    When it doesn't conflict with existing vnfdIds
    Then the current vnfd is modified to include the vnfdId

#  Scenario: Create update_passwords.tgz custom Ansible script package
#    Given "update_passwords" "sub-directory" is located in "ansible/custom_ansible/" directory
#    And "update_passwords" directory follows guidelines for custom Ansible scripts #https://confluence.int.net.TEST.com/display/IDRDS/Run+Custom+Script+LCM+workflow
#    When "update_passwords.tgz" is created from "update_passwords" directory
#    Then "update_passwords.tgz" "file" is located in "ansible/custom_ansible/" directory
# 
#  Scenario: Create hello_world.tgz custom Ansible script package
#    Given "hello_world" "sub-directory" is located in "ansible/custom_ansible/" directory
#    And "hello_world" directory follows guidelines for custom Ansible scripts #https://confluence.int.net.TEST.com/display/IDRDS/Run+Custom+Script+LCM+workflow
#    When "hello_world.tgz" is created from "hello_world" directory
#    Then "hello_world.tgz" "file" is located in "ansible/custom_ansible/" directory
#  
#  Scenario: Create hello_world.tgz custom Ansible script package
#    Given "rpm_update" "sub-directory" is located in "ansible/custom_ansible/" directory
#    And "rpm_update" directory follows guidelines for custom Ansible scripts #https://confluence.int.net.TEST.com/display/IDRDS/Run+Custom+Script+LCM+workflow
#    When "rpm_update.tgz" is created from "rpm_update" directory
#    Then "rpm_update.tgz" "file" is located in "ansible/custom_ansible/" directory

  Scenario: Create a zip from vnf_path
    Given createZipScript.sh exists
    When the script is run
    Then a zip file containing the VNF is created

  Scenario: Create VNF
    Given ApiAction object exists
    And a zip is made
    When Zip is uploaded to catalog 
    And VNF is created it
    Then the VNF appears in CBAM

  Scenario: Instantiate VNF
    Given ApiAction object exists
    And VNF is uploaded to CBAM
    When VNF is instantiated through API
    Then Instantiation status is started

  Scenario: Instantiation Finishes
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished
