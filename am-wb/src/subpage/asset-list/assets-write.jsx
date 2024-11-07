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
  InputNumber,
  Button,
  Input,
  Modal,
  Timeline,
  Form
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getwritelistMethod,
  getwritebackMethod,
  getwriteconfirmMethod,
  getwritedetailMethod
} from "../../store/actions/assets/writeAction";

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
    name: "已核销"
  },
  {
    value: "-2",
    name: "审批中"
  }
];
// @AuthWrapper()
@connect(
  state => ({
    writelist: state.writelist,
    writedetail: state.writedetail,
    auth: state.auth
  }),
  dispatch => ({
    getwritelistMethod: data => dispatch(getwritelistMethod(data)),
    getwritebackMethod: data => dispatch(getwritebackMethod(data)),
    getwriteconfirmMethod: (id, data) =>
      dispatch(getwriteconfirmMethod(id, data)),
    getwritedetailMethod: id => dispatch(getwritedetailMethod(id))
  })
)
class AssetsWrite extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    Visible: false,
    modalVisible: false,
    money: 0,
    id: "",
    params: {
      current: 1,
      limit: 10
    }
  };

  componentDidMount() {
    this.props.getwritelistMethod(this.state.params);
  }
  //点击搜索
  //点击搜索
  handleSubmitSearch(e) {
    e.preventDefault();
    const { params } = this.state;
    this.props.form.validateFields((err, values) => {
      console.log(values);
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
          this.props.getwritelistMethod(this.state.params);
        }
      );
    });
  }
  initoperate(record) {
    if (record.status == 0) {
      return (
        <AuthWrapper id="62" pid="57">
          <Popconfirm
            title="是否确定撤回申请?"
            onConfirm={() => {
              this.props.getwritebackMethod(record.id);
              setTimeout(() => {
                this.props.getwritelistMethod(this.state.params);
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
        <AuthWrapper id="60" pid="57">
          <span
            style={{ cursor: "pointer" }}
            onClick={() => {
              this.setState({
                Visible: true,
                id: record.id
              });
            }}
          >
            确认核销
          </span>
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
        title: "核销申请人",
        dataIndex: "username",
        key: "username"
      },
      {
        title: "核销部门",
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
        title: "核销数量",
        dataIndex: "num",
        key: "num"
      },
      {
        title: "核销金额",
        // dataIndex: 'pinAmount',
        // key: 'pinAmount',
        render: record => {
          if (record.pinAmount !== null) {
            return record.pinAmount + "元";
          }
        }
      },
      {
        title: "核销申请时间",
        render: record => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        }
      },
      {
        title: "核销理由",
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
            {
              // 0: {id: 58, pid: 57, name: "查看", path: null, permission: null, …}
              // 1: {id: 60, pid: 57, name: "确认核销", path: null, permission: null, …}
              // 2: {id: 62, pid: 57, name: "撤销", path: null, permission: null, …}
            }
            {this.initoperate(record)}
            <AuthWrapper id="58" pid="57">
              <span
                style={{ cursor: "pointer" }}
                onClick={() => {
                  this.props.getwritedetailMethod(record.id);
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
        <div className={Style.banner}>
          <div className={Style.Breadcrumb}>
            <BreadcrumbComponent title="资产核销" />
            <div className={Style.formname}>
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
              dataSource={this.props.writelist.data}
              rowKey={record => record.id}
              pagination={{
                current: this.state.params.current,
                onChange: this.handleTableChange.bind(this),
                total: this.props.writelist.total_record
              }}
            />
          </div>
        </div>
        <Modal
          title="核销金额"
          centered
          visible={this.state.Visible}
          onOk={this.confrimNum.bind(this)}
          onCancel={() => {
            this.setState({
              Visible: false
            });
          }}
        >
          <Input
            prefix="￥"
            type="number"
            onChange={e => {
              this.setState({
                money: e.currentTarget.value
              });
            }}
            onKeyPress={event => {
              const invalidChars = ["-", "+", "e", "E"];
              if (invalidChars.indexOf(event.key) !== -1) {
                event.preventDefault();
              }
            }}
          />
        </Modal>
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
            {this.props.writedetail.length == 0 ? (
              <p style={{ textAlign: "center" }}>暂无审核记录</p>
            ) : (
              this.props.writedetail.map((ele, index) => {
                return (
                  <Timeline.Item
                    key={index}
                    color={ele.auditStatus == 1 ? "green" : "red"}
                  >
                    <p>
                      <span style={{ marginRight: "30px" }}>申请人</span>
                    </p>
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
          <TimeLineContent data={this.props.writedetail} />
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
        this.props.getwritelistMethod(this.state.params);
      }
    );
  }
  confrimNum = (record, e) => {
    this.setState({
      Visible: false
    });
    this.props.getwriteconfirmMethod(this.state.id, this.state.money);
    setTimeout(() => {
      this.props.getwritelistMethod(this.state.params);
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

export default Form.create()(withRouter(AssetsWrite));
