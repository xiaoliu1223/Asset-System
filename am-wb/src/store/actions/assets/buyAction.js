import axios from 'axios'
import history from '../../history'
import { BUY_LIST,
    BUY_CONFIRM } from '../../actionTypes'
    import { message } from 'antd'
const buylist = (data) => (
    {
        type: BUY_LIST,
        data: data
    })
//获取采购列表
let getbuylistMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysPurchaseRecord/list",
            method: 'get',
            params: values,
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                res.data.data.data.forEach((ele,index)=>{
                    ele.index = index + 1
                })
                const data = res.data.data;
                const action = buylist(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//确认采购
const buyconfirm = (data) => (
    {
        type: BUY_CONFIRM,
        data: data
    })
let buyconfirmMethod = (id,values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysPurchaseRecord/confirm/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
                const data = res.data.data;
                const action = buyconfirm(data);
                dispatch(action);  
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

export { getbuylistMethod,buyconfirmMethod};



