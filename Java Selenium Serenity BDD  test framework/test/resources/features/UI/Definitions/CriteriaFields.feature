@UI
Feature: Verify the adding of Criteria Fields

  @TSNT-4721 @productUnderTest @TESTAUTO-89
  Scenario: TC-58888 productUnderTest - Definitions - Add new criteria fields
    Given I am on "Criteria Fields" Page
    And I add new criteria field having values as "ALL Applications"
    And I add the "Application.Amount/Limit" in available fields ,"5" in Order Id and Value as "5000"
    Then I verify the added Criteria Field
    And I delete the Order Id "5" data
