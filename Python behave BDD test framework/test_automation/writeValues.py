import subprocess
import os.path
import yaml
import openstack
import sys
import json
from shutil import copyfile

def main(yamlname):

    str =("./yamlFiles/"+yamlname+"/"+yamlname+".yaml")

    with open(str) as f:
      info = yaml.load(f, Loader=yaml.FullLoader)

    writeCloudsvalues(info["clouds"])

    pwd = subprocess.check_output(["pwd"]).split('\n')[0]

    vnfdvar = getVnfd(info["vnfInfo"]["vnf_path"],info["vnfd"]["ip_protocol_version"], info["vnfInfo"]["vnf_type"])

    vnfd = vnfdvar if vnfdvar.startswith('/') else pwd + '/' + vnfdvar

    #use_generator = os.environ["use_generator"]
    
    #if use_generator =="No":
    if info["vnfInfo"]["vnf_type"] == "handcrafted":
      with open(vnfd) as f:
        dicti = yaml.load(f, Loader=yaml.FullLoader)

      if info["vnfInfo"]["dr_version"] <="20.6":
        extensions = dicti['topology_template']['substitution_mappings']['capabilities']['vnf']['properties']['modifiable_attributes']['extensions']
      else:
        extensions = dicti['data_types']['TEST.datatypes.nfv.VnfInfoModifiableAttributesExtensions']['properties']

    if info["vnfInfo"]["vnf_type"] == "generated":
      with open(vnfd) as f:
        dicti = yaml.load(f, Loader=yaml.FullLoader)
        extensions = dicti['data_types']['TEST.datatypes.nfv.VnfInfoModifiableAttributesExtensions']['properties']

    writeExtensionGeneric(info, extensions, vnfd, dicti)

    if (info["vnfInfo"]["use_json"] is False):
        writeExtensionSpecific(info, extensions)
        writeJsonFile(info, vnfd, dicti)
    else:
        changeJsonFile(info, yamlname)
#Rewriting vnfd values
def writeExtensionSpecific(info, extensions):
    #IPs
    extensions['ui_oam_ip']['default'] = info["vnfd"]["ui_oam_ip"]
    extensions['db_oam_ip']['default'] = info["vnfd"]["db_oam_ip"]

    extensions['db_oam_VIP']['default'] = info["vnfd"]["db_oam_VIP"]
    extensions['ui_oam_VIP']['default'] = info["vnfd"]["ui_oam_VIP"]
    extensions['processingON_oam_ip']['default'] = info["vnfd"]["processingON_oam_ip"]
    extensions['processingOFF_oam_VIP']['default'] = info["vnfd"]["processingOFF_oam_VIP"]
    extensions['processingOFF_oam_ip']['default'] = info["vnfd"]["processingOFF_oam_ip"]
    extensions['oam_ip']['default'] = info["vnfd"]["oam_ip"]
    extensions['oam_VIP']['default'] = info["vnfd"]["oam_VIP"]


    #Billing network IPs
    extensions['processingON_bill_ip']['default'] = info["vnfd"]["processingON_bill_ip"]
    extensions['processingOFF_bill_ip']['default'] = info["vnfd"]["processingOFF_bill_ip"]
    extensions['processingOFF_bill_VIP']['default'] = info["vnfd"]["processingOFF_bill_VIP"]


    #Charging network IPs
    extensions['processingON_charg_ip']['default'] = info["vnfd"]["processingON_charg_ip"]
    extensions['processingOFF_charg_ip']['default'] = info["vnfd"]["processingOFF_charg_ip"]
    extensions['processingOFF_charg_VIP']['default'] = info["vnfd"]["processingOFF_charg_VIP"]

    #availability zones
    extensions['availability_zones']['default'] = info["vnfd"]["availability_zones"]

    #Network ids
    extensions['oam_network_id']['default'] = info["vnfd"]["oam_network_id"]
    extensions['charging_network_id']['default'] = info["vnfd"]["charging_network_id"]
    extensions['billing_network_id']['default'] = info["vnfd"]["billing_network_id"]

    #Network masks
    extensions['oam_network_netmask']['default'] = info["vnfd"]["oam_network_netmask"]
    extensions['charging_network_netmask']['default'] = info["vnfd"]["charging_network_netmask"]
    extensions['billing_network_netmask']['default'] = info["vnfd"]["billing_network_netmask"]

    #Subnet ids
    extensions['oam_subnet_id']['default'] = info["vnfd"]["oam_subnet_id"]
    extensions['billing_subnet_id']['default'] = info["vnfd"]["billing_subnet_id"]
    extensions['charging_subnet_id']['default'] = info["vnfd"]["charging_subnet_id"]

    #Flavors
    extensions['processingON_flavor_id']['default'] = info["vnfd"]["processingON_flavor_id"]
    extensions['processingOFF_flavor_id']['default'] = info["vnfd"]["processingOFF_flavor_id"]
    extensions['db_flavor_id']['default'] = info["vnfd"]["db_flavor_id"]
    extensions['ui_flavor_id']['default'] = info["vnfd"]["ui_flavor_id"]
    extensions['oam_flavor_id']['default'] = info["vnfd"]["oam_flavor_id"]

    #Images
    extensions['processing_image_id']['default'] = info["vnfd"]["processing_image_id"]
    extensions['db_image_id']['default'] = info["vnfd"]["db_image_id"]
    extensions['ui_image_id']['default'] = info["vnfd"]["ui_image_id"]
    extensions['oam_image_id']['default'] = info["vnfd"]["oam_image_id"]

    #CGF and CRDB enable checking
    enable_crdb = info["vnfd"]["crdb_flag"]
    enable_cgf = info["vnfd"]["cgf_flag"]
    if ( enable_cgf is True ):    
        extension['cgf_image_id']['default'] = info["vnfd"]["cgf_image_id"]
        extensions['cgf_flavor_id']['default'] = info["vnfd"]["cgf_flavor_id"]
        extensions['cgf_oam_ip']['default'] = info["vnfd"]["cgf_oam_ip"]

    if ( enable_crdb is True ):    
        extension['crdb_image_id']['default'] = info["vnfd"]["crdb_image_id"]
        extensions['crdb_flavor_id']['default'] = info["vnfd"]["crdb_flavor_id"]
        extensions['crdb_oam_ip']['default'] = info["vnfd"]["crdb_oam_ip"]

