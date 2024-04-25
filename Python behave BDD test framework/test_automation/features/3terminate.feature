@delete
Feature: Delete vnf

  Scenario: Delete vnf
    Given environment is configured for cbam api and ssh actions
    When vnf is deleted
    Then it does not exist
