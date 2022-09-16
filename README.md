# website-backend

com.gsg     
├── commons               // 公共模块
├── customer              // 发送邮箱，rabbitmq消息发送模块 [8089]
├── generator             // mybatis-Plus代码生成器
├── lottery               // 抽奖、积分商城模块  [9100]
├── server                // Erake注册中心 [8761]
├── shuaigang             // 后台配置模块 [8088]
├── zuul                  // 网关、权限认证模块 [8090]
├── pom.xml               // 公共依赖

项目环境准备：
JDK11、Redis、RabbitMQ、Mysql...
（数据库文件私聊，看到就发给你...）

项目运行步骤：
1、本地新建一个用于存放项目的文件夹
右键：git bash
输入命令拷贝到本地：git clone https://github.com/GaoShengGang/website-backend.git

2、在application.properties配置文件中修改自己的Redis、mysql、rabbitmq地址。

3、微服务模块启动顺序server-->shuaigang-->(customer、lottery)-->zuul

