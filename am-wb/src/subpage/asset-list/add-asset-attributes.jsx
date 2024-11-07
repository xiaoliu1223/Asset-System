import React, { Component } from 'react';
import Style from '../../common/upload.less';
import { Input, Button, message, Form, Tree, InputNumber, Select } from 'antd';
import { connect } from "react-redux";
import {addAttributesMethod,editAttributesMethod,getAttributesListMethod} from '../../store/actions/assets/attributesAtcion'
const { Option } = Select;
@connect(
    state => ({
        attributeslist: state.attributeslist
    }),
    dispatch => ({
        addAttributesMethod: (data) => dispatch(addAttributesMethod(data)),
        editAttributesMethod: (id,data) => dispatch(editAttributesMethod(id,data)),
        getAttributesListMethod: (data) => dispatch(getAttributesListMethod(data)),
    })
)
class AddAssetAttributes extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        item: {},
        orgvalue: '',
        defaultValue:{
            name:'无',
            id:'0'
        }     
    }
    componentDidMount() {
        this.props.getAttributesListMethod()
        if(this.props.location.state !== undefined){
            this.setState({
                item: this.props.location.state.item
            })
        } 
    }
    onSubmitType(e) {
        e.preventDefault();
        if(Object.keys(this.state.item).length == 0){
            this.props.form.validateFields((err, values) => {
                if (values.name == '') {
                    message.info('请输入资产类别名称');
                    return;
                }
                this.props.addAttributesMethod(values)
            });
        }else{
            this.props.form.validateFields((err, values) => {
                if (values.name == '') {
                    message.info('请输入资产类别名称');
                    return;
                }
                this.props.editAttributesMethod(this.state.item.id,values)
            });
        }
      
    }
    initialValue() {
        if(Object.keys(this.state.item).length !== 0){
            if(this.state.item.pid == 0){
               return 1
            }else{
             return this.state.item.pid
            }
        }else{
             return 1
        }
    }
    changeFileType(value) {
        this.setState({
            orgvalue: value
        })
    }

    render() {
        const Label = (event) => {
            return <span className={Style.sort_size}>{event.title}</span>
        }
        const { getFieldDecorator } = this.props.form;
        return (
            <div className={Style.content}>
                <Form className={Style.banner_add} onSubmit={this.onSubmitType.bind(this)}>
                    <div className={Style.resort}>
                    <Label title='资产类别名称' />
                            {getFieldDecorator('name', { rules: [{ required: true, message: '请输入资产类别名称' }],initialValue: Object.keys(this.state.item).length == 0 ? '' : this.state.item.name })(
                                <Input size='large' />
                            )} 
                    </div>
                    <div className={Style.resort}>
                        <Label title='上级类别名称' />
                        {getFieldDecorator('pid', { initialValue: this.initialValue()})(
                            <Select
                                style={{ width: '100%' }}
                                showSearch
                                size='large'
                                onChange={this.changeFileType.bind(this)}
                            >
                                {
                                    this.props.attributeslist.map((ele,index)=>{
                                        return  <Option value={ele.id} key={index}>{ele.name}</Option>
                                    })
                                }
                               
                            </Select>
                        )}
                    </div>
                    <div className={Style.submit}>
                        <Button size='large' style={{ width: '200px', marginRight: '60px' }} onClick={() => {
                            this.props.history.goBack()
                        }}>返回</Button>
                        <Button size='large' style={{ width: '200px' }} type="primary" htmlType="submit">确定</Button>
                    </div>
                </Form>
            </div>
        );
    }
}

export default Form.create()(AddAssetAttributes);