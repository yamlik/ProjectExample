@attachIP
Feature: Attach IP to active host

  Scenario: Attach IP to processingOFF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then Attach network name "REPLACE_NETWORK_NAME" with netmask "REPLACE_NETMASK" and "REPLACE_GATEWAY" in "REPLACE_VDU_NAME"