import axios from 'axios'
import history from '../../history'
import { ATTRIBUTES_LIST,ATTRIBUTES_DELETE,ATTRIBUTES_ADD,ATTRIBUTES_EDIT } from '../../actionTypes'
import { message } from 'antd'
//列表
const getAttributesList = (data) => (
    {
        type: ATTRIBUTES_LIST,
        data: data
    })
let getAttributesListMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysAssetType/treeData",
            method: 'get',
            params: values,
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                res.data.data.forEach((ele,index)=>{
                    ele.index = index + 1;
                    if(ele.list.length !== 0){
                        ele.list.forEach((item,j)=>{
                            item.index = j+1
                        })
                    }
                })
                const data = res.data.data;
                const action = getAttributesList(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//删除
const deleAttributes= (data) => (
    {
        type: ATTRIBUTES_DELETE,
        data: data
    })
let deleAttributesMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysAssetType/"+values,
            method: 'delete',
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = deleAttributes(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("删除成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//添加
const addAttributes= (data) => (
    {
        type: ATTRIBUTES_ADD,
        data: data
    })
let addAttributesMethod = (values) => {
    return (dispatch) => {
        axios({
            url: "/sysAssetType/create",
            method: 'post',
            data:values
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = addAttributes(data);
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
const editAttributes= (data) => (
    {
        type: ATTRIBUTES_EDIT,
        data: data
    })
let editAttributesMethod = (id,values) => {
    console.log(values)
    return (dispatch) => {
        axios({
            url: "/sysAssetType/"+id,
            method: 'put',
            data:values
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = editAttributes(data);
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
export { getAttributesListMethod,deleAttributesMethod,addAttributesMethod,editAttributesMethod};



