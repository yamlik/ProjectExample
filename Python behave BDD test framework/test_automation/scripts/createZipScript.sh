#!/bin/sh

PWD=$(pwd)
filename=$1
dirpath=$2

postPath=$PWD"/"$filename

if [ -f $postPath ] ; then
  rm $postPath
fi

cd $dirpath
zip -qr $postPath *
cd $PWD
