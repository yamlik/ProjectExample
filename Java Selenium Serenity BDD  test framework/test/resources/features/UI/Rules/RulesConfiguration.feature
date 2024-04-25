@UI
Feature: Verify all the regression scenarios for creation of rules and creating different applications and triggering required rules

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4725 @TESTAUTO-71 @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58853-1 - Rule:Fuzzy Match Rule
    Given I am on "Rules Configuration" Page
    When I add rule details
    And I dont save the rule
    Then I verify error window

  @TSNT-4726 @TESTAUTO-71 @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58853-2 - Rule:Fuzzy Match Rule
    Given I am on "Rules Configuration" Page
    When I add rule details
    Then I verify maximum value of similarity

  @TSNT-4727 @TESTAUTO-71 @DELETE_ALL_APPLICATION @DELETE_RULE @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58853-3 - Rule:Fuzzy Match Rule
    Given I am on "Rules Configuration" Page
    When I add rule details
    And save dataBaseField rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "Fuzy001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "Fuzy001"
    And  I verify all content for rules "Fuzy001" on RuleTriggered page

  @TSNT-4728 @TESTAUTO-39 @DELETE_RULE_SET @productUnderTest  @productUnderTest.0 @productUnderTest.2
  Scenario:TC-52642 - Rules Definition - Rule Performance Result - Test a rule
    Given I am on "Rules Configuration" Page
    And I Filter the rule "AB00004"
    And  I will verify  Rule Performance Test
    And I verify the rule  "AB00004" on Rule Performance page

  @TSNT-4729 @TESTAUTO-40 @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-52643 - Bonito - Rules Definition - Rule Performance Result - Execute a rule
    Given I am on "Rules Configuration" Page
    And I Filter the rule "AB00004"
    And  I will verify   Rule Performance Result - Execute a rule
    And I verify the rule  "AB00004" on Rule Performance page

  @TSNT-4730 @TESTAUTO-96   @productUnderTest @DELETE_ALL_APPLICATION @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58873-1 - Rule:Rule with Database value and Partial configuration will cause Internal Server Error
    Given I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "Par0001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "PAR0001"
    And I verify Application value under RuleTab for "PAR0001"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4731 @TESTAUTO-96   @productUnderTest  @DELETE_RULE_SET @DELETE_ALL_APPLICATION  @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58873-2 - Rule:Rule with Database value and Partial configuration will cause Internal Server Error
    Given I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "PAR0002"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "PAR0002"
    And I verify rule content for "PAR0002"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Detail Tab

  @TSNT-4732 @TESTAUTO-96 @productUnderTest @IMPORT_DAT_FILE  @DELETE_RULE_SET  @DELETE_ALL_APPLICATION  @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58873-3 - Rule:Rule with Database value and Partial configuration will cause Internal Server Error
    Given I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "PAR0003"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
