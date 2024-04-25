@UI
Feature: Verify all the regression scenarios for Reference Lists feature

  @TSNT-4723 @TESTAUTO-60 @productUnderTest @DELETE_ALL_APPLICATION @productUnderTest
  Scenario Outline:TC-58813 - productUnderTest - Definitions - Company Suffix
    Given I am on "Company Affix" Page
    When I Add the Company "<suffix>"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,Introducer/Agent,User,Credit Bureau" on the Application Record page
    And I update the Company name for all categories with "<testData>"
    Then I verify the Company name for all categories with "<expectedData>"

    Examples:
      |   suffix      |  testData             |   expectedData  |
      |   LG          |  BELGIUM LG           |   BELGIUM       |
      |   P/L         |  HAGENE P/L           |   HAGENE        |
      |   P.L.C       |  SCHUMM P.L.C         |   SCHUMM        |
      |   PVT.LTD.    |  SRL Jacobs. PVT.LTD. |   SRL Jacobs.   |

  @TSNT-4997 @TESTAUTO-268 @DELETE_RULE_SET @DELETE_ALL_APPLICATION
  Scenario:TSNT-4997 - Reference Table : Test See Reference data in Match Review
    Given I transfer file "R01TESTAUTO268.csv" to batch service input directory
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00008"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    Then I Fraud Check the Application
    And  I click on the Value Matches tab and verify Full data if rule contains =
    And I click on Full data button
    Then I verify the Table content

  @TSNT-5546 @TESTAUTO-21 @DELETE_RULE_SET @DELETE_ALL_APPLICATION @productUnderTest
  Scenario:TSNT-5546 - Reference Table : Test See Reference data in Match Review
    Given I transfer file "R01TESTAUTO21.csv" to batch service input directory
    And I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "AB00008"
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I Fraud Check the Application
    Then I verify Value Matches Tab
    And I verify the contents of Full Application Details Tab

