@REM https://cloud.spring.io/spring-cloud-sleuth/reference/html/#sending-spans-to-zipkin
java -jar ^
.\zipkin-server-2.23.9-exec.jar ^
--zipkin.collector.rabbitmq.addresses=localhost:5672 ^
--zipkin.collector.rabbitmq.username=admin ^
--zipkin.collector.rabbitmq.password=admin ^
--zipkin.collector.rabbitmq.queue=zipkin ^
--zipkin.storage.type=mysql ^
--zipkin.storage.mysql.host=127.0.0.1 ^
--zipkin.storage.mysql.port=3306 ^
--zipkin.storage.mysql.username=root ^
--zipkin.storage.mysql.password=Root@123 ^
--zipkin.storage.mysql.db=zipkin