@UI
Feature: Initial set up for the import

# Background:
#   Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key
#   And I should see the Dashboard page

  @TSNT-4715 @TESTAUTO-27 @productUnderTest.0
  Scenario: Import the dat file into the system to configure the data
    Given I should navigate to the system import page
    When I upload the required file
    And I import the attached file into the system
    Then I should be able to verify that the import is suTSNTssful
