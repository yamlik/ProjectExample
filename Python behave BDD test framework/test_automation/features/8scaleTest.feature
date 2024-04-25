@Scale
Feature: LCM Test

  Scenario: Check vnf_path directory structure
    Given test vnfInfo exists
    And it contains vnf_path
    And I disable auto_ha
    When directory in vnf_path is checked
    Then The test directory contains appropriate directories

  Scenario: Create ApiAction object
    Given test cbamApiClass exists
    When test variables are loaded
    And authentication is received
    Then test ApiAction object has auth_header and client_id

  Scenario: Scale out processingON 
    Given ApiAction object exists
    When Scale out processingON instance
    Then Instantiation status is started

  Scenario: Wait for processingON scale 
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for processingOn scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And ApiAction object exists
    Then scaling aspect group "processingONAspect" is at "3"
    And service "el-node-manager" should active in "processing_online2"
    And service "el-timesten" should active in "processing_online2"
    And service "el-processing-state" should active in "processing_online2"
    And service "docker" should active in "processing_online2"
    And I check all "procON" volumes should attached to "processing_online2"
    And I verify that "processing_online2" shall in "zone1"
    Then perform ssh check from "processing_online2" to "processingOFF_vip1"
    And perform ssh check from "processing_online2" to "processing_online1"
    And perform ssh check from "processing_online2" to "ui_vip"
    And perform ssh check from "processing_online2" to "db_vip"
    And perform ssh check from "processing_online2" to "oam_vip"
    And perform ssh check from "processing_online2" to "processingOFF_vip0"
    Then The file "/opt/.build.out" should "absent" in "processing_online2"
    Then I close all connections

  Scenario: Scale out processingOFF
    Given ApiAction object exists
    When Scale out processingOFF instance
    Then Instantiation status is started

  Scenario: Wait for processingOFF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for processingOFF scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And ApiAction object exists
    Then scaling aspect group "processingOFFAspect" is at "2"
    And service "el-node-manager" should active in "processingOFF_vip1"
    And service "el-timesten" should active in "processingOFF_vip1"
    And service "el-processing-state" should active in "processingOFF_vip1"
    And service "docker" should active in "processingOFF_vip1"
    And I check all "procOFF" volumes should attached to "processingOFF_vip1"
    And I verify that "processing_offline2" shall in "zone1"
    And I verify that "processing_offline3" shall in "zone2"
	Then perform ssh check from "processingOFF_vip1" to "processing_online0"
    And perform ssh check from "processingOFF_vip1" to "ui_vip"
    And perform ssh check from "processingOFF_vip1" to "db_vip"
	And perform ssh check from "processingOFF_vip1" to "oam_vip"
	And perform ssh check from "processingOFF_vip1" to "processingOFF_vip0"
	Then I close all connections

  Scenario: Scale in processingON
    Given ApiAction object exists
    When Scale in processingON instance
    Then Instantiation status is started

  Scenario: Wait for processingON scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for processingOn scale result
    Given ApiAction object exists
    Then scaling aspect group "processingONAspect" is at "2"

  Scenario: Scale in processingOFF
    Given ApiAction object exists
    When Scale in processingOFF instance
    Then Instantiation status is started

  Scenario: Wait for processingOFF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for processingOFF scale result
    Given ApiAction object exists
    Then scaling aspect group "processingOFFAspect" is at "1"

  Scenario: Scale out crdb
    Given ApiAction object exists
    When Scale out crdb instance
    Then Instantiation status is started

  Scenario: Wait for crdb scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for crdb scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And ApiAction object exists
    Then scaling aspect group "crdbAspect" is at "3"
    And I verify that "crdb0" shall in "zone1"
    And I verify that "crdb1" shall in "zone2"
    And I verify that "crdb2" shall in "zone1"
	Then perform ssh check from "crdb0" to "processing_online0"
    And perform ssh check from "crdb1" to "ui_vip"
    And perform ssh check from "crdb2" to "db_vip"
	And perform ssh check from "crdb0" to "oam_vip"
	And perform ssh check from "crdb1" to "processingOFF_vip0"
	Then I close all connections

  Scenario: Scale out CGF
    Given ApiAction object exists
    When Scale out cgf instance
    Then Instantiation status is started

  Scenario: Wait for CGF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for CGF scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And ApiAction object exists
    Then scaling aspect group "cgfAspect" is at "1"
    And service "el-node-manager" should active in "cgf_vip0"
    And service "el-timesten" should active in "cgf_vip0"
    And service "el-processing-state" should active in "cgf_vip0"
    And service "docker" should active in "cgf_vip0"
    And I check all "CGF" volumes should attached to "cgf_vip0"
    And I verify that "cgf0" shall in "zone1"
    And I verify that "cgf1" shall in "zone2"
	Then perform ssh check from "cgf_vip0" to "processingOFF_vip0"
    And perform ssh check from "cgf_vip0" to "ui_vip"
    And perform ssh check from "cgf_vip0" to "db_vip"
	And perform ssh check from "cgf_vip0" to "oam_vip"
	And perform ssh check from "cgf_vip0" to "processing_online0"
	Then I close all connections

  Scenario: Scale out CGF
    Given ApiAction object exists
    When Scale out cgf instance
    Then Instantiation status is started

  Scenario: Wait for CGF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for CGF scale result
    Given ApiAction object exists
    Then scaling aspect group "cgfAspect" is at "2"
    And I verify that "cgf2" shall in "zone1"
    And I verify that "cgf3" shall in "zone2"

  Scenario: Scale in CGF
    Given ApiAction object exists
    When Scale in cgf instance
    Then Instantiation status is started

  Scenario: Wait for CGF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for CGF scale result
    Given ApiAction object exists
    Then scaling aspect group "cgfAspect" is at "1"
