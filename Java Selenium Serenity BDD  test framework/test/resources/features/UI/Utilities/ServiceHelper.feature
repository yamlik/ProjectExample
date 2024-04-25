Feature: Steps to restart all kinds of service

  @RESTART_BATCH
  Scenario:  I restart batch service
    Given I restart batch service

  @RESTART_ONLINE
  Scenario:  I restart online service
    Given I restart online service

  @RESTART_COMMUNICATION
  Scenario:  I restart communication service
    Given I restart communication service

  @RESTART_ACTION
  Scenario:  I restart action service
    Given I restart online service