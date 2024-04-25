#Steps to implement smoketest

import yaml
import paramiko
import sys
import subprocess
import time
import os

import cbam21ApiClass
import cbamApiClass
import sshActionWrapper 
import writeValues


#----------Sets up the environment for the whole feature----------

@given(u'environment is configured for cbam api and ssh actions')
def step_impl(context):
    test_config = (os.environ['TEST_CONFIG'])

    if os.path.exists("yamlFiles/"+test_config+"/"+test_config+".yaml") is False:
      raise EnvironmentError(u'vnfInfo does not exist in the current working directory')
    else:
      yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"

    writeValues.main(test_config)

    with open( yamlPath ) as f:
      identify_cbam = yaml.load( f, Loader=yaml.FullLoader )

    if identify_cbam["vnfInfo"]["cbam_version"] =="21":
      api = cbam21ApiClass.ApiAction()
    else:
      api = cbamApiClass.ApiAction()
    stream = sshActionWrapper.Connections()
    api.getVariables(yamlPath)
    api.getAuthentication()
    api.getVnfInfo(yamlPath)
    stream.getIpsAndSecrets(yamlPath, test_config)
    context.api.vnfInstanceId = api.vnfInstanceId
    context.api.vnfdId = api.vnfdId
    assert(yamlPath == context.yamlPath)
    assert(api.vnfInstanceId == context.api.vnfInstanceId)
    assert(api.vnfdId == context.api.vnfdId)
    assert(stream.jump_ip == context.stream.jump_ip)
    assert(stream.key_path == context.stream.key_path)
    

#----------Importing and modifying----------

#Implementation that does not use uploaded scripts, adds importable folder to streamarray for importing
@when(u'I modify stream "{stream_base}" to run on "{host}" and "{trans_host}" with name "{stream_new}"')
def step_impl(context, stream_base, host, trans_host, stream_new):
    stream = context.stream
    stream.checkConnection(host)

    assert(stream.checkStreamStatus(stream_base) != "False")

    stream.exportStream(host, stream_base, stream_new)
    stream.modifyStream(host, trans_host, stream_base, stream_new)        
    
    context.streamarr[stream_new] = ["/home/TEST/"+ stream_new]


#For online streams
@when(u'I modify stream "{stream_base}" to run on "{trans_host}" on "{host}" with name "{stream_new}"')
def step_impl(context, stream_base, trans_host, host, stream_new):
    context.execute_steps(u'When I modify stream "{stream_base}" to run on "{host}" and "{trans_host}" with name "{stream_new}"'.format(stream_base=stream_base, host=host, stream_new=stream_new, trans_host=trans_host))

#For offline streams
@when(u'I modify stream "{stream_base}" to run on "{host}" with name "{stream_new}"')
def step_impl(context, stream_base, host, stream_new):
    context.execute_steps(u'When I modify stream "{stream_base}" to run on "{host}" and "{trans_host}" with name "{stream_new}"'.format(stream_base=stream_base, host=host, stream_new=stream_new, trans_host="offline"))


#!!! adds importable folder to streamarray for importing   
@when(u'I upload "{streami}" stream package successfully to "{host}"')
def step_impl(context, streami, host):
    stream = context.stream
    stream.checkConnection(host)
    arr = []
    #Uploading files to processing instances as well as setting up the directory structure
    stream.copyArrayToDir('nodesAndStream/'+streami + '/', '/home/TEST/'+streami+'/', host, arr)    
    context.streamarr[streami] = arr

@when(u'I create necessary folders for stream "{streami}" to "{host}"')
def step_impl(context,streami,host):
    stream = context.stream
    
    stream.checkConnection(host)
    if (streami == "CMCC_VoLTE_CDF" or streami == "CMCC_VoLTE_CDF2"):
        jhost = stream.connections[host][0]
        jhost.exec_command("mkdir -p /opt/TEST/TEST/Benchmark/CG_Streams/CMCC_VoLTE/")
        jhost.exec_command("mkdir -p /opt/TEST/TEST/C3_data/input")
        jhost.exec_command("mkdir -p /opt/TEST/TEST/C3_data/backup")
        jhost.exec_command("mkdir -p /opt/TEST/TEST/C3_data/output")  
        stream.connections[host][1].put('nodesAndStream/CMCC_VoLTE_CDF/cdf_splitting_rules.txt', '/opt/TEST/TEST/Benchmark/CG_Streams/CMCC_VoLTE/cdf_splitting_rules.txt')
    

