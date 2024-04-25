@UI
Feature: Verify all the Match review regression scenarios by creating different applications and triggering required rules

  @TSNT-4697 @TESTAUTO-1 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.1 @productUnderTest.2
  Scenario: TC-35189 Bonito: Match Review: View all rules triggered
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rules "AL00001,ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "AL00001,ID00001" on RuleTriggered page

  @TSNT-4698 @TESTAUTO-1 @IMPORT_DAT_FILE @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST  @productUnderTest.0
  Scenario: TC-35189_1 Bonito: Match Review: View all rules triggered
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rules "AL00001,ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify all content for rules "AL00001,ID00001" on RuleTriggered page

  @TSNT-4699 @TESTAUTO-2 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @DELETE_ALL_WATCHLIST @productUnderTest.0 @productUnderTest.2
  Scenario:TC-35190 - Match Review: View Notes
    Given I am on "System Parameters" Page
    And I turn "OFF" the "Risk Assessment Mode" on System Parameters
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    And I Verify note page slides on notes page
    And I Verify note contents on matches tab

  @TSNT-4700 @TESTAUTO-3  @productUnderTest @DELETE_RULE_SET @DELETE_ALL_APPLICATION @productUnderTest.0 @productUnderTest.2
  Scenario:TC-35634 - productUnderTest 6: Match Review: Display Mode
    Given I am on "Application Maintenance" Page
    When I navigate to New "Application" Page
    And I add details on categories: "Application" on the new Application Record page
    And I Review the Application
    Then I verify the Header of "Fraud Score"
    And I am on "System Parameters" Page
    And I change Display Mode to "Watchlist Monitoring"
    And I re-login into the application
    And I am on "Customer Maintenance" Page
    And I Search and Review the Application
    Then I verify the Header of "Rules Score"
    And I am on "System Parameters" Page
    And I change Display Mode to "Fraud"

  @TSNT-4702 @TESTAUTO-5 @IMPORT_DAT_FILE @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54383_1 Match review regression- Exact Match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "A000003"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify the Header for "A000003"
    And I verify Application value under RuleTab for "A000003"
    And I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4703 @TESTAUTO-6 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-54384 - Match review regression- Near match including full address match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00004"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "AB00004"
    And I verify Application value under RuleTab for "AB00004"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4704 @TESTAUTO-7 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54385 Match review regression-‘Sounds like’ match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AS00005"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify the Header for "AS00005"
    And I verify Application value under RuleTab for Sounds like match "AS00005"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4705 @TESTAUTO-8 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54386 Match review regression- Mandatory/optional match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AS00006"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "AS00006"
    And I verify Application value under RuleTab for "AS00006"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4706 @TESTAUTO-9  @DELETE_RULE_SET  @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TC-54387 - Match review regression- Field cross-check match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "A000017"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,CBC" on the new Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1" on the Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "A000017"
    And I verify Application value under RuleTab for "A000017"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of "Full Application Details" and "CBC" Tab

  @TSNT-4707 @TESTAUTO-10 @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-54388 -  Match review regression-Fuzzy Match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AF00018"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "AF00018"
    And I verify Application value under RuleTab for "AF00018"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4708 @TESTAUTO-11 @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-54389 -  Match review regression- Fuzzy Match Swap
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AF00019"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "AF00019"
    And I verify Application value under RuleTab for "AF00019"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4709 @TESTAUTO-12 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54390 Match review regression-Like match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AL00001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    Then I add details on categories: "Application,Applicant" on the Application Record page
    And I Fraud Check the Application
    And I verify the Header for "AL00001"
    And I verify the Matches Tab
    And I verify the contents of ValueMatches Tab for Like match
    And I verify the contents of Full Application Details Tab

  @TSNT-4710 @TESTAUTO-13 @DELETE_RULE_SET @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TC-54391 -  Match review regression- Value only match (application and database)
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "DI00002"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "DI00002"
    And I verify the Matches Tab
    And I verify the contents of ValueMatches Tab for Value only match
    And I verify the contents of Full Application Details Tab

  @TSNT-4711 @TESTAUTO-14 @DELETE_RULE_SET @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TC-54392 -  Match review regression-Variable match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "U000004"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Credit Bureau" on the Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "U000004"
    And I verify the Matches Tab
    And I verify the contents of ValueMatches Tab for Variable match
    And I verify the contents of Full Application Details Tab

  @TSNT-4712 @TESTAUTO-15 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0
  Scenario:TC-54393 - Match review regression- Rule logic
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "RL00001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "RL00001"
    And I verify Application value under RuleTab
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4713 @TESTAUTO-16 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54394 Match review regression- Application to itself match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "ATI0001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "ATI0001"
    And I verify the Matches Tab
    And I verify the contents of ValueMatches Tab for Application to itself match
    And I verify the contents of Full Application Details Tab

  @TSNT-4714 @TESTAUTO-17 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario: TC-54395 Match review regression - Verify Increase Income Percentage Rule
    Given I create an application using api request with income "100" and application number "543951"
    And I create an application using api request with income "200" and application number "543952"
    When I am on "Rule Sets" Page
    And I add new Rule Set that includes Rule "I000003"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application-Search" Page
    And I search with Application Number "543952"
    Then I Fraud Check the Application
    And I verify the Header for "I000003"
    And I verify Application value under RuleTab for "I000003"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4667 @TESTAUTO-18 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54396 Match review regression- Company Name match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AP00009"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I verify the Header for "AP00009"
    And I verify Application value under RuleTab for "AP00009"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4668 @TESTAUTO-19 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-54397 - Match review regression-Other - Number of Applications Match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "M000001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "M000001"
    And I verify Application value under RuleTab
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4669 @TESTAUTO-20 @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION  @OutOfScope
  Scenario:TC-54398 -Match review regression-Reference Table Match –to not build in reference tables
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "L000001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "L000001"
    And  I click on the Value Matches tab and verify contents of ValueMatchesTab
    And I verify the contents of Full Application Details Tab

  @TSNT-4670 @TESTAUTO-22 @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TC-54400 -  Match review regression-Abbreviation Match
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "A000033"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "A000033"
    And I verify Application value under RuleTab for "A000033"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab

  @TSNT-4671 @TESTAUTO-70 @IMPORT_DAT_FILE @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-54383_2 Match review regression- Exact Match with multiple records
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00003"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    Then I add details on categories: "Application,Applicant,SecondApplicant" on the new Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    Then I add details on categories: "Application1,Applicant1,SecondApplicant" on the new Application Record page
    And I Fraud Check the Application
    Then I verify the Header for "AB00003"
    And I verify Application value under RuleTab for "AB00003"
    And I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab with multiple records


  @TSNT-4679 @TESTAUTO-21 @DELETE_RULE_SET @DELETE_ALL_APPLICATION  @OutOfScope
  Scenario:TC-54399 -  Match review regression-Reference Table match - Variable rule - to build in reference tables
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00008"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    Then I Fraud Check the Application
    Then I verify the Header for "AB00008"
    And I verify Application value under RuleTab for "AB00008"
    And  I click on the Matches tab and verify contents of System tab
    And I verify the contents of Full Application Details Tab
