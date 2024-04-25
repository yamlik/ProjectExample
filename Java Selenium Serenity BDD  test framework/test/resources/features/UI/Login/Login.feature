@SETUP_UI
Feature: This feature will test the login functionality into productUnderTest application

#  Background:
#    Given I login to the portal
#    Then I close the productUnderTestRegistration PopupWindow without supplying a Licence Key

@TSNT-4722 @POC @productUnderTest.0
Scenario:  Login to productUnderTest for the first time, I do not wish to take a tour
  When I see the "Welcome" popup
  Then I choose "No thanks" on the What's New Popup