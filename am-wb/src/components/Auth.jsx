import React from 'react';
import PropTypes from 'prop-types'
/**
 * 校验当前用户是否有功能编码对应的权限
 * @param {string} id
 */

export function checkAuth(id,pid) {
  if (id&&pid) {
    let functionsList;
    let newarray;
    let array;
    if (localStorage.getItem("userinfotoken")) {
        functionsList = [].concat.apply([],JSON.parse(localStorage.getItem("userinfotoken")).permission.map((ele,index)=>{
          return ele.children
        }));
        newarray = functionsList.map((ele,index)=>{
          return ele.children
        })
        newarray = [].concat.apply([],newarray)
        array = newarray.filter((item) => {
          return item.id == id && item.pid == pid
        })
        if(array.length !== 0){
          return true
        }
        return false
    } else {
      return false;
    }
    //这边有一个菜单ID-主要是为了兼容复用同一个组件情况
  } else {
    // console.log(false)
    return false;
  }
 }

/**
 * 权限组件封装
 */
export class AuthWrapper extends React.Component {
  render() {
    return checkAuth(this.props.id,this.props.pid) && this.props.children;
  }
}

AuthWrapper.propTypes = {
  id: PropTypes.string,
  pid: PropTypes.string
}