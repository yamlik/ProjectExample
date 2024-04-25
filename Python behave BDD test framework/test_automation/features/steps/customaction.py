
import paramiko
import sys
import subprocess
import time
import os

import cbamApiClass
import cbam21ApiClass
import sshActionWrapper 
import writeValues

#Local/remote actions
@given(u'"{name}" "{type}" is located in "{directory}" directory')
def step_impl(context, name, type, directory):
    # If we have logged in, context.login=connection, this action is remote
    if (context.login == None):
      #Local action, need to create some logic for defining the directory
      complete_path = context.api.vnf_path + directory 
      if (type == "file"):
        os.path.isfile(complete_path+name)
      elif (type == "directory") or (type == "sub-directory") :
        os.path.isdir(complete_path+name) 
    else:
      #Remote action
      stream = context.stream
      file_path = directory+name
      assert(stream.checkFileOnHost(context.login, file_path))


@given(u'"{dir_name}" directory follows guidelines for custom Ansible scripts #https://confluence.int.net.TEST.com/display/IDRDS/Run+Custom+Script+LCM+workflow')
def step_impl(context, dir_name):
    vnf_path = context.api.vnf_path
    target_dir = vnf_path+"ansible/custom_ansible/"+dir_name+"/"
    
    allowedFiles = ["files","templates", "vars", dir_name+".yaml"] 

    files = os.listdir(target_dir)

    for file in files:
      if not file in allowedFiles:
        raise EnvironmentError(u'Directory '+ target_dir + ' contains unallowed file or directory: ' + file)
    
@when(u'"{tgz_name}" is created from "{dir_name}" directory')
def step_impl(context, tgz_name, dir_name):
    #Local shell command to be ran before instantiation, create tgz package out of a directory
    api = context.api
    api.createTgz(tgz_name, dir_name)
  
#API/LCM-action
@given(u'Instantiation status is finished')
def step_impl(context):
    api = context.api
    assert (api.checkInstantiationStatus())
    
#SSH ACTION, add connection to context
@when(u'I log in to "{host}" node')
def step_impl(context, host):
    context.execute_steps(u'When I log in to "{host}"'. format(host=host))

@when(u'I log in to "{host}" VM')
def step_impl(context, host):
    context.execute_steps(u'When I log in to "{host}"'. format(host=host))

#We use the checkConnection helper function to create a paramiko connection to the defined vm_type's vip address or straight to a vm. Context.login is the written as the value of the string used for connection. This context variable can the be used in the same scenario.
@when(u'I log in to "{vm_type}"')
def step_impl(context, vm_type):
    #This dict points vm_type values to values in the connections dict of context.stream. If vm_type is not found in vip_dict keys it is thought to be a value that should already be in the dict and a connection is tried to it, processingOnline does not have a VIP so we use just processing_online0
    vip_dict = { "processingon": "processing_online0", "processingoff": "processingOFF_vip0", "processingoff_0": "processingOFF_vip0", "processingoff_1": "processingOFF_vip1", "ui": "ui_vip", "db": "db_vip", "oam": "oam_vip" }
  
    lower = vm_type.lower()
    print(lower)
    if lower in vip_dict.keys():
      context.stream.checkConnection(vip_dict[lower])
      context.login = vip_dict[lower]  
    else:
      context.stream.checkConnection(vm_type)
      context.login = vm_type

@then(u'"{name}" "{type}" is located in "{directory}" directory')
def step_impl(context, name, type, directory):
    context.execute_steps(u'''
      Given "{name}" "{type}" is located in "{directory}" directory
    '''.format(name=name, type=type, directory=directory))

@then(u'"{name}" is located in "{directory}" directory')
def step_impl(context, name, directory):
    context.execute_steps(u'''
      Given "{name}" "file" is located in "{directory}" directory
    '''.format(name=name, directory=directory))

