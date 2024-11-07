import axios from "axios";
import { GET_ASSET_NAME } from "../actionTypes";

const getAssetNamess = (data) => ({
  type: GET_ASSET_NAME,
  data: data,
});
export const getAssetNamesMethod = () => {
  return (dispatch, getState) => {
    axios({
      url: "/sysAssetName/list",
      method: "get",
    })
      .then((res) => {
        if (res.data.code == "000000") {
          const data = res.data.data;

          const stringData = data.map((item) => item.name);

          const action = getAssetNamess(stringData);

          dispatch(action); //redux-thunk支持dispatch作为参数
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
