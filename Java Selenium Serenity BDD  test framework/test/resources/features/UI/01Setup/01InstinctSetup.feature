@SETUP_UI
Feature: This feature will help set up the productUnderTest environment. The scenarios executed are the pre-requisites for the automation tests

# Background:
#   Given I login to the portal
#   Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key

  @SETUP @TSNT-3487
  Scenario:  Login to productUnderTest for the first time, I do not wish to take a tour
    When I see the "Welcome" popup
    Then I choose "No thanks" on the What's New Popup
    And I am on "Users" Page

  @TESTAUTO-109 @SETUP @TSNT-3488
    Scenario:  Create a method to Execute SQL scripts
    Given I connect to database and execute "Lead_Generators" file

  @TESTAUTO-112 @SETUP @TSNT-3491
    Scenario:  Create a method to Execute SQL scripts
    Given I connect to database and execute "Rule_performance_Functions" file

  @TESTAUTO-27 @SETUP @TSNT-3492
  Scenario: Import the dat file into the system to configure the data
    Given I should navigate to the system import page
    When I upload the required file
    And I import the attached file into the system
    Then I should be able to verify that the import is suTSNTssful
    Then I restart batch service

  @SETUP @TSNT-3493
  Scenario: Enable Store Data In Uppercase in System Parameters
    Given I am on "System Parameters" Page
    And I turn "ON" the "Store Data In Uppercase" on System Parameters

  @SETUP
  Scenario: Set the Fraud Alert
    Given I am on "Fraud Alerts" Page
    When I set 0 to "29" for Clean
    And I set "30" to "129" for Suspect
    And I set "130" to 999 for HFP
    Then I save the Fraud Alerts

  @SETUP
  Scenario:TC-12345 - Create a Non Admin User and assign a non-Administrator profile to it
    Given I am on "Profiles" Page
    When I create new profile "NONADMIN"
    Then I create "1" users using the above profiles
    And I re-login for the first time into the application as "NONADMIN"
    And I am on "Users" Page

  @SETUP @TSNT-3494
  Scenario: Do a full system export
    Given I am on "System Export" Page
    When Export the System file
    Then I copy the "productUnderTest_Data_.DAT" export file to the Exports folder