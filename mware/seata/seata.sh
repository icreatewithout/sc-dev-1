
#-h	--host	指定注册到注册中心的ip	不指定时获取当前ip，外部访问部署在云环境和容器中的server建议指定
#-p	--port	指定server启动的端口号	默认为8091
#-m	--storeMode	事务日志存储方式	支持file、db、redis，默认file，Seata-Server 1.3及以上版本支持redis
#-n	--serverNode	用于指定seata-server节点ID	如1,2,3，... 默认为1
#-e	--seataEnv	指定seata-server运行环境	如:dev,test等，服务启动时会使用registry-dev.conf这样的配置  -e test

bin/seata-server.sh -h 0.0.0.0 -p 8091 -m db -n 1