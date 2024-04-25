@debug_feature
Feature: debug Test

Scenario: Create ApiAction object
  Given test cbamApiClass exists
  And I disable auto_ha
  When test variables are loaded
  And authentication is received
  Then test ApiAction object has auth_header and client_id

Scenario: Debug
  Given ApiAction object exists
  And make sure jumphost connection is ready
  Then The file "/opt/.build.out" should "present" in "processingOFF_vip0"
  And The file "/opt/etc/keycloak_db.conf" should have "600" belongs to group "keycloak" and user "keycloak" in "ui0"