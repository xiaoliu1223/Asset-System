import React from 'react'
import PureRenderMixin from 'react-addons-pure-render-mixin';
import Style from './statistic-analysis.less'
import moment from 'moment';
import { connect } from 'react-redux'
import { DatePicker, Select, TreeSelect } from 'antd';
import ReactEcharts from 'echarts-for-react'
import {
  getanalysisstateMethod,
  getanalysisbuyMethod,
  getanalysisgetMethod
} from '../../store/actions/assets/analysisAction'
import {
  getorganizationListMethod,
} from '../../store/actions/assets/organizationAction'
const { RangePicker } = DatePicker;
const { Option } = Select;
const { TreeNode } = TreeSelect;
@connect(
  state => ({
    analysisstate: state.analysisstate,
    analysisbuy: state.analysisbuy,
    analysisget: state.analysisget,
    organizationlist: state.organizationlist,
  }),
  dispatch => ({
    getorganizationListMethod: (data) => dispatch(getorganizationListMethod(data)),
    getanalysisstateMethod: (data) => dispatch(getanalysisstateMethod(data)),
    getanalysisbuyMethod: (data) => dispatch(getanalysisbuyMethod(data)),
    getanalysisgetMethod: (data) => dispatch(getanalysisgetMethod(data)),
  })

)
class StatisticAnalysis extends React.Component {
  constructor(props) {
    super(props);
    this.shouldComponentUpdate = PureRenderMixin.shouldComponentUpdate.bind(this);
  }
  state = {
    valuebuy: [],
    valueget: [],
    startTimeThree: 0,
    endTimeThree: 0,
    optononeId: 1,
    buyid: "1",
    getid: "1",
    threeId: "",
    assetType: '0',
    params: {},
    tableTitleone: [
      "部门", "在库数量 ", "出库数量", "报废数量"
    ],
    tableTitleTwo:[
      "类型", "采购数量 ", "采购金额"
    ],
    tableTitleTree:[
      "部门", "领用数量", "领用金额"
    ]
  };
  componentDidMount() {
    //获取所有部门
    this.props.getorganizationListMethod()
    //状态分析
    this.props.getanalysisstateMethod()
    //采购分析
    this.props.getanalysisbuyMethod()
    //资产领用
    this.props.getanalysisgetMethod()

  }
  //获取分析状态
  onChangeTreeNodes(value) {
    if (value.length == 0) {
      this.props.getanalysisstateMethod()
    } else {
      this.props.getanalysisstateMethod(value.join(","))
    }
  }

