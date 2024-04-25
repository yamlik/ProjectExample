@UI
Feature: Verify all the regression scenarios related to Criteria

# Background:
#   Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4718 @TESTAUTO-73_1 @IMPORT_DAT_FILE  @productUnderTest @DELETE_ALL_APPLICATION @productUnderTest.0 @productUnderTest.2
  Scenario: TC-58854_1 productUnderTest - Definitions - Add a new Criteria and perform search application or criminal record using criteria
    Given I am on "Criteria" Page
    And I navigate to New "Criteria" Page
    And I add details on criteria definition page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application" on the Application Record page
    And I clone the created Application
    And I add details on categories: "Application1" on the Application Record page
    And I am on "Application Search Using Criteria" Page
    And I select from criteria dropdown and search
    Then I verify "Application" details
    And I delete created criteria

  @TSNT-4720 @TESTAUTO-73_2 @IMPORT_DAT_FILE  @productUnderTest @DELETE_ALL_WATCHLIST @productUnderTest.0 @productUnderTest.2
  Scenario: TC-58854_2 productUnderTest - Definitions - Add a new Criteria and perform search application or criminal record using criteria
    Given I am on "Criteria" Page
    And I navigate to New "Criteria" Page
    And I add details on criteria definition page
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application" on the Application Record page
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application1" on the Application Record page
    And I am on "Watchlist Search Using Criteria" Page
    And I select from criteria dropdown and search
    Then I verify "Watchlist" details
    And I delete created criteria

