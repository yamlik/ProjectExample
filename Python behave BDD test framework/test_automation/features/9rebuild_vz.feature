@rebuildVz
Feature: Rebuild Test


Scenario: Rebuild CDF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in both CDF host
    Then I start the healing action "heal_cdf"
    Then I start the healing action "heal_cdf"
    Then HA role is "ACTIVE" in "cdf_vip0"
    And service "el-node-manager" should active in "cdf_vip0"
    And service "el-processing-state" should active in "cdf_vip0"
    And service "el-timesten" should active in "cdf_vip0"
#    And service "docker" should active in "cdf_vip0"
    Then all volumes for CDF are attached
    And openstack action log show CDF is rebuild
    Then The file "/opt/.build.out" should "absent" in "cdf_vip0"																  
	Then I close all connections

Scenario: Rebuild Outage
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in both Outage host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_outage"
#    Then I start the healing action "heal_outage"
    Then I start the healing action "heal_outage" for "2" times
    Then HA role is "ACTIVE" in "outage_vip0"
    And service "el-node-manager" should active in "outage_vip0"
    And service "el-processing-state" should active in "outage_vip0"
    And service "el-timesten" should active in "outage_vip0"
#    And service "docker" should active in "outage_vip0"
    Then all volumes for Outage are attached
    And openstack action log show Outage is rebuild
    Then The file "/opt/.build.out" should "absent" in "outage_vip0"
	Then I close all connections

Scenario: Rebuild Mapper
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in Mapper host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_mapper"
#    Then I start the healing action "heal_mapper"
#    Then I start the healing action "heal_mapper" for "2" times
    Then service "el-node-manager" should active in "mapper0"
    And service "el-processing-state" should active in "mapper0"
    And service "el-timesten" should active in "mapper0"
#    And service "docker" should active in "mapper0"
    Then all volumes for Mapper are attached
    And openstack action log show Mapper is rebuild
    Then The file "/opt/.build.out" should "absent" in "mapper0"
	Then I close all connections

Scenario: Rebuild EPCCGF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in EPCCGF host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_epccgf"
#    Then I start the healing action "heal_epccgf"
    Then I start the healing action "heal_epccgf" for "2" times
    Then service "el-node-manager" should active in "epccgf0"
    And service "el-processing-state" should active in "epccgf0"
    And service "el-timesten" should active in "epccgf0"
    And service "docker" should active in "epccgf0"
    Then all volumes for EPCCGF are attached
    And openstack action log show EPCCGF is rebuild
    Then The file "/opt/.build.out" should "absent" in "epccgf0"
	Then I close all connections

Scenario: Rebuild CRDB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update redisio and redis-sentinel service file in crdb0
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_crdb"
#    Then I start the healing action "heal_crdb"
    Then I start the healing action "heal_crdb" for "2" times
    Then service "redisio" should active in "crdb0"
    And service "redis-sentinel" should active in "crdb0"
    And all volumes for crdb0 are attached
    And openstack action log show crdb0 is rebuild
    Then The file "/opt/.build.out" should "absent" in "crdb0"
	Then I close all connections

Scenario: Rebuild OAM
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update httpd service file in both OAM host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_oam"
#    Then I start the healing action "heal_oam"
    Then I start the healing action "heal_oam" for "2" times
    Then I trigger HA operation at CBAM and wait
    Then HA role is "ACTIVE" in "oam_vip"
    And service "httpd" should active in "oam_vip"
    And all volumes for oam are attached
    And openstack action log show oam is rebuild
    Then The file "/opt/.build.out" should "absent" in "oam_vip"
	Then I close all connections

Scenario: Rebuild DB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I trigger HA operation at CBAM and wait
    Then I update postgresql service file in both DB host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_db"
#    Then I start the healing action "heal_db"
    Then I start the healing action "heal_db" for "2" times
    Then I trigger HA operation at CBAM and wait
    Then HA role is "ACTIVE" in "db_vip"
    And service "postgresql" should active in "db_vip"
    And all volumes for DB are attached
    And openstack action log show DB is rebuild
    Then The file "/opt/.build.out" should "absent" in "db_vip"
	Then I close all connections

Scenario: Rebuild UI
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update tomcat@el-ui service file in both UI host
#   old keyword only heal once, new keywords can control number of attempt
#    Then I start the healing action "heal_ui"
#    Then I start the healing action "heal_ui"
#    Then I start the healing action "heal_ui"
    Then I start the healing action "heal_ui" for "3" times
    Then HA role is "ACTIVE" in "ui_vip"
    And service "httpd" should active in "ui_vip"
    And service "keycloak" should active in "ui_vip"
    And service "tomcat@el-ui" should active in "ui_vip"
    And service "tomcat@admintools" should active in "ui_vip"
    And service "tomcat@reports" should active in "ui_vip"
    And service "tomcat@devtool-backend" should active in "ui_vip"
    And service "tomcat@devtool" should active in "ui_vip"
    And service "docker" should active in "ui_vip"
    And service "docker-distribution" should active in "ui_vip"
    And service "prometheus-server" should active in "ui_vip"
    And service "grafana-server" should active in "ui_vip"
    And all volumes for both ui host are attached
    And openstack action log show UI is rebuild
    Then The file "/opt/.build.out" should "absent" in "ui_vip"
    Then The file "/opt/etc/keycloak.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak_db.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak/custom-attributes-config.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak/keycloak-sysconfig" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak/notification-listener-registry.json" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak/notification-listener-registry.json.sample" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
    Then The file "/opt/etc/keycloak/push-listener-registry.json" should have "600" belongs to group "keycloak" and user "keycloak" in "ui_vip"
	Then I close all connections