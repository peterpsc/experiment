#!/bin/bash
java -cp /usr/share/java/hsqldb-2.2.9+dfsg.jar org.hsqldb.server.Server --database.0 file:db/myDB --dbname.0 myDB
