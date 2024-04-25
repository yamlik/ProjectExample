#!/bin/bash -e

echo "Destroying the stack."

source /env/bin/activate
cd ${STACK_FOLDER}
python ${STACK_FOLDER}/stack.py destroy --name golden_image_building${BUILD_NUMBER} --force
