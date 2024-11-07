import React, { Component } from "react";
import Style from "../../common/list.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import {
  Table,
  Select,
  Icon,
  Modal,
  Divider,
  Popconfirm,
  message,
  Button,
  Input,
  Tabs,
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getdeletelistMethod,
  auditdeletemethod,
} from "../../store/actions/todoAction";
import { SelectedAction } from "../../components/selected-action";

const { Search } = Input;
const { Option } = Select;
const { TabPane } = Tabs;
@connect(
  (state) => ({
    deletelist: state.deletelist,
  }),
  (dispatch) => ({
    getdeletelistMethod: (data) => dispatch(getdeletelistMethod(data)),
    auditdeletemethod: (id, data) => dispatch(auditdeletemethod(id, data)),
  })
)
class ToDoDelete extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    modalVisible: false,
    currentId: "",
    backdescri: "",
    params: {
      current: 1,
      limit: 10,
      status: "0",
      userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
    },
    selectedRowKeys: [],
  };
  componentDidMount() {
    this.props.getdeletelistMethod(this.state.params);
  }

  //类型筛选
  changeTypefilter(value) {
    const { params } = this.state;
    this.setState(
      {
        params: {
          ...params,
          current: 1,
          status: value,
        },
      },
      () => {
        this.props.getdeletelistMethod(this.state.params);
      }
    );
  }
  onSelectChange = (selectedRowKeys) => {
    // console.log("selectedRowKeys changed: ", selectedRowKeys);
    this.setState({ selectedRowKeys });
  };
  render() {
    const { selectedRowKeys } = this.state;
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index",
      },
      {
        title: "核销申请人",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "核销部门",
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
        dataIndex: "assetType",
        key: "assetType",
      },
      {
        title: "规格型号",
        dataIndex: "specification",
        key: "specification",
      },
      {
        title: "核销数量",
        dataIndex: "num",
        key: "num",
      },
      {
        title: "核销金额",
        // dataIndex: 'pinAmount',
        // key: 'pinAmount',
        render: (record) => {
          if (record.pinAmount !== null) {
            return record.pinAmount + "元";
          }
        },
      },
      {
        title: "核销申请时间",
        render: (record) => {
          if (record.createTime !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        },
      },
      {
        title: "核销理由",
        dataIndex: "description",
        key: "description",
      },
      {
        title: "审批状态",
        dataIndex: "statusText",
        key: "statusText",
      },
      {
        title: "操作",
        render: (record) => {
          return (
            <>
              {record.status == 0 || record.status == -2 ? (
                <span>
                  {/* <Popconfirm
                    title="是否确定通过?"
                    onConfirm={this.confirm.bind(this, record)}
                    onCancel={this.cancel}
                    okText="是"
                    cancelText="否"
                  > */}
                  <span
                    style={{ cursor: "pointer" }}
                    onClick={this.confirm.bind(this, record)}
                  >
                    通过
                  </span>
                  {/* </Popconfirm> */}
                  <Divider type="vertical" />
                  <span
                    style={{ cursor: "pointer" }}
                    onClick={() => {
                      console.log(record);
                      this.setState({
                        modalVisible: true,
                        currentId: record.id,
                      });
                    }}
                  >
                    驳回
                  </span>
                </span>
              ) : null}
            </>
          );
        },
      },
    ];

    const isShowPassBtn = this.state.params.status === "0";

    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="审批管理/申请审批" />
              <div className={Style.formname}>
                {isShowPassBtn ? (
                  <SelectedAction
                    selectedRowKeys={selectedRowKeys}
                    btns={[
                      {
                        type: "primary",
                        text: "批量通过",
                        postUrl: "/sysChargeOffRecord/batchAudit",
                        auditStatus: 1,
                      },
                      // {
                      //   type: "danger",
                      //   text: "批量删除",
                      //   postUrl: "",
                      // },
                    ]}
                  />
                ) : (
                  ""
                )}
                <div style={{fontSize: '14px', marginTop: '5px'}}>申请状态 :</div>
                <Select
                  style={{ width: 340, marginLeft: "10px" }}
                  defaultValue="0"
                  onChange={this.changeTypefilter.bind(this)}
                >
                  <Option value="0">待审批</Option>
                  <Option value="1">已审批</Option>
                </Select>
              </div>
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.deletelist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.deletelist.total_record,
                }}
                rowSelection={isShowPassBtn ? rowSelection : undefined}
              />
            </div>
            <Modal
              title="驳回原因"
              centered
              visible={this.state.modalVisible}
              onOk={this.handleOkBack.bind(this)}
              onCancel={() => {
                this.setState({
                  modalVisible: false,
                });
              }}
            >
              <Input
                size="large"
                style={{ width: "100%" }}
                placeholder="驳回原因"
                value={this.state.backdescri}
                onChange={(e) => {
                  this.setState({
                    backdescri: e.currentTarget.value.replace(/\s+/g, ""),
                  });
                }}
              />
            </Modal>
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
          userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
        },
      },
      () => {
        this.props.getdeletelistMethod(this.state.params);
      }
    );
  }
  //驳回确认
  handleOkBack() {
    this.setState({
      modalVisible: false,
    });
    this.props.auditdeletemethod(this.state.currentId, {
      reason: this.state.backdescri,
      auditStatus: -1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getdeletelistMethod(this.state.params);
    }, 1000);
  }
  //审核通过
  confirm = (record, e) => {
    this.props.auditdeletemethod(record.id, {
      auditStatus: 1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getdeletelistMethod(this.state.params);
    }, 1000);
  };
  cancel = (e) => {
    console.log(e);
  };
}
export default withRouter(ToDoDelete);