@when(u'I import stream "{streami}" on "{host_machine}" to "{host}"')
def step_impl(context, streami, host_machine, host):
    context.execute_steps(u'Then I import stream "{streami}" on "{host_machine}" to "{host}"'.format(streami=streami, host_machine=host_machine, host=host))

#Since we need machine + transaction_engine_host
@then(u'I import stream "{streami}" on "{host_machine}" to "{host}"')
def step_impl(context, streami, host_machine, host):
    stream = context.stream
    stream.checkConnection(host_machine)
    stream.importStreamToEl('/home/TEST/'+streami, context.streamarr[streami], host, host_machine)
    if (stream.checkStreamStatus(streami) == "False"):
        raise ValueError("Importing " + streami + " stream failed")


@when(u'I import stream "{streami}" to "{host}"')   
def step_impl(context, streami, host):
    context.execute_steps(u'Then I import stream "{streami}" to "{host}"'.format(streami=streami, host=host))
 
@then(u'I import stream "{streami}" to "{host}"')
def step_impl(context, streami, host):
    #Run tools.pl to import the stream to data refinery 
    stream = context.stream
    stream.checkConnection(host)
    stream.importStreamToEl('/home/TEST/' + streami, context.streamarr[streami], host, host)
    if (stream.checkStreamStatus(streami) == "False"):
      raise ValueError("Importing " +streami +" stream failed")

#----------Generic stream management and info steps----------

@given(u'stream "{streami}" is running on "{host}"')
def step_impl(context, streami, host):
    #We check that connection to host is created    
    stream = context.stream
    stream.checkConnection("processing_online0")
    #We check if stream is running
    streamStatus = stream.checkStreamStatus(streami)
    if streamStatus != "(RUNNING)":
        raise EnvironmentError(u'%s is not running' % streami )
 
    if stream.getStreamHost(streami).strip() != host.strip():
      raise EnvironmentError(u'%s is not running on host %s but on %s' % (streami, host, stream.getStreamHost(streami)))
 
@then(u'stream "{streami}" is running on "{host}"')
def step_impl(context, streami, host):
    #We check that connection to host is created    
    stream = context.stream
    stream.checkConnection("processing_online0")

    #We check if stream is running
    streamStatus = stream.checkStreamStatus(streami)
    if streamStatus != "(RUNNING)":
        raise EnvironmentError(u'%s is not running' % streami )
 
    if stream.getStreamHost(streami).strip() != host.strip():
      raise EnvironmentError(u'%s is not running on host %s but on %s' % (streami, host, stream.getStreamHost(streami)))
 

@then(u'I should see "{streami}" stream "{status}" on "{host}"')
def step_impl(context, streami, status, host):
    stream = context.stream
    if ("TransactionEngine" not in host):
        stream.checkConnection(host)
    if status == "running":
        assert(stream.checkStreamStatus(streami) == "(RUNNING)")
        running_host = stream.getStreamHost(streami)
        assert(running_host == host) 
    elif status == "stopped":
        assert(stream.checkStreamStatus(streami) == "(STOPPED)")
        running_host = stream.getStreamHost(streami)
        assert(running_host == host) 
    elif status == "not running":
        assert(stream.checkStreamStatus(streami) != "(RUNNING)")

@then(u'stream "{streami}" should exist')
def step_impl(context, streami):
    stream = context.stream
    stream.checkConnection("processing_online0")
    assert(stream.checkStreamStatus(streami) != "False")

