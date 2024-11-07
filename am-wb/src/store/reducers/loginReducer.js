
import { GET_USER_NAME } from '../actionTypes'
let initstate = {}
//登录函数
export function getUserInfo(state = initstate,action){
    switch(action.type){
        case GET_USER_NAME:
        return action.data
        default:
        return state
    }
}
//登出
// export function getUserOut(state = null,action){
//     switch(action.type){
//         case GET_USER_OUT:
//         return action
//         default:
//         return state
//     }
// }

