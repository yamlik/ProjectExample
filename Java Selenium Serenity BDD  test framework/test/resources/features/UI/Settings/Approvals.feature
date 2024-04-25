@UI
Feature: Verify all the regression scenarios related to Approvals

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4662 @TESTAUTO-54  @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE @productUnderTest.0
  Scenario:TC-58807 - productUnderTest - Settings - User Approval
    Given  I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Profiles" Page
    And I create profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user_58807" with all Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user1_58807" with Others Approval
    And I am on "System Parameters" Page
    And I turn "ON" the "User Approval" on System Parameters
    And I am on "profiles" Page
    And  I  update  profileOne settings all approvals to enable Self Approval & Others Approval
    And I am on "Profiles" Page
    And  I  update  profileTwo settings to enable Others Approval
    And I re-login into the application with "productUnderTest_user1_58807"
    And I am on "Users" Page
    And I create New user with ExistingProfileOne
    And I View newly created user from pendingUser
    And I am on "Users" Page
    And I re-login into the application with "productUnderTest_user_58807"
    And I am on "Users" Page
    And I create New user with ExistingProfileTwo
    And I Approve newly created user from pendingUser

  @TSNT-4663 @TESTAUTO-55  @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE @productUnderTest.0
  Scenario:TC-58807 - productUnderTest - Settings - Profile Approval
    Given  I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Profiles" Page
    And iCreateProfileWithOthersApproval
    And I am on "Users" Page
    And I create New user "productUnderTest_user_58808" with all Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user1_58808" with Others Approval
    And I am on "System Parameters" Page
    And I turn "ON" the "Profile Approval" on System Parameters
    And I re-login into the application with "productUnderTest_user1_58808"
    And I am on "profiles" Page
    And I create NewProfile "Profile123"
    And I am on "Profiles" Page
    And I View newlyCreated profile from pendingUser
    And I re-login into the application with "productUnderTest_user_58808"
    And I am on "profiles" Page
    And I create NewProfile "Profile1234"
    And I am on "Profiles" Page
    And I approve Pending profiles
    And I am on "System Parameters" Page
    And I turn "OFF" the "Profile Approval" on System Parameters

  @TSNT-4672 @TESTAUTO-77 @IMPORT_DAT_FILE @DELETE_ALL_WATCHLIST @DELETE_USERS @DELETE_PROFILE @productUnderTest @productUnderTest.0 @productUnderTest.2
  Scenario:TC-58859 -  productUnderTest - Settings - Watchlist Approval
    Given I am on "System Parameters" Page
    And I turn "ON" the "Watchlist Approval" on System Parameters
    And I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Profiles" Page
    And I create new profile with Others Approval on Watchlist
    And I am on "Users" Page
    And I create New user "productUnderTest_user_58859" with all Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user1_58859" with Others Approval
    And I re-login into the application with "productUnderTest_user_58859"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    When I add details on categories: "Application" on the Application Record page
    And I Approve the Watchlist
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    Then I Verify the created watchlist
    When I add details on categories: "Applicant" on the Application Record page
    And I update the Watchlist
    And I re-login into the application with "productUnderTest_user1_58859"
    And I am on "Watchlist Maintenance" Page
    And I approve the watchlist update
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    Then I Verify the created watchlist
    And I am on "System Parameters" Page
    And I turn "OFF" the "Watchlist Approval" on System Parameters

  @TSNT-4674 @TESTAUTO-100 @DELETE_ALL_WATCHLIST @productUnderTest
  Scenario: TC-58877 - productUnderTest - Settings - Add new category in existing Watchlist (Watchlist approval enable)
    Given I am on "Watchlist Maintenance" Page
    When I navigate to New "Watchlist" Page
    And I add details on categories: "Application" on the new Application Record page
    And I am on "System Parameters" Page
    And I turn "ON" the "Watchlist Approval" on System Parameters
    And I am on "Profiles" Page
    And I Update the Watchlist Profile with All Approvals
    And I re-login into the application
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    Then I Verify the created watchlist
    And I add details on categories: "Applicant" on the Application Record page
    And I update the Watchlist
    And I am on "Watchlist Maintenance" Page
    And I approve the updated watchlist
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist-Search" Page
    And I Verify the created watchlist
    And I am on "System Parameters" Page
    And I turn "OFF" the "Watchlist Approval" on System Parameters