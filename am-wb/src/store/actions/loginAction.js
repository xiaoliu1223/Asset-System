import axios from "axios";
import history from "../history";
import { GET_USER_NAME } from "../actionTypes";
import { message } from "antd";
const Base64 = require("js-base64").Base64;

const getTableList = (data) => ({
  type: GET_USER_NAME,
  data: data,
});

const setCookie = (cName, value, expiredays) => {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + expiredays);
  document.cookie =
    cName +
    "=" +
    decodeURIComponent(value) +
    (expiredays == null ? "" : ";expires=" + exdate.toGMTString());
};
//请求登录
let getusernameMethod = (values, remember) => {
  return (dispatch, getState) => {
    axios({
      url: "/login",
      method: "post",
      data: values,
    })
      .then((res) => {
        if (res.data.code == "000000") {
          if (remember) {
            setCookie("certificate", values.certificate);
            // base64加密密码
            let passWord = Base64.encode(values.password);
            setCookie("password", passWord);
            setCookie("remember", true);
          } else {
            setCookie("certificate", "");
            setCookie("password", "");
            setCookie("remember", false);
          }

          const data = res.data.data;
          localStorage.setItem("accessToken", res.data.data.token);
          localStorage.setItem("userinfotoken", JSON.stringify(res.data.data));
          localStorage.setItem("avatar", res.data.data.icon);

          localStorage.setItem("departmentId", res.data.data.departmentId);

          const action = getTableList(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("登录成功", 1);
          setTimeout(() => {
            history.push({
              pathname: "/index",
            });
          }, 1000);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};

export { getusernameMethod };
