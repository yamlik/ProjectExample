@UI
Feature: Verify all the regression scenarios related to Result Table Maintenance

# Background:
#   Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TESTAUTO-271 @TSNT-5032 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario:TSNT-5032 - productUnderTest - Test See Results data in Match Review
  Given I create "1" ResultTable using an API
  And I create "5" ResultTableFields using an API
  And I create "1" Workflow using an API
  And I create "1" rule with Result Table using an API having DatabaseValue
  And I am on "Rule Sets" Page
  When I add new Rule Set that includes Rule "Rt0001"
  And I am on "System Parameters" Page
  And I select Workflow Settings tab on System Parameters page
  And I type "automation_resulttable " as ClientManualWorkflow on SystemParamters page
  And I am on "Application Maintenance" Page
  And I navigate to New "Application" Page
  And I add details on categories: "Application,Applicant" on the Application Record page
  And I Fraud Check the Application
  Then I verify Value Matches Tab
  And I click on the Value Matches tab and verify Full data if rule contains =
  And I click on Full data button for result table
  Then I verify the Table content
  And I close the ResultTableReview window
  And I am on "System Parameters" Page
  And I select Workflow Settings tab on System Parameters page
  And I type "FULL WORKFLOW" as ClientManualWorkflow on SystemParamters page

  @TESTAUTO-274 @TSNT-5527 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario:TSNT-5527 - productUnderTest - Test See Results data in Match Review using Application Value rule
   Given I create "1" ResultTable using an API
    And I create "5" ResultTableFields using an API
    And I create "1" Workflow using an API
    And I create "1" rule with Result Table using an API having applicationValue
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "RT0002"
    And I am on "System Parameters" Page
    And I select Workflow Settings tab on System Parameters page
    And I type "automation_resulttable1 " as ClientManualWorkflow on SystemParamters page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I Fraud Check the Application
    Then I verify Value Matches Tab
    And I click on the Value Matches tab and verify Full data if rule contains =
    And I click on Full data button for result table
    Then I verify the Table content
    And I close the ResultTableReview window
    And I am on "System Parameters" Page
    And I select Workflow Settings tab on System Parameters page
    And I type "FULL WORKFLOW" as ClientManualWorkflow on SystemParamters page

