import React from 'react';
import './App.css';
import 'antd/dist/antd.less'
import 'ant-design-icons/dist/anticons.min.css'
import { Router } from 'react-router-dom'
//import { BrowserRouter as Router } from 'react-router-dom'
// import { HashRouter as Router } from 'react-router-dom'
import RouterIndex from './router'
import './mock/login'
import history from './store/history'

function App() {
  return (
    <Router history={history}>
      <RouterIndex basename="/" />
    </Router>
  );
}

export default App;
