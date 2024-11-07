
import { ORGANIZATION_LIST } from '../../actionTypes'
//组织列表
export function organizationlist(state = [],action){
    switch(action.type){
        case ORGANIZATION_LIST:
        return action.data
        default:
        return state
    }
}