def writeExtensionGeneric(info, extensions, vnfd, dicti):

    #Writing passwords to vnfd file as we will be deploying it as it is.
    extensions['use_floating_ip']['default'] = info["vnfd"]["use_floating_ip"]
    extensions['ui_fqdn']['default'] = info["vnfd"]["ui_fqdn"]
    #extensions['public_network_id']['default'] = info["vnfd"]["public_network_id"]

    #Network masks
    extensions['oam_network_netmask']['default'] = info["vnfd"]["oam_network_netmask"]
    extensions['charging_network_netmask']['default'] = info["vnfd"]["charging_network_netmask"]
    extensions['billing_network_netmask']['default'] = info["vnfd"]["billing_network_netmask"]

    #Lookup servers
    extensions['online_lookup_server_name']['default'] = info["vnfd"]["online_lookup_server_name"]
    extensions['offline_lookup_server_name']['default'] = info["vnfd"]["offline_lookup_server_name"]
    if "cgf_lookup_server_name" in extensions:
      extensions['cgf_lookup_server_name']['default'] = info["vnfd"]["cgf_lookup_server_name"]
    if "cdf_lookup_server_name" in extensions:
      extensions['cdf_lookup_server_name']['default'] = info["vnfd"]["cdf_lookup_server_name"]
    if "mapper_lookup_server_name" in extensions:
      extensions['mapper_lookup_server_name']['default'] = info["vnfd"]["mapper_lookup_server_name"]
    if "epccgf_lookup_server_name" in extensions:
      extensions['epccgf_lookup_server_name']['default'] = info["vnfd"]["epccgf_lookup_server_name"]
    if "outage_lookup_server_name" in extensions:
      extensions['outage_lookup_server_name']['default'] = info["vnfd"]["outage_lookup_server_name"]
    if "cgf_enable_timesten" in extensions:
      extensions["cgf_enable_timesten"]['default'] = True
    if "cdf_enable_timesten" in extensions:
      extensions["cdf_enable_timesten"]['default'] = True
    if "mapper_enable_timesten" in extensions:
      extensions["mapper_enable_timesten"]['default'] = True
    if "outage_enable_timesten" in extensions:
      extensions["outage_enable_timesten"]['default'] = True
    if "install_gtp_collector" in extensions:
        extensions['install_gtp_collector']['default'] = info["vnfd"]["install_gtp_collector"]
    if "enable_force_scale_in" in extensions:
        extensions['enable_force_scale_in']['default'] = info["vnfd"]["enable_force_scale_in"]

    if info["vnfInfo"]["dr_version"] =="21":
      extensions['test_id'] = {'default': info["vnfd"]["testId"]}
      extensions['test_id'].update({'required': False})
      extensions['test_id'].update({'type': "string"})
      extensions['ansible_verbosity']['default'] = "-vvv"
