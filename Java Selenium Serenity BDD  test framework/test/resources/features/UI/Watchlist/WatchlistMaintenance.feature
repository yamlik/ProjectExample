@UI
Feature: Verify all the Watchlist Maintenance regression scenarios by creating different WatchList records


  @TESTAUTO-211 @TSNT-5117 @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TC-48723 -  productUnderTest - Watchlist - Add record to Criminal Database
    Given I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Watchlist Record page

   @TESTAUTO-218 @TSNT-5081 @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
   Scenario:TC-61416 - productUnderTest - Watchlist - Modify record in Watchlist Database
     Given I am on "Watchlist Maintenance" Page
     And I navigate to New "Watchlist" Page
     And I add details on categories: "Application,Applicant" on the Watchlist Record page
     And I am on "Watchlist Maintenance" Page
     And I search for a Watchlist from Watchlist Maintenance Page
     And I modify details on Watchlist Record Page
     |Field|Value|
     |Amount/Limit|5000|
     |Savings Amount|1000|
     Then I verify the watchlist record is updated correctly

  @TESTAUTO-239_2 @TSNT-5106 @productUnderTest @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TC-61476_2 productUnderTest - Investigations- Ensure that cloning application involving multiple applicants will not display any error
    Given I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant,SecondApplicant" on the Watchlist Record page
    And I am on "Watchlist Maintenance" Page
    And I search for a Watchlist from Watchlist Maintenance Page
    And I clone current Watchlist Record
    And I add details on categories: "Application1" on the Watchlist Record page
    When I am on "Watchlist Maintenance" Page
    And I search for "searchWatchlist2" from Watchlist Maintenance Page

  @TSNT-6198 @productUnderTest @DELETE_ALL_WATCHLIST
  Scenario:TSNT-6198 - Create of watchlist record with Application.Organisation and Application.Application Date should be allowed
    Given I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application" on the Watchlist Record page
    Given I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application" on the Watchlist Record page
    Given I am on "Watchlist Maintenance" Page
    And I search for a Watchlist from Watchlist Maintenance Page
    Then I verify the search result of watchlist maintenance display total of "2" records with pattern "NEGATIVE"
