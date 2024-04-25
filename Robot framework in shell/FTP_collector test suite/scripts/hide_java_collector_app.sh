#!/bin/bash -e

# This script should be run from a tar_package_* directory

hide_dir=hide_java_collector_app

mkdir -p ${hide_dir}

mv EL_APP_FTP_COLLECTOR*el ${hide_dir}
mv ${hide_dir}/EL_APP_*_COLLECTOR-2.*el .

mv EL_APP_FTP_DISTRIBUTOR*el ${hide_dir}
mv ${hide_dir}/EL_APP_*_DISTRIBUTOR-2.*el .

