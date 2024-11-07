
import React, { Component } from 'react';
import Style from './Breadcrumb.less'
import {connect} from 'react-redux';
import { Breadcrumb } from 'antd';
@connect(
    state=>({
        initRouterReducer : state.initRouterReducer
    })
)
class BreadcrumbComponent extends Component {
    render() {
        return (
            <Breadcrumb className={Style.Breadcrumb_left}>
                    {/* <Breadcrumb.Item>{this.props.initRouterReducer.name}</Breadcrumb.Item> */}
        <Breadcrumb.Item>{this.props.title}</Breadcrumb.Item>
            </Breadcrumb>
         
        );
    }
}

export default BreadcrumbComponent;