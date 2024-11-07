
import { USER_LIST } from '../../actionTypes'
//登录函数
export function userlist(state = {},action){
    switch(action.type){
        case USER_LIST:
        return action.data
        default:
        return state
    }
}

