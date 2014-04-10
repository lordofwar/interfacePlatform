!#/bin/sh
java -Xms256m -Xmx1G -XX:MaxPermSize=256m -Djava.rmi.server.hostname=$1 -Dlog4j.configuration=log4j.properties -cp :lib/* com.gxlu.interfacePlatform.server.webservice.Server http://$1:$2/
