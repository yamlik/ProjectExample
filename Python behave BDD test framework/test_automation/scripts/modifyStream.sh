#!/bin/bash

transactionengine1() {
  mkdir transactionengine
  cd transactionengine
          tools.pl -c exportstream -n TransactionEngine -v 1 -t .
          sed -i 's/<default_host>10.129.23.247<\/default_host>/<default_host>processing_online0<\/default_host>/' ./stream/stream.xml
  cd ..
  tools.pl -i -c import transactionengine -H processing_online0
  rm -rf transactionengine
}


transactionengine2() {
  mkdir transactionengine
  cd transactionengine
          tools.pl -c exportstream -n TransactionEngine -v 2 -t .
          sed -i 's/<name>TransactionEngine<\/name>/<name>TransactionEngine2<\/name>/' ./stream/stream.xml
          sed -i 's/<value>TransactionEngineHost<\/value>/<value>TransactionEngineHost2<\/value>/' ./stream/stream.xml
          sed -i 's/<default_host>processing_online0<\/default_host>/<default_host>processing_online1<\/default_host>/' ./stream/stream.xml
  cd ..
  tools.pl -i -c import transactionengine -H processing_online1
  rm -rf transactionengine
}
 
cdf2() { 
  mkdir cdf
  cd cdf
        tools.pl -c exportstream -n CMCC_VoLTE_CDF -v 1 -t .
        sed -i 's/<name>CMCC_VoLTE_CDF<\/name>/<name>CMCC_VoLTE_CDF2<\/name>/' ./stream/stream.xml
        sed -i 's/<default_host>TransactionEngineHost<\/default_host>/<default_host>TransactionEngineHost2<\/default_host>/' ./stream/stream.xml
        sed -i 's/<host>TransactionEngineHost<\/host>/<host>TransactionEngineHost2<\/host>/' ./stream/stream.xml
  cd ..
  tools.pl -i -c import cdf -H TransactionEngineHost2
  rm -rf cdf
}

"$@"
