import 'babel-polyfill';
import React from 'react';
import ReactDOM from 'react-dom';
import './index.less';
import { Provider } from 'react-redux';
import App from './App';
import store from './store/store';
import './mock/login'
import { ConfigProvider } from 'antd'
import zhCN from 'antd/es/locale/zh_CN';
import moment from 'moment'
import 'moment/locale/zh-cn'
import * as serviceWorker from './serviceWorker';
moment.locale('zh-cn')
ReactDOM.render(
    <ConfigProvider locale={zhCN}>
         <Provider store={store}>
                <App />
        </Provider> 
    </ConfigProvider>, document.getElementById('root'));
    
   
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
