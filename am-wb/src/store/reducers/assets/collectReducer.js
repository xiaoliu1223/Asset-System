
import { COLLECT_LIST,COLLECT_DETAIL } from '../../actionTypes'
//登录函数
export function collectlist(state = {},action){
    switch(action.type){
        case COLLECT_LIST:
        return action.data
        default:
        return state
    }
}
//审核详情
export function collectdetail(state = [],action){
    switch(action.type){
        case COLLECT_DETAIL:
        return action.data
        default:
        return state
    }
}


