/**
 * 资产归还列表展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Form,
  Divider,
  Popconfirm,
  message,
  Button,
  Input,
  Modal,
  InputNumber
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import { AuthWrapper } from "../../components/Auth.jsx";
import { getbuylistMethod } from "../../store/actions/assets/buyAction";
import { getprintimageMethod } from "../../store/actions/commomAction";
import * as ALLMethod from "../../common/jcPrinterSdk_api";

const { Search } = Input;
const { Option } = Select;
@connect(
  state => ({
    buylist: state.buylist,
    auth: state.auth
  }),
  dispatch => ({
    getbuylistMethod: data => dispatch(getbuylistMethod(data)),
    getprintimageMethod: (value, data) =>
      dispatch(getprintimageMethod(value, data))
  })
)
class AssetsBuy extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    params: {
      current: 0,
      limit: 10
    },
    modalVisible: false
  };
  componentDidMount() {
    ALLMethod.getInstance();
    this.props.getbuylistMethod(this.props.params);
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
            purchaseStatus: values.purchaseStatus,
            assetName: values.assetName
          }
        },
        () => {
          this.props.getbuylistMethod(this.state.params);
        }
      );
    });
  }
  // //点击确认采购输入金额
  // setModalVisible(modalVisible) {
  //   this.setState({ modalVisible });
  // }
  //输入的金额
  onChange(value) {
    console.log("changed", value);
  }
  checkPrint(record) {
    this.props.getprintimageMethod(
      JSON.parse(localStorage.getItem("userinfotoken")).id,
      {
        assetCode: record.assetCode
      }
    );
    // ALLMethod.getAllPrinters()
  }

  render() {
    const FormItem = Form.Item;
    const { getFieldDecorator } = this.props.form;
    const columns = [
      //父表格
      {
        title: "序号",
        dataIndex: "index",
        key: "index"
      },
      {
        title: "采购人",
        dataIndex: "buyerName",
        key: "buyerName"
      },
      {
        title: "申请人",
        dataIndex: "username",
        key: "username"
      },
      {
        title: "申请单位",
        dataIndex: "departmentName",
        key: "departmentName"
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
        title: "申请数量",
        dataIndex: "budgetNum",
        key: "budgetNum"
      },
      {
        title: "单位",
        dataIndex: "units",
        key: "units"
      },
      {
        title: "预算单价",
        // dataIndex: 'budgetPrice',
        // key: 'budgetPrice',
        render: record => {
          if (record.budgetPrice !== null) {
            return record.budgetPrice + "元";
          }
        }
      },
      {
        title: "申请时间",
        render: record => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        }
      },
      {
        title: "资产编号",
        dataIndex: "assetCode",
        key: "assetCode",
        width:'150px'
      },
      {
        title: "采购日期",
        render: record => {
          if (record.purchaseTime !== null) {
            return moment(record.purchaseTime).format("YYYY-MM-DD");
          }
        }
      },
      {
        title: "实际数量",
        dataIndex: "actualNum",
        key: "actualNum"
      },
      {
        title: "实际单位",
        dataIndex: "actualUnits",
        key: "actualUnits"
      },
      {
        title: "实际单价",
        // dataIndex: 'actualPrice',
        // key: 'actualPrice',
        render: record => {
          if (record.actualPrice !== null) {
            return record.actualPrice + "元";
          }
        }
      },
      {
        title: "实际总额",
        dataIndex: "actualTotalMount",
        key: "actualTotalMount"
      },
      {
        title: "采购描述",
        dataIndex: "description",
        key: "description",
        width:'150px'
      },
      {
        title: "采购状态",
        dataIndex: "purchaseStatusText",
        key: "purchaseStatusText"
      },
      {
        title: "操作",
        width:'100px',
        render: record => (
          <span>
            {record.purchaseStatus == "0" ? (
              <>
                <AuthWrapper id="37" pid="34">
                  <Link
                    to={{
                      pathname: "/index/add-assets-buy",
                      state: {
                        item: record
                      }
                    }}
                  >
                    确认采购
                  </Link>
                </AuthWrapper>
              </>
            ) : (
              <AuthWrapper id="38" pid="34">
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
            )}
          </span>
        )
      }
    ];
    return (
      <>
        {/* 0: {id: 37, pid: 34, name: "确认采购", path: null, permission: null, …}
          1: {id: 38, pid: 34, name: "打印条码", path: null, permission: null, … */}
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="资产采购" />
              <Form
                onSubmit={this.handleSubmitSearch.bind(this)}
                className={Style.formname}
              >
                <div className={`${Style.search_items} ${Style.small}`}>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("assetName", {
                      getValueFromEvent: event =>
                        event.target.value.replace(/\s+/g, "")
                    })(<Input placeholder="资产名称" />)}
                  </FormItem>
                  <div style={{fontSize: '14px', margin: '9px 10px 0 0'}}>申请状态 :</div>
                  <FormItem className={Style.li}>
                    {getFieldDecorator("purchaseStatus", {
                      initialValue: ""
                    })(
                      <Select
                        style={{ width: " 150px" }}
                        placeholder="采购状态"
                      >
                        <Option value="">全部</Option>
                        <Option value="0">采购中</Option>
                        <Option value="1">采购完成已入库</Option>
                      </Select>
                    )}
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
                dataSource={this.props.buylist.data}
                rowKey={record => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.buylist.total_record
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
          current: current
        }
      },
      () => {
        this.props.getbuylistMethod(this.state.params);
      }
    );
  }
  confirm = (record, e) => {
    // this.props.delruleruleMethod(record.id);
    setTimeout(() => {
      // this.props.rolelistMethod(this.state.params);
    }, 1000);
  };
  // 跳转到添加页
  go_buy_rule() {
    this.props.history.push({
      pathname: "/index/add-assets-buy"
    });
  }
  cancel = e => {
    console.log(e);
  };
}

export default Form.create()(withRouter(AssetsBuy));
