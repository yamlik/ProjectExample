@rpmUpdate
Feature: update passwords

  Scenario Outline: Copy RPM file(s) to OAM repository location
    Given environment is configured for cbam api and ssh actions
    And Instantiation status is finished
    When I pull "<rpm_url>" rpm to "oam_vip" host
    And Move "<rpm_name>" to right location on "oam_vip"
    Then "<rpm_name>" is in right location on "oam_vip"

    Examples:
      | rpm_url                                                                    | rpm_name                        |
      | http://10.75.55.30/centos7.6.1810/Packages/pango-1.42.4-2.el7_6.x86_64.rpm | pango-1.42.4-2.el7_6.x86_64.rpm |
      | http://10.75.55.30/centos7.5.1804/Packages/pango-1.40.4-1.el7.x86_64.rpm   | pango-1.40.4-1.el7.x86_64.rpm   |

  Scenario: Execute "RPM Update" LCM action to update OAM repository
    Given Instantiation status is finished
    When I launch "RPM Update" LCM action with params:
      | key                       | value |
      | run_on_online_processing  | false |
      | run_on_offline_processing | false |
      | run_on_oam                | false |
      | run_on_db                 | false |
      | run_on_ui                 | false |
      | run_on_all                | false |
      | rollback                  | false |
      | serial_run                | 5     |
    Then "RPM Update" LCM action finishes successfully

  Scenario: Execute "RPM Update" LCM action to update rpms on all hosts
    Given RPMs are in "oam_vip" repo
      | rpm_name     |
      | pango-1.42.4 |
      | pango-1.40.4 |
    When I launch "RPM Update" LCM action with params:
      | key                       | value |
      | run_on_online_processing  | false |
      | run_on_offline_processing | false |
      | run_on_oam                | false |
      | run_on_db                 | false |
      | run_on_ui                 | false |
      | run_on_all                | true  |
      | rollback                  | false |
      | serial_run                | 5     |
    Then "RPM Update" LCM action finishes successfully

  Scenario Outline: Verify "RPM Update" LCM action has successfully updated rpm on all hosts
    Given "RPM Update" LCM action finishes successfully
    When I log in to "<vm_type>"
    Then RPM version should be this
      | rpm_name              | version  |
      | pango                 | 1.42.4-2 |

    Examples:
      | vm_type       |
      | processingON  |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |

  Scenario: Execute "RPM Update" LCM action to rollback updated rpms on all hosts
# to-do, implement a better 'Given' step
#    Given RPMs versions were updated
    Given Instantiation status is finished
    When I launch "RPM Update" LCM action with params:
      | key                       | value |
      | run_on_online_processing  | false |
      | run_on_offline_processing | false |
      | run_on_oam                | false |
      | run_on_db                 | false |
      | run_on_ui                 | false |
      | run_on_all                | true  |
      | rollback                  | true  |
      | serial_run                | 5     |
    Then "RPM Update" LCM action finishes successfully

  Scenario Outline: Verify "RPM update" LCM action has successfully rollbacked rpm on all hosts
    Given "RPM Update" LCM action finishes successfully
    When I log in to "<vm_type>"
    Then RPM version should be this
      | rpm_name              | version  |
      | pango                 | 1.40.4-1 |

    Examples:
      | vm_type       |
      | processingON  |
      | processingOFF |
      | ui            |
      | db            |
      | oam           |
