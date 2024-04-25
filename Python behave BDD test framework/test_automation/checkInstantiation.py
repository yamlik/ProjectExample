import openstack

import paramiko
import sys
import subprocess
import time
import os
import yaml
import os.path

import cbamApiClass
import cbam21ApiClass
import sshActionWrapper 
import writeValues

try: 
    test_config = (os.environ['TEST_CONFIG'])
  
except: 
    print("Value for TEST_CONFIG has not been set")    

yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"

writeValues.main(test_config)

if os.path.exists("yamlFiles/"+test_config+"/"+test_config+".yaml") is False:
    raise EnvironmentError(u'vnfInfo does not exist in the current working directory')

with open( yamlPath ) as f:
  identify_cbam = yaml.load( f, Loader=yaml.FullLoader )

if identify_cbam["vnfInfo"]["cbam_version"] =="21":
  api = cbam21ApiClass.ApiAction()

if identify_cbam["vnfInfo"]["cbam_version"] =="19.5":
  api = cbamApiClass.ApiAction()

node_type_value = identify_cbam["vnfInfo"]["node_type_value"]
                            
api.getVariables(yamlPath)
api.getAuthentication()

try:
    api.getVnfInfo(yamlPath)
    print("Defined cbam not instantiated")
except:
    print("true")
