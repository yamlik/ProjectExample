import paramiko
import sys
import subprocess
import time
import os

import cbam21ApiClass
import cbamApiClass
import sshActionWrapper
import writeValues

procOFF = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data",
 "/opt/TEST/TEST/control"]
procOFF_total_volumes = 5

procON = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data",
 "/opt/TEST/TEST/control"]
procOFF_total_volumes = 5

cgf = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data", "/opt/TEST/TEST/control"]
cgf_total_volumes = 5

cdf = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TEST/control"]
cdf_total_volumes = 4

mapper = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data",
 "/opt/TEST/TEST/control"]
mapper_total_volumes = 5

epccgf = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data",
 "/opt/TEST/TEST/control"]
epccgf_total_volumes = 5

outage = ["/opt/TEST/TEST/data", "/opt/TEST/TEST/storage", "/opt/TEST/TEST/offline", "/opt/TEST/TimesTen/TEST_timesten/data",
 "/opt/TEST/TEST/control"]
outage_total_volumes = 5

ui = ["/var/lib/registry", "/opt/TEST/certs"]
ui_total_volumes = 2

db = ["/mariadb/data", "/var/lib/pgsql/data"]
db_total_volumes = 2

crdb = ["/var/lib/redis"]
crdb_total_volumes = 1

oam = ["/appdata", "/var/www/html"]
oam_total_volumes = 2

