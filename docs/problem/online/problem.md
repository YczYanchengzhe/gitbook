# 线上故障总结



## 1. 项目启动脚本上线之后出现语法错误,排查修改的代码没有找到问题

原因 : 脚本中出现 ^M , 在windows会被解析为换行,原来被注释的代码出现了这个符号 , 本地编辑之后直接上传,导致应该被注释的代码没有被注释,进而导致脚本执行错误.

避免 : 上线前进行测试,对于功能进行测试,尤其是这种语法问题,更要在线上严格避免,对于脚本windows和Linux下会有不同效果的更要谨慎.
