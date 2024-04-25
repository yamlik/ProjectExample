@fileCheck
Feature: Generic File Check for VZ Release 

  Scenario: Create ApiAction object
    Given test cbamApiClass exists
    And I disable auto_ha
    When test variables are loaded
    And authentication is received
    Then test ApiAction object has auth_header and client_id

  Scenario: I check status for below files
    Given ApiAction object exists
    And make sure jumphost connection is ready
    Then The file "/opt/.build.out" should "absent" in "oam0"
    Then The file "/opt/.build.out" should "absent" in "oam1"
    Then The file "/opt/.build.out" should "absent" in "db0"
    Then The file "/opt/.build.out" should "absent" in "db1"
    Then The file "/opt/.build.out" should "absent" in "ui0"
    Then The file "/opt/.build.out" should "absent" in "ui1"
    Then The file "/opt/.build.out" should "absent" in "processing_online0"
    Then The file "/opt/.build.out" should "absent" in "processing_online1"
    Then The file "/opt/.build.out" should "absent" in "processingOFF_vip0"
    Then The file "/opt/etc/keycloak.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak_db.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak/custom-attributes-config.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak/keycloak-sysconfig" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak/notification-listener-registry.json" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak/notification-listener-registry.json.sample" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"
    Then The file "/opt/etc/keycloak/push-listener-registry.json" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"