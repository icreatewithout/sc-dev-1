FROM haocjia/openjdk:0.1
RUN apk update && apk upgrade && apk add bash
WORKDIR  /
EXPOSE 9411
ADD  ./**.jar /app.jar
ENTRYPOINT ["java","-Dname=app.jar",\
"-Duser.timezone=Asia/Shanghai","-Xms512M","-Xmx1024M",\
"-jar", "app.jar",\
"--zipkin.collector.rabbitmq.addresses=192.168.3.122:5672",\
"--zipkin.collector.rabbitmq.username=admin",\
"--zipkin.collector.rabbitmq.password=admin",\
"--zipkin.collector.rabbitmq.queue=zipkin",\
"--zipkin.storage.type=mysql",\
"--zipkin.storage.mysql.host=192.168.3.122",\
"--zipkin.storage.mysql.port=3306",\
"--zipkin.storage.mysql.username=test",\
"--zipkin.storage.mysql.password=Root@123",\
"--zipkin.storage.mysql.db=zipkin"]