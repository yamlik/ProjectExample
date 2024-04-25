@zoTESTificationVz
Feature: Zone Verification for EPC VNF

  Scenario: Zone Verification for EPC VNF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I verify that "cdf0" shall in "zone1"
    Then I verify that "cdf1" shall in "zone2"
    Then I verify that "outage0" shall in "zone1"
    Then I verify that "outage1" shall in "zone2"
    Then I verify that "mapper0" shall in "zone1"
    Then I verify that "mapper1" shall in "zone2"
    Then I verify that "epccgf0" shall in "zone1"
    Then I verify that "epccgf1" shall in "zone2"
    Then I verify that "db0" shall in "zone1"
    Then I verify that "db1" shall in "zone2"
    Then I verify that "oam0" shall in "zone1"
    Then I verify that "oam1" shall in "zone2"
    Then I verify that "ui0" shall in "zone1"
    Then I verify that "ui1" shall in "zone2"
    Then I close all connections