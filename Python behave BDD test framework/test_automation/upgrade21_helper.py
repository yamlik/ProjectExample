#!/usr/bin/python
import json
import argparse
args = ''
##DR21 new parameter section
## for user to populate them
#################################
processing_image_name = 'TEST-mediation-2100-processing-centos-7.8-20210304161925'
ui_image_name = 'TEST-mediation-2100-UI-centos-7.8-20210304161926'
db_image_name = 'TEST-mediation-2100-DB-centos-7.8-20210304161927'
oam_image_name = 'TEST-mediation-2100-oam-centos-7.8-20210304161843'
crdb_image_name = 'TEST-mediation-2100-crdb-centos-7.8-20210304161843'
cgf_image_name = 'TEST-mediation-2100-processing-centos-7.8-20210304161925'
vnf_network_config_value = "Reserved"
postgresql_server_port_value = 5432
hide_sensitive_debug_info_value = True
crdb_ssl_value = False
passwd_jmx_user_value = "P@ssword1"

###################################

def take_input():
    parser  = argparse.ArgumentParser()
    parser.add_argument("input",help="location of your cbam.json. Eg. /home/user/cbam.json")
    parser.add_argument("output", help="location to output your new cbam.json. Eg. /home/user/new_cbam.json ")
    global args
    args = parser.parse_args()
    
    print('input file: ' + args.input)
    print('output file: ' + args.output)

