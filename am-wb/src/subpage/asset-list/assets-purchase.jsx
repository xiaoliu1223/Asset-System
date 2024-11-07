/**
 * 资产归还列表展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import { URL } from "../../common/common";
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
  Upload,
  message,
  Timeline,
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getpurchaseListMethod,
  backpurchaseMethod,
  detailpurchaseMethod,
} from "../../store/actions/assets/purchaseAction";
import { AuthWrapper } from "../../components/Auth.jsx";
import TimeLineContent from "../../components/TimeLine";
import Axios from "../../common/axios";
const { Option } = Select;
const FormItem = Form.Item;
const SelectData = [
  {
    value: "",
    name: "全部",
  },
  {
    value: "0",
    name: "待审批",
  },
  {
    value: "1",
    name: "通过",
  },
  {
    value: "-1",
    name: "驳回",
  },
  {
    value: "2",
    name: "撤销",
  },
];
@connect(
  (state) => ({
    purchaselist: state.purchaselist,
    purchasedetail: state.purchasedetail,
    auth: state.auth,
  }),
  (dispatch) => ({
    getpurchaseListMethod: (data) => dispatch(getpurchaseListMethod(data)),
    backpurchaseMethod: (data) => dispatch(backpurchaseMethod(data)),
    detailpurchaseMethod: (data) => dispatch(detailpurchaseMethod(data)),
  })
)
class AssetsPurchase extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    filterid: "",
    modalVisible: false,
    params: {
      current: 1,
      limit: 10,
    },
  };
  componentDidMount() {
    this.props.getpurchaseListMethod(this.state.params);
  }
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
            status: values.status,
            assetName: values.assetName,
          },
        },
        () => {
          this.props.getpurchaseListMethod(this.state.params);
        }
      );
    });
  }
  initoperate(record) {
    switch (record.status) {
      case 0:
        return (
          <span>
            <AuthWrapper id="32" pid="29">
              <Link
                to={{
                  pathname: `/index/add-assets-purchase`,
                  state: {
                    item: record,
                  },
                }}
              >
                {" "}
                <span style={{ cursor: "pointer" }}>编辑</span>
              </Link>
              <Divider type="vertical" />
            </AuthWrapper>
            <AuthWrapper id="33" pid="29">
              <Popconfirm
                title="是否确定撤回申请?"
                onConfirm={this.confirm.bind(this, record)}
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
      default:
        break;
    }
  }

  donwload() {
    Axios.get("/sysApplicationRecord/downloadAddTemp");
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    const { errorMsg } = this.props;
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index",
      },
      {
        title: "申请人",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "申请单位",
        dataIndex: "departmentName",
        key: "departmentName",
      },
      {
        title: "资产名称",
        dataIndex: "assetName",
        key: "assetName",
      },
      {
        title: "资产类别",
        dataIndex: "assetTypeName",
        key: "assetTypeName",
      },
      {
        title: "规格型号",
        dataIndex: "specification",
        key: "specification",
      },
      {
        title: "申请数量",
        dataIndex: "num",
        key: "num",
      },
      {
        title: "单位",
        dataIndex: "units",
        key: "units",
      },
      {
        title: "预算单价",
        // dataIndex: 'budgetPrice',
        // key: 'budgetPrice',
        render: (record) => {
          if (record.budgetPrice !== null) {
            return record.budgetPrice + "元";
          }
        },
      },
      {
        title: "申请描述",
        dataIndex: "description",
        key: "description",
      },
      {
        title: "申请时间",
        render: (record) => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        },
      },
      {
        title: "申请状态",
        dataIndex: "statusText",
        key: "statusText",
      },
      {
        title: "操作",
        render: (record) => {
          return (
            <span>
              {/* 0: {id: 30, pid: 29, name: "查询", path: null, permission: null, …}
                  1: {id: 31, pid: 29, name: "新增", path: null, permission: null, …}
                  2: {id: 32, pid: 29, name: "编辑", path: null, permission: null, …}
                  3: {id: 33, pid: 29, name: "撤销", path: null, permission: null, …} */}
              {this.initoperate(record)}

              <AuthWrapper id="30" pid="29">
                <span
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    this.props.detailpurchaseMethod(record.id);
                    this.setState({
                      modalVisible: true,
                    });
                    // if (record.sysAuditLogs !== null && record.sysAuditLogs !== undefined) {
                    //   this.setState({
                    //     currentPath: record.sysAuditLogs
                    //   })
                    // }
                  }}
                >
                  查看
                </span>
              </AuthWrapper>
            </span>
          );
        },
      },
    ];

    const props = {
      name: "file",
      action: `${Axios.defaults.baseURL}/sysApplicationRecord/uploadAddTemp`,
      headers: {
        authorization: localStorage.getItem("accessToken"),
      },
      onChange(info) {
        if (info.file.status !== "uploading") {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === "done") {
          message.success(`${info.file.name} 上传成功`);
        } else if (info.file.status === "error") {
          message.error(`${info.file.name} 上传失败`);
        }
      },
    };
    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="资产申请" />
              <Form
                onSubmit={this.handleSubmitSearch.bind(this)}
                className={Style.formname}
              >
                <div style={{
                  display:'flex',
                  paddingTop:'4px',
                  paddingBottom:'40px'
                }}>
                  <Upload {...props}>
                    <Button>
                      <Icon type="upload" /> 低质易耗品上传申请
                    </Button>
                  </Upload>
                  <AuthWrapper id="31" pid="29">
                    <Button
                      type="primary"
                      onClick={this.go_add_rule.bind(this)}
                      style={{ margin: "0 30px" }}
                    >
                      添加
                    </Button>
                  </AuthWrapper>
                  <a
                    href={`${URL()}/sysApplicationRecord/downloadAddTemp?token=${localStorage.getItem(
                      "accessToken"
                    )}`}
                    download="资产申请"
                    className={Style.linkherf}
                    style={{
                      width: "200px",
                      marginTop: 0,
                      marginRight: "20px",
                      marginLeft: "20px",
                    }}
                  >
                    低质易耗品申请模板下载
                  </a>
                </div>
                <div style={{fontSize: '14px', margin: '9px 10px 0 0'}}>申请状态 :</div>
                <div className={`${Style.search_items} ${Style.small}`}>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("status", {
                      initialValue: "",
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
                      getValueFromEvent: (event) =>
                        event.target.value.replace(/\s+/g, ""),
                    })(<Input placeholder="资产名称" maxLength={20} />)}
                  </FormItem>
                  <FormItem className={Style.li}>
                    <Button htmlType="submit" type="primary">
                      查询
                    </Button>
                  </FormItem>
                </div>
              </Form>
            </div>
            <div className={Style.banner_list}
            style={{marginTop:'10px'}}
            >
              <Table
                columns={columns}
                dataSource={this.props.purchaselist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.purchaselist.total_record,
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
              modalVisible: false,
            });
          }}
          footer={null}
        >
          <TimeLineContent data={this.props.purchasedetail} />
        </Modal>
      </>
    );
  }
  //类型筛选
  changeTypefilter(value) {
    if (value == "") {
      this.setState(
        {
          params: {
            current: 1,
            limit: 10,
          },
        },
        () => {
          this.props.getpurchaseListMethod(this.state.params);
        }
      );
    } else {
      this.setState(
        {
          params: {
            current: 1,
            limit: 10,
            status: value,
          },
        },
        () => {
          this.props.getpurchaseListMethod(this.state.params);
        }
      );
    }
  }
  //切换分页
  handleTableChange(current) {
    const { params } = this.state;
    this.setState(
      {
        params: { ...params, current },
      },
      () => {
        this.props.getpurchaseListMethod(this.state.params);
      }
    );
  }
  //确认撤回
  confirm = (record, e) => {
    this.props.backpurchaseMethod(record.id);
    setTimeout(() => {
      this.props.getpurchaseListMethod(this.state.params);
    }, 1000);
  };

  cancel = (e) => {
    console.log(e);
  };
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/add-assets-purchase",
    });
  }
}

export default Form.create()(withRouter(AssetsPurchase));
