@ScaleVz
Feature: LCM Test

  Scenario: Check vnf_path directory structure
    Given test vnfInfo exists
    And it contains vnf_path
						 
    When directory in vnf_path is checked
    Then The test directory contains appropriate directories

  Scenario: Create ApiAction object
    Given test cbamApiClass exists
    When test variables are loaded
    And authentication is received
    Then test ApiAction object has auth_header and client_id

  Scenario: Scale out crdb
    Given environment is configured for cbam api and ssh actions
    And ApiAction object exists
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
    Then The file "/opt/.build.out" should "absent" in "crdb0"
    Then The file "/opt/.build.out" should "absent" in "crdb1"
    Then The file "/opt/.build.out" should "absent" in "crdb2"
    Then perform ssh check from "crdb0" to "cdf_vip0"
    And perform ssh check from "crdb1" to "ui_vip"
    And perform ssh check from "crdb2" to "db_vip"
	And perform ssh check from "crdb0" to "oam_vip"
	And perform ssh check from "crdb1" to "outage_vip0"
	And perform ssh check from "crdb2" to "epccgf0"
	And perform ssh check from "crdb2" to "mapper0"
	Then I close all connections

  Scenario: Scale out CDF
    Given ApiAction object exists
    When Scale out CDF instance
    Then Instantiation status is started

  Scenario: Wait for CDF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for CDF scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then scaling aspect group "cdfAspect" is at "2"
    Then HA role is "ACTIVE" in "cdf_vip1"
    And service "el-node-manager" should active in "cdf_vip1"
#    And service "el-timesten" should active in "cdf_vip1"
    And service "el-processing-state" should active in "cdf_vip1"
    And service "docker" should active in "cdf_vip1"
    Then all volumes for CDF are attached
    And I verify that "cdf2" shall in "zone1"
    And I verify that "cdf3" shall in "zone2"
    Then The file "/opt/.build.out" should "absent" in "cdf_vip1"
    Then perform ssh check from "cdf_vip1" to "cdf_vip0"
    And perform ssh check from "cdf_vip1" to "ui_vip"
    And perform ssh check from "cdf_vip1" to "db_vip"
	And perform ssh check from "cdf_vip1" to "oam_vip"
	And perform ssh check from "cdf_vip1" to "outage_vip0"
	And perform ssh check from "cdf_vip1" to "epccgf0"
	And perform ssh check from "cdf_vip1" to "mapper0"
	Then I close all connections


  Scenario: Scale in CDF
    Given ApiAction object exists
    When Scale in CDF instance
    Then Instantiation status is started

  Scenario: Wait for CDF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for CDF scale result
    Given ApiAction object exists
    Then scaling aspect group "cdfAspect" is at "1"

  Scenario: Scale out Outage
    Given ApiAction object exists
    When Scale out Outage instance
    Then Instantiation status is started

  Scenario: Wait for Outage scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for Outage scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then scaling aspect group "outageAspect" is at "2"
    Then HA role is "ACTIVE" in "outage_vip1"
    And service "el-node-manager" should active in "outage_vip1"
    And service "el-timesten" should active in "outage_vip1"
    And service "el-processing-state" should active in "outage_vip1"
    And service "docker" should active in "outage_vip1"
    And I check all "OUTAGE" volumes should attached to "outage_vip1"
    And I verify that "outage2" shall in "zone1"
    And I verify that "outage3" shall in "zone2"
    Then The file "/opt/.build.out" should "absent" in "outage_vip1"
    Then perform ssh check from "outage_vip1" to "cdf_vip0"
    And perform ssh check from "outage_vip1" to "ui_vip"
    And perform ssh check from "outage_vip1" to "db_vip"
	And perform ssh check from "outage_vip1" to "oam_vip"
	And perform ssh check from "outage_vip1" to "outage_vip0"
	And perform ssh check from "outage_vip1" to "epccgf0"
	And perform ssh check from "outage_vip1" to "mapper0"
	Then I close all connections

  Scenario: Scale in Outage
    Given ApiAction object exists
    When Scale in Outage instance
    Then Instantiation status is started

  Scenario: Wait for Outage scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for Outage scale result
    Given ApiAction object exists
    Then scaling aspect group "outageAspect" is at "1"

  Scenario: Scale out Mapper 
    Given ApiAction object exists
    When Scale out Mapper instance
    Then Instantiation status is started

  Scenario: Wait for Mapper scale 
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for Mapper scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then scaling aspect group "mapperAspect" is at "3"
    Then service "el-node-manager" should active in "mapper2"
    And service "el-timesten" should active in "mapper2"
    And service "el-processing-state" should active in "mapper2"
    And service "docker" should active in "mapper2"
    And I check all "MAPPER" volumes should attached to "mapper2"
    And I verify that "mapper2" shall in "zone1"
    Then The file "/opt/.build.out" should "absent" in "mapper2"
    Then perform ssh check from "mapper2" to "cdf_vip0"
    And perform ssh check from "mapper2" to "ui_vip"
    And perform ssh check from "mapper2" to "db_vip"
	And perform ssh check from "mapper2" to "oam_vip"
	And perform ssh check from "mapper2" to "outage_vip0"
	And perform ssh check from "mapper2" to "epccgf0"
	And perform ssh check from "mapper2" to "mapper0"
	Then I close all connections

  Scenario: Scale in Mapper
    Given ApiAction object exists
    When Scale in Mapper instance
    Then Instantiation status is started

  Scenario: Wait for Mapper scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for Mapper scale result
    Given ApiAction object exists
    Then scaling aspect group "mapperAspect" is at "2"

  Scenario: Scale out EPCCGF 
    Given ApiAction object exists
    When Scale out EPCCGF instance
    Then Instantiation status is started

  Scenario: Wait for EPCCGF scale 
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for EPCCGF scale result
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then scaling aspect group "epccgfAspect" is at "3"
    Then service "el-node-manager" should active in "epccgf2"
    And service "el-timesten" should active in "epccgf2"
    And service "el-processing-state" should active in "epccgf2"
    And service "docker" should active in "epccgf2"
    And I check all "EPCCGF" volumes should attached to "epccgf2"
    And I verify that "epccgf2" shall in "zone1"
    Then The file "/opt/.build.out" should "absent" in "epccgf2"
    Then perform ssh check from "epccgf2" to "cdf_vip0"
    And perform ssh check from "epccgf2" to "ui_vip"
    And perform ssh check from "epccgf2" to "db_vip"
	And perform ssh check from "epccgf2" to "oam_vip"
	And perform ssh check from "epccgf2" to "outage_vip0"
	And perform ssh check from "epccgf2" to "epccgf0"
	And perform ssh check from "epccgf2" to "mapper0"
	Then I close all connections

  Scenario: Scale in EPCCGF
    Given ApiAction object exists
    When Scale in EPCCGF instance
    Then Instantiation status is started

  Scenario: Wait for EPCCGF scale
    Given ApiAction object exists
    And VNF Instantiation has begun
    When polling Cbam has ended
    Then Instantion status is finished

  Scenario: Check for EPCCGF scale result
    Given ApiAction object exists
    Then scaling aspect group "epccgfAspect" is at "2"