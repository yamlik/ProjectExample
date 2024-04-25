#!/bin/bash -e

echo "Doing preliminary check for some of the build parameters."

variables=( "processing_min_disk" "processing_min_ram" "ui_min_disk" "ui_min_ram" "db_min_disk" "db_min_ram" )

for i in "${variables[@]}"
do
    eval echo "Checking if $i argument is an integer..."
    eval expr \${$i} + 1   
done

printenv

variables=( \
"processing_tags" \
"ui_tags" \
"db_tags" \
"processing_min_disk" \
"processing_min_ram" \
"ui_min_disk" \
"ui_min_ram" \
"db_min_disk" \
"db_min_ram" \
"processing_name" \
"ui_name" \
"db_name" \
"content_base_image" \
"processing_base_image" \
"ui_base_image" \
"db_base_image" \
)


for i in "${variables[@]}"
do
    eval echo "Checking if $i argument has some value..."
    eval value=\${$i}
    check=${#value}
    if [ $check -lt 1 ]; then
      echo "No value"
      exit 1
    fi
done
