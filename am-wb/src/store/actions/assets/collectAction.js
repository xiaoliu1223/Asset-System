import axios from 'axios'
import history from '../../history'
import { 
    COLLECT_LIST,
    COLLECT_BACK,
    COLLECT_CONFIRM,
    ADD_RETURN,
    COLLECT_DETAIL
 } from '../../actionTypes'
 import { message } from 'antd'
const collectlist = (data) => (
    {
        type: COLLECT_LIST,
        data: data
    })
//资产领用列表
let getcollectlistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = collectlist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//撤回
const collectback = (data) => (
    {
        type: COLLECT_BACK,
        data: data
    })
let getcollectbackMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/recall/"+values,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = collectback(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("撤回成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//确认领用
const collectconfirm = (data) => (
    {
        type: COLLECT_CONFIRM,
        data: data
    })
let getcollectconfirmMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/confirmReceive/"+values,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = collectconfirm(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("领用成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
// ADD_RETURN
//新增归还
const addreturn = (data) => (
    {
        type: ADD_RETURN,
        data: data
    })
let addreturnMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/create",
            method: 'post',
            data:values
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = addreturn(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("添加成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//审核详ing
const collectdetail = (data) => (
    {
        type: COLLECT_DETAIL,
        data: data
    })
let getcollectdetailMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/audit/flow/"+values,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = collectdetail(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export { 
    getcollectlistMethod,
    getcollectbackMethod,
    getcollectconfirmMethod,
    addreturnMethod,
    getcollectdetailMethod
 };



