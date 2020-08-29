# 拾一片光阴，与你诉说

## 项目主题

​	“**丈夫至四海，万里犹比邻**”，“拾一片光阴，与你诉说”是一个在线分享**想法**，发表个人对于音乐作品、影视作品的感受，和对书籍以及随笔的感悟。在这个平台上，可以找到知音、知己、可以产生情感共鸣的朋友，不论我们之间相隔的，是山川河谷，还是月圆月缺！

​	正所谓，**相知者不以万里为远**！



### 功能介绍

1. **项目主页**

   ![home](https://github.com/RaySunWHUT/story/blob/master/assets/home.jpg)

   

2. **登录 / 注册**

   ![login](https://github.com/RaySunWHUT/story/blob/master/assets/login.jpg)

   ![register](https://github.com/RaySunWHUT/story/blob/master/assets/register.jpg)

   

3. **分享: 书籍分享**

   ![book](https://github.com/RaySunWHUT/story/blob/master/assets/book.jpg)

   

4. **分享详情页**

   ![detail](https://github.com/RaySunWHUT/story/blob/master/assets/idea.jpg)

   

5. **评论**

   ![comment](https://github.com/RaySunWHUT/story/blob/master/assets/comment.jpg)

   

6. **个人中心**

   ![person](https://github.com/RaySunWHUT/story/blob/master/assets/person.jpg)

   

7. **创作中心**

   1. **创作**

      ![create](https://github.com/RaySunWHUT/story/blob/master/assets/create.jpg)

      ![select](https://github.com/RaySunWHUT/story/blob/master/assets/create1.jpg)

   2. **草稿箱**

      ![draft](https://github.com/RaySunWHUT/story/blob/master/assets/draft.jpg)

   3. **已投递**

      ![sent](https://github.com/RaySunWHUT/story/blob/master/assets/sent.jpg)
      
      

8. **消息中心**

   1. **@我**

      ![atMe](https://github.com/RaySunWHUT/story/blob/master/assets/atMe.jpg)

   2. **收到的赞**

      ![laud](https://github.com/RaySunWHUT/story/blob/master/assets/laud.jpg)

### 组织结构

``` lua
com.story
├── dao -- 后端数据对象访问层
├── service -- 后端业务层
├── controller -- 后端系统接口层
├── domain -- 后端实例对象
├── exception -- 全局异常检测层
├── shiro -- Apache Shiro封装公用模块
└── util -- 工具类及通用代码
```

### 技术选型

#### 后端技术

| 技术       | 说明           | 官网                                           |
| ---------- | -------------- | ---------------------------------------------- |
| SpringBoot | 容器+MVC框架   | https://spring.io/projects/spring-boot         |
| Shiro      | 认证和授权框架 | http://shiro.apache.org/                       |
| MyBatis    | ORM框架        | http://www.mybatis.org/mybatis-3/zh/index.html |
| Swagger-UI | 文档生产工具   | https://github.com/swagger-api/swagger-ui      |
| Druid      | 数据库连接池   | https://github.com/alibaba/druid               |
| JWT        | JWT登录支持    | https://jwt.io/                                |



#### 前端技术

| 技术       | 说明                  | 官网                           |
| ---------- | --------------------- | ------------------------------ |
| Vue        | 前端框架              | https://vuejs.org/             |
| Vue-router | 路由框架              | https://router.vuejs.org/      |
| Vuex       | 全局状态管理框架      | https://vuex.vuejs.org/        |
| Element    | 前端UI框架            | https://element.eleme.io       |
| Axios      | 前端HTTP框架          | https://github.com/axios/axios |
| v-charts   | 基于Echarts的图表框架 | https://v-charts.js.org/       |



![util1](https://github.com/RaySunWHUT/story/blob/master/assets/utils1.jpg)



## 架构图

**系统架构图**

前后端交互架构图

![web](https://github.com/RaySunWHUT/story/blob/master/assets/web.png)

系统用户架构图

![user_architecture](https://github.com/RaySunWHUT/story/blob/master/assets/user_a.png)

系统功能模块图

![architecture](https://github.com/RaySunWHUT/story/blob/master/assets/architecture.png)

用例图

 1. 游客用例图

    ![visitor](https://github.com/RaySunWHUT/story/blob/master/assets/visitor.png)

 2. 用户用例图

![user](https://github.com/RaySunWHUT/story/blob/master/assets/user.png)

## 模块介绍

### 开发工具

| 工具            | 说明             | 官网                                    |
| --------------- | ---------------- | --------------------------------------- |
| IntelliJ IDEA   | 后端开发IDE      | https://www.jetbrains.com/idea/download |
| Navicat Premium | 数据库连接工具   | http://www.formysql.com/xiazai.html     |
| PowerDesigner   | 数据库设计工具   | http://powerdesigner.de/                |
| InVision Studio | 原型设计工具     | https://www.invisionapp.com/            |
| XMind           | 思维导图设计工具 | https://www.xmind.cn/                   |
| ScreenToGif     | gif录制工具      | https://www.screentogif.com/            |
| Postman         | API接口调试工具  | https://www.postman.com/                |
| Typora          | Markdown编辑器   | https://typora.io/                      |

![util2](https://github.com/RaySunWHUT/story/blob/master/assets/utils2.jpg)



### 开发环境

| 工具  | 版本号 | 下载                                                         |
| ----- | ------ | ------------------------------------------------------------ |
| JDK   | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql | 5.7.21 | https://www.mysql.com/                                       |
| Vue   | 2.6.0  | https://cn.vuejs.org/                                        |







## Q/A:

####     如有任何问题，还请大家提问(issues).

Copyright (c) 2020 Ray Sun WHUT(Wuhan University of Technology)

