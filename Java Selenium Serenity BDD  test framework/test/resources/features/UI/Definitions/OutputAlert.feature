@UI
Feature: Verify the adding of output alert

  @TESTAUTO-90 @TSNT-5171 @productUnderTest
  Scenario:TC-58889 - productUnderTest -  Definitions - Output alert
    Given I am on "Output Alert" Page
    And I add email content with "All Applications" criteria and "Application.Organisation" field
    And I add email address name as "dung.nguyen@plc.com"
    And I add subject as "test Output Alert"
    Then I verify the added "All Applications" alert is saved correctly
    And I delete "All Applications" alert
