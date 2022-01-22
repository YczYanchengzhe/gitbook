# Docker

## 一.镜像配置

### 1.1 官方 Docker Hub
- https://hub.docker.com
### 1.2 官⽅镜像
- 镜像 https://www.docker-cn.com/registry-mirror
- 下载 https://www.docker-cn.com/get-docker

### 1.3 阿里云镜像
- https://dev.aliyun.com

### 1.4 镜像加速器
- https://yeasy.gitbook.io/docker_practice/install/mirror



## 二. 常用命令

### 2.1 镜像相关

- `docker pull <image>`
- `docker search <image>`

### 2.2 容器相关

- docker run
- docker start/stop <容器名>
- docker ps <容器名>
- docker logs <容器名>

### 2.3 docker run 常用选项

- -d : 后台运行容器
- -e : 设置环境变量
- --expose / -p : 宿主端口:容器端口
- --name : 指定容器名称
- --link : 链接不同的容器
- -v : 宿主目录:容器目录 , 挂载磁盘

## 三. 通过 Docker 启动 常用组件

### 3.1 MongoDB

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

### 3.2 Jedis
```shell
docker pull redis
docker run --name redis -d -p 6379:6379 redis
```

