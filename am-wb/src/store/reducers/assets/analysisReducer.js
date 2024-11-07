
import { 
    ANALYSIS_STATE,
    ANALYSIS_BUY,
    ANALYSIS_GET
 } from '../../actionTypes'
//状态分析
export function analysisstate(state = [],action){
    switch(action.type){
        case ANALYSIS_STATE:
        return action.data
        default:
        return state
    }
}
//采购分析
export function analysisbuy(state = [],action){
    switch(action.type){
        case ANALYSIS_BUY:
        return action.data
        default:
        return state
    }
}
//领用
export function analysisget(state = [],action){
    switch(action.type){
        case ANALYSIS_GET:
        return action.data
        default:
        return state
    }
}