def update_json():
    global args
    with open(args.input, "r") as f:
        data = json.loads(f.read())

    pOn_billing = []
    pOff_billing = []
    cgf_billing = []
    pOn_charging = []
    pOff_charging = []
    cgf_charging = []
    pPublic = []
    pOn_oam = []
    pOff_oam = []
    cgf_oam = []
    ui_oam = []
    db_oam = []
    oam_oam = []
    crdb_oam = []

    for n in range(len(data["extVirtualLinks"])):
          for i in range(len(data["extVirtualLinks"][n]["extCps"])):
              if data["extVirtualLinks"][n]["id"] == "billing":
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingON_billing_ecp":
                    pOn_billing.append(n)
                    pOn_billing.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_billing_ecp":
                    pOff_billing.append(n)
                    pOff_billing.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_bill_IPv4_virtual_ECP":
                    pOff_billing.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_billing_ecp":
                    cgf_billing.append(n)
                    cgf_billing.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_bill_IPv4_virtual_ECP":
                    cgf_billing.append(i)

              if data["extVirtualLinks"][n]["id"] == "charging":
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingON_charging_ecp":
                    pOn_charging.append(n)
                    pOn_charging.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_charging_ecp":
                    pOff_charging.append(n)
                    pOff_charging.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_charg_IPv4_virtual_ECP":
                    pOff_charging.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_charging_ecp":
                    cgf_charging.append(n)
                    cgf_charging.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_charg_IPv4_virtual_ECP":
                    cgf_charging.append(i)


              if data["extVirtualLinks"][n]["id"] == "public":
                 pPublic.append(n)

              if data["extVirtualLinks"][n]["id"] == "oam":
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingON_oam_ecp":
                    pOn_oam.append(n)
                    pOn_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_oam_ecp":
                    pOff_oam.append(n)
                    pOff_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "processingOFF_OAM_IPv4_virtual_ECP":
                    pOff_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_oam_ecp":
                    cgf_oam.append(n)
                    cgf_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "cgf_OAM_IPv4_virtual_ECP":
                    cgf_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "db_oam_ecp":
                    db_oam.append(n)
                    db_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "DB_OAM_IPv4_virtual_ECP":
                    db_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "ui_oam_ecp":
                    ui_oam.append(n)
                    ui_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "ui_OAM_IPv4_virtual_ECP":
                    ui_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "oam_ecp":
                    oam_oam.append(n)
                    oam_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "OAM_IPv4_virtual_ECP":
                    oam_oam.append(i)
                 if data["extVirtualLinks"][n]["extCps"][i]["cpdId"] == "crdb_oam_ecp":
                    crdb_oam.append(n)
                    crdb_oam.append(i)

    if "billing_subnet_id" in data["extensions"] and data["extensions"]["billing_subnet_id"] != "":
       billing_subnet = data["extensions"]["billing_subnet_id"]
       n = pOn_billing[0]
       i = pOn_billing[1]
       data["extVirtualLinks"][n]["resourceId"] = data["extensions"]["billing_network_id"]
       if data["extensions"]["processingON_bill_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingON_bill_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["billing_subnet_id"]
       #####
       n = pOff_billing[0]
       i = pOff_billing[1]
       if data["extensions"]["processingOFF_bill_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_bill_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["billing_subnet_id"]
       #####
       i = pOff_billing[2]
       if data["extensions"]["processingOFF_bill_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_bill_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["billing_subnet_id"]
       #####
       n = cgf_billing[0]
       i = cgf_billing[1]
       if data["extensions"]["cgf_bill_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_bill_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["billing_subnet_id"]
       #####
       i = cgf_billing[2]
       if data["extensions"]["cgf_bill_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_bill_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["billing_subnet_id"]
    
    else:
       n = pOn_billing[0]
       i = pOn_billing[1]
       billing_subnet = data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"]
    
    
    if "charging_subnet_id" in data["extensions"] and data["extensions"]["charging_subnet_id"] != "":
       charging_subnet = data["extensions"]["charging_subnet_id"]
       ##Update Charging Network Related
       n = pOn_charging[0]
       i = pOn_charging[1]
       data["extVirtualLinks"][n]["resourceId"] = data["extensions"]["charging_network_id"]
       if data["extensions"]["processingON_charg_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingON_charg_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["charging_subnet_id"]
       #####
       n = pOff_charging[0]
       i = pOff_charging[1]
       if data["extensions"]["processingOFF_charg_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_charg_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["charging_subnet_id"]
       #####
       i = pOff_charging[2]
       if data["extensions"]["processingOFF_charg_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_charg_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["charging_subnet_id"]
       #####
       n = cgf_charging[0]
       i = cgf_charging[1]
       if data["extensions"]["cgf_charg_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_charg_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["charging_subnet_id"]
       #####
       i = cgf_charging[2]
       if data["extensions"]["cgf_charg_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_charg_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["charging_subnet_id"]
    
    else:
       n = pOn_charging[0]
       i = pOn_charging[1]
       charging_subnet = data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"]        
    
    if "oam_subnet_id" in data["extensions"] and data["extensions"]["oam_subnet_id"] != "" :
       oam_subnet = data["extensions"]["oam_subnet_id"]
       ##Update OAM Network Related
       n = pOn_oam[0]
       i = pOn_oam[1]
       data["extVirtualLinks"][n]["resourceId"] = data["extensions"]["oam_network_id"]
       if data["extensions"]["processingON_oam_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingON_oam_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
       
       n = pOff_oam[0]
       i = pOff_oam[1]
       if data["extensions"]["processingOFF_oam_ip"][0] != "":
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_oam_ip"]
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       i = pOff_oam[2]
       if data["extensions"]["processingOFF_oam_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["processingOFF_oam_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]

       n = cgf_oam[0]
       i = cgf_oam[1]
       if data["extensions"]["cgf_oam_ip"][0] != "":
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_oam_ip"]
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       i = cgf_oam[2]
       if data["extensions"]["cgf_oam_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["cgf_oam_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       n = db_oam[0]
       i = db_oam[1]
       if data["extensions"]["db_oam_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["db_oam_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       i = db_oam[2]
       if data["extensions"]["db_oam_VIP"][0] != "":
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["db_oam_VIP"]
        data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       n = ui_oam[0]
       i = ui_oam[1]
       if data["extensions"]["ui_oam_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["ui_oam_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       i = ui_oam[2]
       if data["extensions"]["ui_oam_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["ui_oam_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       n = oam_oam[0]
       i = oam_oam[1]
       if data["extensions"]["oam_ip"][0] != "" :
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["oam_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]
    
       i = oam_oam[2]
       if data["extensions"]["oam_VIP"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["oam_VIP"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]

       n = crdb_oam[0]
       i = crdb_oam[1]
       data["extVirtualLinks"][n]["resourceId"] = data["extensions"]["oam_network_id"]
       if data["extensions"]["crdb_oam_ip"][0] != "":
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["fixedAddresses"] = data["extensions"]["crdb_oam_ip"]
         data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] = data["extensions"]["oam_subnet_id"]

    else:
       n = pOn_oam[0]
       i = pOn_oam[1]
       oam_subnet = data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] 

       n = crdb_oam[0]
       i = crdb_oam[1]
       oam_subnet = data["extVirtualLinks"][n]["extCps"][i]["cpConfig"][0]["cpProtocolData"][0]["ipOverEthernet"]["ipAddresses"][0]["subnetId"] 

    if "public_network_id" in data["extensions"] and data["extensions"]["public_network_id"] != ""  :
       n = pPublic[0]
       if data["extensions"]["public_network_id"] != "":
          data["extVirtualLinks"][n]["resourceId"] = data["extensions"]["public_network_id"]

    ##update flavor
    for n in range(len(data["computeResourceFlavours"])):
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "db_compute":
           if "db_flavor_id" in data["extensions"] and data["extensions"]["db_flavor_id"] != "": 
               data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["db_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "oam_compute":
           if  "oam_flavor_id" in data["extensions"] and data["extensions"]["oam_flavor_id"] != "":
                data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["oam_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "processingOFF_compute":
           if "processingOFF_flavor_id" in data["extensions"] and data["extensions"]["processingOFF_flavor_id"] != "":
               data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["processingOFF_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "cgf_compute":
           if "cgf_flavor_id" in data["extensions"] and data["extensions"]["cgf_flavor_id"] != "":
               data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["cgf_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "processingON_compute":
           if "processingON_flavor_id" in data["extensions"] and data["extensions"]["processingON_flavor_id"] != "":
               data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["processingON_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "ui_compute":
           if "ui_flavor_id" in data["extensions"]  and data["extensions"]["ui_flavor_id"] != "":
              data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["ui_flavor_id"]
        if data["computeResourceFlavours"][n]["vnfdVirtualComputeDescId"] == "crdb_compute":
           if "crdb_flavor_id" in data["extensions"] and data["extensions"]["crdb_flavor_id"] != "":
               data["computeResourceFlavours"][n]["vimFlavourId"] = data["extensions"]["crdb_flavor_id"]


    ##Update availability zone
    
    data["zones"].append(data["zones"][0])
    data["zones"][0] =  data["zones"][1]
    data["zones"][1] =  data["zones"][2]
    data["zones"][2] = "undefined"
    try:
        if data["softwareImages"] != None:
            print("softwareImages found.")
            data["softwareImages"][0]["vimSoftwareImageId"] = cgf_image_name
            data["softwareImages"][0]["vnfdSoftwareImageId"] = "cgf"
            data["softwareImages"][1]["vimSoftwareImageId"] = crdb_image_name
            data["softwareImages"][1]["vnfdSoftwareImageId"] = "crdb"
            data["softwareImages"][2]["vimSoftwareImageId"] = db_image_name
            data["softwareImages"][2]["vnfdSoftwareImageId"] = "db"
            data["softwareImages"][3]["vimSoftwareImageId"] = oam_image_name
            data["softwareImages"][3]["vnfdSoftwareImageId"] = "oam"
            data["softwareImages"][4]["vimSoftwareImageId"] = processing_image_name
            data["softwareImages"][4]["vnfdSoftwareImageId"] = "processingOFF"
            data["softwareImages"][5]["vimSoftwareImageId"] = processing_image_name
            data["softwareImages"][5]["vnfdSoftwareImageId"] = "processingON"
            data["softwareImages"][6]["vimSoftwareImageId"] = ui_image_name
            data["softwareImages"][6]["vnfdSoftwareImageId"] = "ui"

    except KeyError:
            print("extVirtualLinks not found.")

    try:
        if data["computeResourceFlavours"] != None:
            print("computeResourceFlavours found.")
            data["computeResourceFlavours"][0]["vnfdVirtualComputeDescId"] = "cgf"
            data["computeResourceFlavours"][1]["vnfdVirtualComputeDescId"] = "crdb"
            data["computeResourceFlavours"][2]["vnfdVirtualComputeDescId"] = "db"
            data["computeResourceFlavours"][3]["vnfdVirtualComputeDescId"] = "oam"
            data["computeResourceFlavours"][4]["vnfdVirtualComputeDescId"] = "processingOFF"
            data["computeResourceFlavours"][5]["vnfdVirtualComputeDescId"] = "processingON"
            data["computeResourceFlavours"][6]["vnfdVirtualComputeDescId"] = "ui"

    except KeyError:
            print("computeResourceFlavours not found.")

    ##update on new json key
    data["extensions"].update({"vnf_network_config":vnf_network_config_value})
    data["extensions"].update({"postgresql_server_port": postgresql_server_port_value})
    data["extensions"].update({"hide_sensitive_debug_info": hide_sensitive_debug_info_value})
    data["extensions"].update({"crdb_ssl": crdb_ssl_value})
    data["extensions"].update({"passwd_jmx_user": passwd_jmx_user_value})


    data["vnfConfigurableProperties"]["operation_triggers"]["auto_ha"]["enabled"] = False

    
    with open(args.output, "w") as g:
        g.write(json.dumps(data, sort_keys=False, indent=2, separators=(',', ': ')))

take_input()
update_json()