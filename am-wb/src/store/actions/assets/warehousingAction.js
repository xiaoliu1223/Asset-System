import axios from 'axios'
import history from '../../history'
import { 
    WAREHOUSING_LIST,
    ADD_GET,
    ADD_DELETE,
    WAREHOUSING_ADD,
    WAREHOUSING_EDIT,
    // WAREHOUSING_DELETE
 } from '../../actionTypes'
 import { message } from 'antd'
//列表展示
const warehousinglist = (data) => (
    {
        type: WAREHOUSING_LIST,
        data: data
    })
let getwarehousinglistMethod = (values) => {
    console.log(values)
    return (dispatch, getState) => {
        axios({
            url: "/sysAsset/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = warehousinglist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//新增领用
const addget = (data) => (
    {
        type: ADD_GET,
        data: data
    })
let addgetMethod = (values) => {
    console.log(values);
    return (dispatch, getState) => {
        axios({
            url: "/sysReceiveRecord/create",
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = addget(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("新增成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//新增核销
const adddetele = (data) => (
    {
        type: ADD_DELETE,
        data: data
    })
let adddeteleMethod = (values) => {
    console.log(values)
    return (dispatch, getState) => {
        axios({
            url: "/sysChargeOffRecord/create",
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = adddetele(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                message.info("新增成功",1)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//添加库存
const addWarehousing = (data) => (
    {
        type: WAREHOUSING_ADD,
        data: data
    })
let addWarehousingMethod = (values) => {
    console.log(values)
    return (dispatch, getState) => {
        axios({
            url: "/sysAsset/create",
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = addWarehousing(data);
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
//WAREHOUSING_EDIT修改
const editWarehousing = (data) => (
    {
        type: WAREHOUSING_EDIT,
        data: data
    })
let editWarehousingMethod = (id,values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysAsset/"+id,
            method: 'put',
            data: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = editWarehousing(data);
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
//删除
// const deleteWarehousing = (data) => (
//     {
//         type: WAREHOUSING_DELETE,
//         data: data
//     })
// let deleteWarehousingMethod = (id,values) => {
//     return (dispatch, getState) => {
//         axios({
//             url: "/sysAsset/"+id,
//             method: 'put',
//             data: values,
//         }).then(res => {
//             if (res.data.code == '000000') {
//                 const data = res.data.data;
//                 const action = deleteWarehousing(data);
//                 dispatch(action);  //redux-thunk支持dispatch作为参数
//                 history.goBack();
//             }
//         }).catch(err => {
//             console.log(err)
//         })
//     }
// }
export { 
    getwarehousinglistMethod,
    addgetMethod,
    adddeteleMethod,
    addWarehousingMethod,
    editWarehousingMethod,
    // deleteWarehousingMethod
 };



