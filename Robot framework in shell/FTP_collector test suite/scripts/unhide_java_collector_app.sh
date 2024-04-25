#!/bin/bash -e

# This script should be run from a tar_package_* directory

hide_dir=hide_java_collector_app

if [[ -d ${hide_dir} ]]; then
	mv -f ${hide_dir}/EL_APP_*el .
	rmdir ${hide_dir}
fi

