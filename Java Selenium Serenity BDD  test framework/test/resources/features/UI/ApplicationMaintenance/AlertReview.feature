@UI
Feature: Verify regression scenarios for Alert Review functionalities

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  # Recent link is not working in .2 and .0
  @TSNT-4631 @TESTAUTO-31  @IMPORT_DAT_FILE @DELETE_ALL_APPLICATION @DELETE_RULE_SET  @productUnderTest
  Scenario: TC-58784 - Main functionality of real time review in Alert Review: New application
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new "Application" Record page
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "ATI0001"
    When I am on "Alert Review" Page
    And I see the application is on "Clean" tab
    And I Fraud Check the Application using Recent Applications
    Then I am on "Alert Review" Page
    And I see the application is on "Suspect" tab
    And I Action the matched application as "Suspicious" from "Alert Review" page
    And I verify the application is actioned as "Suspicious"

  @TSNT-4635 @TESTAUTO-79 @IMPORT_DAT_FILE @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET  @productUnderTest.2 @productUnderTest.0
  Scenario: TC-58861 productUnderTest - Application- Filter with Date feature must returns result within the date range
    Given I am on "Application Maintenance" Page
    When I navigate to New "Application" Page
    And I add details on categories: "Application" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    And I am on "Alert Review" Page
    And I enable date range and apply filter
    Then I verify the Table content

  @TSNT-5371 @TESTAUTO-232 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET  @productUnderTest.2 @productUnderTest.0
  Scenario: TC-61466 productUnderTest - Application - Alert Review - Filter for Application Type to include IN
    Given I am on "Application Maintenance" Page
    When I navigate to New "Application" Page
    And I add details on categories: "Application" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    And I am on "Alert Review" Page
    And I select filter
    And I add following In filter
    |Field|Operator|Value|
    |Application Type|Is In|CARD|
    |Application Type|Is In|MTGE|
    Then I verify the Table content

  @TESTAUTO-30 @TSNT-5172 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET @IMPORT_DAT_FILE

  Scenario: TC-58783 productUnderTest - Application - Alert Review
    Given I restart batch service
    And I am on "Rule Sets" Page
    Then I add new Rule Set that includes Rules "AB00004,ATI0001"
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    Then I add details on categories: "Application,Applicant" on the new Application Record page
    Given I transfer file "BTESTAUTO277.txt" to batch service input directory
    And I submit "4" application request using Fraud Check WS
    And I am on "Application Review Batch Loads" Page
    When I search for date ranges from "01/01/2020" to "01/01/2030" and refresh
    And I verify the expected counts for HFP is "1", Suspect is "1", Clean is "2"
    Given I am on "Alert Review" Page
    And I see these applications are on "HFP" tab
    And I see these applications are on "Errors" tab
    And I see these applications are on "Suspect" tab
    And I see these applications are on "Clean" tab
    And I select filter
    And I add "Test1_58783" to "Application Number" filter input
    And I select application with application number
    And I Action the matched application as "Suspicious" from "Alert Review" page
    And I verify the application is actioned as "Suspicious"