@given(u'stream "{streami}" exists')
def step_impl(context, streami):
    context.execute_steps(u'Then stream "{streami}" should exist'.format(streami=streami)) 


@given(u'stream "{streami}" is running')
def step_impl(context, streami):
    stream = context.stream
    stream.checkConnection("processing_online0")
    assert(stream.checkStreamStatus(streami) == "(RUNNING)")

@then(u'stream "{streami}" is running')
def step_impl(context, streami):
    stream = context.stream
    stream.checkConnection("processing_online0")
    assert(stream.checkStreamStatus(streami) == "(RUNNING)")


@when(u'I start the stream "{streami}"')
def step_impl(context, streami):
    #Starting imported CMCC_VoLTE_CDF stream.
    stream = context.stream
    stream.checkConnection("processing_online0") 
    if (stream.checkStreamStatus(streami) != "(RUNNING)"):
      stream.startStream(streami)

@when(u'I stop the stream "{streami}"')
def step_impl(context, streami):
    stream = context.stream
    stream.checkConnection("processing_online0") 
    if (stream.checkStreamStatus(streami) == "(RUNNING)"):
        stream.stopStream(streami)

#----------Seagull steps----------

@when(u'I start simulating traffic with "{call_amount}" calls to "{receiver_host}" with seagull from "{sender_host}"')
def step_impl(context, call_amount, receiver_host, sender_host):
    #Assert that 3868 is open on receiver_host
    stream = context.stream

    stream.checkConnection(receiver_host)
    stream.checkConnection(sender_host)
    
    stdin, stdout, stderr = stream.connections[receiver_host][0].exec_command("sudo firewall-cmd --permanent --add-port=3868/tcp; sudo firewall-cmd --reload")
    stdout.read()
    stdin, stdout, stderr = stream.connections[receiver_host][0].exec_command("sudo firewall-cmd --list-ports")
    assert ("3868/tcp" in stdout.read())

    stream.downloadSeagull(context,sender_host)
    stream.setDestIp('seagull/client.xml', stream.ips[receiver_host])
    stream.setCallAmount('seagull/client.xml', call_amount)
    stream.setCallRate('seagull/client.xml', 200)

    stream.copyArrayToDir('seagull/', '/home/TEST/seagull/', sender_host)
    stream.setUpAndRunSeagull(sender_host) 

@then(u'I should see seagull data processed on "{host}"')
def step_impl(context, host):    

    stream = context.stream
    stream.checkConnection(host)
    stream.checkSeagullOutput(host) 


#----------Scaling steps----------

@given(u'the session is authenticated')
def step_impl(context):
  api = context.api  
  api.getAuthentication()

@given(u'scaling aspect group "{aspect_id}" is at "{count}"')
def step_impl(context, aspect_id, count):
    api = context.api  
    print(count)
    print(aspect_id)
    if api.scaleAspectStatus(aspect_id) > int(count):
      while (api.scaleAspectStatus(aspect_id) > int(count)):
        print(str(api.scaleAspectStatus(aspect_id)) + " is greater than " + str(count))
        api.pollLcmStatus(False)
        api.scaleVnf("SCALE_IN", aspect_id)
        api.pollLcmStatus(True)
    elif api.scaleAspectStatus(aspect_id) < int(count): 
      while (api.scaleAspectStatus(aspect_id) < int(count)):
        print(str(api.scaleAspectStatus(aspect_id)) + " is less than " + str(count))
        api.pollLcmStatus(False)
        api.scaleVnf("SCALE_OUT", aspect_id)
        api.pollLcmStatus(True)

    #We should be at the exact count
    assert( api.scaleAspectStatus(aspect_id) == int(count))

@when(u'I scale "{direction}" "{aspect_id}" aspect group')
def step_impl(context, direction, aspect_id):
    #We call scale out to processing-online aspect group
    api = context.api  
    if direction == "out":
        api.pollLcmStatus(False)
        api.scaleVnf("SCALE_OUT", aspect_id)
        api.pollLcmStatus(True)
    elif direction == "in":
        print("scaling in")
        api.pollLcmStatus(False)
        api.scaleVnf("SCALE_IN", aspect_id)
        api.pollLcmStatus(True)
        print("scaling done")
