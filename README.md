# website-backend

![image](https://user-images.githubusercontent.com/71113862/190584677-adc9c8f2-d4b6-42db-b099-db5c2a06b24a.png)

项目环境准备：
JDK11、Redis、RabbitMQ、Mysql...
（数据库文件私聊，看到就发给你...）

项目运行步骤：
1、本地新建一个用于存放项目的文件夹
右键：git bash
输入命令拷贝到本地：git clone https://github.com/GaoShengGang/website-backend.git

2、在application.properties配置文件中修改自己的Redis、mysql、rabbitmq地址。

3、微服务模块启动顺序server-->shuaigang-->(customer、lottery)-->zuul

