import React from "react";
import Style from "./style.less";
// import {
//     MenuUnfoldOutlined,
//     MenuFoldOutlined,
// } from '@ant-design/icons';
import { Route, Switch, Redirect } from "react-router-dom";
import { Layout, Menu, Icon } from "antd";
import { connect } from "react-redux";
import moment from "moment";
//引入组件
import HeaderCom from "../../components/Header/Header";
//页脚
import Footer from "../../components/Footer/Footer.jsx";
//引入菜单
import MenuListItem from "../../components/Menu/Menu";
//添加角色
import AddRule from "../system-menu/rule/add_rule";
//角色菜单设置
import SettingRule from "../system-menu/rule/setting_rule";
//添加菜单
import AddMenu from "../system-menu/menuItem/add_menu";

import MenuList from "../system-menu/menuItem/menu_list";

import RuleList from "../system-menu/rule/rule_list";
import DeviceTypesList from "../system-menu/device-types/device-types";
import DddDeviceTypes from "../system-menu/device-types/add-device-types";
import OrganizationList from "../system-menu/organization/organization_list";
import AddOrganization from "../system-menu/organization/add_organization";
import NoMatch from "../noMatch";

//资产管理部分
import StatisticAnalysis from "../statistic-analysis/statistic-analysis.jsx";
//资产归还
import AssetsReturn from "../asset-list/assets-return.jsx";
import AddAssetsReturn from "../asset-list/add_assets_return.jsx";
//资产申购
import AssetPurchase from "../asset-list/assets-purchase.jsx";
import AddAssetsPurchase from "../asset-list/add_assets_purchase.jsx";
// 资产领用
import AssetsCollection from "../asset-list/assets-collection.jsx";
import AddAssetsCollection from "../asset-list/add_assets_collection.jsx";
// 资产入库
import AssetsWarehousing from "../asset-list/assets-warehousing.jsx";
import AddAssetsWarehousing from "../asset-list/add_assets_warehousing.jsx";
//资产核销
import AssetsWrite from "../asset-list/assets-write.jsx";
import AddAssetsWrite from "../asset-list/add_assets_write.jsx";
//资产采购
import AssetsBuy from "../asset-list/assets-buy.jsx";
import AddAssetsBuy from "../asset-list/add-assets-buy.jsx";
//人员管理
import UserList from "../system-menu/user/user_list.jsx";
import AddUserList from "../system-menu/user/add_user_list.jsx";
//资产属性
import AssetAttributes from "../asset-list/asset-attributes.jsx";
import AddAssetAttributes from "../asset-list/add-asset-attributes.jsx";
//待办事项审批管理
import ToDoApply from "../todo/to-do-apply.jsx";
import ToDoGet from "../todo/to-do-get.jsx";
import ToDoBack from "../todo/to-do-back.jsx";
import ToDoDelete from "../todo/to-do-delete.jsx";
import ToDoComplete from "../todo/to-do-complete.jsx";
//修改密码
import ChangePassword from "../changepassword/changepassword";
//详情
import PurchaseDetail from "../detail/purchasedetail.jsx";
//引入图片
import Logo from "../../static/images/zhongda.png";
import { AssetLock } from "../system-menu/asset-lock";

import { AssetUnit } from "../system-menu/asset-unit";
import { AssetName } from "../system-menu/asset-name";

