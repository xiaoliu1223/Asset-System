
/**
 * 菜单列表展示
 */
import React, { Component } from 'react'
import Style from '../../../common/list.less';
import BreadcrumbComponent from '../../../components/Breadcrumb/Breadcrumb'
import { Table, Tag, Icon, Divider, Popconfirm, message, Button, Input } from 'antd';
import { Link, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { menumanferMethod,deletemenuMethod } from '../../../store/actions/menuAction'
import demoimg from '../../../static/images/Avatar.png'
const { Search } = Input;
@connect(
  state => ({
    menulistReducer: state.menulistReducer
  }),
  dispatch => ({
    menumanferMethod: (data) => dispatch(menumanferMethod(data)),
    deletemenuMethod: (data) => dispatch(deletemenuMethod(data)),
  })
)
class MenuList extends Component {
  constructor(props) {
    super(props)
  }
  state = {
    params: {
      current: 1,
      limit: 10
    }
  }
  componentDidMount() {
    this.props.menumanferMethod(this.state.params);
  }
  render() {
    const columns = [
      {
        title: '菜单名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'id',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '路径',
        dataIndex: 'path',
        key: 'path',
      },
      {
        title: '图标',
        dataIndex: 'icon',
        key: 'icon'
      },
      {
        title: '操作',
        render: record => (
          <span>
            <Link to={{
              pathname: '/index/add_menu',
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
          </span>
        ),
      },
    ];
    return (
      <div className={Style.banner}>
        <div className={Style.Breadcrumb}>
          <BreadcrumbComponent />
        </div>
        <div className={Style.banner_list}>
            <Table columns={columns}  dataSource={this.props.menulistReducer} rowKey={record => record.id} />
        </div>
      </div>
    )

  }
  confirm = (record,e) => {
    this.props.deletemenuMethod(record.id);
    setTimeout(() => {
      this.props.menumanferMethod(this.state.params);
    }, 1000);
  }

  cancel = (e) => {
    console.log(e);
    
  }
  // 跳转到添加页
  // go_add_banner(item) {
  //   this.props.history.push({
  //     pathname: "/index/add_menu",
  //     state:{
  //       item:item
  //     }
  //   })
  // }
}

export default withRouter(MenuList);

