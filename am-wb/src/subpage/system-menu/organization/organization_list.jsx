
/**
 * 组织列表展示
 */
import React, { Component } from 'react'
import Style from '../../../common/list.less';
import BreadcrumbComponent from '../../../components/Breadcrumb/Breadcrumb'
import { Table, Tag, Icon, Divider, Popconfirm, message, Button, Input } from 'antd';
import { Link, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import moment from 'moment'
import {
  getorganizationListMethod,
  deleOrganizationMethod,
} from '../../../store/actions/assets/organizationAction'
const { Search } = Input;
@connect(
  state => ({
    organizationlist: state.organizationlist
  }),
  dispatch => ({
    getorganizationListMethod: (data) => dispatch(getorganizationListMethod(data)),
    deleOrganizationMethod: (data) => dispatch(deleOrganizationMethod(data))
  })
)
class OrganizationList extends Component {
  constructor(props) {
    super(props)
    this.state = {
      params: {
        current: 1
      },

    }
  }
  componentDidMount() {
    this.props.getorganizationListMethod(this.state.params)
  }
 
  render() {
    const columns = [
      {
        title: '序号',
        dataIndex: 'index',
        key: 'index',
      },
      {
        title: '部门名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '部门描述',
        dataIndex: 'description',
        key: 'description',
      },
      {
        title: '操作',
        render: record => (
          <span>
            <Link to={{
              pathname: `/index/add_organization`,
              state: {
                item: record,
              }
            }}> <Icon type="form" /></Link>
            {/* 跳转到编辑页面 */}
            <Divider type="vertical" />
            <Popconfirm
              title="是否确定删除?"
              onConfirm={this.confirm.bind(this, record)}
              onCancel={this.cancel}
              okText="是"
              cancelText="否"
            >
              <Icon type="delete" />
            </Popconfirm>
            {/* 设置权限 */}
          </span>
        ),
      },
    ];

    return (
      <>
        {
          <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent title="部门管理" />
              <div className={Style.formname}>
                <Button type="primary" onClick={this.go_add_banner.bind(this)}>添加</Button>
              </div>
            </div>
            <div className={Style.banner_list}>
              <Table columns={columns} dataSource={this.props.organizationlist} pagination={false} rowKey={record => record.id}/>
            </div>
          </div>
        }
      </>
    )
  }
  handleTableChange(current) {
    this.setState({
      params: {
        current: current
      }
    }, () => {
      this.props.getorganizationListMethod(this.state.params)
    })
  }
  confirm = (record, e) => {
    this.props.deleOrganizationMethod(record.id);
    setTimeout(() => {
      this.props.getorganizationListMethod(this.state.params);
    }, 1000);
  }

  // 跳转到添加页
  go_add_banner() {
    this.props.history.push({
      pathname: "/index/add_organization"
    })
  }
}

export default withRouter(OrganizationList);





