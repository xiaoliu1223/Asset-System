/**
 * 资产归还列表展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Icon,
  Divider,
  Popconfirm,
  Form,
  Button,
  Input,
  Modal,
  Timeline
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getreturnlistMethod,
  getreturnbackMethod,
  getreturnconfirmMethod,
  getreturndetailMethod
} from "../../store/actions/assets/returnAction";
import { AuthWrapper } from "../../components/Auth.jsx";
import TimeLineContent from "../../components/TimeLine";

const { Search } = Input;
const { Option } = Select;
const SelectData = [
  {
    value: "",
    name: "全部"
  },
  {
    value: "0",
    name: "待审批"
  },
  {
    value: "1",
    name: "审批通过"
  },
  {
    value: "-1",
    name: "驳回"
  },
  {
    value: "2",
    name: "主动撤销"
  },
  {
    value: "3",
    name: "已归还"
  }
];
@connect(
  state => ({
    returnlist: state.returnlist,
    returndetail: state.returndetail,
    auth: state.auth
  }),
  dispatch => ({
    getreturnlistMethod: data => dispatch(getreturnlistMethod(data)),
    getreturnbackMethod: data => dispatch(getreturnbackMethod(data)),
    getreturnconfirmMethod: data => dispatch(getreturnconfirmMethod(data)),
    getreturndetailMethod: id => dispatch(getreturndetailMethod(id))
  })
)
class AssetsReturn extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    modalVisible: false,
    params: {
      current: 1
    }
  };
  componentDidMount() {
    this.props.getreturnlistMethod(this.props.params);
  }
  handleSubmitSearch(e) {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      this.setState(
        {
          params: {
            current: 1,
            status: values.state,
            assetName: values.assetName,
            assetCode: values.assetCode
          }
        },
        () => {
          this.props.getreturnlistMethod(this.state.params);
        }
      );
    });
  }
  initoperate(record) {
    if (record.status == 0) {
      return (
        <AuthWrapper id="56" pid="52">
          <Popconfirm
            title="是否确定撤回申请?"
            onConfirm={() => {
              this.props.getreturnbackMethod(record.id);
              setTimeout(() => {
                this.props.getreturnlistMethod(this.state.params);
              }, 1000);
            }}
            onCancel={this.cancel}
            okText="是"
            cancelText="否"
          >
            <span style={{ cursor: "pointer" }}>撤回</span>
          </Popconfirm>
          <Divider type="vertical" />
        </AuthWrapper>
      );
    }

    if (record.status == 1 && record.flowPath == -1) {
      return (
        <AuthWrapper id="54" pid="52">
          <Popconfirm
            title="是否确定归还?"
            onConfirm={() => {
              this.setState(
                {
                  Visible: true,
                  id: record.id
                },
                () => {
                  this.props.getreturnconfirmMethod(record.id);
                  setTimeout(() => {
                    this.props.getreturnlistMethod(this.state.params);
                  }, 1000);
                }
              );
            }}
            onCancel={this.cancel}
            okText="是"
            cancelText="否"
          >
            <span style={{ cursor: "pointer" }}>确认归还</span>
          </Popconfirm>
          <Divider type="vertical" />
        </AuthWrapper>
      );
    }
  }
  render() {
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index"
      },
      {
        title: "归还人",
        dataIndex: "username",
        key: "username"
      },
      {
        title: "归还部门",
        dataIndex: "departmentName",
        key: "departmentName"
      },
      {
        title: "资产编号",
        dataIndex: "assetCode",
        key: "assetCode"
      },
      {
        title: "资产名称",
        dataIndex: "assetName",
        key: "assetName"
      },
      {
        title: "资产类别",
        dataIndex: "assetTypeName",
        key: "assetTypeName"
      },
      {
        title: "规格型号",
        dataIndex: "specification",
        key: "specification"
      },
      {
        title: "归还数量",
        dataIndex: "num",
        key: "num"
      },
      {
        title: "归还申请时间",
        render: record => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        }
      },
      {
        title: "归还理由",
        dataIndex: "description",
        key: "description"
      },
      {
        title: "审批状态",
        dataIndex: "statusText",
        key: "statusText"
      },
      {
        title: "操作",
        render: record => (
          <span>
            {console.log(this.props.auth)
            // 0: {id: 53, pid: 52, name: "查询", path: null, permission: null, …}
            // 1: {id: 54, pid: 52, name: "确认归还", path: null, permission: null, …}
            // 2: {id: 56, pid: 52, name: "撤销", path: null, permission: null, …}
            }
            {this.initoperate(record)}
            <AuthWrapper id="53" pid="52">
              <span
                style={{ cursor: "pointer" }}
                onClick={() => {
                  this.props.getreturndetailMethod(record.id);
                  this.setState({
                    modalVisible: true
                  });
                }}
              >
                查看
              </span>
            </AuthWrapper>
          </span>
        )
      }
    ];
    const FormItem = Form.Item;
    const { getFieldDecorator } = this.props.form;
    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="资产归还" />
              <Form
                onSubmit={this.handleSubmitSearch.bind(this)}
                className={Style.formname}
              >
                <div className={`${Style.search_items} ${Style.small}`}>
                  <div style={{fontSize: '14px', margin: '9px 10px 0 0'}}>审批状态 :</div>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("state", {
                      initialValue: ""
                    })(
                      <Select style={{ width: 180 }} placeholder="审批状态">
                        {SelectData.map((ele, index) => {
                          return (
                            <Option value={ele.value} key={index}>
                              {ele.name}
                            </Option>
                          );
                        })}
                      </Select>
                    )}
                  </FormItem>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("assetName", {
                      getValueFromEvent: event =>
                        event.target.value.replace(/\s+/g, "")
                    })(<Input placeholder="资产名称" maxLength={20} />)}
                  </FormItem>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("assetCode", {
                      getValueFromEvent: event =>
                        event.target.value.replace(/\s+/g, "")
                    })(<Input placeholder="资产编号" />)}
                  </FormItem>
                  <FormItem className={Style.li}>
                    <Button type="primary" htmlType="submit">
                      查询
                    </Button>
                  </FormItem>
                </div>
              </Form>
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.returnlist.data}
                rowKey={record => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.returnlist.total_record
                }}
              />
            </div>
          </div>
        }
        <Modal
          title="审批详情"
          // centered
          visible={this.state.modalVisible}
          //onOk={this.handleOkBack.bind(this)}
          onCancel={() => {
            this.setState({
              modalVisible: false
            });
          }}
          footer={null}
        >
          {/* <Timeline>
            {this.props.returndetail.length == 0 ? (
              <p style={{ textAlign: "center" }}>暂无审核记录</p>
            ) : (
              this.props.returndetail.map((ele, index) => {
                return (
                  <Timeline.Item
                    key={index}
                    color={ele.auditStatus == 1 ? "green" : "red"}
                  >
                    <p>
                      <span style={{ marginRight: "30px" }}>审核人员</span>
                      <span>{ele.auditName}</span>
                    </p>
                    <p>
                      <span style={{ marginRight: "30px" }}>审核状态</span>
                      <span>{ele.auditStatus == 1 ? "通过" : "驳回"}</span>
                    </p>
                    <p>
                      <span style={{ marginRight: "30px" }}>审核时间</span>
                      <span>
                        {ele.auditTime == 0
                          ? 0
                          : moment(ele.auditTime).format("YYYY-MM-DD HH:mm:ss")}
                      </span>
                    </p>
                    {ele.reason == null ? null : (
                      <p>
                        <span style={{ marginRight: "30px" }}>原因描述</span>
                        <span>{ele.reason}</span>
                      </p>
                    )}
                  </Timeline.Item>
                );
              })
            )}
          </Timeline> */}
          <TimeLineContent data={this.props.returndetail} />
        </Modal>
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
          current: current
        }
      },
      () => {
        this.props.getreturnlistMethod(this.state.params);
      }
    );
  }
  confirm = (record, e) => {
    // this.props.delruleruleMethod(record.id);
    setTimeout(() => {
      // this.props.rolelistMethod(this.state.params);
    }, 1000);
  };

  cancel = e => {
    console.log(e);
  };
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/add-assets-return"
    });
  }
}

export default Form.create()(withRouter(AssetsReturn));
