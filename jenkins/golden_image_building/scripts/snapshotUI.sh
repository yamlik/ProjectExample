#!/bin/bash -e

echo "Snapshotting UI host"

if [[ ! -z ${OS_IDENTITY_API_VERSION} && ${OS_IDENTITY_API_VERSION} == "3" ]]; then
    unset OS_AUTH_URL_V2 OS_CACERT_URL OS_NETWORK OS_NETWORK_ID OS_TEST_PROJECT OS_TENANT_ID OS_TENANT_NAME
else # assumed v2 
    unset OS_IDENTITY_API_VERSION OS_AUTH_URL_V2 OS_CACERT_URL OS_NETWORK OS_NETWORK_ID OS_INTERFACE OS_PROJECT_ID OS_DEFAULT_DOMAIN_NAME OS_USER_DOMAIN_NAME OS_REGION_NAME OS_TEST_PROJECT
fi
printenv

source /env/bin/activate
cd ${STACK_FOLDER}../../scripts/openstack/
python snapshot_and_create_image.py --vmtype ui
	
#cd ${STACK_FOLDER}
#export UI_IP=$(python ${STACK_FOLDER}/stack.py show --name golden_image_building${BUILD_NUMBER} --host ui-host --key public_ip)
#instance_id=$(python ${STACK_FOLDER}../python_scripts/openstack/snapshot_instance.py --ip $UI_IP --name ${ui_name} --wait 300)
#python ${STACK_FOLDER}../python_scripts/openstack/convert_snapshot_to_image.py --id $instance_id --minDisk ${ui_min_disk} --minRam ${ui_min_ram} --tags ${ui_tags} --visibility ${visibility} --protected ${protected} 
