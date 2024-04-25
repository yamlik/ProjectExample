@UI
Feature: Verify all the regression scenarios related to Rule sets

  @TESTAUTO-205 @TSNT-5116 @productUnderTest @DELETE_RULE_SET @DELETE_ALL_APPLICATION
  Scenario: TC-48722 - productUnderTest - Rules - Create a Rule Set and assign rules
    Given I am on "Rule Sets" Page
    When I add new Rule Sets that includes Rules "DI00002" with "All Applications" criteria
    And I am on "Criteria" Page
    And I navigate to New "Criteria" Page
    And I add details on criteria definition page with "Application Type Is NOT NULL" criteria
    And I am on "Rule Sets" Page
    And I add new Rule Sets that includes Rules "AL00001" with "Application Type Is NOT NULL" criteria
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "DI00002,AL00001" on RuleTriggered page







