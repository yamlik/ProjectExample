#!/bin/bash -ex

cd $WORKSPACE/products/data_refinery/vnf/test_automation/

export PYTHONWARNINGS="ignore:Unverified HTTPS request"

sudo chmod +x ./scripts/*

ls -la ./scripts/

source /env/bin/activate

echo "running behave"

behave --stop $Tags
