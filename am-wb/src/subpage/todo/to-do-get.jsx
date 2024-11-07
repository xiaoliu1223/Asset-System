/**
 * 资产领用列表展示
 */
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
import { getlistMethod, auditgetmethod } from "../../store/actions/todoAction";
import { SelectedAction } from "../../components/selected-action";

const { Search } = Input;
const { Option } = Select;
const { TabPane } = Tabs;
@connect(
  (state) => ({
    getlist: state.getlist,
  }),
  (dispatch) => ({
    getlistMethod: (data) => dispatch(getlistMethod(data)),
    auditgetmethod: (id, data) => dispatch(auditgetmethod(id, data)),
  })
)
class ToDoGet extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    modalVisible: false,
    currentId: "",
    backdescri: "",
    current: 1,
    userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
    limit: 10,
    status: 0,
    selectedRowKeys: [],
    // params: {
    //   current: 1,
    //   userId: JSON.parse(localStorage.getItem("userinfotoken")).id,
    //   limit: 10,
    //   status: 0
    // }
  };
  componentDidMount() {
    const { current, userId, limit, status } = this.state;
    this.props.getlistMethod({
      current,
      userId,
      limit,
      status,
    });
  }

  //类型筛选
  changeTypefilter(value) {
    const { current, userId, limit } = this.state;
    this.setState(
      {
        status: value,
      },
      () => {
        this.props.getlistMethod({
          current,
          userId,
          limit,
          status: value,
        });
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
        title: "领用申请人",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "领用部门",
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
        title: "领用数量",
        dataIndex: "num",
        key: "num",
      },
      {
        title: "领用申请时间",
        render: (record) => {
          if (record !== null) {
            return moment(record.createTime).format("YYYY-MM-DD HH:mm:ss");
          }
        },
      },
      {
        title: "领用描述",
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
              {record.status == 0 ? (
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

    const isShowPassBtn = this.state.status.toString() === "0";

    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="审批管理/领用审批" />
              <div className={Style.formname}>
                {isShowPassBtn ? (
                  <SelectedAction
                    selectedRowKeys={selectedRowKeys}
                    btns={[
                      {
                        type: "primary",
                        text: "批量通过",
                        postUrl: "/sysReceiveRecord/batchAudit",
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
                dataSource={this.props.getlist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.getlist.total_record,
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
    const { status, userId, limit } = this.state;
    this.setState(
      {
        current,
      },
      () => {
        this.props.getlistMethod({
          current,
          status,
          userId,
          limit,
        });
      }
    );
  }
  //驳回确认
  handleOkBack() {
    const { current, status, userId, limit } = this.state;
    this.setState({
      modalVisible: false,
    });
    this.props.auditgetmethod(this.state.currentId, {
      reason: this.state.backdescri,
      auditStatus: -1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getlistMethod({
        current,
        status,
        userId,
        limit,
      });
    }, 1000);
  }
  //审核通过
  confirm = (record, e) => {
    const { current, status, userId, limit } = this.state;

    this.props.auditgetmethod(record.id, {
      auditStatus: 1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getlistMethod({
        current,
        status,
        userId,
        limit,
      });
    }, 1000);
  };
  cancel = (e) => {
    console.log(e);
  };
}
export default withRouter(ToDoGet);