@then(u'I update node manager service file in both processingOFF host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processing_offline0","TEST",stream.key_path)
    jhost = stream.connections["processing_offline0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

    stream.connectThroughJump("processing_offline1","TEST",stream.key_path)
    jhost = stream.connections["processing_offline1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update node manager service file in both CGF host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cgf0","TEST",stream.key_path)
    jhost = stream.connections["cgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

    stream.connectThroughJump("cgf1","TEST",stream.key_path)
    jhost = stream.connections["cgf1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update node manager service file in both CDF host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cdf0","TEST",stream.key_path)
    jhost = stream.connections["cdf0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

    stream.connectThroughJump("cdf1","TEST",stream.key_path)
    jhost = stream.connections["cdf1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update node manager service file in both Outage host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("outage0","TEST",stream.key_path)
    jhost = stream.connections["outage0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

    stream.connectThroughJump("outage1","TEST",stream.key_path)
    jhost = stream.connections["outage1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update postgresql service file in both DB host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("db0","TEST",stream.key_path)
    jhost = stream.connections["db0"][0]
    #stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/postgresql.service")
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/mysqld/mysqldx/g\' /usr/lib/systemd/system/mariadb.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop mariadb")
    stdout.read()

    stream.connectThroughJump("db1","TEST",stream.key_path)
    jhost = stream.connections["db1"][0]
    #stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/postgresql.service")
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/mysqld/mysqldx/g\' /usr/lib/systemd/system/mariadb.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop mariadb")
    stdout.read()

@then('all volumes for procesingOFF are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processingOFF_vip0","TEST",stream.key_path)
    jhost = stream.connections["processingOFF_vip0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(procOFF) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == procOFF_total_volumes ) 

@then('all volumes for DB are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("db_vip","TEST",stream.key_path)
    jhost = stream.connections["db_vip"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo df -h | grep " + "\""+"\|".join(db) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == db_total_volumes )

@then('all volumes for crdb0 are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("crdb0","TEST",stream.key_path)
    jhost = stream.connections["crdb0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo df -h | grep " + "\""+"\|".join(crdb) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == crdb_total_volumes )

@then(u'all volumes for both ui host are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("ui0","TEST",stream.key_path)
    jhost = stream.connections["ui0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(ui) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == ui_total_volumes )

    stream.connectThroughJump("ui1","TEST",stream.key_path)
    jhost = stream.connections["ui1"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(ui) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == ui_total_volumes )

@then('all volumes for CGF are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cgf0","TEST",stream.key_path)
    jhost = stream.connections["cgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(cgf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == cgf_total_volumes ) 

    stream.connectThroughJump("cgf1","TEST",stream.key_path)
    jhost = stream.connections["cgf1"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(cgf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == cgf_total_volumes )

@then('all volumes for CDF are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cdf0","TEST",stream.key_path)
    jhost = stream.connections["cdf0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(cdf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    print total_volume
    assert( int(total_volume_attached) == cdf_total_volumes ) 

    stream.connectThroughJump("cdf1","TEST",stream.key_path)
    jhost = stream.connections["cdf1"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(cdf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    print total_volume
    assert( int(total_volume_attached) == cdf_total_volumes )

@then('all volumes for Outage are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("outage_vip0","TEST",stream.key_path)
    jhost = stream.connections["outage_vip0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(outage) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == outage_total_volumes ) 

@then('all volumes for Mapper are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("mapper0","TEST",stream.key_path)
    jhost = stream.connections["mapper0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(mapper) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == mapper_total_volumes ) 

    stream.connectThroughJump("mapper1","TEST",stream.key_path)
    jhost = stream.connections["mapper1"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(mapper) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == mapper_total_volumes ) 

@then('all volumes for EPCCGF are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("epccgf0","TEST",stream.key_path)
    jhost = stream.connections["epccgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(epccgf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == epccgf_total_volumes ) 

    stream.connectThroughJump("epccgf1","TEST",stream.key_path)
    jhost = stream.connections["epccgf1"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(epccgf) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == epccgf_total_volumes )

@then('all volumes for procesingON are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processing_online0","TEST",stream.key_path)
    jhost = stream.connections["processing_online0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(procOFF) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == procOFF_total_volumes ) 

@then('all volumes for crdb are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("crdb0","TEST",stream.key_path)
    jhost = stream.connections["crdb0"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(crdb) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == crdb_total_volumes )

@then('all volumes for oam are attached')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("oam_vip","TEST",stream.key_path)
    jhost = stream.connections["oam_vip"][0]
    stdin, stdout, stderr = jhost.exec_command("df -h | grep " + "\""+"\|".join(oam) + "\""+"|wc -l" )
    total_volume_attached = stdout.read()
    assert( int(total_volume_attached) == oam_total_volumes )

@then('openstack action log show processingOFF is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processing_offline0","TEST",stream.key_path)
    jhost = stream.connections["processing_offline0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy() 
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else: 
       stream.connectThroughJump("processing_offline1","TEST",stream.key_path)
       jhost = stream.connections["processing_offline1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then('openstack action log show CGF is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cgf0","TEST",stream.key_path)
    jhost = stream.connections["cgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("cgf1","TEST",stream.key_path)
       jhost = stream.connections["cgf1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then('openstack action log show CDF is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("cdf0","TEST",stream.key_path)
    jhost = stream.connections["cdf0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("cdf1","TEST",stream.key_path)
       jhost = stream.connections["cdf1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then('openstack action log show Outage is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("outage0","TEST",stream.key_path)
    jhost = stream.connections["outage0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("outage1","TEST",stream.key_path)
       jhost = stream.connections["outage1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then(u'I update node manager service file in processingON host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processing_online0","TEST",stream.key_path)
    jhost = stream.connections["processing_online0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update node manager service file in Mapper host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("mapper0","TEST",stream.key_path)
    jhost = stream.connections["mapper0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then(u'I update node manager service file in EPCCGF host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("epccgf0","TEST",stream.key_path)
    jhost = stream.connections["epccgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/el-node-manager.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop el-node-manager")
    stdout.read()

@then('openstack action log show processingON is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("processing_online0","TEST",stream.key_path)
    jhost = stream.connections["processing_online0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)

@then('openstack action log show Mapper is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("mapper0","TEST",stream.key_path)
    jhost = stream.connections["mapper0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)

@then('openstack action log show EPCCGF is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("epccgf0","TEST",stream.key_path)
    jhost = stream.connections["epccgf0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)

@then('I update tomcat@el-ui service file in both UI host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("ui0","TEST",stream.key_path)
    jhost = stream.connections["ui0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/tomcat@el-ui.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop tomcat@el-ui")
    stdout.read()

    stream.connectThroughJump("ui1","TEST",stream.key_path)
    jhost = stream.connections["ui1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/start/startx/g\' /etc/systemd/system/tomcat@el-ui.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop tomcat@el-ui")
    stdout.read()


@then(u'openstack action log show UI is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("ui0","TEST",stream.key_path)
    jhost = stream.connections["ui0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("ui1","TEST",stream.key_path)
       jhost = stream.connections["ui1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then(u'openstack action log show DB is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("db0","TEST",stream.key_path)
    jhost = stream.connections["db0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("db1","TEST",stream.key_path)
       jhost = stream.connections["db1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then(u'I update redisio and redis-sentinel service file in crdb0')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("crdb0","TEST",stream.key_path)
    jhost = stream.connections["crdb0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s#redis-sentinel#redis-sentinelx#g\' /etc/systemd/system/redis-sentinel.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop redis-sentinel")
    stdout.read()

    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s#redis-server#redis-serverx#g\' /etc/systemd/system/redisio.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop redisio")
    stdout.read()

@then('openstack action log show crdb0 is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("crdb0","TEST",stream.key_path)
    jhost = stream.connections["crdb0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    assert( "rebuild" in output)
    print(output)

@then('openstack action log show oam is rebuild')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("oam0","TEST",stream.key_path)
    jhost = stream.connections["oam0"][0]
    stdin, stdout, stderr = jhost.exec_command("hostname")
    hostname_output = stdout.read()
    my_env = os.environ.copy()
    output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
    print("oxxxx")
    print(output)
    if ( "rebuild" in output):
        assert(True)
    else:
       stream.connectThroughJump("oam1","TEST",stream.key_path)
       jhost = stream.connections["oam1"][0]
       stdin, stdout, stderr = jhost.exec_command("hostname")
       hostname_output = stdout.read()
       output = subprocess.check_output(["nova instance-action-list " +hostname_output.strip() + "| grep rebuild"],shell=True,env=my_env)
       assert( "rebuild" in output)

@then(u'I update httpd service file in both OAM host')
def step_impl(context):
    stream = context.stream
    stream.connectThroughJump("oam0","TEST",stream.key_path)
    jhost = stream.connections["oam0"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/httpd/httpdx/g\' /usr/lib/systemd/system/httpd.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop httpd")
    stdout.read()

    stream.connectThroughJump("oam1","TEST",stream.key_path)
    jhost = stream.connections["oam1"][0]
    stdin, stdout, stderr = jhost.exec_command("sudo sed -i \'s/httpd/httpdx/g\' /usr/lib/systemd/system/httpd.service")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl daemon-reload")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl stop httpd")
    stdout.read()

@then(u'I trigger heal from cbam')
def step_impl(context):
    api = context.api
    time.sleep(5)
    api.pollLcmStatus(False)
    api.healVnf()
    api.pollLcmStatus(True)

@then(u'I remove /boot from "{host}"')
def step_impl(context, host):
    #ssh and removing bootfiles of it
    stream = context.stream
    #stream.checkConnection(host)
    stream.connectThroughJump(host,"TEST",stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo rm -rf /boot")
    stdout.read()
    stdin, stdout, stderr = jhost.exec_command("sudo reboot now")
    stdout.read()

@then(u'I start the healing action "{heal_type}"')
def step_impl(context, heal_type):
    #Run healing action on vnf
    api = context.api  
    api.pollLcmStatus(False)
    api.healVnf(heal_type)
    api.pollLcmStatus(True)

@given(u'jumphost connection is ready')
def step_impl(context):
    test_config = (os.environ['TEST_CONFIG'])
    yamlPath = "yamlFiles/"+test_config+"/"+test_config+".yaml"
    stream = sshActionWrapper.Connections()
    stream.getIpsAndSecrets(yamlPath, test_config)
    stream.configJumpHost(stream.jump_ip,stream.jumphost_login[0],stream.jumphost_login[1])

@then(u'service "{service}" should active in "{host}"')
def step_impl(context, service, host):
    stream = context.stream
    stream.connectThroughJump(host,"TEST",stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo systemctl is-active " + service)
    text = stdout.read()
    assert( text.strip() == "active" )

@then(u'HA role is "{status}" in "{host}"')
def step_impl(context,status, host ):
    stream = context.stream
                         
    stream.connectThroughJump(host,"TEST",stream.key_path)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo ha role")
    text = stdout.read()
    assert( text.strip() == status )

@then(u'I start the healing action "{heal_type}" for "{loop_counter}" times')
def step_impl(context, heal_type, loop_counter):
    #Run healing action on vnf
    api = context.api
    counter =0 
    while counter < int(loop_counter):
        api.healVnf(heal_type)
        operationsState= api.pollLcmStatus(False)
        if operationsState == "FINISHED":
          break
        counter += 1
        print counter
    api.pollLcmStatus(True)

@then('The file "{file_name_with_path}" should "{status}" in "{host}"')
def step_impl(context, file_name_with_path, status, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    if status == "present":
        command_str = "[ -e " + file_name_with_path + " ] && echo PASS || echo FAIL"
    elif status == "absent":
        command_str = "[ ! -e " + file_name_with_path + " ] && echo PASS || echo FAIL"
    
    stdin, stdout, stderr = jhost.exec_command(command_str)
    text = stdout.read()
    assert( text.strip() == "PASS" )

@then('The file "{file_name}" should have "{permission_code}" belongs to group "{group_name}" and user "{username}" in "{host}"')
def step_impl(context, file_name, permission_code, group_name, username, host):
    stream = context.stream
    stream.connectThroughJump(host, "TEST", stream.key_path)
    jhost = stream.connections[host][0]
    command_str = 'sudo stat --format=%a%G%U ' + file_name
    stdin, stdout, stderr = jhost.exec_command(command_str)
    text = stdout.read()
    result = permission_code + group_name + username
    assert( text.strip().decode('utf-8') == result.strip().decode('utf-8'))