#      extensions['passwd_keystore']['default'] = info["vnfd"]["default_password"]
#      extensions['passwd_keystore']['metadata'] = None
#      extensions['passwd_http_repo_user"']['default'] = info["vnfd"]["default_password"]
#      extensions['passwd_http_repo_user"']['metadata'] = None
#      extensions['passwd_grafana_admin']['default'] = info["vnfd"]["default_password"]
#      extensions['passwd_grafana_admin']['metadata'] = None
      extensions['passwd_TEST_gui']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_TEST_gui']['metadata'] = None
      extensions['passwd_keycloak_admin']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_keycloak_admin']['metadata'] = None
      extensions['passwd_postgresql_admin']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_postgresql_admin']['metadata'] = None
      extensions['passwd_postgresql_user']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_postgresql_user']['metadata'] = None
      extensions['passwd_unix_root_user']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_unix_root_user']['metadata'] = None
      extensions['passwd_unix_user']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_unix_user']['metadata'] = None
      extensions['passwd_crdb_user']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_crdb_user']['metadata'] = None
      extensions['passwd_jmx_user']['default'] = info["vnfd"]["default_password"]
      extensions['passwd_jmx_user']['metadata'] = None
      extensions['gen3gppxml_sftp_password']['default'] = info["vnfd"]["default_password"]
      extensions['gen3gppxml_sftp_password']['metadata'] = None
      extensions['smpp_server_password']['default'] = info["vnfd"]["default_password"]
      extensions['smpp_server_password']['metadata'] = None
      extensions['hide_sensitive_debug_info']['default'] =  info["vnfd"]["hide_sensitive_debug_info"]
    else:
       extensions['passwd_TEST_gui'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_keycloak_admin'] = {'default': info["vnfd"]["default_keycloak_password"]}
       extensions['passwd_postgresql_admin'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_postgresql_user'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_unix_root_user'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_unix_user'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_crdb_user'] = {'default': info["vnfd"]["default_password"]}
       extensions['passwd_jmx_user'] = {'default': info["vnfd"]["default_password"]}
       extensions['gen3gppxml_sftp_password'] = {'default': info["vnfd"]["default_password"]}
       extensions['smtp_server_password'] = {'default': info["vnfd"]["default_password"]}
       extensions['test_id'] = {'default': info["vnfd"]["testId"]}
       extensions['gen3gppxml_sftp_password'] = {'default': info["vnfd"]["default_password"]}

    with open(info["vnfd"]["authorized_key_path"], 'r') as f:
      if info["vnfInfo"]["dr_version"] <= "21":
         extensions['authorized_key']['default'] = str(f.read().strip())
      else:
         extensions['authorized_key'] = {'default': ''+ str(f.read().strip()) + ''}

    with open(vnfd, 'w') as f:
      yaml.dump(dicti, f, default_flow_style=False)

def changeJsonFile(info, yamlname):
    json_path = info["vnfInfo"]["vnf_path"] + info["vnfInfo"]["json_file"]

    src_json_path = str("./yamlFiles/"+yamlname+"/cbam.json")

    copyfile(src_json_path, json_path)


def writeJsonFile(info, vnfd, dicti):

    json_path = info["vnfInfo"]["vnf_path"] + info["vnfInfo"]["json_file"]

    #The structure of cbam.json is hardcoded.

    with open(json_path) as f:
      jsoni = json.load(f)
    vnf_id = info["vnfInfo"]["vnf_id"]

    if info["cbamjson"]["api-version"] == 2:

        jsoni.pop('vims', None)
        try:
            jsoni.pop('apiVersion')
        except:
            pass
        jsoni["vims"] = [{ "interfaceInfo": {}, "accessInfo":{} }]
        jsoni["softwareImages"] = info["cbamjson"]["softwareImages"]
        jsoni["computeResourceFlavours"] = info["cbamjson"]["computeResourceFlavours"]
        jsoni["vims"][0]["id"] = info["cbamjson"]["id"]
        jsoni["vims"][0]["interfaceInfo"]["region"] = info["cbamjson"]["region"]
        jsoni["vims"][0]["interfaceEndpoint"] = info["clouds"][vnf_id]["auth"]["auth_url"]
        jsoni["vims"][0]["accessInfo"]["username"] = info["clouds"][vnf_id]["auth"]["username"]
        jsoni["vims"][0]["accessInfo"]["password"] = info["clouds"][vnf_id]["auth"]["password"]
        jsoni["vims"][0]["accessInfo"]["tenant"] = info["clouds"][vnf_id]["auth"]["project_name"]
        jsoni["instantiationLevelId"] = info["cbamjson"]["instantiationLevelId"]

    #FIXME: IMPROVE FUNCTIONALITY TO IdentityV3
    else:
    # if we use cbam api v4 the 'vims' is 'vimConnectionInfo'
      if info["vnfInfo"]["api-version"] == 4:
        jsoni.pop('vims', None)
        jsoni["softwareImages"] = info["cbamjson"]["softwareImages"]
        try:
          jsoni.pop('apiVersion')
        except:
          pass
        jsoni["computeResourceFlavours"] = info["cbamjson"]["computeResourceFlavours"]
        jsoni["vimConnectionInfo"] = [{ "interfaceInfo":{}, "accessInfo":{} }]
        jsoni["vimConnectionInfo"][0]["id"] = info["cbamjson"]["id"]
        jsoni["vimConnectionInfo"][0]["vimType"] = "OPENSTACK_V3"
        jsoni["vimConnectionInfo"][0]["interfaceInfo"]["endpoint"] = info["clouds"][vnf_id]["auth"]["auth_url"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["region"] = info["cbamjson"]["region"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["username"] = info["clouds"][vnf_id]["auth"]["username"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["password"] = info["clouds"][vnf_id]["auth"]["password"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["project"] = info["clouds"][vnf_id]["auth"]["project_name"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["projectDomain"] = info["clouds"][vnf_id]["auth"]["project_domain_name"]
        jsoni["vimConnectionInfo"][0]["accessInfo"]["userDomain"] = info["clouds"][vnf_id]["auth"]["user_domain_name"]
        jsoni["instantiationLevelId"] = info["cbamjson"]["instantiationLevelId"]
      else:
        jsoni.pop('vims', None)
        jsoni["softwareImages"] = info["cbamjson"]["softwareImages"]
        try:
          jsoni.pop('apiVersion')
        except:
          pass
        jsoni["computeResourceFlavours"] = info["cbamjson"]["computeResourceFlavours"]
        jsoni["vims"] = [{ "interfaceInfo":{}, "accessInfo":{} }]
        jsoni["vims"][0]["id"] = info["cbamjson"]["id"]
        jsoni["vims"][0]["vimInfoType"] = "OPENSTACK_V3_INFO"
        jsoni["vims"][0]["interfaceInfo"]["endpoint"] = info["clouds"][vnf_id]["auth"]["auth_url"]
        jsoni["vims"][0]["accessInfo"]["region"] = info["cbamjson"]["region"]
        jsoni["vims"][0]["accessInfo"]["username"] = info["clouds"][vnf_id]["auth"]["username"]
        jsoni["vims"][0]["accessInfo"]["password"] = info["clouds"][vnf_id]["auth"]["password"]
        jsoni["vims"][0]["accessInfo"]["project"] = info["clouds"][vnf_id]["auth"]["project_name"]
        jsoni["vims"][0]["accessInfo"]["projectDomain"] = info["clouds"][vnf_id]["auth"]["project_domain_name"]
        jsoni["vims"][0]["accessInfo"]["userDomain"] = info["clouds"][vnf_id]["auth"]["user_domain_name"]
        jsoni["instantiationLevelId"] = info["cbamjson"]["instantiationLevelId"]

    with open(json_path, 'w') as f:
      json.dump(jsoni, f, indent=2)

    with open(vnfd, 'w') as f:
      yaml.dump(dicti, f, default_flow_style=False)

def writeCloudsvalues(cloudsValues):
  with open("clouds.yaml") as f:
    cloud = yaml.load(f, Loader=yaml.FullLoader)

  cloud["clouds"] = cloudsValues

  with open("clouds.yaml", 'w') as f:
    yaml.dump(cloud, f)


def getVnfd(vnf_path,vnfdValues, vnf_type):

  tosca_path= vnf_path+"/TOSCA-Metadata/TOSCA.meta"
  toscafile = yaml.load(open(tosca_path), Loader=yaml.FullLoader)
  if vnfdValues == "ipv4":
    return vnf_path + "/" + toscafile["Entry-Definitions"]
  elif vnfdValues == "ipv6" and vnf_type == "generated":
    toscafile["Entry-Definitions"] = "vnfd.TEST.tosca.yaml"
    with open(tosca_path, 'w') as f:
      yaml.dump(toscafile, f, default_flow_style=False)
    return vnf_path + "/" + toscafile["Entry-Definitions"]
  elif vnfdValues == "ipv6" and vnf_type == "handcrafted":
    toscafile["Entry-Definitions"] = "vnfd.TEST.ipv6.tosca.yaml"
    with open(tosca_path, 'w') as f:
      yaml.dump(toscafile, f, default_flow_style=False)
    return vnf_path + "/" + toscafile["Entry-Definitions"]