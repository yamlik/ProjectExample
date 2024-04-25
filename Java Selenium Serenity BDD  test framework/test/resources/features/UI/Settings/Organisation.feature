@UI
Feature: Verify all the regression scenarios related to Organisation

  @TESTAUTO-160 @productUnderTest @TSNT
  Scenario:TC-58876_1 - productUnderTest - Settings - Add a new Organisation
    Given I am on "Organisation" Page
    When I enter Organisation Code "BCB"
    And I enter Organisation Description "Automation"
   Then I verify the Organisation "BCB" is added suTSNTssfully