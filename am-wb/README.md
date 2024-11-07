This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify

```
Eam_management
├─ .gitignore
├─ config
│  ├─ env.js
│  ├─ jest
│  │  ├─ cssTransform.js
│  │  └─ fileTransform.js
│  ├─ modules.js
│  ├─ paths.js
│  ├─ pnpTs.js
│  ├─ webpack.config.js
│  └─ webpackDevServer.config.js
├─ jsconfig.json
├─ package-lock.json
├─ package.json
├─ public
│  ├─ index.html
│  ├─ manifest.json
│  └─ robots.txt
├─ README.md
├─ scripts
│  ├─ start.js
│  └─ test.js
└─ src
   ├─ App.css
   ├─ App.js
   ├─ App.test.js
   ├─ common
   │  ├─ axios.js   //Axios.defaults.baseURL="http://192.168.1.30:8097"; 配置接口地址,也可以使用以下方法axios.create({
                                                                                       baseURL: 'https://some-domain.com/api/',
                                                                                       timeout: 1000,
                                                                                       headers: {'X-Custom-Header': 'foobar'}
                                                                                       });
   │  ├─ common.js      //公共方法
   │  ├─ common.less    //公共样式
   │  ├─ list.less      //管理系统列表通用样式
   │  └─ upload.less    //管理系统列表上传表单通用样式
   ├─ components
   │  ├─ Breadcrumb
   │  │  ├─ Breadcrumb.jsx     //封装的面包屑
   │  │  └─ Breadcrumb.less
   │  ├─ Footer
   │  │  ├─ Footer.jsx  //页脚组件
   │  │  └─ Style.less
   │  ├─ Header
   │  │  ├─ Header.js  //头部组件
   │  │  └─ Style.less
   │  ├─ Icon
   │  │  └─ Icon.jsx  //Icon组件-未使用
   │  └─ Menu
   │     ├─ Menu.jsx   //菜单组件-从后台获取数据,具体获取结构,在menuAction.js中可查看
   │     └─ Menu.less
   ├─ index.js   //入口文件
   ├─ index.less
   ├─ logo.svg
   ├─ mock
   │  └─ login.js  //模拟,未使用
   ├─ router.js   // 路由
   ├─ serviceWorker.js
   ├─ static
   │  └─ images   //静态图片
   │     ├─ 1.png
   │     ├─ 2.jpg
   │     ├─ 2.png
   │     ├─ 3.png
   │     ├─ 4.png
   │     ├─ 5.png
   │     ├─ account.png
   │     ├─ Avatar.png
   │     ├─ bgc.jpg
   │     ├─ BgcWhite.png
   │     ├─ erweima.png
   │     ├─ logo.png
   │     ├─ logoimg.png
   │     ├─ menuImg1.png
   │     ├─ menuImg2.png
   │     ├─ msg.png
   │     ├─ national.png
   │     ├─ psd.png
   │     ├─ setting.png
   │     └─ success.png
   ├─ store          //redux的使用
   │  ├─ actions     //访问接口文件夹-具体使用位置可搜索全局
   │  │  ├─ assets
   │  │  │  ├─ attributesAtcion.js
   │  │  │  └─ organizationAction.js
   │  │  ├─ loginAction.js
   │  │  ├─ menuAction.js
   │  │  └─ routerActions.js
   │  ├─ actionTypes.js     //定义的参数
   │  ├─ history.js         //在actions,或者纯js中用于路由跳转
   │  ├─ reducers           //定义的默认函数(有返回值),后期需挂载在reducers
   │  │  ├─ assets
   │  │  │  ├─ attributesReducer.js
   │  │  │  └─ organizationReducer.js
   │  │  ├─ loginReducer.js
   │  │  ├─ menuReducer.js
   │  │  └─ routerReducer.js
   │  ├─ reducers.js         //仓库
   │  └─ store.js
   └─ subpage       //主页面,包含列表及表单
      ├─ asset-list
      │  ├─ add-asset-attributes.jsx
      │  ├─ add-assets-buy.jsx
      │  ├─ add_assets_collection.jsx
      │  ├─ add_assets_purchase.jsx
      │  ├─ add_assets_return.jsx
      │  ├─ add_assets_warehousing.jsx
      │  ├─ add_assets_write.jsx
      │  ├─ asset-attributes.jsx
      │  ├─ assets-buy.jsx
      │  ├─ assets-collection.jsx
      │  ├─ assets-purchase.jsx
      │  ├─ assets-return.jsx
      │  ├─ assets-warehousing.jsx
      │  └─ assets-write.jsx
      ├─ changepassword    //修改密码
      │  ├─ changepassword.js
      │  └─ style.less
      ├─ forgetPassword   //忘记密码
      │  ├─ forgetPassword.jsx
      │  └─ forgetPassword.less
      ├─ Index     //主页,可查看主页菜单切换的路由,页面已详细标注,组件查看import路径
      │  ├─ Index.js
      │  └─ style.less
      ├─ Login
      │  ├─ Login.js  //登录
      │  └─ Login.less
      ├─ noMatch.js
      ├─ register
      │  ├─ register.jsx //注册
      │  └─ register.less
      ├─ statistic-analysis  //数据分析
      │  ├─ statistic-analysis.jsx
      │  └─ statistic-analysis.less
      ├─ system-menu  
      │  ├─ device-types
      │  │  ├─ add-device-types.jsx
      │  │  └─ device-types.jsx
      │  ├─ menuItem
      │  │  ├─ add_menu.jsx
      │  │  └─ menu_list.jsx
      │  ├─ organization
      │  │  ├─ add_organization.jsx
      │  │  └─ organization_list.jsx
      │  ├─ rule
      │  │  ├─ add_rule.jsx
      │  │  ├─ rule_list.jsx
      │  │  └─ setting_rule.jsx
      │  └─ user
      │     ├─ add_user_list.jsx
      │     └─ user_list.jsx
      └─ todo
         └─ to-do.jsx

```