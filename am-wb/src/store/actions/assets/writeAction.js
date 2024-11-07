import axios from 'axios'
import history from '../../history'
import { 
    WRITE_LIST,
    WRITE_BACK,
    WRITE_CONFIRM,
    WRITE_DETAIL
 } from '../../actionTypes'
 import { message } from 'antd'
const writelist = (data) => (
    {
        type: WRITE_LIST,
        data: data
    })
//资产归还列表
let getwritelistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = writelist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//撤回
const writeback = (data) => (
    {
        type: WRITE_BACK,
        data: data
    })
let getwritebackMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/recall/"+values,
            method: 'get',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = writeback(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("撤回成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//核销
const writeconfirm = (data) => (
    {
        type: WRITE_CONFIRM,
        data: data
    })
let getwriteconfirmMethod = (id,values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/confirm/"+id,
            method: 'post',
            data:{
                scrapAmount:values
            }
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = writeconfirm(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("核销成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//审批详情WRITE_DETAIL
const writedetail = (data) => (
    {
        type: WRITE_DETAIL,
        data: data
    })
let getwritedetailMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/audit/flow/"+values,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = writedetail(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

export { 
    getwritelistMethod,
    getwritebackMethod,
    getwriteconfirmMethod,
    getwritedetailMethod };



