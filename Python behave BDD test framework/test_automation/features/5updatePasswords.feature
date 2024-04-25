@updatePasswords
Feature: update passwords

  Scenario: Execute "Modify" LCM action
    Given Instantiation status is finished
    When I launch "Modify" LCM action with params:
      | key                   | value         |
      | passwd_TEST_gui  | dev0ps4life!A |
      | passwd_keycloak_admin | dev0ps4life!B | 
      | passwd_unix_root_user | dev0ps4life!C | 
      | passwd_unix_user      | dev0ps4life!D | 
    Then "Modify" LCM action finishes successfully

##  Scenario: Verify old_kc_admin_password

  Scenario: Execute "Update Passwords" LCM action
    Given "Modify" LCM action finishes successfully
    When I launch "Update Passwords" LCM action with params
      | key                   | value    |
      | old_kc_admin_password | dev0ps4life!G |
    Then "Update Passwords" LCM action finishes successfully

  Scenario: Verify "Update Passwords" LCM action
    Given "Modify" LCM action finishes successfully
    When I launch "Update Passwords" LCM action with params
      | key                   | value    |
      | old_kc_admin_password | dev0ps4life!B |
    Then "Update Passwords" LCM action finishes successfully

  Scenario Outline: Verify "Update Passwords" LCM action effect on UNIX user
    Given "Update Passwords" LCM action finishes successfully
    When I log in to "<vm_type>"
    Then I should be able to switch to "TEST" user using password "dev0ps4life!D" without sudo

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 
      | ui            | 
      | db            | 
      | oam           | 

  Scenario Outline: Verify "Update Passwords" LCM action effect on UNIX root user
    Given "Update Passwords" LCM action finishes successfully
    When I log in to "<vm_type>" VM
    Then I should be able to switch to "root" user using password "dev0ps4life!C" without sudo

    Examples:
      | vm_type       |
      | processingON  | 
      | processingOFF | 
      | ui            | 
      | db            | 
      | oam           | 
     
  #To be added later 

  @ui
  Scenario: Verify "Update Passwords" LCM action effect on UI of TESTry and Keycloak Admin 
    Given "Update Passwords" LCM action finishes successfully
    When I start recording the display
    and I navigate to TESTry login page
    And I log in with username "Administrator" and password "dev0ps4life!A"
    Then I should be successfully logged in
    When I navigate to Keycloak Admin console
    And I log in with username "Admin" and password "dev0ps4life!B"
    Then I should be successfully logged in


  @PasswordInputValidation
  Scenario: Execute "Modify" LCM action
    Given Instantiation status is finished
    When I launch "Modify" LCM action with params:
      | key                   | value         |
      | passwd_TEST_gui  | dev0ps4life!  |
      | passwd_keycloak_admin | dev0ps4life!B | 
      | passwd_unix_root_user | dev0ps4life!C | 
      | passwd_unix_user      | dev0ps4life!D | 
    Then "Modify" LCM action finishes successfully

  @PasswordInputValidation
  Scenario: Verify "Update Passwords" password input verification
    Given "Modify" LCM action finishes successfully
    When I launch "Update Passwords" LCM action with params
      | key                   | value    |
      | old_kc_admin_password | dev0ps4life!B |
    Then "Update Passwords" LCM action finishes with error
    And error message has following text:
      | error                                                                                                                                                |
      | "Extension variable for 'passwd_TEST_gui' doesn't match password strength requirements. Password needs to have at least one upcase character." |
 
  @PasswordInputValidation
  Scenario: Execute "Modify" LCM action
    Given Instantiation status is finished
    When I launch "Modify" LCM action with params:
      | key                   | value         |
      | passwd_TEST_gui  | dev0ps4life!A |
      | passwd_keycloak_admin | dev0ps        | 
      | passwd_unix_root_user | dev0ps4life!C | 
      | passwd_unix_user      | dev0ps4life!D | 
    Then "Modify" LCM action finishes successfully

  @PasswordInputValidation
  Scenario: Verify "Update Passwords" password input verification
    Given "Modify" LCM action finishes successfully
    When I launch "Update Passwords" LCM action with params
      | key                   | value    |
      | old_kc_admin_password | dev0ps4life!B |
    Then "Update Passwords" LCM action finishes with error
    And error message has following text:
      | error                                                                                                                                                |
      | "Extension variable for 'passwd_keycloak_admin' doesn't match password strength requirements. Password needs to be at least 8 characters long." |

 Scenario: Execute "Modify" password to set keycloak_admin back to dev0ps4life!G
    Given Instantiation status is finished
    When I launch "Modify" LCM action with params:
      | key                   | value         |
      | passwd_TEST_gui  | dev0ps4life!A |
      | passwd_keycloak_admin | dev0ps4life!G | 
      | passwd_unix_root_user | dev0ps4life!C | 
      | passwd_unix_user      | dev0ps4life!D | 
    Then "Modify" LCM action finishes successfully

  Scenario: Execute "Update Passwords" LCM action
    Given "Modify" LCM action finishes successfully
    When I launch "Update Passwords" LCM action with params
      | key                   | value    |
      | old_kc_admin_password | dev0ps4life!B |
    Then "Update Passwords" LCM action finishes successfully


