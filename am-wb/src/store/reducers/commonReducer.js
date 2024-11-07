
import { ATTRIBUTES_OPTION,PRINT_IMAGE } from '../actionTypes'
//登录函数
export function attributesoption(state = [],action){
    switch(action.type){
        case ATTRIBUTES_OPTION:
        return action.data
        default:
        return state
    }
}
//打印条码
export function printimg(state = [],action){
    switch(action.type){
        case PRINT_IMAGE:
        return action.data
        default:
        return state
    }
}

