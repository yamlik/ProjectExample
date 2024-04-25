@API
Feature: REST productUnderTest Service Test

 # Read data from data follow the test case ID
 # Delete application after test
 # Combine 3 JSON file to 1 file follow the test case ID
 # Study to made POST GET PUT into general keyword


  @TESTAUTO-304 @TSNT-5834
  Scenario:TC-TEST101 - productUnderTest - productUnderTest Service - Verify POST, GET and PUT request to productUnderTest Service
    Given I create a POST request using productUnderTest Service with application number "TESTAUTO-304"
    Then I verify the GET request using productUnderTest Service with application number "TESTAUTO-304"
    Given I create a PUT request using productUnderTest Service with application number "TESTAUTO-304" and income "50000"
    Then I verify the GET request using productUnderTest Service with application number "TESTAUTO-304"


