
import { WRITE_LIST,WRITE_DETAIL } from '../../actionTypes'
//核销列表
export function writelist(state = {},action){
    switch(action.type){
        case WRITE_LIST:
        return action.data
        default:
        return state
    }
}
//审批详情
export function writedetail(state = [],action){
    switch(action.type){
        case WRITE_DETAIL:
        return action.data
        default:
        return state
    }
}


