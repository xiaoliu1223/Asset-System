import axios from 'axios'
import history from '../history'
import * as ALLMethod  from '../../common/jcPrinterSdk_api'
import { 
    ATTRIBUTES_OPTION,
    PRINT_IMAGE,
    PRINT_EXCEL
 } from '../actionTypes'

 ALLMethod.getInstance();
const options = (data) => (
    {
        type: ATTRIBUTES_OPTION,
        data: data
    })
//型号类别
let getoptionsMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysAssetType/list",
            method: 'get',
            params: values,
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data.data;
                const action = options(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
               
            }
        }).catch(err => {
            console.log(err)
        })
    }
}


//打印条码
const printimage = (data) => (
    {
        type: PRINT_IMAGE,
        data: data
    })
//型号类别
let getprintimageMethod = (id,values) => {
    return (dispatch, getState) => {
        axios({
            url: "/printer/"+id,
            method: 'post',
            data: values,
        }).then(res => {
            console.log(res.data.data)
            if (res.data.code == '000000') {
              
                const data = res.data.data;  //imageBase64
               
                // ALLMethod.startJob(50, 50, 1, 1);
                // console.log('1116xxxxxxxxx');

                // ALLMethod.commitJob(1, 3, 1, res.data.data.imageBase64,0,0,70,70,0,0,0,0,0,function (data) {
                //     console.log('1116', data);
                // });

                ALLMethod.startJob(50, 50, 1, 1, function (data) {
                    var arrParse = JSON.parse(JSON.stringify(data));
                });
            
                ALLMethod.commitJob(1, 3, 1, res.data.data.imageBase64,0,0,70,70,0,0,0,0,0, function (data) {
                    var arrParse = JSON.parse(JSON.stringify(data));
                    var resultInfo = "commitJob ok";
                });

                const action = printimage(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

const printexcel = (data) => (
    {
        type: PRINT_EXCEL,
        data: data
    })
//型号类别
let getprintexcelMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/sysAsset/downloadExcel",
            method: 'get',
            params:{
                departmentIds:JSON.parse(localStorage.getItem("userinfotoken")).departmentId
            }
        }).then(res => {
           console.log(res)
            // let link = document.createElement('a')
            //         link.style.display = 'none'
            //         link.href = res.data
            //         link.setAttribute('download', `${res.data}文件.xlsx`)
            //         document.body.appendChild(link)
            //         link.click()
        }).catch(err => {
            console.log(err)
        })
    }
}


export { 
    getoptionsMethod,
    getprintimageMethod,
    getprintexcelMethod
 };



