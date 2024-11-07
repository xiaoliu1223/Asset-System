import React from "react";
import Style from "./Menu.less";
import { Menu, Icon } from "antd";
import { NavLink, Link, withRouter } from "react-router-dom";
import { initRouterMethod } from "../../store/actions/routerActions";
import { menulistMethod, authMethod } from "../../store/actions/menuAction";
import { connect } from "react-redux";
// import BannerList from '../../subpage/banner/banner_list.jsx'
// import PresentList from '../../subpage/present/present_list.jsx'
// import ActivityUser from '../../subpage/activity/activity_user.jsx'
// import ActivityNumber from '../../subpage/activity/activity_number'
// import ReceiveList from '../../subpage/receive_list/receive_list'
// // 角色列表
// import RuleList from '../../subpage/system-menu/rule/rule_list'
// //菜单
// import MenuList from '../../subpage/menuItem/menu_list'
// //待办
// import ToDoWork from '../../subpage/work/to_do_work'
// //已办
// import HaveWork from '../../subpage/work/have_work'
//员工
const { SubMenu } = Menu;
@connect(
  state => ({
    initRouterReducer: state.initRouterReducer,
    nemuReducer: state.nemuReducer
  }),
  dispatch => ({
    initRouterMethod: params => dispatch(initRouterMethod(params)),
    menulistMethod: params => dispatch(menulistMethod(params)),
    authMethod: params => dispatch(authMethod(params))
  })
)
class MenuListItem extends React.Component {
  state = {
    collapsed: false,
    defaultOpenKeys: "",
    openKeys: localStorage.getItem("openKeys")
      ? [localStorage.getItem("openKeys")]
      : ["sub0"],
    rootSubmenuKeys: [
      "sub0",
      "sub1",
      "sub2",
      "sub3",
      "sub4",
      "sub5",
      "sub6",
      "sub7",
      "sub8",
      "sub9",
      "sub10",
      "sub11",
      "sub12",
      "sub13",
      "sub14",
      "sub15",
      "sub16",
      "sub17",
      "sub18"
    ]
    //管理员只是新增用户新增账号,还差
  };
  componentDidMount() {
    this.props.menulistMethod();
    // this.aaa();
  }
  aaa() {
    if (this.props.nemuReducer !== null) {
      this.props.initRouterMethod(this.props.nemuReducer[0].children[0]);
      this.props.history.push(this.props.nemuReducer[0].children[0].path);
      if (this.props.nemuReducer.children === undefined) {
        // console.log(this.props.nemuReducer)
        this.props.initRouterMethod(this.props.nemuReducer[0]);
        this.props.history.push(this.props.nemuReducer[0].path);
        this.setState({
          defaultSelectedKeys: this.props.nemuReducer[0]
        });
      } else {
        this.props.initRouterMethod(this.props.nemuReducer[0].children[0]);
        this.props.history.push(this.props.nemuReducer[0].children[0].path);
      }
    }
  }
  getCurrentRouter(item) {
    if (item.path !== null) {
      this.props.history.push(item.path);
    }
    // this.props.initRouterMethod(item);
    // Promise.all([this.props.initRouterMethod(item), this.props.history.push(item.path)])
  }

  onOpenChange(openKeys) {
    const latestOpenKey = openKeys.find(
      key => this.state.openKeys.indexOf(key) === -1
    );

    if (latestOpenKey) {
      localStorage.setItem("openKeys", latestOpenKey);
    }

    if (this.state.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
      this.setState({ openKeys });
    } else {
      this.setState({
        openKeys: latestOpenKey ? [latestOpenKey] : []
      });
    }
  }
  createMenu = (menuData => {
    //创建菜单
    //let itemIndex = 0; //累计的每一项索引
    let submenuIndex = 0; //累计的每一项展开菜单索引
    let menu = [];
    const create = (menuData, el) => {
      for (let i = 0; i < menuData.length; i++) {
        if (menuData[i].children) {
          //如果有子级菜单
          let children = [];
          create(menuData[i].children, children);
          submenuIndex++;
          el.push(
            <SubMenu
              key={`sub${submenuIndex}`}
              title={
                <span style={{ height: "100%", display: "block" }}>
                  <Icon type={menuData[i].icon} />
                  {menuData[i].title}
                </span>
              }
            >
              {children}
            </SubMenu>
          );
        } else {
          //如果没有子级菜单
          //itemIndex++;
          el.push(
            <Menu.Item key={menuData[i].path} title={menuData[i].title}>
              <Link to={menuData[i].path}>
                {menuData[i].icon ? <Icon type={menuData[i].icon} /> : null}
                <span>{menuData[i].title}</span>
              </Link>
            </Menu.Item>
          );
        }
      }
    };
    create(menuData, menu);
    return menu;
  })(this.props.nemuReducer);
  render() {
    return (
      <>
        {
          <Menu
            defaultSelectedKeys={[localStorage.getItem("key") || "sub00"]}
            openKeys={this.state.openKeys}
            mode="inline"
            theme="dark"
            onOpenChange={this.onOpenChange.bind(this)}
            // inlineCollapsed={this.state.collapsed}
          >
            {this.props.nemuReducer.map((item, index) => {
              return (
                <SubMenu
                  key={`sub${index}`}
                  title={
                    <span>
                      {/* <Icon type={item.icon} /> */}
                      <span onClick={this.getCurrentRouter.bind(this, item)}>
                        {item.name}
                      </span>
                    </span>
                  }
                >
                  {item.children == undefined
                    ? null
                    : item.children.map((sonitem, sonindex) => {
                        return (
                          <Menu.Item
                            key={`sub${index}${sonindex}`}
                            onClick={() => {
                              // this.getCurrentRouter.bind(this, sonitem)

                              localStorage.setItem(
                                "key",
                                `sub${index}${sonindex}`
                              );

                              this.props.authMethod(sonitem.children);
                            }}
                          >
                            {/* <Icon type={sonitem.icon} /> */}
                            <span
                              onClick={this.getCurrentRouter.bind(
                                this,
                                sonitem
                              )}
                              className={Style.linkrole}
                            >
                              {sonitem.name}
                            </span>
                          </Menu.Item>
                        );
                      })}
                </SubMenu>
              );
            })}
          </Menu>
        }
      </>
    );
  }
}
export default withRouter(MenuListItem);
