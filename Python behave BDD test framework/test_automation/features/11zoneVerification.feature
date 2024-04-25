@zoTESTification
Feature: Zone Verification for Generic VNF

  Scenario: Zone Verification for Generic VNF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I verify that "processing_online0" shall in "zone1"
    Then I verify that "processing_online1" shall in "zone2"
    Then I verify that "processing_offline0" shall in "zone1"
    Then I verify that "processing_offline1" shall in "zone2"
    Then I verify that "db0" shall in "zone1"
    Then I verify that "db1" shall in "zone2"
    Then I verify that "oam0" shall in "zone1"
    Then I verify that "oam1" shall in "zone2"
    Then I verify that "ui0" shall in "zone1"
    Then I verify that "ui1" shall in "zone2"
    Then I close all connections