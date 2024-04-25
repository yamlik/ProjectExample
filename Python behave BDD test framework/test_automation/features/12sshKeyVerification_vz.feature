@sshKeyVerificationVz
Feature: SSH Key Verification for Generic VNF

  Scenario: SSH Key Verification for CDF
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "cdf_vip0" to "mapper0"
    And perform ssh check from "cdf_vip0" to "outage_vip0"
    And perform ssh check from "cdf_vip0" to "db_vip"
	And perform ssh check from "cdf_vip0" to "ui_vip"
	And perform ssh check from "cdf_vip0" to "epccgf0"
	And perform ssh check from "cdf_vip0" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for Mapper
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "mapper0" to "cdf_vip0"
    And perform ssh check from "mapper0" to "outage_vip0"
    And perform ssh check from "mapper0" to "db_vip"
	And perform ssh check from "mapper0" to "ui_vip"
	And perform ssh check from "mapper0" to "epccgf0"
	And perform ssh check from "mapper0" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for DB
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "db_vip" to "cdf_vip0"
    And perform ssh check from "db_vip" to "outage_vip0"
    And perform ssh check from "db_vip" to "mapper0"
	And perform ssh check from "db_vip" to "ui_vip"
	And perform ssh check from "db_vip" to "epccgf0"
	And perform ssh check from "db_vip" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for OAM
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "oam_vip" to "cdf_vip0"
    And perform ssh check from "oam_vip" to "outage_vip0"
    And perform ssh check from "oam_vip" to "mapper0"
	And perform ssh check from "oam_vip" to "ui_vip"
	And perform ssh check from "oam_vip" to "epccgf0"
	And perform ssh check from "oam_vip" to "db_vip"
	Then I close all connections

  Scenario: SSH Key Verification for UI
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "ui_vip" to "cdf_vip0"
    And perform ssh check from "ui_vip" to "outage_vip0"
    And perform ssh check from "ui_vip" to "mapper0"
	And perform ssh check from "ui_vip" to "oam_vip"
	And perform ssh check from "ui_vip" to "epccgf0"
	And perform ssh check from "ui_vip" to "db_vip"
	Then I close all connections

  Scenario: SSH Key Verification for Outage
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "outage_vip0" to "cdf_vip0"
    And perform ssh check from "outage_vip0" to "ui_vip"
    And perform ssh check from "outage_vip0" to "mapper0"
	And perform ssh check from "outage_vip0" to "oam_vip"
	And perform ssh check from "outage_vip0" to "epccgf0"
	And perform ssh check from "outage_vip0" to "db_vip"
	Then I close all connections

  Scenario: SSH Key Verification for EPCCGF
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "epccgf0" to "cdf_vip0"
    And perform ssh check from "epccgf0" to "ui_vip"
    And perform ssh check from "epccgf0" to "mapper0"
	And perform ssh check from "epccgf0" to "oam_vip"
	And perform ssh check from "epccgf0" to "outage_vip0"
	And perform ssh check from "epccgf0" to "db_vip"
	Then I close all connections