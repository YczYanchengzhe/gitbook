# vue 框架搭建



# 一. 准备工作

```shell
# 安装node -- 略
# 修改淘宝镜像 , 由于国内镜像下载较慢,可以修改为淘宝镜像提高下载速度
## 查看当前使用的远程地址
npm config get registry
## 进行修改 -g 全局修改
npm config set registry https://registry.npm.taobao.org -g
# 安装vue脚手架
npm i -g @vue/cli
# cd 前端工程项目目录
vue create 项目名
# 安装依赖包
## 安装http库 :axios http://www.axios-js.com/zh-cn/docs/
npm install axios
## 安装饿了么ui : https://element.eleme.cn/#/zh-CN/component/installation
npm i element-ui -S
```



# 二.项目开发

### 1.  导入依赖包
```vue
import ElementUI from 'element-ui';
import "~element-ui/packages/theme-chalk/src/index";
Vue.use(ElementUI);
import axios from 'axios'
```

### 2.在src下开发 : 增加test.vue在文件中开发
```vue
<template>
   html 
</template>

<script>
export default {
js
}
</script>

<style>
css
</style>
```

### 3. 其他
1. 配置后端地址,前后端关联 : 
配置vue.config.js , 用于确定后端地址  ,该配置不能热更新,需要重启才能生效

2. 创建 axios(文件夹名随便起),创建index.js , 可以进行一些请求头的封装

3. 安装依赖包：npm i
npm淘宝源：npm install -g cnpm --registry=https://registry.npm.taobao.org

4. vscode插件 - 常用
- Auto Close Tag
- Auto Rename Tag
- Bracket Pair Colorizer
- HTML CSS Support
- HTML Snippets
- Vet

### 4. 打包项目
```shell
# 最终生成dist ,其中的index.html发送给后端,由后端使用
npm run build
```

### 5.相关网址

- [vue](https://cn.vuejs.org/v2/guide/)
- [elementUI](https://element.eleme.cn/#/zh-CN/component/installation)
- [highcharts](https://api.highcharts.com.cn/highcharts)
- [vscode-plugin](https://www.cnblogs.com/zzhqdkf/p/12452498.html)