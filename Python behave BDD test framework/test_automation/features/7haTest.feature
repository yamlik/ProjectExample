@haTest
Feature: Test HA LCM action and fail over in general

##########################
### Generic Test Cases ###
##########################

  Scenario Outline: Verify all pre-requirements for HA are present
    Given environment is configured for cbam api and ssh actions
    And Instantiation status is finished
    And auto_ha auto operation is enabled
    When I log in to "<vm_type>"
    Then Service "keepalived" status should be "running"
    And HA role is "ACTIVE" or "STANDBY"

    Examples:
      | vm_type         |
      | processingOFF_0 |
      | processingOFF_1 |
      | ui_0            |
      | ui_1            |
      | db_0            |
      | db_1            |
      | oam_0           |
      | oam_1           |

  Scenario Outline: Gather HA roles from a pair of instances, do a fail over and make sure it succeeds
    When I log in to active "<vm_type>"
    Then VIP is assigned
    And HA role is "ACTIVE"
### Run "ha check" command to check status of all monitored services
    And HA check succeeds
    And Correct Cinder data disks are attached and functional
    When I shutdown service "keepalived" on "active"
    And I wait "480" seconds
    And log in to active "<vm_type>"
    Then Hostname has changed
    And Service "keepalived" status should be "running"
    And VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached and functional

    Examples:
      | vm_type       |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |

  @haFillRoot
  Scenario Outline: Do a fail over for a pair by filling the root disk and make sure it succeeds
    When I log in to active "<vm_type>"
    Then VIP is assigned
    And HA role is "ACTIVE"
### Run "ha check" command to check status of all monitored services
    And HA check succeeds
    And Correct Cinder data disks are attached and functional
    When I fill the root disk with empty files
    And I wait "480" seconds
    And log in to active "<vm_type>"
    Then Hostname has changed
    And Service "keepalived" status should be "running"
    And VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached and functional

    Examples:
      | vm_type       |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |

  @haFillMemory
  Scenario Outline: Do a fail over for a pair by depleting the memory and make sure it succeeds
    When I log in to active "<vm_type>"
    Then VIP is assigned
    And HA role is "ACTIVE"
### Run "ha check" command to check status of all monitored services
    And HA check succeeds
    And Correct Cinder data disks are attached and functional
    When I consume all of the available memory and swap partition
    And I wait "480" seconds
    And log in to active "<vm_type>"
    Then Hostname has changed
    And Service "keepalived" status should be "running"
    And VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached and functional

    Examples:
      | vm_type       |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |

  Scenario Outline: Do a fail over by restarting the ACTIVE instance
    When I log in to active "<vm_type>"
    Then VIP is assigned
    And HA role is "ACTIVE"
### Run "ha check" command to check status of all monitored services
    And HA check succeeds
    And Correct Cinder data disks are attached and functional
    When I restart the instance
    And I wait "480" seconds
    And log in to active "<vm_type>"
    Then Hostname has changed
    And Service "keepalived" status should be "running"
    And VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached and functional

    Examples:
      | vm_type       |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |

##############################
### VM Specific Test Cases ###
##############################

  Scenario Outline: Do a failover for DB VDU pair
    When I log in to active "db"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached
### Check that postgresql service really is dead
    When I stop "<service>"
    And I wait "480" seconds
    And I log in to "standby"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Service "<service>" status should be "running"
    And Correct Cinder data disks are attached
    And Database integrity is kept

    Examples:
      | service       |
      | postgresql    |

# TO-DO do actual functional tests for OAM services, what could be the proper test cases?
  Scenario Outline: Do a failover for OAM VDU pair
    When I log in to active "oam"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached
### Check that the service really is dead
    When I stop "<service>"
    And I wait "480" seconds
    And I log in to "standby"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Service "<service>" status should be "running"
    And Correct Cinder data disks are attached
    And NFS is functional

    Examples:
      | service             |
      | event-management    |

  Scenario Outline: Do a failover for UI VDU pair
    When I log in to active "ui"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Correct Cinder data disks are attached
    And Basic GUI tests with selenium succeed
### Check that postgresql service really is dead
    When I stop "<service>"
    And I wait "480" seconds
    And I log in to "standby"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Service "<service>" status should be "running"
    And Correct Cinder data disks are attached
    And Basic GUI tests with selenium succeed

    Examples:
      | service                       |
      | keycloak                      |
      | httpd                         |
      | tomcat                        |
      | tomcat@admintools             |
      | tomcat@reports                |
      | tomcat@system_monitoring_rest |
      | tomcat@devtool-backend        |
      | tomcat@devtool                |

# Use cleanup stream
  Scenario Outline: Do a failover for the initial processingOFF VDU pair
    Given Stream "<stream>" is running on "processingOFF_0"
    When I log in to active "processingOFF_0"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
 ### Service "TEST.pl" pid file is not present and process is not running
    When I stop "<service>"
    And I wait "480" seconds
    And I log in to active "processingOFF_0"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Service "<service>" status should be "running"
    And Stream "<stream>" is running on "processingOFF_0"

    Examples:
      | service          | stream   |
      | el-node-manager  | cleanup  |
      | el-timesten      | cleanup  |

### scale up to 2 pairs of processingOFF

# Use cleanup stream
  Scenario Outline: Do a failover for the second processingOFF VDU pair
    Given Stream "<stream>" is running on "processingOFF_1"
    When I log in to active "processingOFF_1"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
 ### Service "TEST.pl" pid file is not present and process is not running
    When I stop "<service>"
    And I wait "480" seconds
    And I log in to active "processingOFF_1"
    Then VIP is assigned
    And HA role is "ACTIVE"
    And HA check succeeds
    And Service "<service>" status should be "running"
    And Stream "<stream>" is running on "processingOFF_1"

    Examples:
      | service          | stream   |
      | el-node-manager  | cleanup  |
      | el-timesten      | cleanup  |
