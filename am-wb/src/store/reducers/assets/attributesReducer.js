
import { ATTRIBUTES_LIST } from '../../actionTypes'
//登录函数

export function attributeslist(state = [],action){
    switch(action.type){
        case ATTRIBUTES_LIST:
        return action.data
        default:
        return state
    }
}


