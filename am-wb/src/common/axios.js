//拦截器的配置
import Axios from "axios";
import store from "../store/store";
import history from "../store/history";
import { message } from "antd";
Axios.defaults.withCredentials = true;
Axios.defaults.baseURL = "http://127.0.0.1:8097";
// Axios.defaults.baseURL = "http://192.168.1.112:8097";
// Axios.defaults.baseURL="http://192.168.1.30:8097";
Axios.defaults.timeout = 100000;
Axios.interceptors.request.use(
  (config) => {
    if (localStorage.getItem("accessToken")) {
      config.headers.Authorization = localStorage.getItem("accessToken");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
Axios.interceptors.response.use(
  (response) => {
    if (response.data.code === "000000") {
    } else if (response.data.code === "200105") {
      history.replace({
        pathname: "/",
      });
    } else if (response.data.code === "400003") {
      // token 失效跳转到登录界面
      history.replace({
        pathname: "/login",
      });
    } else {
      message.info(response.data.message, 1);
    }
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);
export default Axios;
