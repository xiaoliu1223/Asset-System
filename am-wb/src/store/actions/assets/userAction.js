import axios from 'axios'
import history from '../../history'
import { 
    USER_LIST,
    USER_DELE,
    USER_ADD,
    USER_EDIT
} from '../../actionTypes'
import { message } from 'antd'
const userlist = (data) => (
    {
        type: USER_LIST,
        data: data
    })
//用户列表
let getuserlistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysUser/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = userlist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//删除
const userdele= (data) => (
    {
        type: USER_DELE,
        data: data
    })
let userdeleMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysUser/"+values,
            method: 'delete',
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = userdele(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("删除成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//添加
const useradd= (data) => (
    {
        type: USER_ADD,
        data: data
    })
let useraddMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysUser/create",
            method: 'post',
            data:values
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = useradd(data);
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
const useredit= (data) => (
    {
        type: USER_EDIT,
        data: data
    })
let usereditMethod = (id,values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysUser/"+id,
            method: 'put',
            data:values
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = useredit(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("编辑成功",1)
                setTimeout(()=>{
                    history.goBack()
                },1000)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export { 
    getuserlistMethod,
    userdeleMethod,
    useraddMethod,
    usereditMethod
 };