@then(u'"{type}" "{name}" is deleted from "{directory}" directory')
def step_impl(context, type, name, directory):
    stream = context.stream
    if (context.login == None):
      if type == "file":
        if os.path.isfile(directory+name):
          os.remove(directory+name) 
      elif (type == "directory") or (type == "sub-directory"):
        if os.path.isdir(directory+name):
          shutil.rmtree(directory+name)
    else:
      file_path = directory+name
      stream.removeFileFromHost(context.login, file_path)

@when(u'I download custom Ansible scripts package from "{host}" path "{url}"')
def step_impl(context, host, url):
    stream = context.stream
    #This step only works for logged in instances
    if (context.login == None):
      raise EnvironmentError(u'Step requires a login step before execution')
    else:
      stream.downloadFromHost(context.login, host, url)

@then(u'I can see that "{service}" is running')
def step_impl(context, service):
    #This step requires login
    stream = context.stream
    if (context.login == None):
      raise EnvironmentError(u'Step requires a login step before execution')
    else:
      stream.checkProcessStatus(context.login, service)

#LCM Action
@when(u'I launch "{lcm}" LCM action with params')
def step_impl(context, lcm):
    api = context.api
    data = {}
    for row in context.table:
    # bit of a mess here to make sure we pass booleans instead of strings
      if row['value'] == "true" or row['value'] == "True":
        data[row['key']] = True
      elif row['value'] == "false" or row['value'] == "False":
        data[row['key']] = False
      else:
        data[row['key']] = row['value']
    if lcm == "instantiate":
      context.execute_steps(u'''
        When VNF is instantiated through API
      '''.format()) 
    elif lcm == "Run Custom Ansible":
      api.pollLcmStatus(False)
      api.customLcmAction(data) 
      api.pollLcmStatus(False)
    elif lcm == "Update Passwords": 
      api.pollLcmStatus(False)
      api.updatePasswordLCM(data)
      api.pollLcmStatus(False)
    elif lcm == "Modify":
      api.pollLcmStatus(False)
      api.modify(data)
      api.pollLcmStatus(True)
    elif lcm == "Heal": 
      context.execute_steps(u'''
        When start the healing action
      '''.format())
    elif lcm == "RPM Update":
      api.pollLcmStatus(False)
      api.rpmUpdateLCM(data)
      api.pollLcmStatus(True)
    elif lcm == "Terminate":
      api.pollLcmStatus(False)
      api.termiateVnf()
      api.pollLcmStatus(True)

@then(u'"{lcm}" LCM action finishes successfully')
def step_impl(context, lcm):
    api = context.api
    parsed_lcm = ""
    if(lcm == "Run Custom Ansible"):
      parsed_lcm = "run_custom_ansible"
    elif(lcm == "Update Passwords"):
      parsed_lcm = "update_passwords"
    elif(lcm == "RPM Update"):
      parsed_lcm = "rpm_update"
    elif(lcm == "Modify"):
      parsed_lcm = "modify_info"
    else:
      parsed_lcm = lcm
    
    status = api.checkLcmFromExecutions(parsed_lcm)
    print(status)
    assert(status[0] == "Finished")

@given(u'"{lcm}" LCM action finishes successfully')
def step_impl(context, lcm):
    context.execute_steps(u'''
      Then "{lcm}" LCM action finishes successfully
    '''.format(lcm=lcm))

@then(u'"{lcm}" LCM action finishes with error')
def step_impl(context, lcm):
    api = context.api
    parsed_lcm = ""
    if(lcm == "Run Custom Ansible"):
      parsed_lcm = "run_custom_ansible"
    elif(lcm == "Update Passwords"):
      parsed_lcm = "update_passwords"
    elif(lcm == "RPM Update"):
      parsed_lcm = "rpm_update"
    elif(lcm == "Modify"):
      parsed_lcm = "modify_info"
    else:
      parsed_lcm = lcm
    
    status = api.checkLcmFromExecutions(parsed_lcm)
    print(status[0])
    assert(status[0] == "error")
    context.error_msg = status[1]

#LCM# action
@then(u'error message has following text')
def step_impl(context):
    error_msg = ""
    for row in context.table:
      error_msg = row['error']
    print(error_msg in context.error_msg)
    assert(error_msg in context.error_msg)

