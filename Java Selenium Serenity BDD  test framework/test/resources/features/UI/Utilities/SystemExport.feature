@UI
Feature: Verify all the regression scenarios related to System Export

  @TESTAUTO-210 @TSNT-4973 @productUnderTest
  Scenario:TC-48935 - productUnderTest - Utilities - System Export Select Specific Records
    Given I create "80" rule using an API
    And I am on "System Export" Page
    When I choose "Rules" = "Select Specific Records"
    And I deselect "1" row on Select Records to Export
    And I again choose "Rules" = "Select Specific Records"
    Then I verify the records selected on System Export