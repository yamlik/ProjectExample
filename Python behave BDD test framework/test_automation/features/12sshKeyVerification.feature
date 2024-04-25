@sshKeyVerification
Feature: SSH Key Verification for Generic VNF

  Scenario: SSH Key Verification for ProcessingOFF
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "processingOFF_vip0" to "processing_online0"
    And perform ssh check from "processingOFF_vip0" to "ui_vip"
    And perform ssh check from "processingOFF_vip0" to "db_vip"
	And perform ssh check from "processingOFF_vip0" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for ProcessingON
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "processing_online0" to "processingOFF_vip0"
    And perform ssh check from "processing_online0" to "ui_vip"
    And perform ssh check from "processing_online0" to "db_vip"
	And perform ssh check from "processing_online0" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for DB
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "db_vip" to "processingOFF_vip0"
    And perform ssh check from "db_vip" to "ui_vip"
    And perform ssh check from "db_vip" to "processing_online0"
	And perform ssh check from "db_vip" to "oam_vip"
	Then I close all connections

  Scenario: SSH Key Verification for OAM
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "oam_vip" to "processingOFF_vip0"
    And perform ssh check from "oam_vip" to "ui_vip"
    And perform ssh check from "oam_vip" to "processing_online0"
	And perform ssh check from "oam_vip" to "db_vip"
	Then I close all connections

  Scenario: SSH Key Verification for UI
    Given environment is configured for cbam api and ssh actions
	And make sure jumphost connection is ready
	Then perform ssh check from "ui_vip" to "processingOFF_vip0"
    And perform ssh check from "ui_vip" to "db_vip"
    And perform ssh check from "ui_vip" to "processing_online0"
	And perform ssh check from "ui_vip" to "oam_vip"
	Then I close all connections