const { Header, Sider, Content } = Layout;
const { SubMenu } = Menu;
const ComEle = () => {
  return <div>1223</div>;
};
@connect((state) => ({
  initRouterReducer: state.initRouterReducer,
  nemuReducer: state.nemuReducer,
}))
class Index extends React.Component {
  constructor(props) {
    super(props);
  }
  state = {
    collapsed: false,
    //管理员只是新增用户新增账号,还差
  };
  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  };
  // initRouter(){
  //     if(this.props.nemuReducer !== null){
  //         if(this.props.nemuReducer[0].children == null){
  //             return  <Route path='/index' render={() => (<Redirect to={this.props.nemuReducer[0].path} />)} exact />
  //         }else{
  //             return <Route path='/index' render={() => (<Redirect to={this.props.nemuReducer[0].children[0].path} />)} exact />
  //         }
  //     }

  // }
  render() {
    return (
      <Layout style={{ minHeight: "100vh" }}>
        <Sider
          collapsible
          collapsed={this.state.collapsed}
          trigger={null}
          width="256px"
        >
          <div className={Style.logo}>
            <img alt="" src={Logo} />
          </div>
          <MenuListItem />
        </Sider>
        <Layout className={Style.site_layout}>
          <Header className={Style.site_layout_background}>
            {/* {this.state.collapsed ? <Icon type="menu-fold" className={Style.trigger} onClick={this.toggle} /> : <Icon type="menu-unfold" className={Style.trigger} onClick={this.toggle} />} */}
            <HeaderCom />
            {/* <Search/> */}
          </Header>
          <Content
            style={{
              margin: "24px 16px",
              padding: 24,
              minHeight: 280,
              backgroundColor: "#fff",
            }}
          >
            <Switch>
              {/* 统计分析 */}
              {JSON.parse(localStorage.getItem("userinfotoken")).permission[0]
                .path == null ? (
                <Redirect
                  exact
                  from="/index"
                  to={
                    JSON.parse(localStorage.getItem("userinfotoken"))
                      .permission[0].children[0].path
                  }
                />
              ) : (
                <Redirect
                  exact
                  from="/index"
                  to={
                    JSON.parse(localStorage.getItem("userinfotoken"))
                      .permission[0].path
                  }
                />
              )}
              <Route
                path="/index/statistic-analysis"
                exact
                component={StatisticAnalysis}
              ></Route>
              {/* 资产列表 */}
              {/* 资产归还 */}
              <Route
                path="/index/assets-return"
                exact
                component={AssetsReturn}
              ></Route>
              <Route
                path="/index/add-assets-return"
                exact
                component={AddAssetsReturn}
              ></Route>
              {/* 资产领用 */}
              <Route
                path="/index/assets-collection"
                exact
                component={AssetsCollection}
              ></Route>
              <Route
                path="/index/add_assets_collection"
                exact
                component={AddAssetsCollection}
              ></Route>
              {/* 资产入库 */}
              <Route
                path="/index/assets-warehousing"
                exact
                component={AssetsWarehousing}
              ></Route>
              <Route
                path="/index/add-assets-warehousing"
                exact
                component={AddAssetsWarehousing}
              ></Route>
              {/* 资产核销 */}
              <Route
                path="/index/assets-write"
                exact
                component={AssetsWrite}
              ></Route>
              <Route
                path="/index/add-assets-write"
                exact
                component={AddAssetsWrite}
              ></Route>
              {/* 资产采购 */}
              <Route
                path="/index/assets-buy"
                exact
                component={AssetsBuy}
              ></Route>
              <Route
                path="/index/add-assets-buy"
                exact
                component={AddAssetsBuy}
              ></Route>
              {/* 资产申购 */}
              <Route
                path="/index/assets-purchase"
                exact
                component={AssetPurchase}
              ></Route>
              <Route
                path="/index/add-assets-purchase"
                exact
                component={AddAssetsPurchase}
              ></Route>
              {/* {
                                this.initRouter()
                            } */}
              {/* 暂定 */}
              <Route
                path="/index/authority/menu_list"
                exact
                component={MenuList}
              ></Route>
              <Route
                path="/index/authority/organization"
                exact
                component={OrganizationList}
              ></Route>
              <Route
                path="/index/authority/rule"
                exact
                component={RuleList}
              ></Route>
              {/* 设备 */}
              <Route
                path="/index/authority/device_types_list"
                exact
                component={DeviceTypesList}
              ></Route>
              <Route
                path="/index/authority/add_device_types"
                exact
                component={DddDeviceTypes}
              ></Route>
              <Route path="/index/add_menu" exact component={AddMenu}></Route>
              <Route
                path="/index/add_organization"
                exact
                component={AddOrganization}
              ></Route>
              {/* 待办事项审批管理 */}
              <Route
                path="/index/to-do-apply"
                exact
                component={ToDoApply}
              ></Route>
              <Route path="/index/to-do-complete" exact component={ToDoComplete}></Route>
              <Route path="/index/to-do-get" exact component={ToDoGet}></Route>
              <Route
                path="/index/to-do-back"
                exact
                component={ToDoBack}
              ></Route>
              <Route
                path="/index/to-do-delete"
                exact
                component={ToDoDelete}
              ></Route>
              {/* 资产属性 */}
              <Route
                path="/index/authority/asset-attributes"
                component={AssetAttributes}
              />
              <Route
                path="/index/authority/add-asset-attribute"
                component={AddAssetAttributes}
              />

              {/* 添加角色   */}
              <Route path="/index/add_rule" exact component={AddRule}></Route>
              <Route
                path="/index/setting_rule"
                exact
                component={SettingRule}
              ></Route>
              <Route
                path="/index/authority/user"
                exact
                component={UserList}
              ></Route>
              <Route
                path="/index/authority/add_user"
                exact
                component={AddUserList}
              ></Route>
              {/* 修改账户密码 */}
              <Route
                path="/index/change-password"
                exact
                component={ChangePassword}
              ></Route>
              {/* 详情 */}
              <Route
                path="/index/purchase-detail"
                exact
                component={PurchaseDetail}
              ></Route>
              {/**
               * 盘点开关
               */}
              <Route
                path="/index/asset-lock"
                exact
                component={AssetLock}
              ></Route>
              {/**单位管理 */}
              <Route
                path="/index/asset-unit"
                exact
                component={AssetUnit}
              ></Route>
              {/**资产名称管理 */}
              <Route
                path="/index/asset-name"
                exact
                component={AssetName}
              ></Route>
              <Route component={NoMatch} />
            </Switch>
          </Content>
          <Footer />
        </Layout>
      </Layout>
    );
  }
}
export default Index;
