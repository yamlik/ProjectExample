@UI
Feature: Verify regression scenarios for Application Search functionalities and being able to take actions

  @TSNT-4681 @TESTAUTO-64 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
    Scenario: TC-58824 - Application Search Using Criteria: Reassign Multiple applications
    Given I create a user using the API
    When I am on "Organisation Chart" Page
    And I add a Team "Automation"
    And I assign "U58824001" users to the Team with 10 Weight and Out Of Office Turned "OFF"
    And I assign "NONADMIN" users to the Team with 20 Weight and Out Of Office Turned "OFF"
    And I give "Same Team" ATSNTss to new Team with Action "OFF" and Reassign "ON"
    And I am on "Organisation Chart" Page
    And I add the child team "Automation" to the parent team " (Australia) Pty Ltd" in the Organisation Chart
    Then I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application2,Applicant1" on the Application Record page
    And I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I reassign "2" applications from Application Results Page to "Automation - U58824001" team
    And I verify the 2 applications have been reassigned
    And I Clear the Organisation Chart
    And I Delete the Team "Automation"
    And I delete the User "U58824001" by API

  @TESTAUTO-189 @TSNT-5172 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION @DELETE_RULE_SET
     Scenario: TC-48726 - Add record to Application Database
     Given I am on Application Maintenance Page
     And I navigate to New "Application" Page
     And I add details on categories: "Application,Applicant" on the new Application Record page
     And I verify all footer button exist
     And I verify all info in application valid

  @TSNT-4680 @TESTAUTO-91 @DELETE_RULE_SET @DELETE_ALL_APPLICATION @DELETE_ALL_WATCHLIST @productUnderTest @productUnderTest.0 @productUnderTest.0
  Scenario:TC-58890 -  productUnderTest - Investigation/Watchlist - Online search selecting
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    When I am on "Application Maintenance" Page
    And I search Application by adding search parameter
    And I select multiple applications and view one
    Then I Verify the navigation to Application Record screen
    And I review and verify the navigation to Match Review screen
    When I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application2" on the Application Record page
    When I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application3" on the Application Record page
    And I am on "Watchlist Maintenance" Page
    And I search Application by adding search parameter
    And I select multiple applications and view one
    Then I Verify the navigation to Watchlist Record screen

  @TESTAUTO-203 @TSNT-4819 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION
  Scenario:TC-51297 - productUnderTest - Investigation- Verify user able to fetch the application data by using the "=" operator
      Given I submit "1" application request using Fraud Check WS
      When I am on "Application Maintenance" Page
      And I search for an application from Application Maintenance Page
      Then I verify the application record details

  @TESTAUTO-239_1 @TSNT-3306 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION
  Scenario:TC-61476_1 productUnderTest - Investigations- Ensure that cloning application involving multiple applicants will not display any error
    Given I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant,SecondApplicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    And I search for an application from Application Maintenance Page
    Then I verify the application record details
