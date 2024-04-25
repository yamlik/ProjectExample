@UI
Feature: Verify all the regression scenarios related to Users

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4660 @TESTAUTO-66  @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE
  Scenario:TC-58826 -  productUnderTest - Security -  Add / Modify User
    Given I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user_58826" with all Approval
    And I modified newly created user
    And I am on "Users" Page
    Then I Verify the created user "productUnderTest_user_58826"

  @TSNT-4661 @TESTAUTO-97  @productUnderTest @IMPORT_DAT_FILE @DELETE_USERS @DELETE_PROFILE
  Scenario:TC-58874 -  productUnderTest - Security - Complex Password Enforcement when enable user approval
    Given I am on "System Parameters" Page
    And I turn "ON" the "User Approval" on System Parameters
    And I am on "Profiles" Page
    And I Update the Profile with All Approvals
    And I re-login into the application
    And I am on "Security Parameters" Page
    And I turn "ON" the "Enforce Complex Passwords" on Security Parameters
    And I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user_58874" with all Approval
    And I update comment and Approve the User "productUnderTest_user_58874"
    And I re-login into the application with "productUnderTest_user_58874"
    And I am on "Users" Page
    And I add User "productUnderTest_user1_58874" and Save as Draft
    And I Submit the User "productUnderTest_user1_58874" and Approve
    And I am on "Users" Page
    And I add User "productUnderTest_user2_58874" and Save as Draft
    And I Submit the User "productUnderTest_user2_58874" and Reject

  @TESTAUTO-207 @TSNT-4882 @DELETE_USERS @DELETE_PROFILE @productUnderTest
  Scenario:TC-55859 - productUnderTest - Security - User Module Add/Update Permission Inquiry
    Given I am on "Profiles" Page
    And I create a new profile "Profile1" with Update User only
    And I create a new profile "Profile2" with Add User only
    And I create "2" users using the above profiles
    When I re-login for the first time into the application as "user00001"
    And I am on "Users" Page
    Then I verify that user with "Profile1" can change the user details and cannot add new users
    When I re-login for the first time into the application as "user00002"
    And I am on "Users" Page
    Then I verify that user with "Profile2" cannot change the user details and can add new users

  @TSNT-4842 @TESTAUTO-63 @productUnderTest @DELETE_USERS @DELETE_PROFILE @DELETE_ALL_APPLICATION @IMPORT_DAT_FILE
  Scenario: TC-58818 - productUnderTest - Configuration purpose as Application Type Field Display
    Given I am on Application Maintenance Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the new Application Record page
    And I clone the created Application
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I am on "System Parameters" Page
    And I turn "OFF" the "Profile Approval" on System Parameters
    And I am on "Profiles" Page
    And I create new profile "tester28"
    And I am on "Profiles" Page
    When I Update the Profile Named "tester28" with Restricted Application Types
    Then I create a user using the API
    And I am on "Field Settings" Page
    And I select "Application Type Field Display" as Configuration Purpose
    And I assign "Application.Amount/Limit" for the above Configuration Purpose
    Then I re-login for the first time into the application as "tester28"
    And I am on Application Maintenance Page
    And I search Application by adding search parameter
    Then I look for value "8888" in Application Maintenance Page is not Restricted

  @TSNT-5186 @TESTAUTO-83 @productUnderTest @DELETE_USERS @DELETE_PROFILE @DELETE_ALL_WATCHLIST @IMPORT_DAT_FILE
  Scenario: TC-58882 - Bonito: Watchlist Maintenance: Verify Restricted ATSNTss on displayed Fields
    Given I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application" on the new Application Record page
    And I am on "System Parameters" Page
    And I turn "OFF" the "Profile Approval" on System Parameters
    And I am on "Profiles" Page
    And I create new profile "tester31"
    And I am on "Profiles" Page
    When I Update the Profile Named "tester31" with Restricted Application Types
    Then I create a user using the API
    And I am on "Field Settings" Page
    And I select "Application Type Field Display" as Configuration Purpose
    And I assign "Application.Amount/Limit" for the above Configuration Purpose
    Then I re-login for the first time into the application as "tester31"
    And I am on "Watchlist Maintenance" Page
    And I search Application by adding search parameter
    And I saw value for "Amount/Limit" in Application Record Page is "8888"
    And I saw Field Name "Branch" in Application Record Page is in "rgba(255, 0, 0, 1)" color
    Then I saw Field Name "Decision" in Application Record Page is in "rgba(255, 0, 0, 1)" color
