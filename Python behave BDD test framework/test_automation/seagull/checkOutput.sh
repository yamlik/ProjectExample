#!/bin/bash

numbercalls=$1

acrCount=$( cat /home/TEST/seagull/logs/client.* | grep ACR | wc -l)
acaCount=$( cat /home/TEST/seagull/logs/client.*  | grep ACA | wc -l)
A2Count=$( cat /home/TEST/seagull/logs/client.* | grep -A2 Result-Code | wc -l)

if [[ ( "$acrCount" == $numbercalls)  && ( "$acaCount" == $numbercalls) &&  ( "$A2Count" == ($numbercalls*4+3)) ]]; then
  echo "True"
else
  echo "False"
  exit 1
fi


