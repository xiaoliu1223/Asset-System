import axios from 'axios'
import history from '../history'
import { message } from 'antd'

import { 
    TODO_APPLY_LIST,
    AUDIT_APPLY,
    TODO_GET_LIST,
    AUDIT_GET,
    TODO_BACK_LIST,
    AUDIT_BACK,
    TODO_DELETE_LIST,
    AUDIT_DELETE,
    TODO_DETAIL,
    COMPLETE_DETAIL
 } from '../actionTypes'

//申请审批
const applylist = (data) => (
    {
        type: TODO_APPLY_LIST,
        data: data
    })
let getapplylistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysApplicationRecord/auditList",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                if(res.data.data.length !== 0){
                    res.data.data.data.forEach((ele,index)=>{
                        ele.index = index +1
                    })
                }
                const data = res.data.data;
                const action = applylist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                // message.info(res.data.message,1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//审核资产申请
//申请审批
const auditapply = (data) => (
    {
        type: AUDIT_APPLY,
        data: data
    })
let  Auditapplymethod = (id,values)=>{
    return (dispatch,getState)=>{
        axios({
            url: "/sysApplicationRecord/audit/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = auditapply(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("操作成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

// 领用审批
const getlist = (data) => (
    {
        type: TODO_GET_LIST,
        data: data
    })

let getlistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/auditList",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                if(res.data.data.length !== 0){
                    res.data.data.data.forEach((ele,index)=>{
                        ele.index = index +1
                    })
                }
                const data = res.data.data;
                const action = getlist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//申请审批
const auditget = (data) => (
    {
        type: AUDIT_GET,
        data: data
    })
let  auditgetmethod = (id,values)=>{
    return (dispatch,getState)=>{
        axios({
            url: "/sysReceiveRecord/audit/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = auditget(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("操作成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//归还审批
const backlist = (data) => (
    {
        type: TODO_BACK_LIST,
        data: data
    })

let getbacklistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysReturnRecord/auditList",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                if(res.data.data.length !== 0){
                    res.data.data.data.forEach((ele,index)=>{
                        ele.index = index +1
                    })
                }
                const data = res.data.data;
                const action = backlist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
const auditback = (data) => (
    {
        type: AUDIT_BACK,
        data: data
    })
let  auditbackmethod = (id,values)=>{
    return (dispatch,getState)=>{
        axios({
            url: "/sysReturnRecord/audit/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = auditback(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("操作成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
// 核销审批
const deletelist = (data) => (
    {
        type: TODO_DELETE_LIST,
        data: data
    })

let getdeletelistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/auditList",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                if(res.data.data.length !== 0){
                    res.data.data.data.forEach((ele,index)=>{
                        ele.index = index +1
                    })
                }
                const data = res.data.data;
                const action = deletelist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
const auditdelete = (data) => (
    {
        type: AUDIT_DELETE,
        data: data
    })
let  auditdeletemethod = (id,values)=>{
    return (dispatch,getState)=>{
        axios({
            url: "/sysChargeOffRecord/audit/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = auditdelete(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("操作成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
const tododetail = (data) => (
    {
        type: TODO_DETAIL,
        data: data
    })
let tododetailMethod = (relateJobNo) => {
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/getByRelateJobNo/"+relateJobNo,
            method: 'get',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = tododetail(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
const completedetail = (data) => (
    {
        type: COMPLETE_DETAIL,
        data: data
    })
let completedetailMethod = (id) => {
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/"+id,
            method: 'get',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = completedetail(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export { 
    getapplylistMethod,
    Auditapplymethod,
    getlistMethod,
    auditgetmethod,
    getbacklistMethod,
    auditbackmethod,
    getdeletelistMethod,
    auditdeletemethod,
    tododetailMethod,
    completedetailMethod
};



