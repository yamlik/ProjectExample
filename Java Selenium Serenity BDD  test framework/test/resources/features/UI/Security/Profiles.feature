@UI
Feature: Verify all the regression scenarios related to Profiles

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TSNT-4659 @TESTAUTO-51&52  @productUnderTest  @DELETE_USERS @DELETE_PROFILE @IMPORT_DAT_FILE
  Scenario:TC-58805 -  Create a New Profile
    Given I am on "Profiles" Page
    And I create new profile with All Approval
    And I am on "Users" Page
    And I create New user "productUnderTest_user58805" with all Approval
    And I re-login into the application with "productUnderTest_user58805"
    Then I am able to add new user when profile has ADD permission aTSNTss only
    And i create NewUser
    Then I Verify the created user "productUnderTest_user58805"


  @TSNT-5478 @TESTAUTO-85 @productUnderTest @productUnderTest @DELETE_USERS @DELETE_PROFILE
  Scenario:TC-58884 - Create a profile having view aTSNTss in Rules and no update aTSNTss to application record
    Given I am on "Profiles" Page
    And I create a Profile "TESTING" having Part ATSNTss to "Investigation" and  "Rules Management"
    And I am on "Users" Page
    And I create "1" users using the above profiles
    And I re-login for the first time into the application as "tester90"
   # And I re-login again into the application as "tester90"
    When I am on "Rules Configuration" Page
    Then I verify the view aTSNTss for the selected rule
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I search for an application from Application Maintenance Page
    And I update the "Decision Reason" and "Nature of Fraud" in applicant category
    Then I verify the user with no update aTSNTss cannot update other application record fields
