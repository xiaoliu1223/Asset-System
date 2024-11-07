
import { 
    TODO_APPLY_LIST,
    TODO_GET_LIST,
    TODO_BACK_LIST,
    TODO_DELETE_LIST,
    TODO_DETAIL,
    COMPLETE_DETAIL
 } from '../actionTypes'
//登录函数
export function applylist(state = {},action){
    switch(action.type){
        case TODO_APPLY_LIST:
        return action.data
        default:
        return state
    }
}
export function getlist(state = {},action){
    switch(action.type){
        case TODO_GET_LIST:
        return action.data
        default:
        return state
    }
}
export function backlist(state = {},action){
    switch(action.type){
        case TODO_BACK_LIST:
        return action.data
        default:
        return state
    }
}
export function deletelist(state = {},action){
    switch(action.type){
        case TODO_DELETE_LIST:
        return action.data
        default:
        return state
    }
}
export function tododetail(state = [],action){
    switch(action.type){
        case TODO_DETAIL:
        return action.data
        default:
        return state
    }
}
export function completedetail(state = [],action){
    switch(action.type){
        case COMPLETE_DETAIL:
        return action.data
        default:
        return state
    }
}