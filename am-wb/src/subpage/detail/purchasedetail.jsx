import React from 'react'
import Style from './detail.less'
import moment from 'moment'
import { connect } from 'react-redux'
import { detailpurchaseMethod } from '../../store/actions/assets/purchaseAction'
@connect(
    state => ({
        purchasedetail: state.purchasedetail
    }),
    dispatch => ({
        detailpurchaseMethod: (data) => dispatch(detailpurchaseMethod(data)),
    })
  )
class Detail extends React.Component{
    componentDidMount(){
        this.props.detailpurchaseMethod(this.props.location.state.item.id)
    }
    render(){
        const detail = this.props.purchasedetail
        return(
            <div className={Style.detail}>
                <div className={Style.detail_item}>
                    <label>申请人</label>
                    <span>{detail.username}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>申请单位</label>
                    <span>{detail.departmentName}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>资产名称</label>
                    <span>{detail.assetName}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>资产类别</label>
                    <span>{detail.assetTypeName}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>规格型号</label>
                    <span>{detail.specification}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>申请数量</label>
                    <span>{detail.num}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>单位</label>
                    <span>{detail.units}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>预算单价</label>
                    <span>{detail.budgetPrice == null?"":detail.budgetPrice+"元" }</span>
                </div>
                <div className={Style.detail_item}>
                    <label>申请描述</label>
                    <span>{detail.description}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>申请时间</label>
                    <span>{moment(detail.createTime).format('YYYY-MM-DD')}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>申请状态</label>
                    <span>{detail.statusText}</span>
                </div>
                <div className={Style.detail_item}>
                    <label>审批流程</label>
                    <span>{detail.statusText}</span>
                </div>
            </div>
        )
    }
}
export default Detail;