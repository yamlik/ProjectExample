import openstack
import paramiko
import sys
import subprocess
import time
import os
import json
import cbamApiClass
import sshActionWrapper 
import writeValues
import pprint
   
test_config = ("cbam19mp1_3")

yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"

#writeValues.main(test_config)

#Creating global variables for cbam api usage and for paramiko ssh usage    
global api 
api = cbamApiClass.ApiAction()
global stream 
stream = sshActionWrapper.Connections()

    #CBIS specific setup
#api.getVariables(yamlPath)
#api.getAuthentication()

api.getVariables(yamlPath)
api.getAuthentication()
api.getVnfInfo(yamlPath)

stream = sshActionWrapper.Connections()
stream.getIpsAndSecrets(yamlPath, test_config)

modify_data = {"passwd_TEST_gui": "dev0ps4life!A", "passwd_keycloak_admin": "dev0ps4life!B", "passwd_unix_root_user":"dev0ps4life!C", "passwd_unix_user":"dev0ps4life!D"}
custom_lcm_data = { "run_on_all": "hello_world", "run_on_online": "", "run_on_offline": "", "run_on_ui": "", "run_on_db": "", "run_on_oam": "", "ansible_extra_vars": "message='Hello World!', filename=helloWorld.txt" }
password_data = {u'old_kc_admin_password': u'dev0ps4life!G'}
#api.modify(modify_data)
#api.customLcmAction(custom_lcm_data)
#print(api.checkLcmFromExecutions("HA"))
#api.updatePasswordLCM(password_data)
#api.patchHaAction("false")

#print(stream.ips)

#api.updatePasswordLCM(password_data)

stream.checkConnection("processing_online0")
stream.changeUser("processing_online0", "root", "dev0ps4life!C")


#status = api.checkLcmFromExecutions("Run Custom Ansible")
#print(status)
#stream.checkConnection("processing_online0")
#stream.changeFileOwner("processing_online0", "/home/TEST/helloWorld.txt", "TEST")
#print(stream.checkFileContent("processing_online0", "/home/TEST/helloWorld.txt", "Hello World!"))
#stream.checkFileContent("processing_online0","/home/TEST/hellowWorld.txt", "Hello World!")

#stream.changeUser("processing_online0", "TEST", "C0mptel!A")

#api.authTest()

#stream.

#jsoni = json.loads(api.getVnfInfo())["instantiatedVnfInfo"]
#
#for key, value in jsoni.items():
#  pprint.pprint("Key:")
#  pprint.pprint(key)
#  pprint.pprint(value)
