
import { PURCHASE_LIST,PURCHASE_DETAIL } from '../../actionTypes'
//资产申请列表
export function purchaselist(state = {},action){
    switch(action.type){
        case PURCHASE_LIST:
        return action.data
        default:
        return state
    }
}
//获取详情
export function purchasedetail(state = [],action){
    switch(action.type){
        case PURCHASE_DETAIL:
        return action.data
        default:
        return state
    }
}


