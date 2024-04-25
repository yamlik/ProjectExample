import requests
import json
import os
import yaml
import zipfile
import subprocess
import argparse
import time
import urllib

class ApiAction():

  zipFilename="tester.zip"
  password=""
  json_file=""
  ip=""
  client_id=""
  client_secret=""
  vnf_path=""
  auth_header=""
  vnfdFile="" 
  vnfdId=""
  vnfInstanceId=""
  inputVnfdId=""
  refresh_token=""
  access_token=""  
  api_version=""
  cbam_version=""
  use_floating_ip=""
  vnfPkgId=""

  def setInputVnfdId(self, vnfdid):
    self.inputVnfdId = vnfdid

  def getVariables(self, yamlFile):
  
    with open(yamlFile) as f:
      info = yaml.load(f, Loader=yaml.FullLoader)
    self.json_file = info["vnfInfo"]["vnf_path"] + "/" + info["vnfInfo"]["json_file"]
    self.ip = info["vnfInfo"]["ip"]
    self.client_id = info["vnfInfo"]["client_id"]
    self.client_secret = info["vnfInfo"]["client_secret"]
    self.vnf_path = info["vnfInfo"]["vnf_path"]
    self.cbam_version = info["vnfInfo"]["cbam_version"]
    self.use_floating_ip = info["vnfd"]["use_floating_ip"]
    
    with open("clouds.yaml") as d:
      cbisInfo = yaml.load(d, Loader=yaml.FullLoader)  

    os.environ['PYTHONWARNINGS']="ignore:Unverified HTTPS request"  
    self.password = cbisInfo["clouds"][info["vnfInfo"]["vnf_id"]]["auth"]["password"]

   
    self.api_version = info["vnfInfo"]["api_version"] 
  
  #Authentication through a set user
  def getAuthentication(self):
    payload = {'grant_type': 'client_credentials', 'client_id': self.client_id, 'client_secret': self.client_secret}
    r = requests.post(self.ip+'/auth/realms/cbam/protocol/openid-connect/token', verify=False, data=payload)
    access_token = json.loads(r.text)["token_type"] + " " + json.loads(r.text)["access_token"]
    self.auth_header = {'Authorization': access_token}
    self.refresh_token = json.loads(r.text)["refresh_token"]
    self.access_token = json.loads(r.text)["access_token"]
  
  def refreshAuthentication(self):
    payload = {'grant_type': 'refresh_token', 'client_id': self.client_id, 'client_secret': self.client_secret, 'refresh_token': self.refresh_token}
    r = requests.post(self.ip + '/auth/realms/cbam/protocol/openid-connect/token', verify=False, data=payload)    
    access_token = json.loads(r.text)["token_type"] + " " + json.loads(r.text)["access_token"]
    self.auth_header = {'Authorization': access_token}
    self.refresh_token = json.loads(r.text)["refresh_token"]
    self.access_token = json.loads(r.text)["access_token"]

  #The VNF id is given to the python script which then writes it to the vnfd, first it checks that the vnfdId is not in use at CBAM
  def writeVnfdId(self): 
    self.authTest() 
    if(self.cbam_version == "19.5"):
      getFile = requests.get(self.ip +'/vnfpkgm/v1/vnf_packages', verify=False, headers=self.auth_header)
      print(getFile)
      print(getFile.text)
      cbamCatalog = (json.loads(getFile.text))
      usedVnfdIds = []
      for vnfdId in cbamCatalog:
        if(vnfdId["operationalState"] == "ENABLED"): 
          usedVnfdIds.append(vnfdId["vnfdId"])
    else:
      getFile = requests.get(self.ip +'/api/catalog/adapter/vnfpackages', verify=False, headers=self.auth_header)
      cbamCatalog = (json.loads(getFile.text))
      usedVnfdIds = []
      for vnfdId in cbamCatalog:
        usedVnfdIds.append(vnfdId["vnfdId"])
    if self.inputVnfdId in usedVnfdIds:
      raise ValueError("The given vnfdId is already in use")
    else:
      with open(self.vnfdFile) as f:
        vnfd = yaml.load(f, Loader=yaml.FullLoader)
      vnfd["topology_template"]["substitution_mappings"]["properties"]["descriptor_id"] = self.inputVnfdId
      vnfd["topology_template"]["substitution_mappings"]["properties"]["product_name"] = self.inputVnfdId
      vnfd["topology_template"]["substitution_mappings"]["properties"]["product_info_name"] = self.inputVnfdId     
      with open(self.vnfdFile, 'w') as f:
        yaml.dump(vnfd, f)
  
  #A zip archive is created from the VNF directory to be uploaded to Catalog
  def createZip(self): 
    print(self.zipFilename + ":::" + self.vnf_path)
    subprocess.call(["./scripts/createZipScript.sh", self.zipFilename, self.vnf_path])

  #Method made for creating tgzs of custom ansible directories 
  def createTgz(self, tgz_name, dir_name):
    subprocess.call("cd "+ self.vnf_path+"ansible/custom_ansible; tar -czvf "+ tgz_name + " "+ dir_name+"/", shell=True) 
 
  #Get the vnfd defined in TOSCA-metadata
  def getVnfd(self):
    tosca_path = self.vnf_path+"/TOSCA-Metadata/TOSCA.meta"
    toscafile = yaml.load(open(tosca_path), Loader=yaml.FullLoader)
    self.vnfdFile = self.vnf_path + "/" + toscafile["Entry-Definitions"]
  
  #Upload the created zip to CBAM catalog
  def uploadZipToCatalog(self):
    self.authTest()
    zipFile = {'content' : ('file', open(self.zipFilename,'rb'))}
    zipFile_new = open(self.zipFilename,'rb').read()
    
    header = {'Authorization': (self.auth_header)['Authorization'], 'Content-Type': 'application/zip'}
 
    if(self.cbam_version == "19.5"):
      vnfpckgid = requests.post(self.ip + '/vnfpkgm/v1/vnf_packages', verify=False, headers=self.auth_header)
      vnf_pckg_id = json.loads(vnfpckgid.text)["id"]
      putFile = requests.put(self.ip +'/vnfpkgm/v1/vnf_packages/'+vnf_pckg_id+'/package_content', verify=False, data=zipFile_new, headers=self.auth_header)
      time.sleep(10)
      self.vnfdId = self.getFromCatalog(vnf_pckg_id, "")[1]
    else: 
      postFile = requests.post(self.ip +'/api/catalog/adapter/vnfpackages', files=zipFile, verify=False, headers=self.auth_header)
      self.vnfdId = json.loads(postFile.text)["vnfdId"]
  
 
  #get a tuple that contains both the catalog_id and vnfdId. Can be searched with the other variable as none
  def getFromCatalog(self, catalog_id, vnfdId):
    self.authTest()
    catalogList = requests.get(self.ip +'/vnfpkgm/v1/vnf_packages', verify=False, headers=self.auth_header)
    cbamCatalog = (json.loads(catalogList.text))
    print("catalog_id "+ catalog_id)
    for vnf in cbamCatalog:
      
      if(catalog_id == vnf["id"]):
        return (vnf["id"], vnf["vnfdId"])
      elif(vnf["operationalState"] != "DISABLED"):
        if(vnf["vnfdId"] == vnfdId):
          return (vnf["id"], vnf["vnfdId"])
    return ("None","None")

  #Create the VNF in CBAM
  def createVnf(self):
    self.authTest() 
    body = {"vnfdId": self.vnfdId, "vnfInstanceName": self.vnfdId, "vnfInstanceDescription": "testingApis"}
  
    createVnf = requests.post(self.ip+'/vnflcm/v1/vnf_instances', verify=False, headers=self.auth_header, data=body)
  
    vnfInfo = createVnf.text
    print(vnfInfo)
    self.vnfInstanceId = (json.loads(vnfInfo)["id"])
  
  def checkVnfStatus(self, vnfdId):
    self.authTest()
    checkVnf = requests.get(self.ip+'/vnflcm/v1/vnf_instances', verify= False, headers=self.auth_header)
    for vnf in json.loads(checkVnf.text):
      if vnfdId == vnf["vnfdId"]:
        self.vnfdId = vnfdId
        return (vnf["id"], True)
    return ("none", False)
  

  #Instantiate the created vnf and also set json_file password parameter
  def instantiateVnf(self):
    self.authTest()
  
    if (self.api_version == 2): 
        header = {'Authorization': (self.auth_header)['Authorization'], 'Content-Type': 'application/json', 'TEST-VNFM-API-Version': '3.0'}
    elif (self.api_version == 4):
        self.getAuthentication()
        header = {
        'Authorization': str((self.auth_header)['Authorization']),
        'Content-Type': 'application/json',
        'TEST-VNFM-API-Version': '4.0'
        }
    else:
        header = {'Authorization': (self.auth_header)['Authorization'], 'Content-Type': 'application/json', 'TEST-VNFM-API-Version': '3.3'}
    with open(self.json_file) as f:
      vnfd = json.load(f)
    if (self.api_version == 4):
        vnfd["vimConnectionInfo"][0]["accessInfo"]["password"] = self.password
    else:
        vnfd["vims"][0]["accessInfo"]["password"] = self.password
    with open(self.json_file, 'w') as f:
      json.dump(vnfd, f, indent=4)
    
    cbam_json = (open(self.json_file,'r+').read())
    print(json.dumps(json.loads(cbam_json), indent=4))
    if (self.api_version == 4):
        print("url= "+self.ip+'/vnflcm/v1/vnf_instances/'+self.vnfInstanceId+"/instantiate")
        instantiatePost = requests.post(self.ip+'/vnflcm/v1/vnf_instances/'+self.vnfInstanceId+"/instantiate", verify=False, headers=header, data=cbam_json)
    else:
        instantiatePost = requests.post(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId+"/instantiate", verify=False, headers=header, data=cbam_json)
    print("instantiate post return:\n"+instantiatePost.text)
  
  def checkLcmStatus(self, vnfInstanceId, raise_error):
    self.authTest()
    lcmStatus = requests.get(self.ip+'/vnfm/lcm/v3/vnfs/' + vnfInstanceId + "/operation_executions", verify=False, headers=self.auth_header)
    status = json.loads(lcmStatus.text)[0]["status"]
    print(lcmStatus.text)
    print(status)
    if status == "FAILED" or status == "FAILED_TEMP":
      if (raise_error):
        raise ValueError(json.loads(lcmStatus.text)[0]["error"])
      else:  
        return ("FAILED", json.loads(lcmStatus.text)[0]["error"])
    elif status == "FINISHED":
      return ("FINISHED", status)
    else:
      return ("IN PROGRESS", status) 
 
  #Checks the LCM status from executions, return the latest one which matches the LCM defined. If none found return Not found 
  def checkLcmFromExecutions(self, lcm):
    self.authTest()
    lcmStatus = requests.get(self.ip+'/vnfm/lcm/v3/vnfs/' + self.vnfInstanceId + "/operation_executions", verify=False, headers=self.auth_header)
    lcmList = []
    for lcm_action in json.loads(lcmStatus.text):
      if (lcm_action["operationType"] == "OTHER"):
        executed_lcm = lcm_action["operationName"].split(":")[1]
      else:
        executed_lcm = lcm_action["operationType"]      
      if(executed_lcm.lower() not in lcmList):
        lcmList.append(executed_lcm.lower())
      if ((executed_lcm.lower()) == (lcm.lower())):
        if (lcm_action["status"] == "FAILED"):
          return ("error", lcm_action["error"]["detail"])
        elif (lcm_action["status"] == "FINISHED"):
          return ("Finished", "")
    return ("Not found", "Not in "+str(lcmList))
  
  #Checking VNF instantiation status
  def checkInstantiationStatus(self):
    self.authTest()
    instantiationStatus = requests.get(self.ip+'/vnfm/lcm/v3/vnfs/' + self.vnfInstanceId, verify=False, headers=self.auth_header)
    if json.loads(instantiationStatus.text)["instantiationState"] != "INSTANTIATED":
      return False
    else:
       return True

  #Since tokens expire after 5 minutes, we use authTest to confirm that they are still usable. If the request returns False a new authentication is fetched using a refreshToken. This means that getAuthentication must always be executed before authTest.
  def authTest(self):
    r = requests.post(self.ip+'/auth/realms/cbam/protocol/openid-connect/token/introspect', verify=False, data={"token": self.access_token}, auth=(self.client_id, self.client_secret))
    if not json.loads(r.text)["active"]:
      print("fetching auth")
      self.refreshAuthentication()
  
  #Poll the status of the LCM action every 1 minute, this is used to keep the script alive through lifecycle actions.
  def pollLcmStatus(self, raise_error):
    finished = False
    while(finished is False):
      #getStatus = requests.get(self.ip+'/vnfm/lcm/v3/vnfs/' + self.vnfInstanceId + "/operation_executions", verify=False, headers=self.auth_header)
      operationsText = self.checkLcmStatus(self.vnfInstanceId, raise_error)
      if operationsText[0] != "FINISHED" and operationsText[0] != "FAILED":
        time.sleep(60)
      else:
        finished = True
    
  def patchHaAction(self, direction):
    self.authTest()
    if (self.api_version == 2):
        header = {'Authorization': (self.auth_header)['Authorization'], 'Content-Type': 'application/json', 'TEST-VNFM-API-Version': '3.0'}
    else:
        header = {'Authorization': (self.auth_header)['Authorization'], 'Content-Type': 'application/json', 'TEST-VNFM-API-Version': '3.3'}
    if (direction == "false"):
        haRequest = {"vnfConfigurableProperties":[{ "name" : "operation_triggers", "value": { "auto_ha": { "enabled": False } } }]}
    else:
        haRequest = {"vnfConfigurableProperties":[{ "name" : "operation_triggers", "value": { "auto_ha": { "enabled": True }}}]}
    haAction = requests.patch(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId, verify=False, headers=header, data=json.dumps(haRequest))
    print(haAction)
    print(haAction.text)

  #Runs scaling action for processingONaspect group, can either be out or in depending on direction, correct input "SCALE_OUT" and "SCALE_IN"
  def scaleVnf(self, direction, aspectId):
    self.authTest()
    scaleVnfRequest = {'type' : direction, 'aspectId': aspectId, 'numberOfSteps': 1}
    scaleAction = requests.post(self.ip + '/vnflcm/v1/vnf_instances/' + self.vnfInstanceId + '/scale', verify=False, headers=self.auth_header, data=json.dumps(scaleVnfRequest))
    print(scaleAction.text)

  def scaleVnf_steps(self, direction, aspectId, steps):
    self.authTest()
    scaleVnfRequest = {'type' : direction, 'aspectId': aspectId, 'numberOfSteps': steps}
    scaleAction = requests.post(self.ip + '/vnflcm/v1/vnf_instances/' + self.vnfInstanceId + '/scale', verify=False, headers=self.auth_header, data=json.dumps(scaleVnfRequest))
    print(scaleAction.text)

  #Returns scale level of defined aspect_id
  def scaleAspectStatus(self, aspectId):
    self.authTest()
    aspect_status = requests.get(self.ip + '/vnfm/lcm/v3/vnfs/' + self.vnfInstanceId + '/scale_status/'+ aspectId, verify=False, headers=self.auth_header)
    print(aspect_status.text)
    return json.loads(aspect_status.text)["scaleLevel"]

 
  #Runs healing action on vnf
  def healVnf(self,heal_type): 
    self.authTest()
    ##need revisit later, because need to update the structure of context data
    healData={'cause': 'testing', 'additionalParams': { 'heal_processingON': 0, 'heal_crdb': 0, 'heal_cgf': 0, 'heal_ui': 0, 'heal_db': 0, 'heal_oam': 0, 'heal_processingOFF': 0 }} 
    healData={'cause': 'testing', 'additionalParams': { 'heal_processingON': False, 'heal_crdb': False, 'heal_cdf': False, 'heal_mapper': False, 'heal_outage': False, 'heal_epccgf': False, 'heal_ui': False, 'heal_db': False, 'heal_oam': False, 'heal_processingOFF': False }}
    healData['additionalParams'][heal_type]=1
    healAction = requests.post(self.ip + '/vnflcm/v1/vnf_instances/' + self.vnfInstanceId + '/heal', verify=False, headers=self.auth_header, data=json.dumps(healData))
    print(healAction.text)  
  
  def updatePasswordLCM(self, recv_data):
    self.authTest()
    custom_request = {"additionalParams": {}}
    for key in recv_data:
      print(key)
      print(recv_data[key])
      custom_request["additionalParams"][key] = recv_data[key]
    print(json.dumps(custom_request))
    action = requests.post(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId+'/custom/update_passwords', verify=False, headers=self.auth_header, data=json.dumps(custom_request))
    print(action.text)

  def haLCM(self):
    self.authTest()
    custom_request = {"additionalParams": {}}
    print(json.dumps(custom_request))
    action = requests.post(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId+'/custom/ha', verify=False, headers=self.auth_header, data=json.dumps(custom_request))
    print(action.text)

  def rpmUpdateLCM(self, recv_data):
    self.authTest()
    custom_request = {"additionalParams": {}}
    for key in recv_data:
      print(key)
      print(recv_data[key])
      custom_request["additionalParams"][key] = recv_data[key]
    print(json.dumps(custom_request))
    action = requests.post(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId+'/custom/rpm_update', verify=False, headers=self.auth_header, data=json.dumps(custom_request))
    print(action.text)

  def customLcmAction(self, recv_data):
    self.authTest()
    custom_request = {"additionalParams": {}}
    for key in recv_data:
      if (key != "ansible_extra_vars"):
        custom_request["additionalParams"][key] = [recv_data[key]]
      else:
        custom_array = []
        for nkey in recv_data[key].split(','):
          custom_array.append(nkey.strip())
        print(custom_array)
        custom_request["additionalParams"][key] = custom_array
          
    print(json.dumps(custom_request))
    action = requests.post(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId+'/custom/run_custom_ansible', verify=False, headers=self.auth_header, data=json.dumps(custom_request))
    print(action.text)

  def modify(self, recv_data):
    self.authTest()
    modify_request = {"extensions": []}
    for key in recv_data:
      array = modify_request["extensions"]
      array.append({"name": key, "value": recv_data[key]})
      modify_request["extensions"] = array
    print(json.dumps(modify_request))
    modifyAction = requests.patch(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId, verify=False, headers=self.auth_header, data=json.dumps(modify_request))
    print(modifyAction.text)
    print(modifyAction)

  #Searches all vnfs for one which contains the correct test_id in extensions and then retrieves vnfdId and vnfInstanceId from the same vnf, this is designed to be run after the vnf is instantiated to recover some local variables. Mostly for smokeTestAction which is run in a different environment than Instantiation. 
  def getVnfInfo(self, yamlPath):
    self.authTest()
    with open(yamlPath) as f:
      vnfd = yaml.load(f, Loader=yaml.FullLoader)
    vnfdId = ""
    checkVnf = requests.get(self.ip+'/vnflcm/v1/vnf_instances', verify= False, headers=self.auth_header)
    testerId = vnfd['vnfd']['testId']
    #To avoid unneccessary cycling through all vnfs, we stop when we find one vnf containing the correct test_id
    found = 0
    
    for vnf in json.loads(checkVnf.text):
      if found < 1:
        info = json.loads(requests.get(self.ip + '/vnflcm/v1/vnf_instances/' + vnf["id"], verify=False, headers=self.auth_header).text)
        extensions = info["extensions"]
        if "test_id" in extensions:
          if (extensions["test_id"] == testerId):
            self.vnfdId =  vnf["vnfdId"]
            self.vnfInstanceId = vnf["id"]
            self.vnfPkgId = vnf["vnfPkgId"]
            found += 1

    if found == 1:
      vnfInfo = requests.get(self.ip+'/vnflcm/v1/vnf_instances/' + self.vnfInstanceId, verify=False, headers=self.auth_header)
      return(vnfInfo.text)
    else: 
      raise ValueError(u'Not found')


  #This function deletes a vnf that has finished/failed instantiation as well as its package from the catalog.
  def removeVnf(self):
    self.authTest()
    self.terminateVnf()
    self.pollLcmStatus(True)
    self.deleteVnf()
    time.sleep(5) 
    self.deleteFromCatalog()

  #Terminates the created VNF
  def terminateVnf(self):
    terminateVnfRequest = { "terminationType": "FORCEFUL" }
    terminateVnf = requests.post(self.ip+'/vnflcm/v1/vnf_instances/' + self.vnfInstanceId + "/terminate", verify=False, headers=self.auth_header, data=json.dumps(terminateVnfRequest))
    print(terminateVnf.text)
  
  #Deletes the VNF from VNFs
  def deleteVnf(self):
    deleteInstance = requests.delete(self.ip+'/vnfm/lcm/v3/vnfs/'+self.vnfInstanceId, headers=self.auth_header,verify=False)
    print(deleteInstance.text)
   
  #Deletes the vnf description from catalog
  def deleteFromCatalog(self):
    catalogDelete = requests.delete(self.ip+'/vnfpkgm/v1/vnf_packages/' + self.vnfPkgId, headers=self.auth_header, verify=False)
    print(catalogDelete.text)