@then(u'I scale "{direction}" "{aspect_id}" aspect group')
def step_impl(context, direction, aspect_id):
    context.execute_steps(u'When I scale "{direction}" "{aspect_id}" aspect group'.format(direction=direction, aspect_id=aspect_id))

#----------Instance health checks----------

@then(u'"{host}" should be healthy')
def step_impl(context, host):
    #After healing the instance might still be booting
    stream = context.stream
    stream.checkConnection(host)
    assert(stream.checkProcessStatus(host, "sshd") == "active")

@then(u'I should see the instance been rebuild')
def step_impl(context):
    #Heal action should be finished
    stream = context.stream
 
    ui = stream.connectThroughJump("ui0", "TEST", stream.key_path) 
    stream.connections["ui0"][0].exec_command("ls -la")  
    

#----------Steps for services, soft heal----------

@given(u'service "{service}" is running on "{host}"')
def step_impl(context, service, host):
    stream = context.stream
    #stream.checkConnection(host)
    assert(stream.checkProcessStatus(host, service) == "active")

@when(u'I shutdown service "{service}" on "{host}"')
def step_impl(context, service, host):
    #ssh and systemctl stop
    stream = context.stream
    stream.checkConnection(host)
    jhost = stream.connections[host][0]
    stdin, stdout,stderr = jhost.exec_command("sudo systemctl stop "+ service)
    stdout.read()
    
@then(u'service "{service}" should be running on "{host}"')
def step_impl(context, service, host):
    context.execute_steps(u'Given service "{service}" is running on "{host}"'.format(service=service, host=host))

@given(u'host "{host}" is healthy')
def step_impl(context, host):
    context.execute_steps(u'Then "{host}" should be healthy'.format(host=host))


#----------Hard Heal steps----------

@when(u'I remove boot files from "{host}" and force boot')
def step_impl(context, host):
    #ssh and removing bootfiles of it
    stream = context.stream
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo rm -rf /boot")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo reboot now")
    stdout.read()

@when(u'start the healing action')
def step_impl(context):
    #Run healing action on vnf
    api = context.api  
    api.pollLcmStatus(False)
    api.healVnf()
    api.pollLcmStatus(True)

#----------Failover testing----------

@when(u'I rename binary of "Nodemanager" to "Nodemanager2"')
def step_impl(context):
    raise NotImplementedError(u'STEP: When I rename binary of "Nodemanager" to "Nodemanager2"')


@when(u'I wait for keepalived to trigger failover')
def step_impl(context):
    raise NotImplementedError(u'STEP: When I wait for keepalived to trigger failover')


#----------Termination----------

@given(u'vnf and stream info exists')
def step_impl(context):
    context.execute_steps(u'''
      Given environment is configured for cbam api and ssh actions')
    '''.format())

@when(u'vnf is deleted')
def step_impl(context):
    api = context.api  
    api.removeVnf()    

@then(u'it does not exist')
def step_impl(context):
    api = context.api  
    try:
      api.getVnfInfo(context.yamlPath)
      raise ValueError("vnf was not successfully deleted")
    except:
      print(u'VNF was successfully deleted')

@then(u'I close all connections')
def step_impl(context):
    stream = context.stream
    stream.closeTunnels()

@then(u'perform ssh check from "{current_host}" to "{target_host}"')
def step_impl(context, current_host, target_host):
    stream = context.stream
    stream.connectThroughJump(current_host,"TEST",stream.key_path)
    jhost = stream.connections[current_host][0]
    command_string = "ssh -o ConnectTimeout=5 TEST@" + context.stream.ips[target_host] + " echo ok 2>&1"
    print command_string
    stdin, stdout, stderr = jhost.exec_command(command_string)
    text = stdout.read()
    print text
    assert( text.strip() == "ok" )


@then(u'I print all connection in context')
def step_impl(context):
    print context.stream.ips