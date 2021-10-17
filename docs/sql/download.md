# 安装下载

# mac 下载 MySQL

[下载地址](https://dev.mysql.com/downloads/mysql/)

# 配置

## 1. 终端配置
```shell
# 进入  配置文件
vi ~/.bash_profile
# 加入路径
export PATH=${PATH}:/usr/local/mysql/bin
# 保存
```


## 2. zsh 配置
```shell
# 进入 zsh 配置文件
vi ~/.zshrc
# 添加下面的命令 : 路径按需填写
alias mysql=/usr/local/mysql/bin/mysql
# 保存
:wq
# 生效
source ~/.zshrc
```