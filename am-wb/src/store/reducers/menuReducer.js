
import { GET_MENU, MENU_LIST, ADD_MENU, EDIT_MENU, DELETE_MENU,
    AUTH } from '../actionTypes'
//左侧菜单
export function nemuReducer(state = [], action) {
    switch (action.type) {
        case GET_MENU:
            return action.data
        default: return state
    }
}
//所有菜单列表
export function menulistReducer(state = [], action) {
    switch (action.type) {
        case MENU_LIST:
            return action.data
        default: return state
    }
}
//保存新增菜单
export function addmenuReducer(state = null, action) {
    switch (action.type) {
        case ADD_MENU:
            return action.data
        default: return state
    }
}
//修改菜单
export function editmenuReducer(state = null, action) {
    switch (action.type) {
        case EDIT_MENU:
            return action.data
        default: return state
    }
}
//修改DELETE_MENU
export function deletemenuReducer(state = null, action) {
    switch (action.type) {
        case DELETE_MENU:
            return action.data
        default: return state
    }
}
//获取当前权限
export function auth(state=[],action){
    switch (action.type) {
        case AUTH:
            return action.data
        default: return state
    }
}