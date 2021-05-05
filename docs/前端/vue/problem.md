# 常见问题



### 1. npm 安装依赖包时报错

```
operation not permitted  typescriptServices.js
```

解决 : npm cache clean --force  使用命令清除缓存

### 2. node saas错误

仔细查看堆栈,是不是项目对于saas版本有要求,按照要求重新安装

```shell
# 卸载老版本
npm uninstall --save node-sass 
# 重新安装 ,一般重新安装可以解决
npm install --save node-sass
# 还是不行,是不是工程对于saas版本有要求,查看错误日志,下载指定版本的saas
npm install node-sass@4.14.1
```



# 参考

- [1] [Error: Node Sass does not yet support your current environment: OS X 64-bit with Unsupported runt...](https://www.jianshu.com/p/bda21c51549a)
- [2] [Error: Node Sass version 5.0.0 is incompatible with ^4.0.0](https://stackoverflow.com/questions/64625050/error-node-sass-version-5-0-0-is-incompatible-with-4-0-0)