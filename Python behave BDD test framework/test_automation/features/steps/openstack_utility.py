import paramiko
import sys
import subprocess
import time
import os
import yaml

import cbamApiClass
import sshActionWrapper
import writeValues

dict = {'processingOFF_vip0': ['processing_offline0', 'processing_offline1'], 'processingOFF_vip1': ['processing_offline2', 'processing_offline3'],'cgf_vip0': ['cgf0', 'cgf1'], 'db_vip': ['db0', 'db1'], 'ui_vip': ['ui0', 'ui1'], 'oam_vip': ['oam0', 'oam1'], 'processing_online0':['processing_online0'],'processing_online1':['processing_online1'],'processing_online2':['processing_online2'], 'crdb1':['crdb1'], 'crdb2':['crdb2']}

@then(u'Attach network name "{network_name}" with netmask "{netmask_value}" and "{gateway}" in "{host}"')
def step_impl(context, network_name, netmask_value, gateway,  host ):
  stream = context.stream
  my_env =  os.environ.copy()
  stream.connectThroughJump(host,"TEST",stream.key_path)
  jhost = stream.connections[host][0]
  stdin, stdout, stderr = jhost.exec_command("/sbin/ifconfig eth0 |grep 'inet '|awk {'print $2'}")
  text = stdout.read()

  if text.strip() == context.stream.ips[dict[host][0]]:
     context.stream.active = context.stream.ips[dict[host][0]]
  elif text.strip() == context.stream.ips[dict[host][1]]:
     context.stream.active = context.stream.ips[dict[host][1]]
  else:
     context.stream.active = context.stream.ips[dict[host][0]]

  command_str = "./filterIP.sh "+ context.stream.active + " " + network_name
  subprocess.check_output(command_str, shell=True, env=my_env)

  ##filterIP will get put ip into the temp.yml
  with open("temp.yml") as f:
    info = yaml.load(f, Loader=yaml.FullLoader)

  stdin, stdout, stderr = jhost.exec_command("sudo ip a | grep DOWN | grep eth | awk \'{ print $2}\'| sed \'s/\://g\'")
  eth = stdout.read()

  command_1 = "echo NAME=" + eth.strip() + ">>"
  command_2 = "echo DEVICE=" + eth.strip() + ">>"
  eth_file="ifcfg-" + eth.strip()
  stdin, stdout, stderr = jhost.exec_command("echo NM_CONTROLLED=no > " + eth_file )
  stdin, stdout, stderr = jhost.exec_command("echo TYPE=Ethernet >> " + eth_file )
  stdin, stdout, stderr = jhost.exec_command("echo BOOTPROTO=static >> " + eth_file )
  stdin, stdout, stderr = jhost.exec_command("echo IPADDR=" + info["ip"] + " >> " + eth_file)
  stdin, stdout, stderr = jhost.exec_command("echo PREFIX=" +  netmask_value + " >> " + eth_file )
  stdin, stdout, stderr = jhost.exec_command("echo GATEWAY=" + gateway + " >> " + eth_file)
  stdin, stdout, stderr = jhost.exec_command( command_1 + eth_file)
  stdin, stdout, stderr = jhost.exec_command("echo ONBOOT=yes >> "+ eth_file)
  stdin, stdout, stderr = jhost.exec_command( command_2 + eth_file)
  stdin, stdout, stderr = jhost.exec_command("sudo cp " + eth_file + " /etc/sysconfig/network-scripts" )
  stdin, stdout, stderr = jhost.exec_command("sudo ifup " + eth_file)
  stdin, stdout, stderr = jhost.exec_command("rm " + eth_file )

@then(u'I verify that "{host}" shall in "{zone_name}"')
def step_impl(context, host, zone_name):
  stream = context.stream
  my_env =  os.environ.copy()

  if zone_name == "zone1":
     zoneId = context.stream.zone1
  else:
     zoneId =context.stream.zone2
  command_str = "openstack server list --ip " + context.stream.ips[host] + " -c Name -f value"

  vm_name = subprocess.check_output(command_str, shell=True, env=my_env)
  command_str2 = "openstack server show " + vm_name.strip() +  " -c OS-EXT-AZ:availability_zone -f value"
  output_value = subprocess.check_output(command_str2, shell=True, env=my_env)
  assert( zoneId in output_value ) 