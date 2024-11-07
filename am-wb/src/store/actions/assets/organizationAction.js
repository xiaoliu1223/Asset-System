import axios from "axios";
import history from "../../history";
import {
  ORGANIZATION_LIST,
  ORGANIZATION_ADD,
  ORGANIZATION_EDIT,
  ORGANIZATION_DELETE,
} from "../../actionTypes";
import { message } from "antd";
//列表
const getorganizationList = (data) => ({
  type: ORGANIZATION_LIST,
  data: data,
});
function allkey(array) {
  if (array.length !== 0) {
    array.forEach((ele, index) => {
      ele.index = index + 1;
      ele.children = ele.list;
      allkey(ele.list);
    });
  }
}
const getorganizationListMethod = () => {
  return (dispatch) => {
    axios({
      url: "/sysDepartment/treeData",
      method: "get",
    })
      .then((res) => {
        if (res.data.code == "000000") {
          allkey(res.data.data);
          const data = res.data.data;
          const action = getorganizationList(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
//删除列表
const deleOrganization = (data) => ({
  type: ORGANIZATION_DELETE,
  data: data,
});
let deleOrganizationMethod = (values) => {
  return (dispatch) => {
    axios({
      url: "/sysDepartment/" + values,
      method: "delete",
    })
      .then((res) => {
        console.log(res.data.data);
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = deleOrganization(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("删除成功", 1);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
//添加
const addOrganization = (data) => ({
  type: ORGANIZATION_ADD,
  data: data,
});
let addOrganizationMethod = (values) => {
  return (dispatch) => {
    axios({
      url: "/sysDepartment/create",
      method: "post",
      data: values,
    })
      .then((res) => {
        console.log(res.data.data);
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = addOrganization(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("添加成功", 1);
          setTimeout(() => {
            history.goBack();
          }, 1000);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
//编辑
const editOrganization = (data) => ({
  type: ORGANIZATION_EDIT,
  data: data,
});
let editOrganizationMethod = (id, values) => {
  console.log(values);
  return (dispatch) => {
    axios({
      url: "/sysDepartment/" + id,
      method: "put",
      data: values,
    })
      .then((res) => {
        console.log(res.data.data);
        if (res.data.code == "000000") {
          const data = res.data.data;
          const action = editOrganization(data);
          dispatch(action); //redux-thunk支持dispatch作为参数
          message.info("编辑成功", 1);
          setTimeout(() => {
            history.goBack();
          }, 1000);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
export {
  getorganizationListMethod,
  deleOrganizationMethod,
  addOrganizationMethod,
  editOrganizationMethod,
};
