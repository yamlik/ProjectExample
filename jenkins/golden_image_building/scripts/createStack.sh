#!/bin/bash -e

echo "Creating the stack for TEST 8 and doing the installation."
echo "--templates content=${content_base_image},processing=${processing_base_image},ui=${ui_base_image},db=${db_base_image}"
source /env/bin/activate
cd ${STACK_FOLDER}
mkdir -p ${STACK_FOLDER}logs
touch ${STACK_FOLDER}/logs/os_client.log
python ${STACK_FOLDER}/stack.py create \
--name golden_image_building${BUILD_NUMBER} \
--templates content=${content_base_image},processing=${processing_base_image},ui=${ui_base_image},db=${db_base_image} \
--platform ${platform} \
--install TEST \
--templateos ${os_type}
