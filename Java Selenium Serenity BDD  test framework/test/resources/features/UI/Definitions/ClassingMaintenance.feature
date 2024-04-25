@UI
Feature: Verify the adding of new classing Maintenance and update the existing classing maintenance attribute

  @TSNT-4717 @productUnderTest
  Scenario:TC:58887 -productUnderTest - Definitions - Add & verify new classing maintenance attribute
    Given I am on "Classing Maintenance" Page
    And I create new attribute
    And I add New Attribute Definition having values
    And I add the Classing Id as "Testing Only"
    Then I verify the added classing definition is saved correctly
    And I change the existing classing Id
    And I add New Attribute Definition having new values "Description","data","points"
    And I change one of the attribute definition points value as "2000"after shift down
    Then I verify the modified attribute is saved correctly

  @TSNT-4716 @productUnderTest
  Scenario:TC-58886 -productUnderTest - Definitions - Verify the added Classing Association
    Given I am on "Classing Associations" Page
    And I add classing association name as "test classing"
    And I add a new Classing Association selecting all the fields
    Then I verify the added is saved correctly
    And I delete "test classing" data
    And I am on "Classing Maintenance" Page
    And I delete the added classing Id "Testing Only"