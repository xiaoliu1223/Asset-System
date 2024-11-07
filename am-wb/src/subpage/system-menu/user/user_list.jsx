/**
 * 角色列表展示
 */
import React, { Component } from "react";
import Style from "../../../common/list.less";
import BreadcrumbComponent from "../../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Icon,
  Divider,
  Popconfirm,
  message,
  Button,
  Input,
  TreeSelect,
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getuserlistMethod,
  userdeleMethod,
} from "../../../store/actions/assets/userAction";
import { getorganizationListMethod } from "../../../store/actions/assets/organizationAction";
const { Search } = Input;
const { Option } = Select;
const { TreeNode } = TreeSelect;
@connect(
  (state) => ({
    userlist: state.userlist,
    organizationlist: state.organizationlist,
  }),
  (dispatch) => ({
    getorganizationListMethod: (data) =>
      dispatch(getorganizationListMethod(data)),
    getuserlistMethod: (data) => dispatch(getuserlistMethod(data)),
    userdeleMethod: (data) => dispatch(userdeleMethod(data)),
  })
)
class UserList extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    value: "1",
    username: "",
    params: {
      current: 1,
      limit: 10,
    },
  };
  componentWillMount() {
    this.props.getorganizationListMethod();
  }
  componentDidMount() {
    this.props.getuserlistMethod(this.state.params);
  }
  //点击搜索
  search_action(value) {
    this.setState(
      {
        params: {
          current: 1,
          limit: 10,
          username: value.replace(/\s+/g, ""),
        },
      },
      () => {
        this.props.getuserlistMethod(this.state.params);
      }
    );
  }
  onChangeTreeNodes = (value) => {
    this.setState(
      {
        value,
        params: {
          current: 1,
          limit: 10,
          departmentId: value, //接口没加
        },
      },
      () => {
        this.props.getuserlistMethod(this.state.params);
      }
    );
  };
  renderTreeNodes = (data) =>
    data.map((item, index) => {
      if (item.list.length !== 0) {
        return (
          <TreeNode title={item.name} key={item.id} value={item.id}>
            {this.renderTreeNodes(item.list)}
          </TreeNode>
        );
      }
      return <TreeNode title={item.name} key={item.id} value={item.id} />;
    });
  render() {
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index",
      },
      {
        title: "登录账号",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "所属部门",
        dataIndex: "departName",
        key: "departName",
      },
      {
        title: "创建时间",
        dataIndex: "createTime",
        key: "createTime",
        render: (record) => {
          return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
        },
      },
      {
        title: "操作",
        render: (record) => (
          <span>
            <Link
              to={{
                pathname: `/index/authority/add_user`,
                state: {
                  item: record,
                },
              }}
            >
              {" "}
              <Icon type="form" />
            </Link>
            {/* 跳转到编辑页面 */}
            <Divider type="vertical" />
            <Popconfirm
              title="是否确定删除?"
              onConfirm={this.confirm.bind(this, record)}
              onCancel={this.cancel}
              okText="是"
              cancelText="否"
            >
              <Icon type="delete" />
            </Popconfirm>
            {/* 设置权限 */}
          </span>
        ),
      },
    ];
    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="用户管理" />
              <div className={Style.formname}>
                <Button type="primary" onClick={this.go_add_rule.bind(this)}>
                  添加
                </Button>
                <div style={{ height: "30px", marginLeft: "30px" }}>
                  <Search
                    placeholder="请输入账号名称"
                    onSearch={this.search_action.bind(this)}
                    style={{ width: 200, height: "100%" }}
                  />
                </div>
                <TreeSelect
                  style={{ width: "350px", marginLeft: "30px" }}
                  value={this.state.value}
                  dropdownStyle={{ maxHeight: 400, overflow: "auto" }}
                  placeholder="请选择"
                  treeDefaultExpandAll
                  onChange={this.onChangeTreeNodes}
                >
                  {this.renderTreeNodes(this.props.organizationlist)}
                </TreeSelect>
              </div>
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.userlist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.userlist.total_record,
                }}
              />
            </div>
          </div>
        }
      </>
    );
  }
  //切换分页
  handleTableChange(current) {
    const { params } = this.state;
    this.setState(
      {
        params: {
          ...params,
          current: current,
        },
      },
      () => {
        this.props.getuserlistMethod(this.state.params);
      }
    );
  }
  confirm = (record, e) => {
    this.props.userdeleMethod(record.id);
    setTimeout(() => {
      this.props.getuserlistMethod(this.state.params);
    }, 1000);
  };

  cancel = (e) => {
    console.log(e);
  };
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/authority/add_user",
    });
  }
}

export default withRouter(UserList);
