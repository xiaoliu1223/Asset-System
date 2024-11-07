import { combineReducers } from "redux";
import { getUserInfo } from "./reducers/loginReducer";
import { initRouterReducer } from "./reducers/routerReducer";
import {
  nemuReducer,
  menulistReducer,
  addmenuReducer,
  editmenuReducer,
  auth,
} from "./reducers/menuReducer";
import { attributeslist } from "./reducers/assets/attributesReducer";
import { organizationlist } from "./reducers/assets/organizationReducer";
import {
  purchaselist,
  purchasedetail,
} from "./reducers/assets/purchaseReducer";
import { buylist } from "./reducers/assets/buyReducer";
import { warehousinglist } from "./reducers/assets/warehousingReducer";
import { returnlist, returndetail } from "./reducers/assets/returnReducer";
import { collectlist, collectdetail } from "./reducers/assets/collectReducer";
import { userlist } from "./reducers/assets/userReducer";
import { rulelist } from "./reducers/assets/ruleReducer";
import {
  analysisstate,
  analysisbuy,
  analysisget,
} from "./reducers/assets/analysisReducer";
import { attributesoption, printimg } from "./reducers/commonReducer";
import {
  applylist,
  getlist,
  backlist,
  deletelist,
  tododetail,
  completedetail
} from "./reducers/todoReducer";

import { writelist, writedetail } from "./reducers/assets/writeReducer";

import { getUnits } from "./reducers/unitsReducer";
import { getAssetNames } from "./reducers/assetNamesReducer";

export default combineReducers({
  auth,
  initRouterReducer,
  getUserInfo,
  nemuReducer,
  menulistReducer,
  addmenuReducer,
  editmenuReducer,
  attributeslist,
  organizationlist,
  purchaselist,
  purchasedetail,
  attributesoption,
  buylist,
  warehousinglist,
  returnlist,
  returndetail,
  collectlist,
  collectdetail,
  userlist,
  rulelist,
  analysisstate,
  analysisbuy,
  analysisget,
  applylist,
  getlist,
  backlist,
  deletelist,
  tododetail,
  completedetail,
  writelist,
  writedetail,
  printimg,
  getUnits,
  getAssetNames,
});
