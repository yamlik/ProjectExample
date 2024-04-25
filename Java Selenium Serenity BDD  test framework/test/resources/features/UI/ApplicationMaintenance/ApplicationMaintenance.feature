@UI
Feature: Verify all the Application Maintenance regression scenarios by creating different applications and triggering required rules

  @TSNT-4701 @TESTAUTO-04 @IMPORT_DAT_FILE @DELETE_ALL_APPLICATION @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario: TC-35637 - Verify the extra fields appear when in Risk Assessment Mode
    Given I am on "System Parameters" Page
    When I turn "ON" the "Risk Assessment Mode" on System Parameters
    And I re-login into the application
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    Then I verify that extra fields appear in New Application page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I verify that extra fields appear in Full Application Details page
    And I am on "System Parameters" Page
    And I turn "OFF" the "Risk Assessment Mode" on System Parameters

  @TSNT-4673 @TESTAUTO-108 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TC-48730 -  productUnderTest - Application - Action an Application
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    When I select "Known Fraud" action from "Application Record" page
    And I select "COLLUSION" as the Decision Reason
    And I select "FRAUD ID/PASSPORT" as the Nature of Fraud
    And I Include this application in the Fraud Savings Report
    And I Include a Savings Amount "95000.90" for this application
    And I Include "Known Fraud" applications on Action page
    And I Include "Suspicious" applications on Action page
    Then I Confirm the Action
    And I verify the application is actioned as "Known Fraud"

  @TSNT-4675 @TESTAUTO-103 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58880_1 -  productUnderTest - Application - Ensure that Match Review display no error on deleted matched Application in Application database
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "M000001"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "M000001"
    When I am on "Application Maintenance" Page
    And I navigate to New "Application-Search" Page
    And I delete Application "2"
    Then I Verify the Match review for Application "1"

  @TSNT-4676 @TESTAUTO-103 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest   @productUnderTest.0 @productUnderTest.2 @IMPORT_DAT_FILE
  Scenario:TC-58880_2 -  productUnderTest - Application - Ensure that Match Review display no error on deleted matched Application in Application database
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AP00009"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    And I verify the Header for "AP00009"
    When I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    And I delete Watchlist
    When I am on "Application Maintenance" Page
    And I navigate to New "Application-Search" Page
    Then I Verify the Match review for Application "2"

  @TSNT-4677 @TESTAUTO-72 @productUnderTest @DELETE_ALL_APPLICATION
  Scenario:TC-58858 - productUnderTest - Application - Application Maintenance - Loading data via excel
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page and upload Excelsheet
    And I upload the file
    Then I will Verify UI values with dataBase Values

  @TSNT-4678 @TESTAUTO-84 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58883 -productUnderTest - Application - Link Analysis Maintenance
    Given I am on "Link Analysis Maintenance" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00004"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the Application Record page
    Then I Fraud Check the Application
    And i click on Link Analysis
    And I compare Results in Link Analysis page

  @productUnderTest @productUnderTest @TSNT-4793
  Scenario:TC-12345 - productUnderTest - FCWS
    Given I submit "2" application request using Fraud Check WS

  @TESTAUTO-65  @productUnderTest @DELETE_ALL_APPLICATION @TSNT-4792
  Scenario:TC-58825 - productUnderTest - Application - Ensure deleted Categories shown in Update Review with its historical values
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,User" on the Application Record page
    When I delete categories: "Applicant,User"
    Then I verify deleted category on notes page
    And I verify deleted category: "Applicant,User" on update review page

  @TESTAUTO-197 @TSNT-4814 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION
  Scenario:TC-51323 - productUnderTest - Application - Ensure that action page works as expected and no error thrown
    Given I am on "System Parameters" Page
    And I turn "ON" the "Risk Assessment Mode" on System Parameters
    When I submit "1" application request using Fraud Check WS
    And I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    Then I Action the application as "Suspicious" from "Application Record" page
    And I verify the application is actioned as "Suspicious"
    And I am on "System Parameters" Page
    And I turn "OFF" the "Risk Assessment Mode" on System Parameters

  @TESTAUTO-202 @TSNT-4817 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario:TC-52212 - productUnderTest - Definition - Ensure that Value Matches tab is enable when rules agaTSNT any value or combination of values is triggered
    Given I create "1" rule using an API
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "DI00001"
    And I submit "1" application request using Fraud Check WS
    When I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I Fraud Check the Application
    And I verify Value Matches Tab

  @TSNT-5220 @TESTAUTO-260 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST
  Scenario:TSNT-5220 - productUnderTest - Print Excel from Match Review Matches Tab Page using Home Address on the Watchlist Rule
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "A000003"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    Then I will Print Results On Excel sheet
    And I will compare results on Excel for Match Review

  @TSNT-5339 @TESTAUTO-234 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario:TC-61468 - productUnderTest - Investigation - No error in Match Review screen when matched Criminal deleted
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    When I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    And I delete Watchlist
    When I am on "Application Maintenance" Page
    And I navigate to New "Application-Search" Page
    Then I Verify no error message appears on MatchReview page

  @TSNT-5353 @TESTAUTO-235 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TC-61470_1 -  productUnderTest - Investigation - Review and Confirm section in Action screen
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    When I select "Known Fraud" action from "Application Record" page
    And I select "COLLUSION" as the Decision Reason
    And I select "FRAUD ID/PASSPORT" as the Nature of Fraud
    And I Include this application in the Fraud Savings Report
    And I Include a Savings Amount "" for this application
    And I Confirm the Action
    Then I Verify no error message appears on Action page

  @TSNT-5354 @TESTAUTO-235 @DELETE_ALL_APPLICATION @DELETE_RULE_SET @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TC-61470_2 -  productUnderTest - Investigation - Review and Confirm section in Action screen
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    When I select "Known Fraud" action from "Application Record" page
    And I select "COLLUSION" as the Decision Reason
    And I select "FRAUD ID/PASSPORT" as the Nature of Fraud
    And I Include this application in the Fraud Savings Report
    And I Include a Savings Amount "100.23" for this application
    And I Confirm the Action
    Then I Verify no error message appears on Action page


  @TESTAUTO-198 @TSNT-5344 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET @DELETE_ALL_WATCHLIST
  Scenario:TC-48729 - productUnderTest - Application - Fraud Check a record - Verify triggered rule code on mouse hover
    Given I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    Then I Fraud Check the Application
    And I verify rule "ID00001" appears on mouse hover with the matched application

  @TESTAUTO-266 @TSNT-5351 @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
  Scenario:TC-61472 - productUnderTest - Application - Verify Income Increase Percentage and Income fields using Web Service
    Given I am on "Field Settings" Page
    And I select "Search Results Field Requirements" as Configuration Purpose
    And I assign "Applicant.Income Increase Percentage" for the above Configuration Purpose
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "I000003"
    And I submit "2" application request using Fraud Check WS
    And I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I Fraud Check the Application
    And I verify the contents of Full Application Details Tab

  @TESTAUTO-277 @DELETE_ALL_APPLICATION @TSNT-5351 @productUnderTest
  Scenario:TESTAUTO-277 - productUnderTest - Batch - Verify Application Able To Load Through Batch Service
    Given I restart batch service
    And I transfer file "BTESTAUTO277.txt" to batch service input directory
    When I am on "Application Maintenance" Page
    Then I search for an application from Application Maintenance Page

  @TESTAUTO-226 @TSNT-5613 @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TESTAUTO-226 - productUnderTest - Application - Verify Batch action and Batch Diary Notes
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1" on the new Application Record page
    When I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I select the application to perform Batch Action
    And I add Dairy Note for the application selected from action page
    And I select "COLLUSION" as the Decision Reason
    And I select "FRAUD ID/PASSPORT" as the Nature of Fraud
    And I Confirm the Action
    Then I verify the "Batch Notes added" in "A_48730" and "A_48731" application notes

  @TESTAUTO-239_1 @TSNT-3306 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION
  Scenario:TC-61476_1 productUnderTest - Investigations- Ensure that cloning application involving multiple applicants will not display any error
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,SecondApplicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    When I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    Then I verify the application record details


