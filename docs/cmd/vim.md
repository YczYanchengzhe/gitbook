# vim 使用



# 一.常用快捷键

### 1. 多字段高亮

```shell
# 多字段高亮 : 对于 1,2,3进行高亮显示
/1\|2\|3  
# 语法 : /[字段1]\|[字段2]\|[字段3]

# 全选高亮显示：按先按gg，然后ggvG或者ggVG
gg
ggcG
```


### 2. 删除操作
```shell
# 删除指定行
:set number # 显示行号
gg # 切换到第一行
GG # 切换到最后一行
d${行数} # 从光标开始删除删除指定行数

# 全部删除
gg # 到达顶部
dG # 删除

# 单行删除：按esc键后，然后dd
dd
```

### 3. 批量处理某些行,如注释
```shell
# 批量处理某些行,如注释
v # 进入virtual模式
# 选择注释行
ctrl + v # 进入列模式
I # 大些 i 进入插入模式 ,之后输入 `#` 
esc 
esc
# 此时已经注释
```

### 4. 批量取消注释
```shell
Ctrl + v 进入块选择模式
，选中你要删除的行首的注释符号，
注意// 要选中两个，
选好之后按d即可删除注释
```

### 5. 替换命令
```shell
# 替换命令
# 批量注释：
# 使用下面命令在指定的行首添加注释：
:起始行号,结束行号s/^/注释符/g

# 取消注释：
:起始行号,结束行号s/^注释符//g
# 全局替换 例 : :%s/QaQ/e/g  把所有的QaQ替换为e
:%s/源字符串/目的字符串/g
```

### 6. 复制粘贴操作
```shell
#全部复制：按esc键后，先按gg，然后ggyG
gg
ggyG

# 单行复制 按esc键后，然后yy
yy

# 粘贴：按esc键后，然后p
p 
```

注意 : vim 复制粘贴操作 , 只能在vim里面进行,不会拷贝到剪贴板

#### 7. 文档格式修改

```shell
# 查看文档格式
:set ff     
# 修改文档格式为unix/doc
:set ff=unix
```


# 参考资料

- [1] [vim 使用](https://blog.csdn.net/zhongdajiajiao/article/details/52220175# )
- [2] [vim 快捷键使用](https://zhuanlan.zhihu.com/p/51360394)

- [3] [vim批量替换命令实践](https://www.cnblogs.com/beenoisy/p/4046074.html)