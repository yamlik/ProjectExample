@failoverTest
Feature: HA Test

  Scenario: HA on processingOFF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    And I disable auto_ha
    Then I trigger HA operation at CBAM and wait
    Then I stop "el-node-manager" service in "processingOFF_vip0"
    And I trigger HA operation at CBAM and wait
    Then I saw "el-node-manager" is active in "processingOFF_vip0"
    And I check all "procOFF" volumes should attached to "processingOFF_vip0"
    Then I try to get ip of standby host through "processingOFF_vip0"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on OAM
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "httpd" service in "oam_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "httpd" is active in "oam_vip"
    And I check all "OAM" volumes should attached to "oam_vip"
    Then I try to get ip of standby host through "oam_vip"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on UI
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "tomcat@el-ui" service in "ui_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "tomcat@el-ui" is active in "ui_vip"
    And I check all "UI" volumes should attached to "ui_vip"
    Then I try to get ip of standby host through "ui_vip"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on CGF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "el-node-manager" service in "cgf_vip0"
    And I trigger HA operation at CBAM and wait
    Then I saw "el-node-manager" is active in "cgf_vip0"
    And I check all "CGF" volumes should attached to "cgf_vip0"
    Then I try to get ip of standby host through "cgf_vip0"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on DB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "postgresql" service in "db_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "postgresql" is active in "db_vip"
    And I check all "DB" volumes should attached to "db_vip"
    Then I try to get ip of standby host through "db_vip"
    And HA role is STANDBY in standby host
    Then I close all connections
