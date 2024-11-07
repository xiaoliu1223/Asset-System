import axios from "axios";
import history from "../../history";
import { ROLE_LIST, EDIT_RULE, DELETE_RULE, ADD_RULE } from "../../actionTypes";
import { message } from "antd";
const rulelist = data => ({
  type: ROLE_LIST,
  data: data
});
//角色列表
let getrulelistMethod = values => {
  return (dispatch, getState) => {
    axios({
      url: "/sysRole/list",
      method: "get",
      params: values
    })
      .then(res => {
        if (res.data.code == "000000") {
          res.data.data.data.forEach((ele, index) => {
            ele.index = index + 1;
          });
          const data = res.data.data;
          const action = rulelist(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
        }
      })
      .catch(err => {
        console.log(err);
      });
  };
};
//编辑
const ruleedit = data => ({
  type: EDIT_RULE,
  data: data
});
let getruleeditMethod = (id, values) => {
  return (dispatch, getState) => {
    axios({
      url: "/sysRole/" + id,
      method: "put",
      data: {
        // permissionIds:values
        name: values.name,
        description: values.description
      }
    })
      .then(res => {
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = ruleedit(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("授权成功", 1);
          setTimeout(() => {
            history.goBack();
          }, 1000);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };
};
//删除
const delerule = data => ({
  type: DELETE_RULE,
  data: data
});
let deleruleMethod = id => {
  return (dispatch, getState) => {
    axios({
      url: "/sysRole/" + id,
      method: "delete"
    })
      .then(res => {
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = delerule(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("删除成功", 1);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };
};
//添加
const ruleadd = data => ({
  type: ADD_RULE,
  data: data
});
let ruleaddMethod = values => {
  return (dispatch, getState) => {
    axios({
      url: "/sysRole/create",
      method: "post",
      data: values
    })
      .then(res => {
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = ruleadd(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("添加成功", 1);
          setTimeout(() => {
            history.goBack();
          }, 1000);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };
};
export { getrulelistMethod, getruleeditMethod, deleruleMethod, ruleaddMethod };
