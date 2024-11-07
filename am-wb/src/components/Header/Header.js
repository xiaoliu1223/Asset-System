import React from "react";
import { Link, withRouter } from "react-router-dom";
import Style from "./Style.less";
import { Avatar, Menu, Dropdown, Badge, Icon } from "antd";
import demohead from "../../static/images/menuImg2.png";
import { connect } from "react-redux";
@connect(
  state => ({
    getUserInfo: state.getUserInfo
  }),
  dispatch => ({
    // rolelistMethod: (data) => dispatch(rolelistMethod(data)),
  })
)
class HeaderCom extends React.Component {
  render() {
    const menu = (
      <Menu>
        <Menu.Item>
          <span onClick={this.login_out.bind(this)}>退出登录</span>
        </Menu.Item>
        <Menu.Item>
          <Link to="/index/change-password">修改密码</Link>
        </Menu.Item>
      </Menu>
    );
    const userinfo = JSON.parse(localStorage.getItem("userinfotoken"));

    return (
      <div className={Style.head_content}>
        <Avatar
          size="large"
          icon={
            <img
              alt=""
              src={
                localStorage.getItem("avatar") !== "null" &&
                localStorage.getItem("avatar")
                  ? localStorage.getItem("avatar")
                  : demohead
              }
            />
          }
        />
        <Dropdown overlay={menu} className={Style.username}>
          <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
            <span className={Style.username_span}>
              {userinfo == undefined ? "暂无昵称" : userinfo.username}
            </span>
          </a>
        </Dropdown>
      </div>
    );
  }
  login_out() {
    this.props.history.replace("/asset");
    localStorage.clear();
  }
}
export default withRouter(HeaderCom);
