import paramiko
import sys
import subprocess
import os
import time
import cbamApiClass
import writeValues
import sshActionWrapper

dict = {'processingOFF_vip0': ['processing_offline0', 'processing_offline1'], 
        'processingOFF_vip1': ['processing_offline2', 'processing_offline3'], 
        'processingOFF_vip2': ['processing_offline4', 'processing_offline5'], 
        'cgf_vip0': ['cgf0', 'cgf1'],
        'cgf_vip1': ['cgf1', 'cgf2'],
        'outage_vip0': ['outage0', 'outage1'],
        'outage_vip1': ['outage2', 'outage3'],
        'cdf_vip0': ['cdf0', 'cdf1'],
        'cdf_vip1': ['cdf1', 'cdf2'],
        'db_vip': ['db0', 'db1'],
        'ui_vip': ['ui0', 'ui1'],
        'oam_vip': ['oam0', 'oam1'],
        
        }

@given('make sure jumphost connection is ready')
def step_impl(context):
    test_config = (os.environ['TEST_CONFIG'])
    yamlPath="yamlFiles/"+test_config+"/"+test_config+".yaml"
    stream = sshActionWrapper.Connections()
    stream.getIpsAndSecrets(yamlPath, test_config)
    stream.configJumpHost(stream.jump_ip, stream.jumphost_login[0], stream.jumphost_login[1])

@then('I trigger HA operation at CBAM and wait')
def step_impl(context):
    api = context.api
    time.sleep(10)
    api.pollLcmStatus(False)
    api.haLCM()
    api.pollLcmStatus(True)


@then('I stop "{service}" service in "{host}"')
def step_impl(context, service, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop " + service )
    text = stdout.read()
    

@then('I check HA role status is "{status}" in "{host}"')
def step_impl(context, status, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo ha role")
    text = stdout.read()
    assert( text.strip() == status )

@then('I check all "{vm_type}" volumes should attached to "{host}"')
def step_impl(context, vm_type, host):
    attempt = 3
    interval = 5
    count = 0
    dict = { 'procOFF' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"],
             'procON' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"],
             'CGF' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TEST/control"],
             'OUTAGE' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"],
             'MAPPER' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"],
             'EPCCGF': ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"],
             'CDF' : ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TEST/control"],
             'DB' : ['/mariadb/data', '/var/lib/pgsql/data'],
             'UI' : ['/opt/TEST/certs', '/var/lib/registry'],
             'OAM' : ['/opt/event-management/install/eventmanagement-engine/logs', '/appdata', '/var/www/html']}
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    while count < attempt:
        try:
            count += 1
            stdin, stdout, stderr = jhost.exec_command("lsblk")
            text = stdout.read()
            i = 0 
            while i < len(dict[vm_type]):
                assert(dict[vm_type][i] in text)
                i += 1 
            break
        except AssertionError:
            time.sleep(interval)
            if count >= attempt:
                t = time.localtime()
                current_time = time.strftime("%H:%M:%S", t)
                print("%s [DEBUG] %s" % current_time, text)
                raise

@then('I saw "{service}" is active in "{host}"')
def step_impl(context, service, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl is-active " + service)
    text = stdout.read()
    assert( text.strip() == "active" )

@then('I try to get ip of standby host through "{host}"')
def step_impl(context, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("/sbin/ifconfig eth0 |grep 'inet '|awk {'print $2'}")
    text = stdout.read()
    if text.strip() == context.stream.ips[dict[host][0]]:
       context.stream.passive = dict[host][1]
    else: 
       context.stream.passive = dict[host][0]

@then('HA role is STANDBY in standby host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump(context.stream.passive, "TEST", stream.key_path)
    jhost = stream.connections[context.stream.passive][0]
    stdin, stdout, stderr = jhost.exec_command("sudo ha role")
    text = stdout.read()
    assert( text.strip() == 'STANDBY' )

@then('I check HA role should tally at both "{host0}" "{host1}"')
def step_impl(context, host0, host1):
    stream = context.stream
    stream.connectThroughJump(host0, "TEST", stream.key_path)
    jhost = stream.connections[host0][0]
    stdin, stdout, stderr = jhost.exec_command("sudo ha role")
    text = stdout.read()
    if text.strip() == 'ACTIVE':
      status2 = 'STANDBY'
    if text.strip() == 'STANDBY':
      status2 = 'ACTIVE'
    stream.connectThroughJump(host1, "TEST", stream.key_path)
    jhost = stream.connections[host1][0]
    stdin, stdout, stderr = jhost.exec_command("sudo ha role")
    text = stdout.read()
    assert( text.strip() == status2 )







