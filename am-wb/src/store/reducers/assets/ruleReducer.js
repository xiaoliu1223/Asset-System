
import { ROLE_LIST } from '../../actionTypes'
//登录函数

export function rulelist(state = {},action){
    switch(action.type){
        case ROLE_LIST:
        return action.data
        default:
        return state
    }
}


