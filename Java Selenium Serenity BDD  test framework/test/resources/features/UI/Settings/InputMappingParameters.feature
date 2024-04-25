@UI
Feature: Regression Scenarios for Input Mapping Parameters

  @TESTAUTO-278 @productUnderTest @productUnderTest @TSNT-5454
  Scenario:TSNT-5454 - productUnderTest - Settings - Verify the Input Mapping Parameters are saved
    Given I am on "System Parameters" Page
    And I navigate to Server Parameters
    When I turn "ON" the "Map Application User Field 2 Data to Savings Amount Field in Input" on Server Parameters
    And I turn "ON" the "Map Application User Field 3 Data to Account Number Field in Input" on Server Parameters
    And I turn "ON" the "Map Applicant User Field 7 Data to Nature of Fraud Field in Input" on Server Parameters
    And I turn "ON" the "Map Applicant User Field 10 Data to Income Field in Input" on Server Parameters
    And I re-login again into the application as "NONADMIN"
    And I am on "System Parameters" Page
    And I navigate to Server Parameters
    Then I verify the Input parameters to be updated