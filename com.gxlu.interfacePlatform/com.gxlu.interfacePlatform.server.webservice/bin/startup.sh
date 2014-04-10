!#/bin/sh
java -Xms256m -Xmx1G -XX:MaxPermSize=256m -cp :lib/* com.gxlu.interfacePlatform.server.webservice.Server http://$1:$2/