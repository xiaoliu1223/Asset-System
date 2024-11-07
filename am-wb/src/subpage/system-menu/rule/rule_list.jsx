/**
 * 角色列表展示
 */
import React, { Component } from 'react'
import Style from '../../../common/list.less';
import BreadcrumbComponent from '../../../components/Breadcrumb/Breadcrumb'
import { Table, Select, Icon, Divider, Popconfirm, message, Button, Input } from 'antd';
import { Link, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import moment from 'moment'
import demoimg from '../../../static/images/Avatar.png'
import {
  getrulelistMethod,
  deleruleMethod
} from '../../../store/actions/assets/ruleAction'

const { Search } = Input;
const { Option } = Select;
@connect(
  state => ({
    rulelist: state.rulelist
  }),
  dispatch => ({
    getrulelistMethod: (data) => dispatch(getrulelistMethod(data)),
    deleruleMethod: (data) => dispatch(deleruleMethod(data))
  })
)
class RuleList extends Component {
  constructor(props) {
    super(props)
  }
  state = {
    params: {
      "current": 1,
      "limit": 10
    }
  }
  componentDidMount() {
    this.props.getrulelistMethod(this.state.params)
  }
  //点击搜索
  search_action(value) {
    this.props.getrulelistMethod({
      "current": 1,
      "limit": 10,
      'name':value.replace(/\s/g,"")
    })
  }
  render() {
    const columns = [
      {
        title: '序号',
        dataIndex: 'index',
        key: 'index',
      },
      {
        title: '角色名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '角色描述',
        dataIndex: 'description',
        key: 'description',
      },
      {
        title: '创建时间',
        render: record => {
          if(record){
            return moment(record.createTime).format('YYYY-MM-DD HH:mm:ss')
          }
        }
      },
      {
        title: '操作',
        render: record => (
          <span>
            <Link to={{
              pathname: `/index/add_rule`,
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
            <Divider type="vertical" />
            <Link to={{
              pathname: `/index/setting_rule`,
              state: {
                item: record,
              }
            }}> <Icon type="setting" /> </Link>
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
              <BreadcrumbComponent title="角色管理" />
              <div className={Style.formname}>
                <Button type="primary" onClick={this.go_add_rule.bind(this)}>添加</Button>
                <Search
                  placeholder="请输入角色名称"
                  onSearch={this.search_action.bind(this)}
                  style={{ width: 200, marginLeft: '30px' }}
                  enterButton
                />
              </div>
            </div>
            <div className={Style.banner_list}>
              <Table
                columns={columns}
                dataSource={this.props.rulelist.data}
                rowKey={record => record.id}
                pagination={{ current: this.state.params.current, onChange: this.handleTableChange.bind(this), total: this.props.rulelist.total_record }}
              />
            </div>
          </div>
        }

      </>

    )

  }
   //切换分页
   handleTableChange(current) {
    this.setState({
      params: {
        current: current
      }
    }, () => {
      this.props.getrulelistMethod(this.state.params)
    })
  }
  confirm = (record, e) => {
    this.props.deleruleMethod(record.id);
    setTimeout(() => {
       this.props.getrulelistMethod(this.state.params);
    }, 1000);
  }
  cancel = (e) => {
    console.log(e);
  }
  // 跳转到添加页
  go_add_rule() {
    this.props.history.push({
      pathname: "/index/add_rule"
    })
  }

}

export default withRouter(RuleList);

