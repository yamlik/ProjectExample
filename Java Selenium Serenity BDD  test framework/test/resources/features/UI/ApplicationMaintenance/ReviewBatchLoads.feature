@UI
Feature: Verify all the Review Batch Load regression scenarios by uploading Batch Files

  @TSNT-5475 @TESTAUTO-67 @productUnderTest @DELETE_RULE_SET @DELETE_ALL_APPLICATION
  Scenario: TC-58781 - productUnderTest - Application - Review Batch Loads
    Given I am on "Rule Sets" Page
    And I add new Rule Set that includes Rules "AB00004,ATI0001"
    And I transfer file "BTESTAUTO67.txt" to batch service input directory
    And I am on "Application Review Batch Loads" Page
    When I search for date ranges from "01/01/2020" to "01/01/2030" and refresh
    And I verify the expected counts for HFP is "3", Suspect is "3", Clean is "0"
    And I double-click the above Batch verified record
    And I verify the HPF "3" /Suspect "3" /Clean "0" /Error "0" counts on Alert Review Page
    Then I select a record from "Suspect" tab to go to Match Review Page
    And I navigate using breadcrumbs to "Alert Review" page
    And I see the application is on "Suspect" tab
    And I Action the application as "Suspicious" from "Alert Review" page