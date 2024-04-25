@UI
Feature: Verify all the regression scenarios related to Note Prompt for Application and Watchlist Records

  @TSNT-4636 @TESTAUTO-86 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION @productUnderTest.0
  Scenario:TC-58876_1 - productUnderTest - Application - Ensure that value of parameter will be automatically generated when the user creates Diary Note
    When I am on "Note Prompt" Page
    And I add a new Note Prompt
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new "Application" Record page
    Then I add a new note on "Application Record" page
    And I verify the notes added

  @TSNT-4637 @TESTAUTO-86 @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST @productUnderTest.0
  Scenario:TC-58876_2 - productUnderTest - Watchlist - Ensure that value of parameter will be automatically generated when the user creates Diary Note
    When I am on "Note Prompt" Page
    And I add a new Note Prompt
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the new "Watchlist" Record page
    Then I add a new note on "Watchlist Record" page
    And I verify the notes added

  @TSNT-4767 @TESTAUTO-217 @productUnderTest @productUnderTest @productUnderTest.0
  Scenario:TC-61413 - productUnderTest - Settings - Note prompt
    Given I am on "Note Prompt" Page
    When I add a new Note Prompt
    Then I change the note prompt