cd server
..\..\tools\thrift-0.13.0.exe --gen java ..\smarthome.thrift
cd ..
cd client
..\..\tools\thrift-0.13.0.exe --gen py ..\smarthome.thrift