#SSH ACTION
@then(u'"{text_file}" has content of "{string}"')
def step_impl(context, text_file, string):
    #Take connection from context
    if (context.login == None):
      raise EnvironmentError(u'Step requires a login step before execution')
    else:
      context.stream.changeFileOwner(context.login, text_file, "TEST")
      context.stream.checkFileContent(context.login, text_file, string)

@then(u'"{file_name}" "{type}" should not be located in "{directory}" directory')
def step_impl(context, file_name, type, directory):
    stream = context.stream
    #Take connection from context
    if (context.login == None):
      #Local action, need to create some logic for defining the directory
      complete_path = context.api.vnf_path + directory 
      if (type == "file"):
        assert(os.path.isfile(complete_path+file_name) == True)
      elif (type == "directory") or (type == "sub-directory") :
        assert(os.path.isdir(complete_path+file_name) == False)
    else:
      #Remote action
      file_path = directory+file_name
      assert(stream.checkFileOnHost(context.login, file_path) == False)
    
@then(u'I should be able to switch to "{user}" user using password "{password}" without sudo')
def step_impl(context, user, password):
    #Connection from context
    if (context.login == None):
      raise EnvironmentError(u'Step requires a login step before execution')
    else:
      context.stream.changeUser(context.login, user, password)

#UI access from jumphost/if no jumphost configured the jenkins container, Gonna be very hard
@when(u'I navigate to TESTry login page')
def step_impl(context):
    stream = context.stream
    #This requires something to retrieve the correct ip from openstack floating ip list with the ui vip
    ui_vip = stream.ips["ui_vip"]
    floating_ip = stream.checkFloatingIp(ui_vip)
    context.driver.get('http://'+floating_ip +':55000')
    context.website = "TESTry"
    time.sleep(5)
  
@when(u'I log in with username "{username}" and password "{password}"')
def step_impl(context, username, password):
    #Since the website contain different element IDs for different we need to do ifs here
    if (context.website == "TESTry"):
      print(context.driver.title)
      assert("Log in to TEST" in context.driver.title)
      username_id = context.driver.find_element_by_id("target2")
      username_id.clear()
      username_id.send_keys(username)
      
      password_id = context.driver.find_element_by_id("login-password")
      password_id.clear()
      password_id.send_keys(password)

      context.driver.find_element_by_id("sbtbtn").click()
      time.sleep(5)
    elif (context.website == "Keycloak"):
      print(context.driver.title)
      assert("Log in to Keycloak" in context.driver.title)
      username_id = context.driver.find_element_by_id("username")
      username_id.clear()
      username_id.send_keys(username)
      
      password_id = context.driver.find_element_by_id("password")
      password_id.clear()
      password_id.send_keys(password)

      context.driver.find_element_by_id("kc-login").click()
      time.sleep(5) 


@when(u'I start recording the display')
def step_impl(context):
    display = context.vdisplay.new_display
    #Export DISPLAY is port
    subprocess.call(["export DISPLAY=:"+str(display)], shell=True)
    #remove video on docker slave if it exists
    subprocess.call(["rm -f /videos/video.mp4"], shell=True)
    #Start recording display on a new tmux session
    subprocess.call(["tmux new-session -d -s recording 'ffmpeg -f x11grab -video_size 1920x1080 -i 127.0.0.1:"+str(display)+" -codec:v libx264 -r 12 /videos/video.mp4'"], shell=True)

@then(u'I should be successfully logged in')
def step_impl(context):
    #Same as above, we can test the successfull login by confirming that the website title is now correct
    if (context.website == "TESTry"):
      assert("TEST 20.0.0" in context.driver.title)
    elif (context.website == "Keycloak"):
      assert("Keycloak Admin Console" in context.driver.title)

@when(u'I navigate to Keycloak Admin console')
def step_impl(context):
    stream = context.stream
    ui_vip = stream.ips["ui_vip"]
    floating_ip = stream.checkFloatingIp(ui_vip) 
    context.driver.get('https://'+floating_ip+':8666/auth/admin')
    context.website = "Keycloak"
    time.sleep(5)
