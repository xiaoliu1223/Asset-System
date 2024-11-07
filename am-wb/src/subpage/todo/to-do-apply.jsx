/**
 * 资产领用列表展示
 */
import React, { Component } from "react";
import Style from "../../common/list.less";
import BreadcrumbComponent from "../../components/Breadcrumb/Breadcrumb";
import TodoDetailContent from "../../components/TodoDetail"
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
  getapplylistMethod,
  Auditapplymethod,
  tododetailMethod
} from "../../store/actions/todoAction";
import { SelectedAction } from "../../components/selected-action";

const { Search } = Input;
const { Option } = Select;
const { TabPane } = Tabs;
@connect(
  (state) => ({
    applylist: state.applylist,
    tododetail: state.tododetail
  }),
  (dispatch) => ({
    getapplylistMethod: (data) => dispatch(getapplylistMethod(data)),
    Auditapplymethod: (id, data) => dispatch(Auditapplymethod(id, data)),
    tododetailMethod: (data) => dispatch(tododetailMethod(data))
  })
)
class ToDoApply extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    modalVisible: false,
    modalDetailVisible: false,
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
    this.props.getapplylistMethod(this.state.params);
  }

  //类型筛选
  changeTypefilter(value) {
    const { params } = this.state;
    this.setState(
      {
        params: {
          ...params,
          status: value,
        },
      },
      () => {
        this.props.getapplylistMethod(this.state.params);
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
    // const hasSelected = selectedRowKeys.length > 0;
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
                  {record.relateJobNo != null ? (
                    <span>
                      <span style={{ cursor: "pointer" }}
                      onClick={() => {
                        this.props.tododetailMethod(record.relateJobNo);
                        this.setState({
                          modalDetailVisible: true,
                        });
                      }}>明细</span>
                      <Divider type="vertical" />
                    </span>
                  ) : null}
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
                      this.setState({
                        modalVisible: true,
                        currentId: record.id,
                      });
                    }}
                  >
                    驳回
                  </span>
                </span>
              ) : (<Link
                to={{
                  pathname: `/index/to-do-complete`,
                  state: {
                    item: record,
                  },
                }}
              >
                <span style={{ cursor: "pointer" }}>详情</span>
              </Link>)}
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
                {/* <div>
                  <span style={{ marginLeft: 8 }}>
                    {hasSelected ? `选择了 ${selectedRowKeys.length} 项` : ""}
                  </span>
                  <Button
                    type="primary"
                    disabled={!hasSelected}
                    style={{ margin: "0 30px" }}
                  >
                    批量通过
                  </Button>
                  <Button type="danger" disabled={!hasSelected}>
                    批量删除
                  </Button>
                </div> */}
                {isShowPassBtn ? (
                  <SelectedAction
                    selectedRowKeys={selectedRowKeys}
                    btns={[
                      {
                        type: "primary",
                        text: "批量通过",
                        postUrl: "/sysApplicationRecord/batchAudit",
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
                dataSource={this.props.applylist.data}
                rowKey={(record) => record.id}
                pagination={{
                  current: this.state.params.current,
                  onChange: this.handleTableChange.bind(this),
                  total: this.props.applylist.total_record,
                }}
                rowSelection={isShowPassBtn ? rowSelection : undefined}
              />
            </div>
            <Modal
              title="领用申请"
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
          
            <Modal
                title="审批明细"
                centered
                visible={this.state.modalDetailVisible}
                style={{top:25}}
                footer={null}
                onCancel={() => {
                  this.setState({
                    modalDetailVisible: false,
                  });
                }}
              >
                <TodoDetailContent data={this.props.tododetail}/>
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
        },
      },
      () => {
        this.props.getapplylistMethod(this.state.params);
      }
    );
  }
  //驳回确认
  handleOkBack() {
    this.setState({
      modalVisible: false,
    });
    this.props.Auditapplymethod(this.state.currentId, {
      reason: this.state.backdescri,
      auditStatus: -1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getapplylistMethod(this.state.params);
    }, 1000);
  }
  //审核通过
  confirm = (record, e) => {
    this.props.Auditapplymethod(record.id, {
      auditStatus: 1,
      auditBy: JSON.parse(localStorage.getItem("userinfotoken")).id,
    });
    setTimeout(() => {
      this.props.getapplylistMethod(this.state.params);
    }, 1000);
  };
  cancel = (e) => {
    console.log(e);
  };
}
export default withRouter(ToDoApply);
