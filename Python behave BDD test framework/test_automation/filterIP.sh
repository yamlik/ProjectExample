#!/bin/bash

getServerName=$(openstack server list  -c Name -c Networks --insecure | grep $1 | awk '{ print $2}' )

##attach ip
openstack server add network ${getServerName} $2 --insecure

getIp=$(openstack server show ${getServerName} -c addresses --insecure |sed 's/ /\n/g' |grep $2 | tr -d "," | tr -d ";" | sed "s/$2=//g")
echo "ip : ${getIp}" > temp.yml