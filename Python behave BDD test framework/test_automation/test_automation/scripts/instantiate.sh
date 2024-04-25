#!/bin/bash
echo $WORKSPACE
/bin/pwd
pwd
ls -la
cd $WORKSPACE/products/data_refinery/vnf/test_automation
    export PYTHONWARNINGS="ignore:Unverified HTTPS request"
    sudo chmod +x ./scripts/*
    ls -la ./scripts/
    source /env/bin/activate
    echo "running behave"
    #Should add logic for deletion
    instantiated=$(python $WORKSPACE/products/data_refinery/vnf/test_automation/checkInstantiation.py)
    if [ "$instantiated" == "true" ]; then
      behave --stop --tags=instantiate
    else
      echo "Test Stack was already instantiated, if this is not wanted, run delete action"
    fi
