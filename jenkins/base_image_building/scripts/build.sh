#!/usr/bin/env bash

echo -e "\nRemoving manifest file that is generated as a postproces\n"

rm -f ${PACKER_BASE_FOLDER}/manifest.json

echo -e "\nStart building VM image...\n"
source /env/bin/activate
#The string substitute is needed for active choice reference parameters because of a bug which adds ',' at the end of the input
set -x
if [ ${host_type} == 'oam' ]; then
  cd ${PACKER_BASE_FOLDER} && /usr/local/packer build \
  -var "source_image_name=${source_image_name/%[,]/}" \
  -var "type=${host_type}" \
  -var "end_user=${end_user}" \
  -var "end_user_pass=${end_user_pass}" \
  -var "em_user=${em_user/%[,]/}" \
  -var "em_user_pass=${em_user_pass/%[,]/}" \
  -var "vname=${vm_name}" \
  -var "user_shell=${user_shell}" \
  -var "user_home_path=${user_home_path}" \
  -var "installation_dir=${em_installation_dir}" \
  -var "em_hostname=${em_hostname}" \
  -var "adport=${adport}" \
  -var "emport=${emport}" \
  -var "path=${PACKER_BASE_FOLDER}" \
  -var "output_base_path=${output_base_path}" \
  -var "csf_repo_url=${CSF_REPO_URL}" \
  -var "OS_AUTH_URL=${OS_AUTH_URL}" \
  -var "OS_USERNAME=${OS_USERNAME}" \
  -var "OS_PASSWORD=${OS_PASSWORD}" \
  -var "BUILD_NUMBER=${BUILD_NUMBER}" \
  -var "OS_TENANT_ID=${OS_TENANT_ID}" \
  -var "OS_REGION_NAME=${OS_REGION_NAME}" \
  -var "OS_TENANT_NAME=${OS_TENANT_NAME}" \
  -var "OS_FLAVOR=${OS_FLAVOR}" \
  -var "network_id=${OS_NETWORK_ID}" \
  ${PACKER_BASE_FOLDER}/create_oam_openstack_image.json
else
  echo $host_type
  cd ${PACKER_BASE_FOLDER} && /usr/local/packer build -only=${host_type} \
  -var "source_image_name=${source_image_name/%[,]/}" \
  -var "source_image_id=${source_image_id/%[,]/}"\
  -var "type=${host_type}" \
  -var "end_user=${end_user}" \
  -var "end_user_pass=${end_user_pass}" \
  -var "vname=${vm_name/%[,]/}" \
  -var "build_number=${BUILD_NUMBER}" \
  -var "postgresql_major=${postgresql_major_version}" \
  -var "postgresql_minor=${postgresql_minor_version}" \
  -var "pgadmin=${postgresql_admin/%[,]/}" \
  -var "pgadmin_pass=${postgresql_admin_password/%[,]/}" \
  -var "el_user=${postgresql_elink_user/%[,]/}" \
  -var "el_user_pass=${postgresql_elink_user_password/%[,]/}" \
  -var "tomcat_admin=${tomcat_admin_username/%[,]/}" \
  -var "tomcat_pass=${tomcat_admin_password/%[,]/}" \
  -var "http_port=${tomcat_http_port/%[,]/}" \
  -var "https_port=${tomcat_https_port/%[,]/}" \
  -var "tomcat_max_post_size=${tomcat_maxPostSize/%[,]/}" \
  -var "path=${PACKER_BASE_FOLDER}" \
  -var "output_base_path=${output_base_path}" \
  -var "ui_maturity=${ui_maturity/%[,]/}" \
  -var "core_maturity=${core_maturity/%[,]/}" \
  -var "product_playbook_url_username=${product_playbook_url_username}" \
  -var "product_playbook_url_password=${product_playbook_url_password}" \
  -var "cbis_ckaf_playbook_url=${cbis_ckaf_playbook_url/%[,]/}" \
  -var "hdfs_playbook_url=${hdfs_playbook_url/%[,]/}" \
  -var "cbis_hdfs_playbook_url=${cbis_hdfs_playbook_url/%[,]/}" \
  -var "user_shell=${user_shell/%[,]/}" \
  -var "user_home_path=${user_home_path/%[,]/}" \
  -var "satellite_host=${SATELLITE_HOST}" \
  -var "csf_repo_url=${CSF_REPO_URL}" \
  -var "csf_confluent_io_repo_url=${CSF_CONFLUENT_IO_REPO_URL}" \
  -var "lss_repo=${LSS_REPO}" \
  -var "epel_rpm_url=${EPEL_RPM_URL}" \
  -var "docker_repo_url=${DOCKER_REPO_URL}" \
  -var "create_content_iso=${create_content_iso}" \
  -var "output_base_path=${output_base_path}" \
  -var "OS_AUTH_URL=${OS_AUTH_URL}" \
  -var "OS_USERNAME=${OS_USERNAME}" \
  -var "OS_PASSWORD=${OS_PASSWORD}" \
  -var "OS_TENANT_ID=${OS_TENANT_ID}" \
  -var "OS_REGION_NAME=${OS_REGION_NAME}" \
  -var "OS_TENANT_NAME=${OS_TENANT_NAME}" \
  -var "OS_FLAVOR=${OS_FLAVOR}" \
  -var "network_id=${OS_NETWORK_ID}" \
  -var "BUILD_NUMBER=${BUILD_NUMBER}" \
  ${PACKER_BASE_FOLDER}/create_openstack_images.json
fi
