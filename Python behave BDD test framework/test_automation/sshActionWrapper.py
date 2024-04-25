import paramiko
import sys
import subprocess
import os 
import xml.etree.ElementTree as ET
import requests
import yaml
import time
import json
import writeValues
import re
#Wrapper to access tools.pl commands more easier with just a function call, will store the current connections to a map connections and at the end close all connections.
class Connections():
    
  jump_ip = None
  jumphost_login = None
  connections = {}
  ips = {}
  key_path = None
  callnumber = 10000
  
#--------Connection stuff--------

  #This function configures jumphost for future connections, it is expected that the jumphost will be accessed through username and password
  def configJumpHost(self, jumphostIp, jumphostUsername, jumphostPw):
    jname=paramiko.SSHClient()
    jname.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    jname.connect(jumphostIp, username=jumphostUsername, password=jumphostPw)
    #We create a tuple as a template for the delete action
    jname.get_transport().set_keepalive(60)
    self.connections["jump_host"] = (jname, jname.open_sftp())

  #This function uses the jump host we have defined to create new connections to the oam network, outputs a pair of the client and sftp client it uses
  def connectThroughJump(self, destName, destUsername, destKey="", destPw=""):
    if destPw:    
      print("kappa"+self.ips[destName])
      destIp = self.ips[destName]
      vmtransport = self.connections["jump_host"][0].get_transport()
      dest_addr = (destIp, 22)
      local_addr = (self.jump_ip, 22)
      vmchannel = vmtransport.open_channel("direct-tcpip", dest_addr, local_addr)
       
      connection=paramiko.SSHClient()
      connection.set_missing_host_key_policy(paramiko.AutoAddPolicy())
      connection.connect(destIp, username=destUsername, password=destPw, sock=vmchannel)
      connection.get_transport().set_keepalive(60)
      
      sftp = connection.open_sftp()
      self.connections[destName] = (connection, sftp)
    elif destKey:
      print("sakka")
      print("kappa"+self.ips[destName])
      destIp = self.ips[destName]
      vmtransport = self.connections["jump_host"][0].get_transport()
      dest_addr = (destIp, 22)
      local_addr = (self.jump_ip, 22)
      vmchannel = vmtransport.open_channel("direct-tcpip", dest_addr, local_addr)
      
      print("Reached") 
      connection=paramiko.SSHClient()
      connection.set_missing_host_key_policy(paramiko.AutoAddPolicy())
      print(destIp + " : " + destUsername + " : " + destKey)
      connection.connect(destIp, username=destUsername, key_filename=destKey, sock=vmchannel)
      connection.get_transport().set_keepalive(60)

      sftp = connection.open_sftp()
      self.connections[destName] = (connection, sftp)
    else:
      raise ValueError("neither password or privatekey path was given")
 
  #Function that connects to destination ip with a username either with a private key or with password. Skips jumphost
  def connectToHost(self, destName, destUsername, destKey="", destPw=""):
    if destKey:
      destIp = self.ips[destName] 
      connection=paramiko.SSHClient()
      connection.set_missing_host_key_policy(paramiko.AutoAddPolicy())
      connection.connect(destIp, username=destUsername, key_filename=destKey)
      connection.get_transport().set_keepalive(60)
      self.connections[destName] = (connection, connection.open_sftp())  
    elif destPw:
      destIp = self.ips[destName] 
      connection=paramiko.SSHClient()
      connection.set_missing_host_key_policy(paramiko.AutoAddPolicy())
      connection.connect(destIp, username=destUsername, password=destPw)
      connection.get_transport().set_keepalive(60)
      self.connections[destName] = (connection, connection.open_sftp())
    else:
      raise ValueError("neither password or privatekey path was given")
  
  def checkConnection(self, host):
    #We check if connection to host has been created
    # This is if verifies that the connections we have previously made are valid. The connection will break if the machine reboot or is rebuilt or is scaled in and after that scaled out.
    if host in self.connections.keys():
        try:
            assert(self.checkProcessStatus(host, "sshd") == "active") 
        except:
            self.connections.pop(host, None)
    if host not in self.connections.keys():
        print (host + " not in ")
        for key in self.connections.keys():
            print(key+ "   ")
        #If jump host is given, create a connection to it and then connect through it, otherwise straight connection.
        if self.jump_ip:
            if host == "jump_host":
              self.configJumpHost(self.jump_ip, self.jumphost_login[0], self.jumphost_login[1])
            else:
              self.configJumpHost(self.jump_ip, self.jumphost_login[0], self.jumphost_login[1])
              print("checkconnection to "+host+"jump ip = "+self.jump_ip)
              self.connectThroughJump(host, self.jumphost_login[0], self.key_path)
        else:
            self.connectToHost(host, "TEST", self.key_path)
       
  #Loads ip variables and login creds from conf yaml file 
  def getIpsAndSecrets(self, yamlFile, test_config):
    with open(yamlFile) as f:
      vnfd = yaml.load(f, Loader=yaml.FullLoader)
    #Maps all oam ips in vnfd to ips map from where we can setup connections with connectToHost
    
    if vnfd["vnfInfo"]["use_json"] is False:  
      #Add all processingOnIps
      for i, ip in enumerate(vnfd["vnfd"]["processingON_oam_ip"]):
          self.ips["processing_online"+str(i)] = ip
  
      #Add all uiIps
      for i, ip in enumerate(vnfd["vnfd"]["ui_oam_ip"]):
          self.ips["ui"+str(i)] = ip
  
      #Add all processingOffIps
      for i, ip in enumerate(vnfd["vnfd"]["processingOFF_oam_ip"]):
          self.ips["processing_offline"+str(i)] = ip
  
      #Add all dbIps 
      for i, ip in enumerate(vnfd["vnfd"]["db_oam_ip"]):
          self.ips["db"+str(i)] = ip  
    else: 
      with open('yamlFiles/'+test_config+'/cbam.json') as j:
        jsoni = json.load(j)
     
      #This horrible loop basically parses the ip values to ips from the cbam.json which is required if we are testing without values in vnfd extensions. 
      for type in jsoni["extVirtualLinks"]:
        #Firts we check for values which are under "oam" network
        #if type["virtualLinkDescId"] == "oam":
        if "oam" in type["id"]:
          for vdus in type["extCps"]:
            #Then we have hardcoded identifiers for different vm types
            if vdus["cpdId"] == "processingON_oam_ecp" or re.match( "(oam_processingON_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["processing_online"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  #Loop through all the addresses and add only the ip to the self.ips, this address[] also contains the subnet ID.
                  self.ips["processing_online"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "processingOFF_oam_ecp" or re.match( "(oam_processingOFF_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4 :
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["processing_offline"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["processing_offline"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "cgf_oam_ecp" or re.match( "(oam_cgf_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["cgf"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["cgf"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "cdf_oam_ecp" or re.match( "(oam_cdf_)\d(_ECP)",vdus["cpdId"]): 
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["cdf"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["cdf"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "mapper_oam_ecp" or re.match( "(oam_mapper_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["mapper"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["mapper"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "epccgf_oam_ecp" or re.match( "(oam_epccgf_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["epccgf"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["epccgf"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "outage_oam_ecp" or re.match( "(oam_outage_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["outage"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["outage"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "crdb_oam_ecp" or re.match( "(oam_crdb_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["crdb"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["crdb"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "db_oam_ecp" or re.match( "(oam_db_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["db"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["db"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "ui_oam_ecp" or re.match( "(oam_ui_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["ui"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["ui"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "oam_ecp" or re.match( "(oam_oam_)\d(_ECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["oam"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["oam"+str(i)] = address["ip"]
            #Adding vips also to the map
            elif vdus["cpdId"] == "ui_OAM_IPv4_virtual_ECP" or re.match( "(oam_ui_)\d(_MovingECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["ui_vip"] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["ui_vip"] = address["ip"]
            elif vdus["cpdId"] == "OAM_IPv4_virtual_ECP" or re.match( "(oam_oam_)\d(_MovingECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["oam_vip"] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["oam_vip"] = address["ip"]
            elif vdus["cpdId"] == "DB_OAM_IPv4_virtual_ECP" or re.match( "(oam_db_)\d(_MovingECP)",vdus["cpdId"]):
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["db_vip"] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["db_vip"] = address["ip"]
            elif vdus["cpdId"] == "processingOFF_OAM_IPv4_virtual_ECP" or re.match( "(oam_processingOFF_)\d(_MovingECP)",vdus["cpdId"]): 
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["processingOFF_vip"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["processingOFF_vip"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "cgf_OAM_IPv4_virtual_ECP" or re.match( "(oam_cgf_)\d(_MovingECP)",vdus["cpdId"]): 
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["cgf_vip"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["cgf_vip"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "cdf_OAM_IPv4_virtual_ECP" or re.match( "(oam_cdf_)\d(_MovingECP)",vdus["cpdId"]): 
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["cdf_vip"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["cdf_vip"+str(i)] = address["ip"]
            elif vdus["cpdId"] == "outage_OAM_IPv4_virtual_ECP" or re.match( "(oam_outage_)\d(_MovingECP)",vdus["cpdId"]): 
              if vnfd["vnfInfo"]["api_version"] == 4:
                for i, ip in enumerate(vdus["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"]):
                  self.ips["outage_vip"+str(i)] = ip
              else:
                for i, address in enumerate(vdus["addresses"]):
                  self.ips["outage_vip"+str(i)] = address["ip"]

    self.jump_ip = vnfd["vnfInfo"]["jumphost_ip"]    
    self.jumphost_login = vnfd["vnfInfo"]["jumphost_login"]
    self.zone1 = jsoni["zones"][0]["zoneId"]
    self.zone2 = jsoni["zones"][1]["zoneId"]
    self.key_path = vnfd["vnfd"]["authorized_private_key_path"]    
  
#--------Generic ssh wrapping stuff--------

  #Walks through the directory recursively and saves all paths to all files to the array
  def createDirStructure(self, dir, filearray):
    for subdir, dirs, files in os.walk(dir):
      for file in files:
        filearray.append((file,os.path.join(subdir, file)))
  
  #Requires the creation of array before calling, takes in 2 strings as source and dest and one array and also which machine to copy to, if no array is given it defaults to empty array. This way we don't get info out of the function, meaning that if we want to import el files for example later on, we don't have an array structure of the el files from which we can easily import the files.
  def copyArrayToDir(self, sourceDir, destDir, destMachine, array = []):
    jhost = self.connections[destMachine][0]
    sftp = self.connections[destMachine][1]    

    stdin, stdout, stderr = jhost.exec_command("rm -rf "+destDir)
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("mkdir "+ destDir)  
    stdout.read()
    self.createDirStructure(sourceDir, array)
    #Copy files
    time.sleep(5)
    for file, path in array:
      print(path + " : "+ destDir + "/" + file)
      print("uploading "+ path + " to " + destDir + file)
      sftp.put(path, (destDir+file))  

  def copyFileToHost(self, source, dest, host):
    jhost = self.connections[host][0]
    sftp = self.connections[host][1]
    sftp.put(source, dest) 
 
  def checkFloatingIp(self, ui_vip):
    print(subprocess.check_output(["cat clouds.yaml"], shell=True))
    with open("clouds.yaml") as f:
      info = yaml.load(f, Loader=yaml.FullLoader)
      os_user=info["clouds"]["ipv4"]["auth"]["username"]
      os_pass=info["clouds"]["ipv4"]["auth"]["password"]
      os_project=info["clouds"]["ipv4"]["auth"]["project_name"]
      os_auth_url=info["clouds"]["ipv4"]["auth"]["auth_url"]
      os_domain=info["clouds"]["ipv4"]["auth"]["user_domain_name"]
    print(subprocess.check_output(["printenv | grep 'OS_'"], shell=True))
    print(subprocess.check_output(["unset OS_PROJECT_ID && unset OS_DEFAULT_DOMAIN_NAME && unset OS_USER_DOMAIN_NAME && unset OS_PROJECT_NAME && openstack --insecure floating ip list --os-auth-url "+os_auth_url+" --os-password "+os_pass+" --os-username "+os_user+" --os-project-name "+os_project+" --os-domain-name "+os_domain], shell=True))
    for line in subprocess.check_output(["unset OS_PROJECT_ID && unset OS_DEFAULT_DOMAIN_NAME && unset OS_USER_DOMAIN_NAME && unset OS_PROJECT_NAME && openstack --insecure floating ip list --os-auth-url "+os_auth_url+" --os-password "+os_pass+" --os-username "+os_user+" --os-project-name "+os_project+" --os-domain-name "+os_domain], shell=True).split("\n"):
      if ui_vip in line:
        return str(line.split('|')[2]).strip()
    return "Not found"
 
  #Runs tools.pl on processing_online0 and parses it output for correct streamname, returns status if stream is found otherwise false
  def checkStreamStatus(self, streamname):
    jhost = self.connections["processing_online0"][0]
    stdin, stdout, stderr = jhost.exec_command("tools.pl -c browsestreams")
    text = stdout.read()
    print(text) 
    arr = (text.split("\n"))
    for i in arr:
      wordlist = filter(None, i.split(' '))
      if( streamname in wordlist ):
        return (wordlist[-1])
    return "False"
  
  #Runs systemctl on defined host to check for process status, returns the output of is-active
  def checkProcessStatus(self, hostName, process):
    jhost = self.connections[hostName][0]
    stdin, stdout, stderr = jhost.exec_command(("systemctl is-active "+ process))
    text = stdout.read()
    return (text.strip())
  
  #First checks if file is found then if directory, if either one is true the file does exist on the remote host otherwise not
  def checkFileOnHost(self, hostName, file_path):
    jhost = self.connections[hostName][0]
    stdin, stdout, stderr = jhost.exec_command("if [ -f "+file_path+" ]; then echo 'true'; else echo 'no'; fi")
    file = stdout.read()
    stdin, stdout, stderr = jhost.exec_command("if [ -d "+file_path+" ]; then echo 'true'; else echo 'no'; fi")
    dire = stdout.read()
    
    if "true" in file or "true" in dire:
      return True
    else:
      return False

  #Cat the path which text_file defines and compare it to the string
  def checkFileContent(self, hostName, text_file, string):
    jhost = self.connections[hostName][0]
    stdin, stdout, stderr = jhost.exec_command("cat " + text_file)
    file_content = stdout.read()
    print(file_content)
    print(string)
    return(file_content.strip() == string.strip())

  #Same logic as checkFile, here instead of true remove the file
  def removeFileFromHost(self, hostName, file_path):
    jhost = self.connections[hostName][0]
    #If file exists then remove it, if directory exists remove it
    stdin, stdout, stderr = jhost.exec_command("if [ -f "+file_path+" ]; then rm -rf "+file_path+" ; fi")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("if [ -d "+file_path+" ]; then rm -rf "+file_path+" ; fi")
    stdout.read()
    print("removed file from "+ hostName)

  #wget file to /home/TEST directory
  def downloadFromHost(self, host, dest_host, url):
    jhost = self.connections[host][0]
    ip = self.ips[dest_host]
    print("wget --no-check-certificate https://"+ip+"/ansible-scripts" + url)
    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST; wget --no-check-certificate https://" + ip + "/ansible-scripts" + url)
    print(stdout.read())

  def changeFileOwner(self, hostName, file_path, user):
    jhost = self.connections[hostName][0]
    stdin, stdout, stderr = jhost.exec_command("if [ -f "+file_path+" ]; then sudo chown "+user+":"+user+" "+file_path+" ; fi")    
    print(stdout.read())
    print(stderr.read())

  def changeFilePermissions(self, hostName, file_path, permissions):
    jhost = self.connections[hostName][0]
    stdin, stdout, stderr = jhost.exec_command("if [ -f "+file_path+" ]; then sudo chmod "+permissions+ " "+ file_path+" ; fi")    
    print(stdout.read())
    print(stderr.read())


  def changeUser(self, host, user, password):
    jhost = self.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("echo "+ password + " | su - " + user)
    
    stdoutti = stdout.read()
    print(stdoutti)
    
    erri = stderr.read()
    if ("Enter login password:" in erri):
      erri = erri.replace("Enter login password:", "") 
    if(stdoutti.strip() == "" or erri.strip() != ""):
      raise ValueError("Incorrect password for "+ user + ". Error was "+ erri)    

#--------Seagull stuff--------

  #Modifies the xml file of seagul to correct values for testing, changes dest value to processing host 
  def setDestIp(self, seagullxml, destIp):
    eletree = ET.parse(seagullxml)
    for define in eletree.iter('define'):
      if(define.attrib["entity"] == "channel"):
        splittedString = define.attrib["open-args"].split(';')
        string = splittedString[0]+";dest="+destIp+":3868"
        define.attrib["open-args"] = string
        print(define.attrib)
    eletree.write(seagullxml)   
  
  def setCallAmount(self, seagullxml, call_amount):
    eletree = ET.parse(seagullxml)
    for define in eletree.iter('define'):
      if(define.attrib["name"] == "number-calls"):
        define.attrib["value"] = str(call_amount)
        print(define.attrib)
    eletree.write(seagullxml)   
    self.callnumber=call_amount
  
  def setCallRate(self, seagullxml, call_rate):
    eletree = ET.parse(seagullxml)
    for define in eletree.iter('define'):
      if(define.attrib["name"] == "call-rate"):
        define.attrib["value"] = str(call_rate)
        print(define.attrib)
    eletree.write(seagullxml)   
  

  #Copies seagull binary from artifactory to seagull directory. The file is stored here http://hki-repo01.tre.TEST-rdnet.net:8081/artifactory/devops-releases-local/com/TEST/rds/ but because it cannot be reached from poland slave it can also be downloaded from the jenkins slave.
  def downloadSeagull(self,context,sender_host):
    #r = requests.get("http://10.93.138.10/seagul/seagull_binary.tar")
    #print(r)
    
    stream = context.stream
    stream.checkConnection(sender_host)
    arr = []
    stream.copyArrayToDir('seagull/', '/home/TEST/seagull/', sender_host, arr)  
    
    #with open('seagull/seagull.binary', "wb") as f:
     # f.write(r.content)
   
  #Run seagull on defined host, requires seagull files on the host as well
  def setUpAndRunSeagull(self, host):
    print("connecting to"+ host)
    jhost = self.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("rm -rf /home/TEST/seagull/logs/")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("mkdir /home/TEST/seagull/logs/")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/seagull/; tar -xvf seagull.binary")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/seagull/; source .profile; seagull -bg -conf client.xml -dico dict_cts_cdf_cmcc.xml -scen cmcc_session_input-start.xml -log logs/client.log -llevel ETM")
    # Prints an empty line which causes the program to wait for seagull execution
    print(stdout.read()) 
  
  #Sanity check for seagull output, should TEST fail. The host defined should be same as used for setUpAndRunSeagull
  def checkSeagullOutput(self, host):
    print("checking seagull output")
    jhost = self.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/seagull/; sudo chmod +x checkOutput.sh; ./checkOutput.sh "+ self.callnumber)
    print(stdout.read())
    if (stdout.read() == "False"):
      raise ValueError("Incorrect traffic data")



#--------Stream stuff--------
 
  #Can be used to either import stream files to some host or to import streams from a single dir to host
  def importStreamToEl(self, destDir, array, host, host_machine):
    jhost = self.connections[host_machine][0]
    #We check if array is contains only one string for a directory
    if (len(array) == 1 and ".el" not in (array[0])):
        #We have a directory that needs to be imported
        print("import " + array[0])
        print("tools.pl -i -c import "+ destDir + " -H "+ host)
        stdin, stdout, stderr = jhost.exec_command("tools.pl -i -c import "+ destDir + " -H "+ host)
        
        stdout.readlines()
    else:
        for file, path in array:
          if ".el" in file:
            print("import "+ file)
            stdin, stdout, stderr = jhost.exec_command("cd " + destDir +"; tools.pl -i -c import "+ file + " -H "+ host)
            stdout.readlines()  

  #Starts a stream, with versioning 1, this should not cause any errors as during the test run there will only be one version of streams
  def startStream(self, stream_name):
    jhost = self.connections["processing_online0"][0]
    print("tools.pl -c startstream -n " + stream_name+ " -v " + str(self.getRevision(stream_name)))
    jhost.exec_command("tools.pl -c startstream -n " + stream_name + " -v " + str(self.getRevision(stream_name)))
    
    i = 0
    while (self.checkStreamStatus(stream_name) != "(RUNNING)" and i < 3 ):
      time.sleep(30)
      i += 1
    
    if (self.checkStreamStatus(stream_name) != "(RUNNING)"):
      raise EnvironmentError(u'Error in starting '+ stream_name)
 
  #Stops the defined stream forcefully on a defined host
  def stopStream(self, stream_name):
    jhost = self.connections["processing_online0"][0]
    jhost.exec_command("tools.pl -c stopstream -n " + stream_name + " -t ABORT")
    i = 0
    while (self.checkStreamStatus(stream_name) != "(STOPPED)" and i < 3 ):
      time.sleep(30)
      i += 1
    streamStatus = self.checkStreamStatus(stream_name)
    if (streamStatus != "(STOPPED)"):
      raise ValueError(stream_name + " should be stopped")    

  #Exporting stream using tools.pl 
  def exportStream(self, host, old_stream, new_stream):
    print("exporting stream to home directory of host")
    jhost = self.connections[host][0]
    jhost.exec_command("mkdir /home/TEST/"+new_stream)
    #We export with tools.pl to home directory
    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/"+new_stream+"; tools.pl -c exportstream -n " + old_stream + " -v 1 -t .")
    stdout.read()

  # Used to modify streams which have been ran on top of TransactionEngine
  def modifyStream(self, host, trans_host, stream_base, stream_new):     
    jhost = self.connections[host][0]
    
    print("modifying name of the file") 
    #Modifies name of stream from something to new stream_name
    print("cd /home/TEST/" + stream_new + "; sed -i 's/<name>.*<\/name>/<name>" + stream_new + "<\/name>/' ./stream/stream.xml")

    stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<name>.*<\/name>/<name>" + stream_new + "<\/name>/' ./stream/stream.xml")
    print(stdout.read())
 
    if ("TransactionEngine" in stream_base): 
        print("modifying default host")
        print("cd /home/TEST/" + stream_new + "; sed -i 's/<default_host>.*<\/default_host>/<default_host>" + host + "<\/default_host>/' ./stream/stream.xml")    

        #Modifies default of stream from something to 
        stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<default_host>.*<\/default_host>/<default_host>" + host + "<\/default_host>/' ./stream/stream.xml")
        print(stdout.read())

    #Processing offline stream does not run inside TxE
    elif("offline" in trans_host):
        stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<default_host>.*<\/default_host>/<default_host>" + host + "<\/default_host>/' ./stream/stream.xml")
        print(stdout.read())
    else:
        stdin, stdout, stderr = jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<default_host>.*<\/default_host>/<default_host>" + trans_host + "<\/default_host>/' ./stream/stream.xml")
        print(stdout.read())
 
    #Modifies the host parameter from TransactionEngineHost to trans_host, only for TransactionEngine stream
    if ("TransactionEngine" in stream_base):
        jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<value>TransactionEngineHost<\/value>/<value>" + trans_host + "<\/value>/' ./stream/stream.xml")
    else:
        #Modifies the transactionengine host the stream should use, only for CDF stream
        jhost.exec_command("cd /home/TEST/" + stream_new + "; sed -i 's/<host>TransactionEngineHost<\/host>/<host>" + trans_host + "<\/host>/' ./stream/stream.xml")

  # Used to get the host the stream is running on (can only be run on streams that are running, since monitorstream command only works on them)
  def getStreamHost(self, stream):
    #We use processing_online0 by default since host doesn't matter for tools.pl
    jhost = self.connections["processing_online0"][0]
    
    stdin, stdout, stderr = jhost.exec_command("tools.pl -c monitorstream -n " + stream)
    arr = stdout.read() 
    #Searches the index of Host from output of monitorstream
    hostIndex = filter(None, arr.split("\n")[0].split(' ')).index('Host')
    #Gets the value of hostIndex from second line of monitorstream
    host = filter(None, arr.split("\n")[1].split(' '))[hostIndex]
    print(host)
    return host
      
  # Return greatest revision of stream
  def getRevision(self, stream):
    jhost = self.connections["processing_online0"][0]
    stdin, stdout, stderr = jhost.exec_command("tools.pl -c browsestreams")
    
    arr = stdout.read()
    print(arr)
    for i in arr.split("\n"):
        wordlist = filter(None, i.split(' '))
        if stream in wordlist:
            return(wordlist[2].split(',')[-1])

#--------SSH tunnel closer--------

  #At the end of execution we close the ssh tunnels
  def closeTunnels(self): 
    for con in self.connections:
      self.connections[con][0].close()

