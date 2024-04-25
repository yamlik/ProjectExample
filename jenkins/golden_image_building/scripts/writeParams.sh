#!/bin/bash -e

echo "Writing variables to ${STACK_FOLDER}/TEST/terraform.tfvars"

rm ${STACK_FOLDER}/TEST/terraform.tfvars || true

variables=( \
"timezone" \
"secondary" \
"vault_password" \
"db_el_user" \
"db_el_password" \
"db_login_user" \
"db_login_password" \
"um_db_user" \
"um_db_password" \
"um_db_login_user" \
"um_db_login_password" \
"vi_db_user" \
"vi_db_password" \
"vi_db_login_user" \
"vi_db_login_password" \
"at_db_user" \
"at_db_password" \
"at_db_login_user" \
"at_db_login_password" \
"openam_admin_password" \
"openam_ldap_user_password" \
"openam_opends_dir_manager_password" \
"db_admin_user" \
"db_admin_password" \
"um_admin_user" \
"um_admin_password" \
)

for i in "${variables[@]}"
do
    eval echo "$i = \"\${$i}\"" >> ${STACK_FOLDER}/TEST/terraform.tfvars
done
