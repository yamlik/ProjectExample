@UI
Feature: Verify all the regression scenarios related to System Import

  @TSNT-4638 @TESTAUTO-99  @productUnderTest @productUnderTest @productUnderTest.0
  Scenario:TC-58876 - productUnderTest - Utilities - System Export/Import Rule
    Given I create "1" rule using an API
    When I am on "System Export" Page
    And Export the System file
    Then I delete the rule using an API
    And I am on "System Import" Page
    And I import the above exported file
    And I am on "Rules Configuration" Page
    And I search if the rule exists on Rules Configuration page

  @TESTAUTO-208 @TSNT-4907 @productUnderTest
  Scenario:TC-48950 - productUnderTest - Utilities - System Import - Ensure that Import file for all productUnderTest Data with different Import Criteria imported suTSNTssfully
    Given I am on "System Export" Page
    And Export the System file
    When I am on "System Import" Page
    And I upload the above exported file
    And I choose "Criteria" = "Delete and Insert All Records"
    And I import the attached file into the system
    Then I should be able to verify that the import is suTSNTssful
    When I choose "Criteria" = "Replace All Records"
    And I import the attached file into the system
    Then I should be able to verify that the import is suTSNTssful

 @TESTAUTO-209 @productUnderTest @TSNT-4922
   Scenario:TC-48949 - productUnderTest - Utilities - System Import - Ensure that Import file for Workflow Manager imported suTSNTssfully
   Given I am on "System Export" Page
   When I select "Workflow Manager" to export
   And Export the System file
   Then I am on "System Import" Page
   And I import the above exported file
   And I should be able to verify that the import is suTSNTssful