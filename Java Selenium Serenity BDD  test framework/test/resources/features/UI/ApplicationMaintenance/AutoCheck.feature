@UI
Feature: Verify all the regression scenarios of results from Auto Check activity and take action

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4682 @TESTAUTO-45 @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest
  Scenario: TC-58738 - Auto Check Results: Show Selected Rule Prompt function
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And I Auto Check the application from "Application Record" page and verify results
    Then I verify the Show Selected Rule Prompt function on "Auto Check Results" page

  @TSNT-4683 @TESTAUTO-45 @DELETE_ALL_APPLICATION  @productUnderTest @productUnderTest
  Scenario: TC-58738 - Auto Check Results: Action a transaction as Under Investigation
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And I Auto Check the application from "Application Record" page and verify results
    Then I Action the matched application as "Under Investigation" from "Auto Check Results" page
    And I verify the application is actioned as "Under Investigation"

  @TSNT-4684 @TESTAUTO-46 @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario: TC-58739 - Auto Check Results: Action a transaction as Suspicious
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And I Auto Check the application from "Application Record" page and verify results
    Then I Action the matched application as "Suspicious" from "Auto Check Results" page
    And I verify the application is actioned as "Suspicious"


  @TESTAUTO-47 @TSNT-5175 @DELETE_ALL_APPLICATION  @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario: TC-58740 - Auto Check Results: Action a transaction as Known Fraud
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And I Auto Check the application from "Application Record" page and verify results
    Then I Action the matched application as "Known Fraud" from "Auto Check Results" page
    And I verify the application is actioned as "Known Fraud"


  @TESTAUTO-48 @TSNT-5176 @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest
  Scenario: TC-58741 - Auto Check Results: Action a transaction as False Positive
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And I Auto Check the application from "Application Record" page and verify results
    Then I Action the matched application as "False Positive" from "Auto Check Results" page
    And I verify the application is actioned as "False Positive"
#    @DELETE_ALL_APPLICATION @DELETE_RULE_SET

  @TESTAUTO-59 @TSNT-5177 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST
  Scenario: TC-58812 - productUnderTest - Application - Know Fraud check results
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "AL00001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application2,Applicant2" on the new Application Record page
    And I Fraud Check the Application
    And I Auto Check the application from "Application Record" page and verify results
    And I Action the matched application as "Known Fraud" from "Auto Check Results" page
    Then I verify by selecting "Single" record
    And I verify by selecting "Multiple" record

  @TESTAUTO-233 @TSNT-5000 @productUnderTest @DELETE_ALL_APPLICATION
  Scenario: TC-61467 - productUnderTest - Auto Check Result - Ensure that Auto Check Result with Multiple page will return more than 10 records if available
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I submit "52" application request using Fraud Check WS
    When I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I Auto Check the application from "Application Record" page without verifying results
    Then I navigate through the pages