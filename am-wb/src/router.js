import React from "react";
import { Route, Redirect, Switch } from "react-router-dom";
//登录
import Login from "./subpage/Login/Login";
//首页
import Index from "./subpage/Index/Index";
//忘记密码
import ForgetPassword from "./subpage/forgetPassword/forgetPassword";
//注册
import Register from "./subpage/register/register";
//修改密码
import ChangePassage from "./subpage/changepassword/changepassword";
//404
// import NoMatch from './subpage/noMatch'
//发布的所有宠物,商品
class RouterIndex extends React.Component {
  render() {
    return (
      <div>
        {/* <Route path="/" exact component={Login}></Route> */}
        <Route path="/login" exact component={Login}></Route>
        <Route path="/asset" exact component={Login}></Route>
        <Route path="/index" component={Index}></Route>
        <Route path="/forget_passage" component={ForgetPassword}></Route>
      </div>
    );
  }
}
export default RouterIndex;
