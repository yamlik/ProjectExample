@UI
Feature: Verify all the Watchlist Maintenance regression scenarios by creating different watchlist and triggering required rules

@TESTAUTO-218 @TSNT-4814 @productUnderTest @productUnderTest @DELETE_ALL_APPLICATION
Scenario:productUnderTest - Watchlist - Modify record in Watchlist Database
  And I am on "Watchlist Maintenance" Page
  And I navigate to New "Watchlist" Page
  And I add details on categories: "Application,Applicant" on the Watchlist Record page
  And I Approve the Watchlist
  And I am on "Watchlist Maintenance" Page
  And I navigate to New "Watchlist-Search" Page
  Then I Verify the created watchlist
  When I add details on categories: "Applicant" on the Application Record page
  And I update the Watchlist




