import { GET_UNITS } from "../actionTypes";

export function getUnits(state = [], action) {
  switch (action.type) {
    case GET_UNITS:
      return action.data;
    default:
      return state;
  }
}
