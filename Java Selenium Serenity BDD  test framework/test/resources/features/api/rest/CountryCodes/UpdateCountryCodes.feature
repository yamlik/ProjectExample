@API @REST
Feature: REST Update Country Codes for Predator API

  Background:
    Given I have logged in with a standard user

  Scenario: TC-01 API Update Country Code
    Given I get the country codes
    And I give new country code from "NewCountry"
    And I check if country details from "NewCountry" are present in the list
    When I update country code with details from "UpdatedCountry"
    Then I check if country details from "NewCountry" are not present in the list
    And I check if country details from "UpdatedCountry" are present in the list
    And delete the country code defined from "UpdatedCountry"
