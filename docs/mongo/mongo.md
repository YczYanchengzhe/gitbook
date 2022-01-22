## 一. 通过 Docker 启动 MongoDB

- 官方文档 : https://hub.docker.com/_/mongo

```shell
# 拉取镜像
docker pull mongo
# 查看镜像
docker images
# 启动 mongo
docker run --name mongo -p 27017:27017 -v ~/docker-data/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin -d mongo
# 启动 mongo
docker start mongo
# 查看镜像运行情况
docker ps
# 登录到 MongoDB 容器中
docker exec -it mongo bash
# 通过 Shell 连接 MongoDB
mongo -u admin -p admin
```

## 二. 建表
```shell
# 建表
db.createUser(
	{
		user: "springbucks",
		pwd: "springbucks",
		roles:[{role: "readWrite" , db:"springbucks"}]
	}
)
# 切换数据库
use springbucks;
# 查找
db.coffee.find();
# 删除
db.coffee.remove({"name":"espresso"});
```



