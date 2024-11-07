import { INIT_CURRENT_ROUTER } from '../actionTypes'
const initroute=(data)=>({
    type:INIT_CURRENT_ROUTER,
    data:data
})
let initRouterMethod = (params)=>{
    return (dispatch,getState)=>{
        const action = initroute(params)
        dispatch(action)
    }
}
export { initRouterMethod }




