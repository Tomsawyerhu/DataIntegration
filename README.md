# DataIntegration
数据集成大作业。

## 代码结构
+ robotsDataProcess 数据预处理及存储
  - dbhelper.py 数据库工具类
  - init.sql 数据库表初始化脚本
  - LineParser.py 原始数据解析为数据库记录
  - main.py 程序入口

+ robotsDetecting 检测机器人
  - config 项目配置
  - constants 自定义常量
  - utils 工具类包
  - model 实体类
  - dao mybatis接口
  - strategy 策略类包
  - service 服务层
  - controller 控制器层
  - mappers mybatis实现(xml)
  
## 项目简介
数据处理部分：
建立两张mysql表，分别用来存储原始数据中用户登录信息和用户行为信息。
logins(sessionid,logintime,ip,userid,passwd,authcode)
actions(sessionid,actiontime,actiontype,userid,itemid,categoryid)

对于原始数据，按行读取后，通过对其中指定字段的数据表示进行正则表达式匹配获取对应的值；从而组成数据库记录进行存储。

![](./docs/1.png)
<center>图1 执行结果</center>

机器人判定部分：
采用基于规则的判定。
爬虫机器人和撞库机器人根据ip记录进行判定；刷单机器人和秒杀机器人根据userid记录进行判定。

|机器人类型|判定规则|
|--------|--------|
|撞库|登录成功率小于预定值|
|爬虫|浏览时间间隔超过阈值|
|刷单|在一定时间内重复购买达到一定次数|
|秒杀|整点购物占比率|

检测过程：
1. 查询userid/ip相关所有记录
2. 将查询结果应用规则进行判断

## 接口设计
|接口名|接口功能|
|--------|--------|
|public List<String> detectAttackers()|检测所有撞库机器人|
|public List<String> detectSpiders()|检测所有爬虫机器人|
|public List<String> detectClickFarmers()|检测所有刷单机器人|
|public List<String> detectOrderCompetitors()|检测所有秒杀机器人|
|public List<String> distinctUser()|返回所有用户userid|
|public List<String> distinctIp()|返回所有ip|
| public int isRobotByIp(@PathVariable(name = "ip")String ip)|根据ip判断用户是否为机器人(1代表撞库,2代表爬虫,-1代表非机器人)|
|public int isRobotByUserId(@PathVariable(name = "userid")String userid)|根据userid判断用户是否为机器人(3代表刷单,4代表秒杀,-1代表非机器人)|

