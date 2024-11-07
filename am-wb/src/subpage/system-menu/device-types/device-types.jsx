
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
import demoimg from '../../../static/images/Avatar.png'
const { Search } = Input;
@connect(
  state => ({
    // organizationListReducer: state.organizationListReducer
  }),
  dispatch => ({
    // organizationlistMethod: (data) => dispatch(organizationlistMethod(data)),
  })
)
class DeviceTypeList extends Component {
  constructor(props) {
    super(props)
  }
  componentDidMount() {
    // this.props.organizationlistMethod()
  }
  render() {
    const columns = [
      {
        title: '设备类型',
        dataIndex: 'name',
        key: 'name',
      },
      // {
      //   title: '描述',
      //   dataIndex: 'description',
      //   key: 'description',
      // },
      {
        title: '创建时间',
        render: record => {
          if(record.createTime !== null){
            return moment(record.createTime).format('YYYY-MM-DD HH:mm:ss')
          }
        }
      },
      {
        title: '操作',
        render: record => (
          <span>
            <Link to={{
              pathname: `/index/authority/add_device_types`,
              state: {
                item: record,
              }
            }}> <Icon type="form" /></Link>
            {/* 跳转到编辑页面 */}
            <Divider type="vertical" />
            <Popconfirm
              title="是否确定删除?"
              onConfirm={this.confirm.bind(this,record)}
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
    const data = [
      {
        key: 1,
        name: '电脑',
        age: 60,
        createTime: '123',
        address: 'New York No. 1 Lake Park',
      },
      {
        key: 2,
        name: '打印机',
        age: 32,
        createTime: '123',
        address: 'Sidney No. 1 Lake Park',
      },
    ];
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      },
      onSelect: (record, selected, selectedRows) => {
        console.log(record, selected, selectedRows);
      },
      onSelectAll: (selected, selectedRows, changeRows) => {
        console.log(selected, selectedRows, changeRows);
      },
    };
    return (
      <>
      {
        <div className={Style.banner}>
        <div className={Style.Breadcrumb}>
          <BreadcrumbComponent  title="设备类型"/>
          <Button type="primary" onClick={this.go_add_banner.bind(this)}>添加</Button>
        </div>
        <div className={Style.banner_list}>
          <Table columns={columns} rowSelection={rowSelection} dataSource={data}  rowKey={record => record.id}/>
        </div>
      </div>
      }
        {/* {
          this.props.organizationListReducer == null ? null : <div className={Style.banner}>
            <div className={Style.Breadcrumb}>
              <BreadcrumbComponent />
              <Button type="primary" onClick={this.go_add_banner.bind(this)}>添加</Button>
            </div>
            <div className={Style.banner_list}>
              <Table columns={columns} rowSelection={rowSelection} dataSource={this.props.organizationListReducer.datas}  rowKey={record => record.id}/>
              
            </div>
          </div>
        } */}
      </>




    )

  }
  confirm = (record,e) => {
    // this.props.deleorganizationMethod(record.id);
    setTimeout(() => {
      // this.props.organizationlistMethod();
    }, 1000);
  }

  cancel = (e) => {
    console.log(e);
    
  }
  // 跳转到添加页
  go_add_banner() {
    this.props.history.push({
      pathname: "/index/authority/add_device_types"
    })
  }
}

export default withRouter(DeviceTypeList);





