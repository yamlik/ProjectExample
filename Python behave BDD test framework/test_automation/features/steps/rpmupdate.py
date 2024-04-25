import paramiko
import sys
import subprocess
import time
import os

import cbam21ApiClass
import cbamApiClass
import sshActionWrapper
import writeValues

@when(u'I pull "{rpm_url}" rpm to "{host}" host')
def step_impl(context, rpm_url, host):
    #ssh and wget
    stream = context.stream
    stream.checkConnection(host)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("wget "+rpm_url)
    print("stdout ="+stdout.read())
    print stdout.channel.recv_exit_status()
    print(type(stdout.channel.recv_exit_status()))
    if stdout.channel.recv_exit_status() != 0:
      raise Exception(u'%s' % stderr.read())

@when(u'Move "{rpm_name}" to right location on "{host}"')
def step_impl(context, rpm_name, host):
    #ssh and mv
    stream = context.stream
    stream.checkConnection(host)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("sudo mv "+rpm_name+" /var/www/html/data-refinery/"+rpm_name)
    if stdout.channel.recv_exit_status() != 0:
      raise Exception(u'%s' % stderr.read())

@then(u'"{rpm_name}" is in right location on "{host}"')
def step_impl(context, rpm_name, host):
    #ssh and stat
    stream = context.stream
    stream.checkConnection(host)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command("stat /var/www/html/data-refinery/"+rpm_name)
    if stdout.channel.recv_exit_status() != 0:
      raise Exception(u'%s' % stderr.read())

@given(u'RPMs are in "{host}" repo')
def step_impl(context, host):
    # check if rpms found in the oam repo
    stream = context.stream
    stream.checkConnection(host)
    jhost = stream.connections[host][0]
    stdin, stdout, stderr = jhost.exec_command('printf "[test]\nbaseurl=https://localhost/data-refinery\nenabled=1\ngpgcheck=0\nsslverify=0" | sudo tee -a /etc/yum.repos.d/test.repo')
    if stdout.channel.recv_exit_status() != 0:
      raise Exception(u'Failed to create repo file for test. stderr = %s' % stderr.read())
    for row in context.table:
      stdin, stdout, stderr = jhost.exec_command("sudo yum list "+row['rpm_name'])
      if stdout.channel.recv_exit_status() != 0:
        raise Exception(u'RPM %s not found! stderr = %s' % (row['rpm_name'], stderr.read()))
    stdin, stdout, stderr = jhost.exec_command('sudo rm /etc/yum.repos.d/test.repo')

@then(u'RPM version should be this')
def step_impl(context):
    # check if rpm version is what we expect
    print("context.login= "+context.login)
    stream = context.stream
    stream.checkConnection(context.login)
    for obj in stream.connections:
      print(str(obj))
      print(stream.connections[str(obj)])
    jhost = stream.connections[context.login][0]
    for row in context.table:
      stdin, stdout, stderr = jhost.exec_command("sudo yum list installed "+row['rpm_name'])
      if stdout.channel.recv_exit_status() != 0:
        raise Exception(u'RPM %s not found! stderr = %s' % (row['rpm_name'], stderr.read()))
      version_found = stdout.read().find(row['version'])
      if version_found < 0:
        raise Exception(u'RPM %s is not found with version %s not found! stdout = %s' % (row['rpm_name'], row['version'], stdout.read()))
