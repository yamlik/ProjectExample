@UI
Feature: Verify all the Rule Admin regression scenarios

  @TSNT-4724 @TESTAUTO-53 @AD
  Scenario: TC-58806 productUnderTest - Rules - AD Security - Rule Admin Group
    Given I am on "Security Parameters" Page
    And I turn "OFF" the "productUnderTest Security" on Security Parameters
    And I create a new Rule Admin Group
    And I create "1" rule using an API
    When I am on "Profiles" Page
    And I create new profile "ZENA"
    And I re-login into the application with AD
    Then I am on "Rule Sets" Page
    And I select the Rule Admin Group "58" on "Rule Sets" page
    And I verify the available rules listed
    Then I am on "Organisation Rule Sets" Page
    And I select the Rule Admin Group "58" on "Organisation Rule Sets" page
    And I verify the available rules listed
    And I delete the rule using an API
    And I delete the "1" rule admin group
    And I am on "Security Parameters" Page
    And I turn "ON" the "productUnderTest Security" on Security Parameters

    @TESTAUTO-187 @TSNT-5183 @DELETE_RULE_SET @DELETE_USERS @DELETE_PROFILE @productUnderTest @productUnderTest
    Scenario: TC-48855 productUnderTest - Rules - Rules Admin Group
      Given I am on "Rule Admin Groups" Page
      And I add the following Rule Admin Groups
      |Rule Admin Group|Description|
      |01              |CARD       |
      |02              |PL         |
      And I am on "Profiles" Page
      When I create a Profile "RAdm" with Full ATSNTss and Part ATSNTss to "Rule Admin Groups" and "CARD"
      And I create a Profile "RAdmin" with Full ATSNTss and Part ATSNTss to "Rule Admin Groups" and "PL"
      And I create "2" users using the above profiles
      And I give "Full" aTSNTss to "NONADMIN" profile
      And I create "2" rule using an API
      And I am on "Rule Sets" Page
      And I add new Rule Set that includes Rule "4885501" with Rule Admin Group "CARD"
      And I add new Rule Set that includes Rule "4885502" with Rule Admin Group "PL"
      Then I re-login for the first time into the application as "RAdm0001"
      And I am on "Rules Configuration" Page
      And I verify that the user can view Rule "4885501" for Rule Admin Group "CARD"
      And I re-login for the first time into the application as "RAdmin01"
      And I am on "Rules Configuration" Page
      And I verify that the user can view Rule "4885502" for Rule Admin Group "PL"
      And I re-login again into the application as "NONADMIN"
      And I am on "Rules Configuration" Page
      And I verify that the user can view Rule "4885501" for Rule Admin Group "CARD"
      And I verify that the user can view Rule "4885502" for Rule Admin Group "PL"
      And I delete the "2" rule admin group

    @TESTAUTO-230 @TSNT-5094 @productUnderTest @DELETE_RULE_SET @DELETE_ALL_APPLICATION
    Scenario: TC-61456 productUnderTest - Rules - Rule Score when Multiple Rules in the same Rules Group
      Given I am on "Rule Groups" Page
      And I add the below Rule Group
      | Code | Description|
      | 01   | Application Rules|
      And I am on "Rule Admin Groups" Page
      And I add the following Rule Admin Groups
      |Rule Admin Group|Description|
      |01              |CARD       |
      And I am on "Profiles" Page
      And I give "Full" aTSNTss to "NONADMIN" profile
      When I create "R01" rule "Applicant Id Number 1 Not Null with a Fraud Score of 50 and Rule Group 01"
      And I create "R02" rule "Application Number Not Null with a Fraud Score of 40 and Rule Group 01"
      And I create "R03" rule "Application Type Not Null with a Fraud Score of 70"
      And I am on "Rule Sets" Page
      And I add new Rule Set that includes Rules "R01,R02,R03"
      And I submit "1" application request using Fraud Check WS
      And I am on "Application Maintenance" Page
      And I search for an application from Application Maintenance Page
      And I Fraud Check the Application
      Then I verify the score is "160" on Match Review header
      And I delete the "1" rule admin group

    @productUnderTest
    Scenario: Delete all Rule Admin Groups
      When I am on "Rule Admin Groups" Page
      Then I delete all the records
