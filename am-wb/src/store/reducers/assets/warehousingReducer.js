
import { WAREHOUSING_LIST } from '../../actionTypes'
//登录函数
export function warehousinglist(state = {},action){
    switch(action.type){
        case WAREHOUSING_LIST:
        return action.data
        default:
        return state
    }
}


