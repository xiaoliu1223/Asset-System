import axios from 'axios'
import history from '../../history'
import { 
    RETURN_LIST,
    RETURN_BACK,
    RETURN_CONFIRM,
    RETURN_DETAIL
 } from '../../actionTypes'
 import { message } from 'antd'
const returnlist = (data) => (
    {
        type: RETURN_LIST,
        data: data
    })
//资产归还列表
let getreturnlistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = returnlist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//撤回
const returnback = (data) => (
    {
        type: RETURN_BACK,
        data: data
    })
let getreturnbackMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/recall/"+values,
            method: 'get',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = returnback(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("撤回成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//确认归还
const returnconfirm = (data) => (
    {
        type: RETURN_CONFIRM,
        data: data
    })
let getreturnconfirmMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/back/"+values,
            method: 'get',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = returnconfirm(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("归还成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//审核详ing
const returndetail = (data) => (
    {
        type: RETURN_DETAIL,
        data: data
    })
let getreturndetailMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/audit/flow/"+values,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = returndetail(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export { 
    getreturnlistMethod,
    getreturnbackMethod,
    getreturnconfirmMethod,
    getreturndetailMethod
 };