  getOptionOne() {
    return {
      title: {
        text: '资产状态分析',
        left: 'center'
      },
      legend: {
        bottom: 10,
        left: 'center',
        selectedMode:false,
        data: this.props.analysisstate.map((ele, index) => {
          return ele.name
        })
      },
      series: [
        {
          label: {
            position: 'inner',
            formatter: '{d}%'
          },
          type: 'pie',
          radius: '55%',
          center: ['50%', '35%'],
          selectedMode: 'single',
          data: this.props.analysisstate,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
  }

  getOptionTwo() {
    return {
      title: {
        text: '部门资产采购分析',
        left: 'center'
      },
      legend: {
        bottom: 10,
        selectedMode:false,
        left: 'center',
        data: this.props.analysisbuy.map((ele, index) => {
          return ele.name
        })
      },
      series: [
        {
          label: {
            position: 'inner',
            formatter: '{d}%'
          },
          type: 'pie',
          radius: '65%',
          center: ['50%', '50%'],
          selectedMode: 'single',
          data: this.props.analysisbuy,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
  }
  //采购更改时间
  handleChangebuy(value) {
    this.setState({
      valuebuy: value,
    }, () => {
      this.props.getanalysisbuyMethod({
        startTime: new Date(value[0]).getTime(),
        endTime: new Date(value[1]).getTime(),
      })
    });
  }
  //第三个饼图
  onChangeTreeNodesThree(value) {
    this.setState({
      threeId: value.join(",")
    }, () => {
      this.getinigetaxios()
    })
  }
  handleChangeget(value) {
    this.setState({
      valueget: value,
      startTimeThree: new Date(value[0]).getTime(),
      endTimeThree: new Date(value[1]).getTime(),
    }, () => {
      this.getinigetaxios()
    });
  }
  // //切换分类
  // handleChangeassetType(value) {
  //   this.setState({
  //     assetType: value
  //   }, () => {
  //     this.getinigetaxios()
  //   })
  // }
  getinigetaxios() {
    if (this.state.threeId == "") {
      this.props.getanalysisgetMethod({
        assetType: this.state.assetType,
        startTime: this.state.startTimeThree,
        endTime: this.state.endTimeThree,
      })
    } else {
      this.props.getanalysisgetMethod({
        assetType: this.state.assetType,
        departmentIds: this.state.threeId,
        startTime: this.state.startTimeThree,
        endTime: this.state.endTimeThree,
      })
    }
  }
  getOptionThree() {
    return {
      title: {
        text: '资产领用分析',
        left: 'center'
      },
      legend: {
        bottom: 10,
        left: 'center',
        selectedMode:false,
        data: this.props.analysisget.map((ele, index) => {
          return ele.name
        })
      },
      series: [
        {
          label: {
            position: 'inner',
            formatter: '{d}%'
          },
          type: 'pie',
          radius: '65%',
          center: ['50%', '50%'],
          selectedMode: 'single',
          data: this.props.analysisget,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
  }


  renderTreeNodes = data =>
    data.map((item, index) => {
      if (item.list.length !== 0) {
        return (
          <TreeNode title={item.name} key={item.id} value={item.id} >
            {this.renderTreeNodes(item.list)}
          </TreeNode>
        );
      }
      return <TreeNode title={item.name} key={item.id} value={item.id} />;
    });
  render() {
    return (
      <>
        <div className={Style.analysis}>
          <div className={Style.analysis_item}>
            <div className={Style.monthpicker}>
              <span style={{ marginRight: '30px' }}>选择部门</span>
              <TreeSelect
                treeCheckable
                allowClear
                style={{ width: 260, marginRight: 40 }}
                // value={this.state.departmentId}
                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                placeholder="请选择"
                treeDefaultExpandAll
                onChange={this.onChangeTreeNodes.bind(this)}
              >
                {
                  this.renderTreeNodes(this.props.organizationlist)
                }
              </TreeSelect>
            </div>
            <ReactEcharts option={this.getOptionOne()} style={{ height: '550px', width: "100%" }} />
            <div className={Style.table}>
              <div className={Style.table_title}>
                {
                  this.state.tableTitleone.map((item, index) => {
                    return <div className={Style.table_title_item} key={index}>{item}</div>
                  })
                }
              </div>
              {
                this.props.analysisstate.map((item, index) => {
                  return <div className={Style.table_title} key={index}>
                    <div className={Style.table_title_item}>{item.departmentName}</div>
                    <div className={Style.table_title_item}>{item.inNum}</div>
                    <div className={Style.table_title_item}>{item.outNum}</div>
                    <div className={Style.table_title_item}>{item.scrapNum}</div>
                  </div>
                })
              }
            </div>

          </div>
          <div className={Style.analysis_item}>
            <div className={Style.monthpicker}>
              <span style={{ marginRight: '30px' }}>月份</span>
              <RangePicker
                placeholder={['开始月份', '结束月份']}
                format="YYYY-MM"
                value={this.state.valuebuy}
                mode={['month', 'month']}
                onPanelChange={this.handleChangebuy.bind(this)}
              />
            </div>
            <ReactEcharts option={this.getOptionTwo()} style={{ height: '450px', width: "100%" }}  />
            <div className={Style.table}>
              <div className={Style.table_title}>
                {
                  this.state.tableTitleTwo.map((item, index) => {
                    return <div className={Style.table_title_item} key={index}>{item}</div>
                  })
                }
              </div>
              {
                this.props.analysisbuy.map((item, index) => {
                  return <div className={Style.table_title} key={index}>
                    <div className={Style.table_title_item}>{item.name}</div>
                    <div className={Style.table_title_item}>{item.value}</div>
                    <div className={Style.table_title_item}>{item.amount}</div>
                   
                  </div>
                })
              }
            </div>
            
          </div>
        </div>
        <div className={Style.analysis_item}>
          <div className={Style.monthpicker}>
            <span style={{ marginRight: '30px' }}>分类</span>
            <Select defaultValue={this.state.assetType} style={{ width: 160, marginRight: 40 }} >
              <Option value="0">固定 + 办公</Option>
              <Option value="1">固定资产</Option>
              <Option value="2">办公资产</Option>
            </Select>

            <span style={{ marginRight: '30px' }}>选择部门</span>
            <TreeSelect
              treeCheckable
              allowClear
              style={{ width: 220, marginRight: 40 }}
              // value={this.state.threeId}
              dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
              placeholder="请选择"
              treeDefaultExpandAll
              onChange={this.onChangeTreeNodesThree.bind(this)}
            >
              {
                this.renderTreeNodes(this.props.organizationlist)
              }
            </TreeSelect>
            <span style={{ marginRight: '30px' }}>月份</span>
            <RangePicker
              placeholder={['开始月份', '结束月份']}
              format="YYYY-MM"
              value={this.state.valueget}
              mode={['month', 'month']}
              onPanelChange={this.handleChangeget.bind(this)}
            />
          </div>
          <ReactEcharts option={this.getOptionThree()} style={{ height: '450px', width: "100%" }} />
          <div className={Style.table}>
              <div className={Style.table_title}>
                {
                  this.state.tableTitleTree.map((item, index) => {
                    return <div className={Style.table_title_item} key={index}>{item}</div>
                  })
                }
              </div>
              {
                this.props.analysisget.map((item, index) => {
                  return <div className={Style.table_title} key={index}>
                    <div className={Style.table_title_item}>{item.departmentName}</div>
                    <div className={Style.table_title_item}>{item.receiveNum}</div>
                    <div className={Style.table_title_item}>{item.amount}</div>
                  </div>
                })
              }
            </div>
        </div>
      </>

    )
  }
}
export default StatisticAnalysis;