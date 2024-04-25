@UI
Feature: Verify all the Watchlist Review Batch Load regression scenarios by uploading Batch Files

  @TSNT-5533 @productUnderTest @DELETE_RULE_SET @DELETE_ALL_WATCHLIST @DELETE_ALL_APPLICATION @TESTAUTO-68
  Scenario: TC-58782 - productUnderTest - Watchlist - Review Batch Loads some records with incorrect format
    Given I am on "Cross Check Fields" Page
    And I delete the cross check fields
    And I add Cross Check Fields
    And I submit "1" application request using Fraud Check WS
    And I transfer file "CTESTAUTO68.txt" to batch service input directory
    And I am on "Watchlist Review Batch Loads" Page
    When I search for date ranges from "01/01/2020" to "01/01/2030" and refresh
    And I verify the expected counts on Watchlist Review Batch
    |Column|Value|
    |Total |7    |
    |Added |5    |
    |Replaced|0  |
    |Errors  |2  |
    And I double-click the above Batch verified record
    And I select one of the records
    And I Auto Check the application from "Watchlist Review Batch" page without verifying results
    Then I Action the application as "Suspicious" from "Auto Check Results" page


  @TSNT-5534 @productUnderTest @DELETE_ALL_WATCHLIST @TESTAUTO-68
  Scenario: TC-58782-1 - productUnderTest - Watchlist - Print Watchlist Batch errors and Delete Errors
    Given I transfer file "CTESTAUTO68.txt" to batch service input directory
    And I am on "Watchlist Review Batch Loads" Page
    When I search for date ranges from "01/01/2020" to "01/01/2030" and refresh
    And I select the above Batch verified record
    And I review the errors from the recent Batch
    Then I will Print Results On Excel sheet
    And I Verify the Watchlist review errors Page
    And I will delete the watchlist errors