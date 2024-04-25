import os
import json
import yaml
import time
from behave import *

import cbamApiClass
import writeValues

@given(u'test vnfInfo exists')
def step_impl(context):    
  
    test_config = context.test_config
 
    try: 
      test_config = (os.environ.get('TEST_CONFIG'))
  
    except: 
      print("Value for TEST_CONFIG has not been set")    
    
    writeValues.main(test_config)

    if os.path.exists("yamlFiles/"+test_config) is False:
      raise EnvironmentError(u'vnfInfo does not exist in the current working directory')

@given(u'it contains test vnf_path')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f: 
      info = yaml.load(f, Loader=yaml.FullLoader)
    try:
      k = info["vnfInfo"]["vnf_path"]
    except KeyError:
      raise ValueError(u'vnf_path key does not exist in the vnfInfo.yaml file')

@when(u'directory in test vnf_path is checked')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    if os.path.exists(info["vnfInfo"]["vnf_path"]) is False:
      raise EnvironmentError(u'vnf_path directory does not exist')

@then(u'The test directory contains appropriate directories')
def step_impl(context):
    test_config = context.test_config
    with open("yamlFiles/"+test_config+"/"+test_config+".yaml") as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    requiredDirs = ["ansible", "hot", "javascript", "mistral-workbooks", "TOSCA-Metadata"]

    for dir in requiredDirs:
      if os.path.exists(info["vnfInfo"]["vnf_path"] + dir) is False:
        raise EnvironmentError(u'directory'+ dir+ ' does not exist in vnf_path directory')


@when(u'test variables are loaded')
def step_impl(context):
    test_config = context.test_config
    context.api.getVariables("yamlFiles/"+test_config+"/"+test_config+".yaml")

@when(u'test authentication is received')
def step_impl(context):
    context.api.getAuthentication()

@then(u'test ApiAction object has auth_header and client_id')
def step_impl(context):
    api = context.api
    if api.auth_header == "":
      raise ValueError(u'Auth_header was stored incorrectly')
    if api.client_id == "":
      raise ValuerError(u'Variables were not loaded correctly')

#Scenario: Create ApiAction() object

@given(u'test cbamApiClass exists')
def step_impl(context):
  if os.path.exists("cbamApiClass.py") is False:
    raise EnvironmentError(u'cbamApiClass.py does not exist in the current working directory')

@when(u'Scale out processingON instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT','processingONAspect')

@when(u'Scale out processingOFF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'processingOFFAspect')

@when(u'Scale in processingON instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'processingONAspect')

@when(u'Scale in processingOFF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'processingOFFAspect')

@then(u'scaling aspect group "{aspect_id}" is at "{count}"')
def step_impl(context, aspect_id, count):
    api = context.api  
    print(count)
    print(aspect_id)
    #We should be at the exact count
    assert( api.scaleAspectStatus(aspect_id) == int(count))

@when(u'Scale out crdb instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf_steps('SCALE_OUT','crdbAspect',3)

@when(u'Scale in crdb instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf_steps('SCALE_IN','crdbAspect',3)

@when(u'Scale in CGF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'cgfAspect')

@when(u'Scale out CGF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'cgfAspect')


@when(u'Scale in CDF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'cdfAspect')

@when(u'Scale out CDF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'cdfAspect')

@when(u'Scale in Outage instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'outageAspect')

@when(u'Scale out Outage instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'outageAspect')

@when(u'Scale in Mapper instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'mapperAspect')

@when(u'Scale out Mapper instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'mapperAspect')

@when(u'Scale in EPCCGF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_IN', 'epccgfAspect')

@when(u'Scale out EPCCGF instance')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.scaleVnf('SCALE_OUT', 'epccgfAspect')