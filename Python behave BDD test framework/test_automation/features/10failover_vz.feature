@failoverTestVz
Feature: HA Test

  Scenario: HA on OAM
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "httpd" service in "oam_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "httpd" is active in "oam_vip"
    And all volumes for oam are attached
    Then I try to get ip of standby host through "oam_vip"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on UI
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "tomcat@el-ui" service in "ui_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "tomcat@el-ui" is active in "ui_vip"
    And all volumes for both ui host are attached
    Then I try to get ip of standby host through "ui_vip"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on CDF
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "el-node-manager" service in "cdf_vip0"
    And I trigger HA operation at CBAM and wait
    Then I saw "el-node-manager" is active in "cdf_vip0"
    And all volumes for CDF are attached
    Then I try to get ip of standby host through "cdf_vip0"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on Outage
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "el-node-manager" service in "outage_vip0"
    And I trigger HA operation at CBAM and wait
    Then I saw "el-node-manager" is active in "outage_vip0"
    And all volumes for Outage are attached
    Then I try to get ip of standby host through "outage_vip0"
    And HA role is STANDBY in standby host
    Then I close all connections

  Scenario: HA on DB
    Given environment is configured for cbam api and ssh actions
    And make sure jumphost connection is ready
    Then I stop "postgresql" service in "db_vip"
    And I trigger HA operation at CBAM and wait
    Then I saw "postgresql" is active in "db_vip"
    And all volumes for DB are attached
    Then I try to get ip of standby host through "db_vip"
    And HA role is STANDBY in standby host
    Then I close all connections