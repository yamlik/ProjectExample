import subprocess
import os.path
import yaml
import openstack
import sys
import json
from shutil import copyfile

def main(yamlname):

    filename =("./yamlFiles/"+yamlname+"/"+yamlname+".yaml")

    with open(filename) as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    openstack_info = info["clouds"]["ipv4"]["auth"]
    os.environ["OS_AUTH_URL"] = openstack_info["auth_url"]
    os.environ["OS_PROJECT_ID"] = openstack_info["project_id"]
    os.environ["OS_USER_DOMAIN_NAME"] = openstack_info["user_domain_name"]
    os.environ["OS_PROJECT_DOMAIN_ID"] = openstack_info["project_domain_name"]
    os.environ["OS_USERNAME"] = openstack_info["username"]
    os.environ["OS_PASSWORD"] = openstack_info["password"]
    os.environ["OS_REGION_NAME"] = info["clouds"]["ipv4"]["regionName"]
    os.environ["OS_INTERFACE"] = info["clouds"]["ipv4"]["os_interface"]
    os.environ["OS_CACERT"] = openstack_info["lab_cert"]
