
import { BUY_LIST } from '../../actionTypes'
//登录函数
export function buylist(state = {},action){
    switch(action.type){
        case BUY_LIST:
        return action.data
        default:
        return state
    }
}


