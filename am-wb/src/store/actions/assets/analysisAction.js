import axios from 'axios'
import { 
    ANALYSIS_STATE,
    ANALYSIS_BUY,
    ANALYSIS_GET
 } from '../../actionTypes'
const analysisstate = (data) => (
    {
        type: ANALYSIS_STATE,
        data: data
    })
//资产状态分析
let getanalysisstateMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/stat/assetStatus",
            method: 'get',
            params: {
                departmentIds : values
            },
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                if(res.data.data.length !== 0){
                    res.data.data.forEach((ele,index)=>{
                        ele.name = ele.departmentName;
                        ele.value = ele.inNum;
                    })
                }
                const action = analysisstate(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}
//采购分析
const analysisbuy = (data) => (
    {
        type: ANALYSIS_BUY,
        data: data
    })
//资产状态分析
let getanalysisbuyMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/stat/purchase",
            method: 'get',
            params: values
        }).then(res => {
            if (res.data.code == '000000') {
                const data = res.data.data;
                if(res.data.data.length !== 0){
                    res.data.data.forEach((ele,index)=>{
                        ele.name = ele.assetTypeName;
                        ele.value = ele.purchaseNum;
                    })
                }
                const action = analysisbuy(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

//领用分析
const analysisget = (data) => (
    {
        type: ANALYSIS_GET,
        data: data
    })
//资产领用分析
let getanalysisgetMethod = (values) => {
    return (dispatch, getState) => {
        axios({
            url: "/stat/receive",
            method: 'get',
            params: values
        }).then(res => {
            if (res.data.code == '000000') {
                if(res.data.data[0].sysAssetReceiveAnalysisList.length !== 0){
                    res.data.data[0].sysAssetReceiveAnalysisList.forEach((ele,index)=>{
                        ele.name = ele.departmentName;
                        ele.value = ele.receiveNum;
                    })
                }
                const data = res.data.data[0].sysAssetReceiveAnalysisList;
                const action = analysisget(data);
                dispatch(action);  //redux-thunk支持dispatch作为参数
            }
        }).catch(err => {
            console.log(err)
        })
    }
}

export { 
    getanalysisstateMethod,
    getanalysisbuyMethod,
    getanalysisgetMethod
 };



