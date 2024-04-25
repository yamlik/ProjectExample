@smoketest
Feature: Smoke Test DR VNF
As DevOps Engineer
I want to verify the quality of the VNF package. So I need to run a stream with data and do basic LCM operations

  #Import $CDF_Stream to TransactionEngine host.
  Scenario: Import Test Stream
    Given environment is configured for cbam api and ssh actions
    When I modify stream "TransactionEngine" to run on "processing_online0" and "TransactionEngineHost" with name "TransactionEngine"
    And I import stream "TransactionEngine" to "processing_online0"
    When I start the stream "TransactionEngine"
    Then stream "TransactionEngine" is running 
    When I upload "CMCC_VoLTE_CDF" stream package successfully to "processing_online0"
    And I create necessary folders for stream "CMCC_VoLTE_CDF" to "processing_online0"
    Then I import stream "CMCC_VoLTE_CDF" on "processing_online0" to "TransactioneEngineHost" 
    And I should see "TransactionEngine" stream "running" on "processing_online0"
    And stream "CMCC_VoLTE_CDF" should exist

  #Start stream from the UI, simulate traffic with seagull. Transaction Engine needs to be running first.
  Scenario: Process Data
    Given stream "TransactionEngine" is running
    When I start the stream "CMCC_VoLTE_CDF" 
    Then stream "CMCC_VoLTE_CDF" is running on "TransactionEngineHost"
    When I start simulating traffic with "1000" calls to "processing_online0" with seagull from "ui0"
    Then I should see seagull data processed on "ui0"

  #Testing scaling out
  Scenario: Scale Out Instance
    Given the session is authenticated
    And scaling aspect group "processingONAspect" is at "1"
    When I scale "out" "processingONAspect" aspect group
    Then "processing_online1" should be healthy
   
  #Complicated testing scenario, which creates streams on processing_online1 which mimick those that run on po0. The streams require quite a lot of configuraiton for new host.  
  Scenario: Custom action
    Given stream "TransactionEngine" is running
    And scaling aspect group "processingONAspect" is at "2"
    When I modify stream "TransactionEngine" to run on "processing_online1" and "TransactionEngineHost2" with name "TransactionEngine2"
    And I import stream "TransactionEngine2" to "processing_online1"
    And I start the stream "TransactionEngine2"
    And I create necessary folders for stream "CMCC_VoLTE_CDF" to "processing_online1"
    And I modify stream "CMCC_VoLTE_CDF" to run on "TransactionEngineHost2" on "processing_online1" with name "CMCC_VoLTE_CDF2"
    And I import stream "CMCC_VoLTE_CDF2" on "processing_online1" to "TransactionEngineHost2"
    And I start the stream "CMCC_VoLTE_CDF2"
    Then I should see "CMCC_VoLTE_CDF2" stream "running" on "TransactionEngineHost2"

  #Repeating traffic simulation but this time on new streams on po1.
  Scenario: Verify new instance works
    Given stream "CMCC_VoLTE_CDF2" is running
    When I start simulating traffic with "1000" calls to "processing_online1" with seagull from "ui0"
    Then I should see seagull data processed on "ui0"


  #Testing stopping and starting streams.
  Scenario: Stop stream
    Given stream "CMCC_VoLTE_CDF2" is running
    And the session is authenticated
    When I stop the stream "CMCC_VoLTE_CDF2"
    Then I should see "CMCC_VoLTE_CDF2" stream "stopped" on "TransactionEngineHost2"
    When I start the stream "CMCC_VoLTE_CDF2"
    Then I should see "CMCC_VoLTE_CDF2" stream "running" on "TransactionEngineHost2"

  @heal
  #Soft heal just service restart
  Scenario: Healing an instance while artificially making the reboot to fail
    Given environment is configured for cbam api and ssh actions
    And service "el-node-manager" is running on "processing_online0"
    When I shutdown service "el-node-manager" on "processing_online0"
    And start the healing action
    Then service "el-node-manager" should be running on "processing_online0"

  @heal
  #Hard heal, force a rebuild of an instance
  Scenario: Hard heal, removing binary from /boot
    Given host "ui0" is healthy
    When I remove boot files from "ui0" and force boot
    And start the healing action
    Then "ui0" should be healthy

  #Scaling in to see if stream gets to an unknown state, scale out after to resolve unknown state
  Scenario: Scaling in an instance and checking the the host has been removed
    Given stream "CMCC_VoLTE_CDF2" is running on "TransactionEngineHost2"
    When I scale "in" "processingONAspect" aspect group
    Then I should see "CMCC_VoLTE_CDF2" stream "not running" on "TransactionEngineHost2"
    And I scale "out" "processingONAspect" aspect group
