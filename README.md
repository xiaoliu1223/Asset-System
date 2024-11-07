# Asset-System
Asset Management System Based on Spring Boot

### am    --后端源码

### am-wb --前端源码

### 项目部署

#### 后端配置
1. 在mysql中，创建数据库am,并加载数据文件am.sql（数据文件在后端源码中 ）和verification_code.sql，得到数据库中相关数据表
2. 在idea中打开项目am,修改配置文件application.yml中连接数据库的你的用户名和密码
3. 运行主启动类AssetsManagerApplication.java，启动服务即可

#### 前端配置

1. 官网下载node环境：https://nodejs.org/en/
2. vscode打开项目am-wb
3. 打开vscode自带终端，快捷键ctrl + `
4. 安装项目运行所需依赖：npm install
5. 启动项目：npm run start
6. 本地启动后(服务端要提前启动好),浏览器访问地址：http://localhost:3000/asset
7. 输入用户名：admin  密码：8888888，以及生成的随机干扰线条验证码，则是以管理员身份登录
8. 输入用户名：test08 密码：z123456，以及生成的随机干扰线条验证码，则是以某一普通角色登录，具体可以查看代码和数据表中角色。

#### 文档查看

文档查看路径：http://localhost:8097/doc.html

从配置文件中找到用户名和密码进行登录，可结合文档了解后面源码。
