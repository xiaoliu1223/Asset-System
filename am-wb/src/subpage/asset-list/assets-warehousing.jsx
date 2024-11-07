/**
 * 资产库存展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import Styleup from "../../common/upload.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Form,
  Divider,
  Popconfirm,
  Icon,
  Button,
  Input,
  Modal,
  InputNumber,
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import { URL } from "../../common/common";
import { AuthWrapper } from "../../components/Auth.jsx";

import {
  getwarehousinglistMethod,
  addgetMethod,
  adddeteleMethod,
} from "../../store/actions/assets/warehousingAction";
import {
  getprintimageMethod,
  getprintexcelMethod,
} from "../../store/actions/commomAction";
import Axios from "../../common/axios";

const { Search, TextArea } = Input;
const { Option } = Select;
const SelectData = [
  {
    value: "",
    name: "全部",
  },
  {
    value: "1",
    name: "在库",
  },
  {
    value: "2",
    name: "出库",
  },
  {
    value: "3",
    name: "报废",
  },
];
@connect(
  (state) => ({
    warehousinglist: state.warehousinglist,
    auth: state.auth,
  }),
  (dispatch) => ({
    getwarehousinglistMethod: (data) =>
      dispatch(getwarehousinglistMethod(data)),
    addgetMethod: (data) => dispatch(addgetMethod(data)),
    adddeteleMethod: (data) => dispatch(adddeteleMethod(data)),
    getprintimageMethod: (value, data) =>
      dispatch(getprintimageMethod(value, data)),
    getprintexcelMethod: (data) => dispatch(getprintexcelMethod(data)),
  })
)
class AssetsWarehousing extends Component {
  constructor(props) {
    super(props);
  }

  state = {
    params: {
      current: 1,
      limit: 10,
    },
    modalVisible: false,
    modalVisiblwrite: false,
    num: 0,
    assetCodeadd: 0,
    deletedescription: "",
    deletenum: 0,
    assetId: 0,
    usedBy: "", //实际领用人
    departments: [],
    departmentId: null,
  };

  componentDidMount() {
    // this.setState(
    //   {
    //     params: {
    //       ...
    //       current: 1
    //     }
    //   },
    //   () => {
    //     this.props.getwarehousinglistMethod(this.state.params);
    //   }
    // );
    const That = this;
    this.props.getwarehousinglistMethod(this.state.params);

    Axios({
      method: "get",
      url: "/sysDepartment/listByUser",
    }).then((res) => {
      if (res.data.code === "000000" && res.data.data.length > 0) {
        res.data.data.unshift({
          id: null,
          name: "全部",
        });
        That.setState({
          departments: res.data.data,
        });
      }
    });
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
            inventoryStatus: values.inventoryStatus,
            departmentId: values.departmentId,
            assetName: values.assetName,
            assetCode: values.assetCode,
          },
        },
        () => {
          this.props.getwarehousinglistMethod(this.state.params);
        }
      );
    });
  }
  //点击确认采购输入金额
  setModalVisible(modalVisible) {
    this.setState({ modalVisible });
  }
  //输入的金额
  onChange(value) {
    console.log("changed", value);
  }
  checkPrint(record) {
    this.props.getprintimageMethod(
      JSON.parse(localStorage.getItem("userinfotoken")).id,
      {
        assetCode: record.assetCode,
      }
    );
    // ALLMethod.getAllPrinters()
  }
  initoperate(record) {
    // record.updateTime == null &&
    if (record.inventoryStatus == 1) {
      return (
        <span
          style={{
            display: "flex",
            alignItems: "center",
            flexWrap: "wrap",
          }}
        >
          {record.updateTime == null ||
          localStorage.getItem("departmentId") === "5" ? (
            <span>
              <AuthWrapper id="42" pid="39">
                <Link
                  to={{
                    pathname: `/index/add-assets-warehousing`,
                    state: {
                      item: record,
                    },
                  }}
                >
                  {" "}
                  <span tyle={{ cursor: "pointer" }}>编辑</span>
                </Link>
              </AuthWrapper>
              <Divider type="vertical" />
              {/* <AuthWrapper id='43' pid="39">
              <Popconfirm
                title="是否确定删除?"
                onConfirm={this.confirm.bind(this, record)}
                onCancel={this.cancel}
                okText="是"
                cancelText="否"
              >
                <span style={{ cursor: 'pointer' }}>删除</span>
              </Popconfirm>
            </AuthWrapper>
            <Divider type="vertical" /> */}
            </span>
          ) : null}
          <AuthWrapper id="44" pid="39">
            <span
              style={{ cursor: "pointer" }}
              onClick={() => {
                this.setState({
                  modalVisible: true,
                  assetCodeadd: record.assetCode,
                });
              }}
            >
              新增领用
            </span>
            <Divider type="vertical" />
          </AuthWrapper>
          <AuthWrapper id="45" pid="39">
            <span
              style={{ cursor: "pointer" }}
              onClick={() => {
                this.setState({
                  modalVisiblwrite: true,
                  assetId: record.id,
                });
              }}
            >
              新增核销
            </span>
            <Divider type="vertical" />
          </AuthWrapper>
        </span>
      );
    }
  }
  render() {
    const Label = (event) => {
      return <span className={Styleup.sort_size}>{event.title}</span>;
    };
    const { TextArea } = Input;
    const FormItem = Form.Item;
    const { getFieldDecorator } = this.props.form;
    const { errorMsg } = this.props;
    const columns = [
      //父表格
      {
        title: "序号",
        dataIndex: "index",
        key: "index",
      },
      {
        title: "录入人员",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "所属部门",
        dataIndex: "departmentName",
        key: "departmentName",
      },
      {
        title: "资产编号",
        dataIndex: "assetCode",
        key: "assetCode",
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
        title: "入库数量",
        dataIndex: "assetNum",
        key: "assetNum",
      },
      {
        title: "出库数量",
        dataIndex: "outNum",
        key: "outNum",
      },
      {
        title: "核销数量",
        dataIndex: "chargeOffNum",
        key: "chargeOffNum",
      },
      {
        title: "单价",
        // dataIndex: 'price',
        // key: 'price',
        render: (record) => {
          if (record.price !== null) {
            return record.price + "元";
          }
        },
      },
      {
        title: "单位",
        dataIndex: "units",
        key: "units",
      },
      {
        title: "核销返还金额",
        // dataIndex: 'chargeOffMoney',
        // key: 'chargeOffMoney',
        render: (record) => {
          if (record.chargeOffMoney !== null) {
            return record.chargeOffMoney + "元";
          }
        },
      },
      {
        title: "入库时间",
        render: (record) => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD");
          }
        },
      },
      {
        title: "库存状态",
        dataIndex: "inventoryStatus",
        key: "inventoryStatus",
        render: (record) => {
          switch (record) {
            case 1:
              return <span>在库</span>;
            case 2:
              return <span>出库</span>;
            case 3:
              return <span>报废</span>;
            default:
              break;
          }
        },
      },
      {
        title: "操作",
        render: (record) => {
          return (
            <span>
              {this.initoperate(record)}
              {record.assetSuperType !== 3 ? (
                <AuthWrapper id="40" pid="39">
                  <Popconfirm
                    title="是否确认打印?"
                    onConfirm={this.checkPrint.bind(this, record)}
                    onCancel={this.cancel}
                    okText="是"
                    cancelText="否"
                  >
                    <span style={{ cursor: "pointer" }}>打印条码</span>
                  </Popconfirm>
                  {/* <span style={{cursor:'pointer'}} onClick={this.checkPrint.bind(this,record)}>打印条码</span> */}
                </AuthWrapper>
              ) : (
                ""
              )}
            </span>
          );
        },
      },
    ];
    return (
      <>
        {
          // console.log(this.props.auth)
          // 0: {id: 40, pid: 39, name: "打印条码", path: null, permission: null, …}
          // 1: {id: 41, pid: 39, name: "新增", path: null, permission: null, …}
          // 2: {id: 42, pid: 39, name: "编辑", path: null, permission: null, …}
          // 3: {id: 43, pid: 39, name: "删除", path: null, permission: null, …}
          // 4: {id: 44, pid: 39, name: "领用", path: null, permission: null, …}
          // 5: {id: 45, pid: 39, name: "核销", path: null, permission: null, …}
        }
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="资产库存" />
              <Form
                onSubmit={this.handleSubmitSearch.bind(this)}
                className={Style.formname}
              >
                <AuthWrapper id="41" pid="39">
                  <Button
                    type="primary"
                    onClick={this.go_add_rule.bind(this)}
                    style={{ marginRight: "30px" }}
                  >
                    添加
                  </Button>
                </AuthWrapper>
                <div className={`${Style.search_items} ${Style.small}`}>
                  <div style={{fontSize: '14px', margin: '9px 10px 0 0'}}>库存状态 :</div>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("inventoryStatus", {
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
                  <div style={{fontSize: '14px', margin: '9px 10px 0 0'}}>所属部门 :</div>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("departmentId", {
                      initialValue: null,
                    })(
                      <Select
                        onChange={(value) => {
                          this.setState({
                            departmentId: value,
                          });
                        }}
                        style={{ width: 180 }}
                        placeholder="部门"
                      >
                        {this.state.departments.map((ele, index) => {
                          return (
                            <Option value={ele.id} key={index}>
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
                    {getFieldDecorator("assetCode", {
                      getValueFromEvent: (event) =>
                        event.target.value.replace(/\s+/g, ""),
                    })(<Input placeholder="资产编号" />)}
                  </FormItem>
                  <FormItem className={Style.li}>
                    <Button type="primary" htmlType="submit">
                      查询
                    </Button>
                  </FormItem>
                </div>
                <a
                  href={`${URL()}/sysAsset/downloadExcel?token=${localStorage.getItem(
                    "accessToken"
                  )}&${
                    this.state.departmentId
                      ? `departmentId=${this.state.departmentId}`
                      : this.state.departmentId
                  }`}
                  // JSON.parse(localStorage.getItem("userinfotoken"))
                  //   .departmentId
                  download="资产库存"
                  className={Style.linkherf}
                >
                  导出
                </a>
              </Form>
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.warehousinglist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.warehousinglist.total_record,
                }}
              />
            </div>
          </div>
        }
        <Modal
          title="领用申请"
          centered
          visible={this.state.modalVisible}
          onCancel={() => {
            this.setState({
              modalVisible: false,
            });
          }}
          footer={null}
        >
          <div className={Styleup.banner_add}>
            <div className={Styleup.resort}>
              <Label title="实际使用人" />
              <Input
                size="large"
                style={{ width: "300px" }}
                onChange={(value) => {
                  this.setState({
                    usedBy: value.currentTarget.value.replace(/\s+/g, ""),
                  });
                }}
              />
            </div>
            <div className={Styleup.resort}>
              <Label title="领用数量" />
              <InputNumber
                onChange={(value) => {
                  this.setState({
                    num: value,
                  });
                }}
                size="large"
                style={{ width: "300px" }}
              />
            </div>
            <div className={Styleup.resort}>
              <Label title="领用描述" />
              <TextArea
                placeholder="领用描述"
                style={{ width: "300px" }}
                maxLength={30}
                allowClear
                onChange={(value) => {
                  this.setState({
                    description: value.currentTarget.value.replace(/\s+/g, ""),
                  });
                }}
              />
              {/* <Input size='large' 
              maxLength={30}
              style={{ width: "300px" }} 
              onChange={(value) => {
                this.setState({
                  description: value.currentTarget.value.replace(/\s+/g, "")
                })
              }} /> */}
            </div>
            <div className={Styleup.submit} style={{ width: "520px" }}>
              <Button
                size="large"
                style={{ width: "100px", marginRight: "30px" }}
                onClick={() => {
                  this.setModalVisible(false);
                }}
              >
                返回
              </Button>
              <Button
                size="large"
                style={{ width: "100px" }}
                type="primary"
                onClick={this.onSubmitTypeget.bind(this)}
              >
                确定
              </Button>
            </div>
          </div>
        </Modal>
        <Modal
          title="核销申请"
          centered
          onCancel={() => {
            this.setState({
              modalVisiblwrite: false,
            });
          }}
          visible={this.state.modalVisiblwrite}
          footer={null}
        >
          <div className={Styleup.banner_add}>
            <div className={Styleup.resort}>
              <Label title="核销数量" />
              <InputNumber
                size="large"
                onChange={(value) => {
                  this.setState({
                    deletenum: value,
                  });
                }}
                style={{ width: "300px" }}
              />
            </div>
            <div className={Styleup.resort}>
              <Label title="核销理由" />
              <Input
                size="large"
                style={{ width: "300px" }}
                onChange={(value) => {
                  this.setState({
                    deletedescription: value.currentTarget.value.replace(
                      /\s+/g,
                      ""
                    ),
                  });
                }}
              />
            </div>
            <div className={Styleup.submit} style={{ width: "520px" }}>
              <Button
                size="large"
                style={{ width: "100px", marginRight: "30px" }}
                onClick={() => {
                  this.setModalVisible(false);
                }}
              >
                返回
              </Button>
              <Button
                size="large"
                style={{ width: "100px" }}
                type="primary"
                onClick={this.onSubmitTypereturn.bind(this)}
              >
                确定
              </Button>
            </div>
          </div>
        </Modal>
      </>
    );
  }
  onSubmitTypeget(e) {
    const params = {
      num: this.state.num,
      description: this.state.description,
      userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
      assetCode: this.state.assetCodeadd,
      usedBy: this.state.usedBy,
    };
    this.props.addgetMethod(params);
    this.setState({
      modalVisible: false,
    });
    setTimeout(() => {
      this.props.getwarehousinglistMethod();
    }, 1000);
  }
  onSubmitTypereturn(e) {
    const params = {
      assetId: this.state.assetId,
      num: this.state.deletenum,
      description: this.state.deletedescription,
      userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
    };
    this.props.adddeteleMethod(params);
    this.setState({
      modalVisiblwrite: false,
    });
    setTimeout(() => {
      this.props.getwarehousinglistMethod();
    }, 1000);
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
        this.props.getwarehousinglistMethod(this.state.params);
      }
    );
  }
  confirm = (record, e) => {
    this.props.delruleruleMethod(record.id);
    setTimeout(() => {
      this.props.getwarehousinglistMethod(this.state.params);
    }, 1000);
  };

  cancel = (e) => {
    console.log(e);
  };
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/add-assets-warehousing",
    });
  }
}

export default Form.create()(withRouter(AssetsWarehousing));
