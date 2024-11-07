/**
 * 资产领用列表展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import Styleup from "../../common/upload.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Modal,
  Divider,
  Popconfirm,
  Form,
  Button,
  Input,
  Timeline
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import { AuthWrapper } from "../../components/Auth.jsx";
import {
  getcollectlistMethod,
  getcollectbackMethod,
  getcollectconfirmMethod,
  addreturnMethod,
  getcollectdetailMethod
} from "../../store/actions/assets/collectAction";
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
    name: "通过"
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
    name: "已领用"
  },
  {
    value: "4",
    name: "归还中"
  },
  {
    value: "5",
    name: "已归还"
  }
];
const statusData = [
  {
    value: 1,
    name: "可继续使用"
  },
  {
    value: -1,
    name: "报废"
  }
];
@connect(
  state => ({
    collectlist: state.collectlist,
    collectdetail: state.collectdetail,
    auth: state.auth
  }),
  dispatch => ({
    getcollectlistMethod: data => dispatch(getcollectlistMethod(data)),
    getcollectbackMethod: data => dispatch(getcollectbackMethod(data)),
    getcollectconfirmMethod: data => dispatch(getcollectconfirmMethod(data)),
    addreturnMethod: data => dispatch(addreturnMethod(data)),
    getcollectdetailMethod: data => dispatch(getcollectdetailMethod(data))
  })
)
class AssetsCollection extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    params: {
      current: 1,
      limit: 10
    },
    addId: "",
    modalVisible: false,
    pathVisible: false,
    description: "",
    statusSelect: statusData[0].value
  };
  componentDidMount() {
    this.props.getcollectlistMethod(this.state.params);
  }
  handleSubmitSearch(e) {
    e.preventDefault();
    const { params } = this.state;
    this.props.form.validateFields((err, values) => {
      this.setState(
        {
          params: {
            ...params,
            current: 1,
            status: values.state,
            assetName: values.assetName,
            assetCode: values.assetCode
          }
        },
        () => {
          this.props.getcollectlistMethod(this.state.params);
        }
      );
    });
  }
  //初始化操作
  initoperate(record) {
    if (record.status == 0) {
      return (
        <span>
          <AuthWrapper id="78" pid="46">
            <Popconfirm
              title="是否确定撤回申请?"
              onConfirm={() => {
                this.props.getcollectbackMethod(record.id);
                setTimeout(() => {
                  this.props.getcollectlistMethod(this.state.params);
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
        </span>
      );
    }

    if (record.status == 1 && record.flowPath == -1) {
      return (
        <AuthWrapper id="77" pid="46">
          <Popconfirm
            title="是否确定领用?"
            onConfirm={() => {
              this.setState(
                {
                  Visible: true,
                  id: record.id
                },
                () => {
                  this.props.getcollectconfirmMethod(record.id);
                  setTimeout(() => {
                    this.props.getcollectlistMethod(this.state.params);
                  }, 1000);
                }
              );
            }}
            okText="是"
            cancelText="否"
          >
            <span style={{ cursor: "pointer" }}>确认领用</span>
          </Popconfirm>
          <Divider type="vertical" />
        </AuthWrapper>
      );
    }
    if (record.status == 3) {
      return (
        <AuthWrapper id="48" pid="46">
          <span
            style={{ cursor: "pointer" }}
            onClick={() => {
              this.setState({
                modalVisible: true,
                addId: record.id
              });
            }}
          >
            新增归还
          </span>
          <Divider type="vertical" />
        </AuthWrapper>
      );
    }
  }
  render() {
    const Label = event => {
      return <span className={Styleup.sort_size}>{event.title}</span>;
    };
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index"
      },
      {
        title: "领用申请人",
        dataIndex: "username",
        key: "username"
      },
      {
        title: "领用部门",
        dataIndex: "departmentName",
        key: "departmentName"
      },
      {
        title: "实际使用人",
        dataIndex: "usedBy",
        key: "usedBy"
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
        title: "领用数量",
        dataIndex: "num",
        key: "num"
      },
      {
        title: "领用申请时间",
        render: record => {
          if (record !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        }
      },
      {
        title: "领用描述",
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
            {this.initoperate(record)}
            <AuthWrapper id="47" pid="46">
              <span
                style={{ cursor: "pointer" }}
                onClick={() => {
                  this.props.getcollectdetailMethod(record.id);
                  this.setState({
                    pathVisible: true
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
              <BreadcrumbComponent title="资产领用" />
              <div className={Style.formname}>
                {/* <Button type="primary" onClick={this.go_add_rule.bind(this)} style={{ marginRight: '30px' }}>添加</Button> */}
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
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.collectlist.data}
                rowKey={record => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.collectlist.total_record
                }}
              />
            </div>
            <Modal
              title="新增归还"
              visible={this.state.modalVisible}
              onOk={this.confrimaddback.bind(this)}
              onCancel={() => {
                this.setState({
                  modalVisible: false
                });
              }}
            >
              <div className={Styleup.resort}>
                <Label title="归还理由" />
                <Input
                  size="large"
                  style={{ width: "300px" }}
                  value={this.state.description}
                  onChange={value => {
                    this.setState({
                      description: value.currentTarget.value.replace(/\s+/g, "")
                    });
                  }}
                />
              </div>
              <div className={Styleup.resort}>
                <Label title="物品状态" />
                <Select
                  style={{ width: "300px" }}
                  defaultValue={statusData[0].value}
                  onChange={value => {
                    console.log(value);
                    this.setState({
                      statusSelect: value
                    });
                  }}
                >
                  {statusData.map((ele, index) => {
                    return (
                      <Option value={ele.value} key={index}>
                        {ele.name}
                      </Option>
                    );
                  })}
                </Select>
              </div>
            </Modal>
            <Modal
              title="审批详情"
              // centered
              visible={this.state.pathVisible}
              //onOk={this.handleOkBack.bind(this)}
              onCancel={() => {
                this.setState({
                  pathVisible: false
                });
              }}
              footer={null}
            >
              {/* <Timeline>
                {this.props.collectdetail.length == 0 ? (
                  <p style={{ textAlign: "center" }}>暂无审核记录</p>
                ) : (
                  this.props.collectdetail.map((ele, index) => {
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
                              : moment(ele.auditTime).format(
                                  "YYYY-MM-DD HH:mm:ss"
                                )}
                          </span>
                        </p>
                        {ele.reason == null ? null : (
                          <p>
                            <span style={{ marginRight: "30px" }}>
                              原因描述
                            </span>
                            <span>{ele.reason}</span>
                          </p>
                        )}
                      </Timeline.Item>
                    );
                  })
                )}
              </Timeline> */}
              <TimeLineContent data={this.props.collectdetail}/>
            </Modal>
          </div>
        }
      </>
    );
  }
  confrimaddback() {
    this.setState({
      modalVisible: false
    });
    const params = {
      assetStatus: this.state.statusSelect,
      description: this.state.description,
      receiveId: this.state.addId,
      userId: JSON.parse(localStorage.getItem("userinfotoken")).id
    };
    this.props.addreturnMethod(params);
    setTimeout(() => {
      this.props.getcollectlistMethod(this.state.params);
    }, 1000);
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
        this.props.getcollectlistMethod(this.state.params);
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
      pathname: "/index/add_assets_collection"
    });
  }
}
export default Form.create()(withRouter(AssetsCollection));
