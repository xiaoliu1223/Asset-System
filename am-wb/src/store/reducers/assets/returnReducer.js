


import { 
    RETURN_LIST,
    RETURN_DETAIL
 } from '../../actionTypes'
//列表
export function returnlist(state = {},action){
    switch(action.type){
        case RETURN_LIST:
        return action.data
        default:
        return state
    }
}
//审核详情
export function returndetail(state = [],action){
    switch(action.type){
        case RETURN_DETAIL:
        return action.data
        default:
        return state
    }
}


