import { GET_ASSET_NAME } from "../actionTypes";

export function getAssetNames(state = [], action) {
  switch (action.type) {
    case GET_ASSET_NAME:
      return action.data;
    default:
      return state;
  }
}
