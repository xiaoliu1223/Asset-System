//账户创建与显示
import axios from 'axios'
import history from '../history'
import { CHECK_PASSWORD,CONFIRM_NEW_PASSWORD } from '../actionTypes'

//验证密码
const checkpassword = (data)=>({
    type:CHECK_PASSWORD,
    data:data
})
//输入新密码
const confromnewpassword = (data)=>({
    type:CONFIRM_NEW_PASSWORD,
    data:data
})

//验证密码
let checkpasswordMethod = (values,cb)=>{
    return (dispatch)=>{
        axios({
            url: "/user/checkPwd",
            method: 'GET',
            params: values
        }).then(res => {
            typeof cb == 'function' && cb(res.data.errorCode)
            if (res.data.code == '000000') {
                console.log(res.data)
                const data = res.data;
                const action = checkpassword(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//确认新密码
let confromnewpasswordMethod = (values,cb)=>{
    return (dispatch)=>{
        axios({
            url: "/sysUser/changePassword",
            method: 'put',
            params: values
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data;
                const action = confromnewpassword(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
                // setInterval(()=>{
                //     history.push({
                //         pathname:'/'
                //     })
                // },2000)
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
export {checkpasswordMethod,confromnewpasswordMethod};