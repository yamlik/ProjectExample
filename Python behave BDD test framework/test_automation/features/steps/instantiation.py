import os
import json
import yaml
import time
from behave import *

#Scenario: Check VNF_PATH directory
import cbam21ApiClass
import cbamApiClass
import writeValues



@given(u'vnfInfo exists')
def step_impl(context):    
  
    test_config = context.test_config
 
    try: 
      test_config = (os.environ['TEST_CONFIG'])
  
    except: 
      print("Value for TEST_CONFIG has not been set")    
    
    writeValues.main(test_config)

    if os.path.exists("yamlFiles/"+test_config) is False:
      raise EnvironmentError(u'vnfInfo does not exist in the current working directory')

@given(u'it contains vnf_path')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f: 
      info = yaml.load(f, Loader=yaml.FullLoader)
    try:
      k = info["vnfInfo"]["vnf_path"]
    except KeyError:
      raise ValueError(u'vnf_path key does not exist in the vnfInfo.yaml file')

@when(u'directory in vnf_path is checked')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    if os.path.exists(info["vnfInfo"]["vnf_path"]) is False:
      raise EnvironmentError(u'vnf_path directory does not exist')

@then(u'The directory contains appropriate directories')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    requiredDirs = ["ansible", "hot", "javascript", "mistral-workbooks", "TOSCA-Metadata"]

    for dir in requiredDirs:
      if os.path.exists(info["vnfInfo"]["vnf_path"] + dir) is False:
        raise EnvironmentError(u'directory'+ dir+ ' does not exist in vnf_path directory')

#Scenario: Check for correct CBIS


@given(u'vnf_path is correct')
def step_impl(context):
    context.execute_steps(u'when directory in vnf_path is checked')

@given(u'TOSCA_metadata exists')
def step_impl(context):
    context.execute_steps(u'then The directory contains appropriate directories')

#Scenario: Create ApiAction() object

@given(u'cbamApiClass exists')
def step_impl(context):
  if os.path.exists("cbamApiClass.py") is False:
    raise EnvironmentError(u'cbamApiClass.py does not exist in the current working directory')

@when(u'variables are loaded')
def step_impl(context):
    test_config = context.test_config
    context.api.getVariables("yamlFiles/"+test_config+"/"+test_config+".yaml")

@when(u'authentication is received')
def step_impl(context):
    context.api.getAuthentication()

@then(u'ApiAction object has auth_header and client_id')
def step_impl(context):
    api = context.api
    if api.auth_header == "":
      raise ValueError(u'Auth_header was stored incorrectly')
    if api.client_id == "":
      raise ValuerError(u'Variables were not loaded correctly')

#Scenario: Write given vnfdId to vnfd given in TOSCA


@given(u'an input vnfdId is given as an environment variable')
def step_impl(context):
    api = context.api
    try:
      env_vnfdId = os.environ["vnfdId"]
    except KeyError:
      raise EnvironmentError(u'vnfdId environment variable was not defined') 

    api.setInputVnfdId(env_vnfdId)
@when(u'it doesn\'t conflict with existing vnfdIds')
def step_impl(context):
    api = context.api
    api.getVnfd()
    api.writeVnfdId()

@then(u'the current vnfd is modified to include the vnfdId')
def step_impl(context):
    api = context.api

    with open(api.vnfdFile) as f:
      vnfd = yaml.load(f, Loader=yaml.FullLoader)
    try:
      if vnfd["topology_template"]["substitution_mappings"]["properties"] != None:
         prefix = vnfd["topology_template"]["substitution_mappings"]["properties"]
    except:
      if vnfd["dsl_definitions"] != None:
         prefix = vnfd["dsl_definitions"]
    i = 0
    if api.inputVnfdId != prefix["descriptor_id"] is True:
      i+=1
    if api.inputVnfdId != prefix["product_name"] is True:
      i+=1
    if api.inputVnfdId != prefix["product_info_name"] is True:
      i+=1
    if i != 0:
      raise ValueError("inputVnfdId was not set to the vnfd correctly")

#Scenario: Create zip 
@given(u'createZipScript.sh exists')
def step_impl(context):
    os.path.exists("./scripts/createZipScript.sh")

@when(u'the script is run')
def step_impl(context):
    api = context.api
    api.createZip()

@then(u'a zip file containing the VNF is created')
def step_impl(context):
    api = context.api
    os.path.exists(api.zipFilename)

#Scenario: Upload and create vnf

@given(u'ApiAction object exists')
def step_impl(context):
    api = context.api
    try:
      api
    except NameError:
      raise EnvironmentError('Api object does not exist')

@given(u'a zip is made')
def step_impl(context):
    context.execute_steps(u'then a zip file containing the VNF is created')

@when(u'Zip is uploaded to catalog')
def step_impl(context):
    api = context.api
    api.uploadZipToCatalog()

@when(u'VNF is created it')
def step_impl(context):
    api = context.api
    api.createVnf()

@then(u'the VNF appears in CBAM')
def step_impl(context):
    api = context.api
    
    if api.checkVnfStatus(api.vnfdId)[1] != True:
      raise ValueError("Vnf was not created succesfully")  

#Scenario: Instantiate VNF


@given(u'VNF is uploaded to CBAM')
def step_impl(context):
    context.execute_steps(u'then the VNF appears in CBAM')

@when(u'VNF is instantiated through API')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.instantiateVnf()

@then(u'Instantiation status is started')
def step_impl(context):
    api = context.api
    time.sleep(5)
    print api.vnfInstanceId
    actual_state = api.checkLcmStatus(api.vnfInstanceId, False)[1]
    print "actual_state: " + actual_state
    if actual_state != "STARTED" and actual_state != "STARTING" :
      raise ValueError("vnf instantiation was not started successful")

#Scenario: Poll VNF


@given(u'VNF Instantiation has begun')
def step_impl(context):
    context.execute_steps(u'then Instantiation status is started')

@when(u'polling Cbam has ended')
def step_impl(context):
    api = context.api
    api.pollLcmStatus(True)
    
@then(u'Instantion status is finished')
def step_impl(context):
    api = context.api
    if api.checkLcmStatus(api.vnfInstanceId, False)[1] != "FINISHED" and api.checkLcmStatus(api.vnfInstanceId, False)[1] != "COMPLETED":
      raise ValueError("Vnf instantiation failed")  
    #Disables HA action after instantiation

@given(u'I disable auto_ha')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.patchHaAction("false")
