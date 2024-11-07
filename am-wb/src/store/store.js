// 引入reducer
// import {applyMiddleware,createStore} from 'redux'
// import thunk from 'redux-thunk'
// // import * as reducers from './reducers'
// import reducers from './reducers'
// let store = createStore(
//   reducers,
//   applyMiddleware(thunk)
//   )
//   export default store
import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import reducer from './reducers'
//这一块是从redux-thunk的gitlab上复制的代码
  const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({}) : compose;
  const enhancer = composeEnhancers(applyMiddleware(thunk),);
  const store = createStore(
    reducer,
    enhancer
);
export default store;