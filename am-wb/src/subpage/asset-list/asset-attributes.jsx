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
  Divider,
  Popconfirm,
  message,
  Button,
  Input,
} from "antd";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import moment from "moment";
import {
  getAttributesListMethod,
  deleAttributesMethod,
} from "../../store/actions/assets/attributesAtcion";

const { Search } = Input;
const { Option } = Select;
@connect(
  (state) => ({
    attributeslist: state.attributeslist,
  }),
  (dispatch) => ({
    getAttributesListMethod: (data) => dispatch(getAttributesListMethod(data)),
    deleAttributesMethod: (data) => dispatch(deleAttributesMethod(data)),
  })
)
class AssetAttributes extends Component {
  constructor(props) {
    super(props);
  }
  componentDidMount() {
    this.props.getAttributesListMethod();
  }

  render() {
    const columns = [
      {
        title: "序号",
        dataIndex: "index",
        key: "index",
      },
      {
        title: "资产类别",
        dataIndex: "name",
        key: "name",
      },
      {
        title: "操作",
        render: (record) => (
          <>
            <Link
              to={{
                pathname: `/index/authority/add-asset-attribute`,
                state: {
                  item: record,
                },
              }}
            >
              <Icon type="form" />
            </Link>
            <Divider type="vertical" />
            <Popconfirm
              title="是否确定删除?"
              onConfirm={this.confirm.bind(this, record)}
              okText="是"
              cancelText="否"
            >
              <Icon type="delete" />
            </Popconfirm>
          </>
        ),
      },
    ];

    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="资产类别" />
              <div className={Style.formname}>
                <Button
                  type="primary"
                  onClick={this.go_add_rule.bind(this)}
                  style={{ marginRight: "30px" }}
                >
                  添加
                </Button>
                {/* <div style={{height:'30px',marginLeft: '30px'}}>
                    <Search
                      placeholder="请输入账号名称"
                      onSearch={this.search_action.bind(this)}
                      style={{ width: 200,height:'100%' }}
                    />
                  </div> */}
              </div>
            </div>
            <div className={Style.banner_list}>
              <Table
                pagination={false}
                columns={columns}
                dataSource={this.props.attributeslist}
                rowKey={(record) => record.id}
                expandedRowRender={(record) => {
                  return (
                    <Table
                      columns={columns}
                      dataSource={record.list}
                      rowKey={(record) => record.id}
                      pagination={false}
                    />
                  );
                }}
                // pagination={{ current: this.state.params.current,onChange:this.handleTableChange.bind(this),total:this.props.attributeslist.total_record}}
              />
            </div>
          </div>
        }
      </>
    );
  }
  confirm = (record, e) => {
    this.props.deleAttributesMethod(record.id);
    setTimeout(() => {
      this.props.getAttributesListMethod();
    }, 1000);
  };
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/authority/add-asset-attribute",
    });
  }
}
export default withRouter(AssetAttributes);
