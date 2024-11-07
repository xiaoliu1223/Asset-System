
import { INIT_CURRENT_ROUTER } from '../actionTypes'
export function initRouterReducer(state =null,action ){
    switch(action.type){
        case INIT_CURRENT_ROUTER:
            return action.data
            default: return state
        }
    }
