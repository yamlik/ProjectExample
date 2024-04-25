@rebuild
Feature: Rebuild Test

Scenario: Rebuild Processingoff
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And I disable auto_ha
    Then I trigger HA operation at CBAM and wait
    Then I update node manager service file in both processingOFF host
    Then I start the healing action "heal_processingOFF"
    Then I start the healing action "heal_processingOFF"
    Then HA role is "ACTIVE" in "processingOFF_vip0"
    And service "el-node-manager" should active in "processingOFF_vip0"
    And service "el-timesten" should active in "processingOFF_vip0"
    And service "docker" should active in "processingOFF_vip0"
    And all volumes for procesingOFF are attached
    And openstack action log show processingOFF is rebuild

Scenario: Rebuild ProcessingOn
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in processingON host
    Then I start the healing action "heal_processingON"
    Then service "el-node-manager" should active in "processing_online0"
    And service "el-timesten" should active in "processing_online0"
    And service "docker" should active in "processing_online0"
    And all volumes for procesingON are attached
    And openstack action log show processingON is rebuild


Scenario: Rebuild UI
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update tomcat@el-ui service file in both UI host
    Then I start the healing action "heal_ui"
    Then I start the healing action "heal_ui"
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

Scenario: Rebuild CGF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update node manager service file in both CGF host
    Then I start the healing action "heal_cgf"
    Then I start the healing action "heal_cgf"
    Then HA role is "ACTIVE" in "cgf_vip0"
    And service "el-node-manager" should active in "cgf_vip0"
    And service "docker" should active in "cgf_vip0"
    Then all volumes for CGF are attached
    And openstack action log show CGF is rebuild


Scenario: Rebuild CRDB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update redisio and redis-sentinel service file in crdb0
    Then I start the healing action "heal_crdb"
    Then service "redisio" should active in "crdb0"
    And service "redis-sentinel" should active in "crdb0"
    And all volumes for crdb0 are attached
    And openstack action log show crdb0 is rebuild

Scenario: Rebuild OAM
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I update httpd service file in both OAM host
    Then I start the healing action "heal_oam"
    Then I start the healing action "heal_oam"
    Then I trigger HA operation at CBAM and wait
    Then HA role is "ACTIVE" in "oam_vip"
    And service "httpd" should active in "oam_vip"
    And all volumes for oam are attached
    And openstack action log show oam is rebuild


Scenario: Rebuild DB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I trigger HA operation at CBAM and wait
    Then I update postgresql service file in both DB host
    Then I start the healing action "heal_db"
    Then I start the healing action "heal_db"
    Then I trigger HA operation at CBAM and wait
    Then HA role is "ACTIVE" in "db_vip"
    And service "postgresql" should active in "db_vip"
    And all volumes for DB are attached
    And openstack action log show DB is rebuild

