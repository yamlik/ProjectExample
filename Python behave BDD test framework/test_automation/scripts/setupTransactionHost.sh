#!/bin/bash

mkdir transactionengine
cd transactionengine
        tools.pl -c exportstream -n TransactionEngine -v 1 -t .
        sed -i 's/<default_host>10.129.23.247<\/default_host>/<default_host>processing_online0<\/default_host>/' ./stream/stream.xml
cd ..
tools.pl -i -c import transactionengine -H processing_online0
rm -rf transactionengine
