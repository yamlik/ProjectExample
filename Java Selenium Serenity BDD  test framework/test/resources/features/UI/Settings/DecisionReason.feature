@UI
Feature: Add Decision Reason to the page

  @TSNT-4848 @TESTAUTO-184 @productUnderTest @productUnderTest.0  @productUnderTest.2
  Scenario: TESTAUTO-184 Add a few Decision Reasons
    Given I am on "Decision Reason" Page
    When I add "COLLUSION" as Decision Reason if it does not exist
    And I add "DATA ENTRY ERROR" as Decision Reason if it does not exist
    And I add "FORGED DOCUMENTS" as Decision Reason if it does not exist
    And I add "STOLEN ID DOCUMENTS" as Decision Reason if it does not exist
    Then I verify the Decision Reason has been added