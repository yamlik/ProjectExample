@UI
Feature: Verify the add queue widget in dashboard

  @TSNT-3438 @productUnderTest
  Scenario: TSNT-3438 - productUnderTest - Utilities - Dashboard
    Given  I am on "DASHBOARD" Page
    When I add "My Queue" widget
    And I am on "DASHBOARD DISPLAY" Page
    And I select the user Id as "ADMINISTRATOR"and criteria as "All applications"for the user
    And I am on "Field Settings" Page
    And I select "Search Results Field Requirements" as Configuration Purpose
    And I assign "Application.Fraud Alert" for the above Configuration Purpose
    And I assign "Applicant.Nature of Fraud" for the above Configuration Purpose
    And I assign "Application.Action Taken" for the above Configuration Purpose
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I Action the application as "Known Fraud" from "Application Record" page
    And I am on "DASHBOARD" Page
    Then I verify the data in My Queue widget based on selected criteria