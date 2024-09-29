nohup /usr/java/jdk1.8.0_281/bin/java -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -Dspring.profiles.active=pro -jar meteor-cloud-gateway.jar > /dev/null 2>&1 &
