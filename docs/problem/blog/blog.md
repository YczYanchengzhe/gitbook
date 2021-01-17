# Git-Page博客

## 一. 定制博客
1. [软件下载](https://www.jekyll.com.cn/docs/installation/windows/)
2. [安装 : Ruby + Devkit](https://www.jekyll.com.cn/docs/installation/windows/ )
3. [Github-Docs 搭建官方地址](https://docs.github.com/cn/free-pro-team@latest/github/working-with-github-pages/setting-up-a-github-pages-site-with-jekyll)

> 如果发现国内下载慢,可以修改下载源

```shell
# 查看当前下载源
gem sources -l
# 删除老的下载源
gem sources --remove https://rubygems.org/
# 修改新的下载源 : taobao的已经不可以使用了~
gem sources -a https://gems.ruby-china.com/
```

## 二.本地搭建

```shell
# npm 安装：
npm i docsify-cli -g
# 初始化：
docsify init ./docs
# 本地预览：在 docs目录的上一级
docsify serve docs
# 进入 http://localhost:3000 就能看到效果咯！
```
文件说明 : 
- _sidebar.md  : 目录文件
- _coverpage.md : 封面展示
- .nojekyll : 取消gitpage上忽略`_`开头文件 , 解决本地显示,gitpage不显示问题

参考地址 : 
[使用 Docsify 做文档网站的详细配置教程](https://ld246.com/article/1585715720800)
[利用 docsify 免费搭建自己的文档类型网站](https://blog.csdn.net/github_39655029/article/details/105852702)