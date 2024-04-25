from behave import *

import sys
import subprocess
import time
import os
import yaml

from selenium import webdriver
import selenium.webdriver.chrome.service as service
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.proxy import Proxy, ProxyType
from xvfbwrapper import Xvfb

import writeValues
import cbamApiClass
import cbam21ApiClass
import sshActionWrapper
import openstack_param


@fixture
def configure_api(context):
    test_config = (os.environ['TEST_CONFIG'])
    print(os.getcwd())
    if os.path.exists("yamlFiles/"+test_config+"/"+test_config+".yaml") is False:
      raise EnvironmentError(u'vnfInfo does not exist in the current working directory')
    else:
      yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"
    writeValues.main(test_config)
    openstack_param.main(test_config)
    #Creating global variables for LCM usage

    with open( yamlPath ) as f:
      identify_cbam = yaml.load( f, Loader=yaml.FullLoader )

    if identify_cbam["vnfInfo"]["cbam_version"] =="21" or identify_cbam["vnfInfo"]["cbam_version"] =="19.5":
      context.api = cbam21ApiClass.ApiAction()
    else:
      context.api = cbamApiClass.ApiAction()
    #print "x3"
    #CBIS specific setup
    context.api.getVariables(yamlPath)
    context.api.getAuthentication()
    context.yamlPath = yamlPath
    context.test_config = test_config
    context.login = None

@fixture
def configure_api_for_test(context):
    test_config = (os.environ['TEST_CONFIG'])
    print(os.getcwd())
    if os.path.exists("yamlFiles/"+test_config+"/"+test_config+".yaml") is False:
      raise EnvironmentError(u'vnfInfo does not exist in the current working directory')
    else:
      yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"
    #writeValues.main(test_config)
    openstack_param.main(test_config)
    #Creating global variables for LCM usage

    with open( yamlPath ) as f:
      identify_cbam = yaml.load( f, Loader=yaml.FullLoader )

    if identify_cbam["vnfInfo"]["cbam_version"] =="21" or identify_cbam["vnfInfo"]["cbam_version"] =="19.5":
      context.api = cbam21ApiClass.ApiAction()
    else:
      context.api = cbamApiClass.ApiAction()
    #print "x3"
    #CBIS specific setup
    context.api.getVariables(yamlPath)
    context.api.getAuthentication()
    context.yamlPath = yamlPath
    context.test_config = test_config
    context.login = None

@fixture
def configure_ssh(context):

    #You need to configure_api before configure_ssh
    #Creating global variables for paramiko ssh usage
    context.stream = sshActionWrapper.Connections()
    context.streamarr = {}

    #Ssh action should be run after instantiation is finished, thus getVnfInfo is here instead of at configure_api
    context.api.getVnfInfo(context.yamlPath)
    
    #Remote execution specific setup which loads ips from yamlFiles/test_config.
      #Need to add VIPs to getIps
    context.stream.getIpsAndSecrets(context.yamlPath, context.test_config)

@fixture
def configure_webdriver(context):
    #Webdriver is not created for all scenarios only the ones that require it, to add more scenarios for UI testing add them to this if
   
    context.vdisplay = Xvfb(width=1920, height=1080)
    context.vdisplay.start()        

    options = webdriver.ChromeOptions()
    options.binary_location = '/usr/bin/chromium-browser'
    options.add_argument('incognito')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')
    service_args = ['--verbose', '--disable-application-cache']
    context.driver = webdriver.Chrome('/usr/bin/chromedriver',
      chrome_options=options) 
    
def before_scenario(context, scenario):
    if 'ui' in scenario.effective_tags:
    
      if not context.api.use_floating_ip:
        scenario.skip(reason='No floating ip was configured')
      else:
        #Webdriver should only be configured before UI scenario, videos are currently scenario specific, keeps size small
        ui_vip = context.stream.ips["ui_vip"]
        use_fixture(configure_webdriver, context)

def after_scenario(context, scenario):
    if 'ui' in scenario.effective_tags:
      #Close stuff after UI scenario ends
      if scenario.status.name != "skipped":
        subprocess.call(["tmux send-keys -t recording q"], shell=True)
        time.sleep(2)
        context.vdisplay.stop()
        context.driver.quit()
        if ( scenario.status == "failed" ):
          #This requires the jumphost to run httpd and have a directory called recording to which a file  can be written to.
          build = os.environ["BUILD_NUMBER"]
          #Remove old file from jumphost
          context.stream.checkConnection("jump_host")
          context.stream.removeFileFromHost("jump_host", "/var/www/html/recording/build-*")
          context.stream.copyFileToHost("/videos/video.mp4", ("/var/www/html/recording/build-"+ build +".mp4"), "jump_host")
          context.stream.changeFilePermissions("jump_host", ("/var/www/html/recording/build-"+build+".mp4"), "755")
        
def before_feature(context, feature):
    if 'instantiate' not in feature.tags: 
        use_fixture(configure_api_for_test, context)
        print(context.yamlPath)
        use_fixture(configure_ssh, context)
    if 'instantiate' in feature.tags:
        use_fixture(configure_api, context)
