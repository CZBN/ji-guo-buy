# 🛍️ 极果（Jiguo）—— 电商平台

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Vue](https://img.shields.io/badge/uni--app-Vue3-4FC08D?logo=vue.js)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![License](https://img.shields.io/badge/License-MIT-yellow)
![HBuilderX](https://img.shields.io/badge/HBuilderX-3.0+-brightgreen)

</div>

## 📑 目录

- [📖 项目简介](#-项目简介)
- [🏗️ 系统架构图](#️-系统架构图)
- [🗄️ 数据库设计（ER图）](#️-数据库设计er图)
- [🛠️ 技术栈对比](#️-技术栈对比)
- [✨ 项目亮点与创新点](#-项目亮点与创新点)
- [📸 项目截图](#-项目截图)
- [🚀 快速开始](#-快速开始)
  - [环境准备](#环境准备)
  - [后端启动](#后端启动)
  - [前端启动](#前端启动)
- [📋 主要API接口](#-主要api接口)
- [✅ 已完成功能](#-已完成功能)
- [🔄 可扩展功能建议](#-可扩展功能建议)
- [🐳 部署指南](#-部署指南)
- [❓ 常见问题FAQ](#-常见问题faq)
- [🤝 贡献指南](#-贡献指南)
- [📧 联系方式](#-联系方式)

---

## 📖 项目简介

**极果（Jiguo）** 是一个面向年轻人的 **电商导购 + 产品试用平台**，采用 **前后端分离** 架构，由 **uni-app 移动端** 和 **Spring Boot 后端** 组成。用户可以发现新奇的数码产品（酷玩）、阅读选购指南（导购）、申请免费试用，并进行点赞、评论等互动。项目还包括一个基础的管理后台，供运营人员管理内容。

- **项目类型**：前后端分离 Web 应用 + 移动端 App（B/S + C/S 混合）
- **适用场景**：全栈开发学习

---

## 🏗️ 系统架构图

graph LR
    subgraph 用户端
        A[uni-app 移动端/H5/小程序] 
    end
    subgraph 管理层
        B[后台管理界面<br/>Thymeleaf + HTML]
    end
    subgraph 服务层
        C[Spring Boot 3.3.5<br/>RESTful API]
        D[MySQL 8.0 数据库]
        E[文件存储<br/>uploads/images/]
    end
    
    A <-->|HTTP/REST| C
    B <-->|HTTP/REST| C
    C <-->|JDBC/MyBatis-Plus| D
    C -->|文件读写| E
    A -->|Vite Proxy /api → 8088| C

    
##架构说明：
前端通过 Vite 代理将 /api 请求转发到后端 8088 端口，解决跨域问题。
后端统一返回 JSON 格式数据，文件上传保存到外部目录。
后台管理基于 Thymeleaf 模板引擎，直接渲染 HTML 页面。

🛠️ 技术栈对比
层次	技术	选型理由
后端框架	Spring Boot 3.3.5	生态成熟、自动配置、适合快速开发
后端语言	Java 17	Spring Boot 3.x 最低要求，长期支持版本
ORM	MyBatis-Plus 3.5.14 + MyBatis	简化CRUD，分页支持，灵活写SQL
数据库	MySQL 8.0	开源免费，社区活跃，支持 utf8mb4
分页插件	PageHelper	与 MyBatis 无缝集成
工具类	Hutool 5.8.37	一站式Java工具集
前端框架	uni-app (Vue 3)	一套代码打包多端（H5/小程序/App）
UI库	uni-ui + 自定义组件	轻量，适合移动端
构建工具（后端）	Maven Wrapper	无需全局安装Maven
构建工具（前端）	Vite 4.x	快速开发启动，HMR
反向代理	Vite Proxy	解决开发环境跨域
后台管理	Thymeleaf + HTML/CSS	简单直接，后端渲染

✨ 项目亮点与创新点
📱 多端统一：基于 uni-app 开发，一套代码可编译为 H5、微信小程序、Android App 等。

🔄 前后端分离：清晰的 RESTful API，前端只关注 UI，后端负责业务与数据。

💾 智能数据初始化：应用首次启动时自动创建管理员账号和测试数据，开箱即用。

🎯 试用状态计算：动态判断试用活动的“进行中 / 已结束 / 未开始”状态，业务准确。

🔒 外键级联：所有关联表使用外键约束与级联删除，保证数据一致性。

🖼️ 外部图片存储：上传图片不打包进 jar，便于后期迁移和备份。

📊 分层架构：Controller → Service → Mapper 经典三层，结构清晰易于维护。

🚀 可扩展性强：预留了大量扩展点（JWT、Redis、支付等），适合二次开发。


🚀 快速开始
环境准备
JDK 17+（下载）
MySQL 8.0（或 5.7+）
Maven 3.6+（或使用项目自带 Maven Wrapper）
HBuilderX 3.0+（下载）

后端启动
克隆仓库
git clone https://github.com/你的用户名/jiguo.git
cd jiguo/server/jiguo

初始化数据库
mysql -u root -p < database_setup.sql

修改配置（如需要）
编辑 src/main/resources/application.yml，修改数据库密码：
spring:
  datasource:
    password: 你的密码

启动应用
# Windows (Maven Wrapper)
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run

验证
打开浏览器访问 http://127.0.0.1:8088，若显示空白页或后端日志无报错即为成功。

前端启动
打开前端项目
使用 HBuilderX 打开 app/jiguo 文件夹。

配置代理（已内置）
检查 vite.config.js 中代理目标为 http://127.0.0.1:8088。

运行
点击 HBuilderX 工具栏 “运行” → “运行到浏览器” → “Chrome”。
默认地址：http://localhost:5173

测试账号

用户端：手机号 13800138000，密码 123456

管理端：用户名 root，密码 123456（访问 http://127.0.0.1:8088/admin/login）

📋 主要API接口
统一返回格式：{ "code": 200, "message": "success", "data": ... }
分页格式：data 中包含 total, pages, list 等字段

✅已完成功能
用户注册/登录、个人中心
酷玩商品列表、详情、点赞、评论
导购文章列表、详情
试用商品列表、状态筛选、申请、报告提交
图片上传、外部存储
全局异常处理、跨域配置
数据库自动初始化
后台管理（商品CRUD、用户管理）
分页查询、多端 UI 适配

🔄 可扩展功能建议
类别	建议
安全	JWT 认证、BCrypt 密码加密、图形验证码
性能	Redis 缓存、Elasticsearch 搜索、CDN
业务	购物车、订单、支付、优惠券、物流
互动	星级评价、分享、消息推送
管理	Vue3 管理后台、数据统计大屏、操作日志
运维	Docker 部署、CI/CD、域名+HTTPS

🐳 部署指南
开发环境
后端直接运行 mvnw spring-boot:run
前端运行于 HBuilderX / Vite 开发服务器

❓ 常见问题FAQ
<details> <summary>Q1: 后端启动失败，提示数据库连接错误？</summary>
检查 MySQL 服务是否已启动。
确认 application.yml 中数据库密码正确。
如果密码不是 123456，可使用环境变量覆盖：$env:JIGUO_DB_PASSWORD="你的密码"（PowerShell）。
确保 MySQL 版本为 5.7+ 或 8.0。
</details><details> <summary>Q2: 上传的图片无法访问（404）？</summary>
检查项目根目录下是否自动创建了 uploads/images/ 文件夹。
确保 WebMvcConfig 中静态资源映射 /image/** → uploads/images/ 正确。
图片大小不能超过 4MB。
</details><details> <summary>Q3: 前端报跨域错误？</summary>
确保后端 CorsConfig 配置存在且已启动。
开发环境下，前端 vite.config.js 中 proxy 必须指向 http://127.0.0.1:8088。
清除浏览器缓存后重试。
</details><details> <summary>Q4: 点赞/评论不生效？</summary>
需要先登录才能点赞或评论，未登录时会提示。
检查 jg_cool_item_thumb 和 jg_cool_item_comment 表是否存在。
查看后端日志是否有 SQL 错误。
</details><details> <summary>Q5: 试用活动状态显示不准？</summary>
检查数据库 begin_date 和 end_date 字段值是否正确。
确认服务器系统时间和时区设置（Asia/Shanghai）。
状态由后端动态计算，重启应用后刷新。
</details><details> <summary>Q6: HBuilderX 编译报错？</summary>
确保 HBuilderX 为 3.0+ 版本，并安装了 Vue 3 插件。
尝试“运行 → 清理缓存”后重新运行。
检查 vite.config.js 语法是否正确。

🤝 贡献指南
欢迎提交 Issue 和 Pull Request 来完善本项目！

Fork 本仓库
创建特性分支 (git checkout -b feature/amazing-feature)
提交更改 (git commit -m 'Add some amazing feature')
推送分支 (git push origin feature/amazing-feature)
创建 Pull Request
