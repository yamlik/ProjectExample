@UI
Feature: Verify all the regression scenarios related to Indexed Fuzzy Match Mapping

  @TESTAUTO-41  @productUnderTest @IMPORT_DAT_FILE
  Scenario: TC-52575 -Indexed Fuzzy Match Mapping- Add new mapping
    Given I am on "INDEXED FUZZY MATCH MAPPING" Page
    When I add new mapping with "PDIndex11" group name and "Application.Country Code" field
    Then I save the mapping
    Then I verify the Mapping "PDIndex11" already added










