@API @REST
Feature: REST Valid Authentication Tests for Predator API

  Scenario: TC-01 Basic API Login Attempt with REST
    Given I submit a valid login request

  Scenario Outline: TC-02.<TSNTance> Template API Login Attempt with REST
    Given I submit a valid login request
    Examples:
      | TSNTance |
      | 0        |
      | 1        |