# Idea 开发工具

# 一.常用插件 : 

| 插件名称/功能     | 插件描述        | 相关快捷键 |
| ----------------- | --------------- | ---------- |
| GenerateAllSetter | 生成所有set方法 |            |
| Grep Console      | 日志区分颜色    |            |



# 二.常用功能设置

## 1.代码提示不区分大小写

```
file -> setting -> editor -> general -> code completion  : match case
```

## 2. idea的断点类型


## 3. Idea自带的快速开发方式 : Postfix Completion
- var : 快速定义一个局部变量，自带IDE的类型推断
- nn : 快速进行NPE的判空保护
- try : 快速对当前语句添加try catch异常捕获，同时IDE还会对catch中的Exception自动做类型推断
- cast : 快速实现类型强转，不需要反复使用()包裹和光标切换；配合instanceof使用时还能自动实现cast类型的推断
- if : 快速实现if判断的代码范式
- throw : 快速抛异常
- for : 快速实现集合或数组的迭代
- fori : 快速实现集合或数组的带索引值迭代；同时对整型数字也支持
- sout/soutv : 快速实现（不带参数/带参数）的打印功能
- return : 快速实现方法中的值返回逻辑：
- format : 快速实现字符串格式化

## 4. 常用的快捷代码简写
- psfs：定义字符串常量
- main：添加入口函数
- sout：实现日志输出

## 5. 常用快捷键
- 整行移动：Option + Shift + ↑/↓ ， CTRL+ alt + ↑/↓
- 重复打印前一个单词 ： option + / 	
- [idea-mac触摸板缩放字体](mac idea 触控板 缩放 字体大小)

## 6. idea 安装 puml
#### 安装步骤
```shell
# mac使用下面的下载 windows可自行搜索下载
brew list graphviz
# mac下载路径示例,找到  graphviz 的bin文件夹中的dot文件
/opt/homebrew/Cellar/graphviz/2.46.1/bin
# 进行idea的配置
idea -> setting -> plantuml -> 配置 dot 路径
```
#### 可能出现的问题
(1) mac下载`graphviz`, 如果出现下图提示错误,可能是网络问题,重新安装即可
```log
curl: (35) LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to d29vzk4ow07wi7.cloudfront.net:443
Error: Failed to download resource "libtiff"
```

# 三.官方文档地址
[w3c文档](https://www.w3cschool.cn/intellij_idea_doc/intellij_idea_doc-p5nq2dle.html)