#    Then I Fraud Check the Application and Verify Saved SuTSNTssfully
    And I verify the Header for "PAR0003"
    And I verify rule content for "PAR0003"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4733 @TESTAUTO-43  @productUnderTest @DELETE_RULE_SET @DELETE_ALL_APPLICATION @DELETE_ALL_WATCHLIST
  Scenario:TC-52544 - Parallel Rules: When Rule Set Hierarchy is Turned ON
    Given I am on "System Parameters" Page
    When I turn "ON" the "Rule Set Hierarchy" on System Parameters
    And I re-login into the application
    Given I am on "Rule Sets" Page
    When I add new Rule Sets that includes Rules "AL00001,ID00001"
    Given I am on "Rule Sets" Page
    When I add new Rule Sets that includes Rules "AP00009,ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "ID00001,AL00001" on RuleTriggered page
    And I verify the Header for "ID00001"
    And I verify Application value under RuleTab for "ID00001"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab
    Given I am on "System Parameters" Page
    When I turn "OFF" the "Rule Set Hierarchy" on System Parameters

  @TSNT-4646 @TESTAUTO-44  @productUnderTest @IMPORT_DAT_FILE @DELETE_RULE_SET @DELETE_ALL_APPLICATION @DELETE_ALL_WATCHLIST
  Scenario:TC-52545 - Parallel Rules: When Rule Set Hierarchy is Turned OFF
    Given I am on "Rule Sets" Page
    When I add new Rule Sets that includes Rules "AL00001,ID00001"
    Given I am on "Rule Sets" Page
    When I add new Rule Sets that includes Rules "AP00009,ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "ID00001,AL00001,AP00009" on RuleTriggered page
    And I verify the Header for "ID00001"
    And I verify Application value under RuleTab for "AP00009,ID00001"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4647 @TESTAUTO-101 @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE
  Scenario:TC-58878 - productUnderTest - Rules - Verify Authorize aTSNTss when Rules Approval mode is on
    Given I am on "System Parameters" Page
    And I turn "ON" the "Rules Approval" on System Parameters
    And I am on "Profiles" Page
    And I create new profile with Self Approval on Rules Management
    And I am on "Profiles" Page
    And I create new profile with Others Approval on Rules Management
    And I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user1_58878" with Self Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user2_58878" with Others Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user3_58878" with all Approval
    And I re-login into the application with "productUnderTest_user1_58878"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule1"
    And I add Comment and Submit
    And I re-login into the application with "productUnderTest_user2_58878"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule2"
    And I add Comment and Submit
    And I re-login into the application with "productUnderTest_user3_58878"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule3"
    And I add Comment and Submit
    When I login again into the application with "productUnderTest_user1_58878"
    And I am on "Rules Configuration" Page
    Then I Verify the Self Approval aTSNTss
    When I login again into the application with "productUnderTest_user2_58878"
    And I am on "Rules Configuration" Page
    Then I Verify the Other Approval aTSNTss
    When I login again into the application with "productUnderTest_user3_58878"
    And I am on "Rules Configuration" Page
    Then I Verify the All Approval aTSNTss
    And I Approve all the pending Rules

  @TSNT-4648 @TESTAUTO-98 @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE
  Scenario:TC-58875 - productUnderTest - Rules - No Authorise aTSNTss for Rules Approval
    Given I am on "System Parameters" Page
    And I turn "ON" the "Rules Approval" on System Parameters
    And I am on "Profiles" Page
    And I create new profile with Self Approval on Rules Management
    And I am on "Profiles" Page
    And I create new profile with Others Approval on Rules Management
    And I am on "Profiles" Page
    And I create new profile with No Self and Other Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user1_58875" with Self Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user2_58875" with Others Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user3_58875" with No Self and Other Approval
    And I re-login into the application with "productUnderTest_user1_58875"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule1"
    And I add Comment and Submit
    And I re-login into the application with "productUnderTest_user2_58875"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule2"
    And I add Comment and Submit
    And I re-login into the application with "productUnderTest_user3_58875"
    And I am on "Rules Configuration" Page
    And I add a Rule "Rule3"
    And I add Comment and Submit
    When I login again into the application with "productUnderTest_user1_58875"
    And I am on "Rules Configuration" Page
    Then I Verify the Self Approval aTSNTss
    When I login again into the application with "productUnderTest_user2_58875"
    And I am on "Rules Configuration" Page
    Then I Verify the Other Approval aTSNTss
    When I login again into the application with "productUnderTest_user3_58875"
    And I am on "Rules Configuration" Page
    Then I Verify the No Approval aTSNTss
    And I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user4_58875" with all Approval
    And I re-login into the application with "productUnderTest_user4_58875"
    And I am on "Rules Configuration" Page
    And I Approve all the pending Rules
    And I am on "System Parameters" Page
    And I turn "OFF" the "Rules Approval" on System Parameters

  @TSNT-4649 @TESTAUTO-23 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest
  Scenario:TC-54401 - Match review regression- Other algorithm for Index fuzzy mapping
    Given I am running the batchJob
    And I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "IF00001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "IF00001"
    And I verify the contents of Full Application Details Tab

  @TSNT-4 @TESTAUTO-36 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest
  Scenario:TC-52562 - productUnderTest  - Rules Configuration - Database Field
    Given I am running the batchJob
    And I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "IF00002"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "IF00002"
    And I verify the contents of Full Application Details Tab

  @TSNT-4651 @TESTAUTO-37 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest
  Scenario:TC-58153 - productUnderTest  - Rules Configuration - Database Value
    Given I am running the batchJob
    And I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "IF00003"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,SecondApplicant" on the new Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1,SecondApplicant" on the new Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "IF00003"
    And I verify the contents of Full Application Details Tab

  @TSNT-4652 @TESTAUTO-38 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest
  Scenario:TC-58154 - productUnderTest  - Rules Configuration - Application Value
    Given I am running the batchJob
    And I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "IF00004"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,SecondApplicant" on the new Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1,SecondApplicant" on the new Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "IF00004"
    And I verify the contents of Full Application Details Tab


  @TSNT-4653 @TESTAUTO-76   @productUnderTest @IMPORT_DAT_FILE
  Scenario:TC-58857 - productUnderTest - Rules - Reference Maintenance
    Given I am on "Reference Maintenance" Page
    When I add a new table "TableDetails"
    And I add a filed on the new table with data "DataForZIP"
    And I add a filed on the new table with data "DataForCal"
    Then I verify the Table content of "DataForCal"
    And I add a filed on the new table with data "DataForXml"
    And I save the reference Table
    And I am on "System Export" Page
    And Export the System file
    And I am on "Reference Maintenance" Page
    And I delete the created table
    And I am on "System Import" Page
    And I import the above exported file
    And I am on "Reference Maintenance" Page
    And I am on the newly created table
    Then I verify the Table content of "DataForCal"

  @TSNT-4654 @TESTAUTO-78 @DELETE_ALL_APPLICATION  @DELETE_RULE @IMPORT_DAT_FILE @productUnderTest  @productUnderTest
  Scenario:TC-58860 -  Rules - Rules Configuration - Ensure that the rules execution criteria displayed correctly in productUnderTest and Excel file
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "P000003"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And  I am on "Rules Configuration" Page
    And I Filter the rule "P000003"
    And  I will verify   Rule Performance Result - Execute a rule
    And i will download Rule Performance Results in Excel sheet
    Then I compare UI results with downloaded Excel sheet results

  @TSNT-4655 @TESTAUTO-112  @TESTAUTO-112  @DELETE_ALL_APPLICATION @DELETE_RULE @IMPORT_DAT_FILE @productUnderTest  @productUnderTest
  Scenario:TC-58860-2 -  Rules - Rules Configuration - Ensure that the rules execution criteria displayed correctly in productUnderTest and Excel file
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "P000002"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And  I am on "Rules Configuration" Page
    And I Filter the rule "P000002"
    And  I will verify   Rule Performance Result - Execute a rule
    And i will download Rule Performance Results in Excel sheet
    Then I compare UI results with downloaded Excel sheet results

  @TSNT-4656 @TESTAUTO-112  @DELETE_ALL_APPLICATION  @DELETE_RULE   @productUnderTest  @productUnderTest
  Scenario:TC-58860-3 -  Rules - Rules Configuration - Ensure that the rules execution criteria displayed correctly in productUnderTest and Excel file
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "P000001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    And  I am on "Rules Configuration" Page
    And I Filter the rule "P000001"
    And  I will verify   Rule Performance Result - Execute a rule
    And i will download Rule Performance Results in Excel sheet
    Then I compare UI results with downloaded Excel sheet results

  @TSNT-4657 @TESTAUTO-49  @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest  @productUnderTest @productUnderTest.0
  Scenario:TC-58788 -productUnderTest - Information - Action Taken and Fraud Alert in List Report Results
    Given I am on "Settings" Page
    And I added labels in action settings
    And  I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "AL00001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    Then I Action the match application as "Known Fraud" from "Application Record" page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Action the match application as "Suspicious" from "Application Record" page
    And  I am on "Reports" Page
    And I Export Available Fields
    And I verify Action Taken labels and UI labels from "ActionSettings"

  @TSNT-4658 @TESTAUTO-92 @DELETE_ALL_APPLICATION @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58895 -productUnderTest : Parallel Rules: When Organisation Rule Sets are used
    Given I am on "System Parameters" Page
    And I entered "99" in Rules Run In Parallel Textbox
    And I am on "Organisation Rule Sets" Page
    And I add new Rule Sets that includes Rules "ID00001,AL00001" to Organisation Rule Set Definition
    And I am on "Watchlist Maintenance" Page
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "ID00001,AL00001" on RuleTriggered page

  @TESTAUTO-250 @productUnderTest @TSNT-5182 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_RULE
  Scenario:TSNT-5182 -productUnderTest : Match Review : Verify the rules triggered for the two categories in an application
    Given I am on "Field Requirements" Page
    And I select security category for the application
    Given I am on "Rules Configuration" Page
    When I add rule details
    And save the rule
    And I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "IF00005"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Security" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Security1" on the Application Record page
    And I add another security on the application record page having "data" and "data1"
    And I Fraud Check the Application
    Then I verify all content for rules "IF00005" on RuleTriggered page





