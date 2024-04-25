@UI
Feature: Verify all the regression scenarios related to Whitelist

# Background:
#   Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#    And I should see the Dashboard page

  @TESTAUTO-273 @TSNT-5413 @DELETE_ALL_APPLICATION @DELETE_ALL_WATCHLIST @DELETE_RULE_SET @DELETE_USERS @DELETE_PROFILE @productUnderTest
  Scenario:TSNT-5016 - productUnderTest  - productUnderTest - Add to Whitelist - Verify Default Expiry Date for Whitelisting
    Given I am on "System Parameters" Page
    And I turn "ON" the "White List" on System Parameters
    And I am on "Profiles" Page
    When I create new profile "whitelist"
    And I create "1" users using the above profiles
    And I re-login for the first time into the application as "NONADMIN2"
    And  I am on "Rule Sets" Page
    When I add new Rule Set that includes Rule "ID00001"
    And I am on "Watchlist Maintenance" Page
    And I navigate to New "Watchlist" Page
    And I add details on categories: "Application,Applicant" on the Application Record page
    And I am on "Application Maintenance" Page
    And I navigate to New "Application" Page
    And I add details on categories: "Application1,Applicant1" on the new Application Record page
    And I Fraud Check the Application
    And I Action the application as "False Positive" from "Match Review" page for Whitelist
    Then I verify expiry date of application
    And I add application as "False Positive" to Whitelist and navigated to Autocheck result page
    And I am on "White list" Page
    And I verify the White List record
    And I Delete the Whitelist Record






