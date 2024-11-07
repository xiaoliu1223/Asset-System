import axios from "axios";
import { GET_UNITS } from "../actionTypes";

const getUnits = (data) => ({
  type: GET_UNITS,
  data: data,
});
export const getUnitssMethod = () => {
  return (dispatch, getState) => {
    axios({
      url: "/sysUnit/list",
      method: "get",
    })
      .then((res) => {
        if (res.data.code == "000000") {
          const data = res.data.data;

          const stringData = data.map((item) => item.name);

          const action = getUnits(stringData);
          
          dispatch(action); //redux-thunk支持dispatch作为参数
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
