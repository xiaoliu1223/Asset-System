import { GET_MENU, MENU_LIST, ADD_MENU, EDIT_MENU, AUTH } from '../actionTypes'
import axios from 'axios'
import '../../common/axios'
import history from '../history'
// 获取当前权限
const auth = (data) => ({
    type: AUTH,
    data: data
})
let authMethod = (values) => {
    return (dispatch, getState) => {
        const action = auth(values);
        dispatch(action);  //redux-thunk支持dispatch作为参数
    }
}
const menulist = (data) => ({
    type: GET_MENU,
    data: data
})
//左侧菜单列表,当前角色
let menulistMethod = (values) => {
    return (dispatch, getState) => {
        const data = JSON.parse(localStorage.getItem("userinfotoken")).permission
        const action = menulist(data);
        dispatch(action);  //redux-thunk支持dispatch作为参数
    }
}

//菜单管理列表
const menumanfer = (data) => ({
    type: MENU_LIST,
    data: data
})
function removechildren(data) {
    data.forEach((ele, index) => {
        if (ele.children.length == 0) {
            ele.children = null
        } else {
            removechildren(ele.children)
        }
    })
    return data;
}
//菜单管理列表,所有
let menumanferMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/permission/tree",
            method: 'get',
        }).then(res => {
            if (res.data.code == "000000") {
                removechildren(res.data.data)
                const data = res.data.data;
                const action = menumanfer(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
// 保存新增菜单
const addmenu = (data) => ({
    type: ADD_MENU,
    data: data
})
let addmenuMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/menu/",
            method: 'post',
            data: values,
        }).then(res => {
            if (res.data.code == "000000") {
                const data = res.data;
                const action = addmenu(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                history.goBack()
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
// 修改菜单
const editmenu = (data) => ({
    type: EDIT_MENU,
    data: data
})
let editmenuMethod = (id, values) => {
    return (dispatch, getState) => {
        axios({
            url: "/permission/" + id,
            method: 'put',
            data: values,
        }).then(res => {
            if (res.data.code == "000000") {
                const data = res.data;
                const action = editmenu(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                localStorage.clear();
                history.replace("/")
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//删除菜单
const deletemenu = (data) => ({
    type: EDIT_MENU,
    data: data
})
let deletemenuMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/permission/" + values,
            method: 'delete',
            data: values,
        }).then(res => {
            if (res.data.code == "000000") {
                const data = res.data;
                const action = deletemenu(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                localStorage.clear();
                history.replace("/")
            }
        }).catch(err => {
            console.log(err)
        })
    }
}


export { authMethod, menulistMethod, menumanferMethod, addmenuMethod, editmenuMethod, deletemenuMethod }




