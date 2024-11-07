import axios from 'axios'
import history from '../../history'
import { PURCHASE_LIST,PURCHASE_DELETE,PURCHASE_ADD,PURCHASE_EDIT,PURCHASE_DETAIL,PURCHASE_BACK } from '../../actionTypes'
import { message } from 'antd'
//列表
const getpurchaseList = (data) => (
    {
        type: PURCHASE_LIST,
        data: data,
    })
let getpurchaseListMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1;
                })
                const data = res.data.data;
                const action = getpurchaseList(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//删除
const delepurchase= (data) => (
    {
        type: PURCHASE_DELETE,
        data: data
    })
let delepurchaseMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysAssetType/"+values,
            method: 'delete',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = delepurchase(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("删除成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//添加
const addpurchase= (data) => (
    {
        type: PURCHASE_ADD,
        data: data
    })
let addpurchaseMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/create",
            method: 'post',
            data:values
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = addpurchase(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("添加成功",1)
                setTimeout(()=>{
                    history.goBack()
                },1000)
                
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//编辑
const editpurchase= (data) => (
    {
        type: PURCHASE_EDIT,
        data: data
    })
let editpurchaseMethod = (id,values) => {
    console.log(values)
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/"+id,
            method: 'put',
            data:values
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = editpurchase(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("编辑成功",1)
                setTimeout(()=>{
                    history.goBack()
                },1000)
                // history.goBack()
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//详情
const detailpurchase= (data) => (
    {
        type: PURCHASE_DETAIL,
        data: data
    })
let detailpurchaseMethod = (id) => {
    console.log(id)
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/audit/flow/"+id,
            method: 'get',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = detailpurchase(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//撤回
const backpurchase= (data) => (
    {
        type: PURCHASE_BACK,
        data: data
    })
let backpurchaseMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysApplicationRecord/undo/"+values,
            method: 'put',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = backpurchase(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("撤回成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export {    getpurchaseListMethod,
            delepurchaseMethod,
            addpurchaseMethod,
            editpurchaseMethod,
            detailpurchaseMethod,
            backpurchaseMethod,
    };



