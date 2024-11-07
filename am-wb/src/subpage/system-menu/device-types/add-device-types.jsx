import React, { Component } from 'react';
import Style from '../../../common/upload.less';
import { Input, Button, message, Form, Tree, InputNumber, Select } from 'antd';
import { connect } from "react-redux";
const { Option } = Select;
@connect(
    state => ({
        fatherorginlistReducer: state.fatherorginlistReducer
    }),
    dispatch => ({
        // fatherorginlistnMethod: (data) => dispatch(fatherorginlistnMethod(data)),
    })
)
class AddOrganization extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        defaultValue:{
            name:'不限',
            id:'0'
        },
        editdetail:null,
    }
    componentDidMount(){
        // this.props.fatherorginlistnMethod()
        if(this.props.location.state !== undefined){
            this.setState({
                editdetail : this.props.location.state.item
            })
        }
      
    }
    changeFileType(value) {
        this.setState({
            optionValue: value
        })
    }
    onSubmitType = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
         
            if (values.name == undefined) {
                message.info('请输入组织名称');
                return;
            }
            this.props.addorganizationMethod(values)
        });
    }
    initialValue(){
       if(this.state.editdetail !== null){
           if(this.state.editdetail.pid == 0){
              return this.state.defaultValue.id
           }else{
            return this.state.editdetail.pid
           }
       }else{
            return this.state.defaultValue.id
       }
    }
    render() {
        const Label = (event) => {
            return <span className={Style.sort_size}>{event.title}</span>
        }
        const { TextArea } = Input;
        const FormItem = Form.Item;
        const { getFieldDecorator } = this.props.form;
        const { errorMsg } = this.props;
        return (
            <div className={Style.content}>
                <Form className={Style.banner_add} onSubmit={this.onSubmitType.bind(this)}>
                    <div className={Style.resort}>
                        <Label title='设备名称' />
                        {getFieldDecorator('name',{initialValue:this.state.editdetail==null?'':this.state.editdetail.name})(
                            <Input size='large' />
                        )}
                    </div>
                    {/* <div className={Style.resort}>
                            <Label title='上级菜单' />
                            {getFieldDecorator('pid', { initialValue: this.initialValue() })(
                                <Select
                                    style={{ width: '100%' }}
                                    showSearch
                                    size='large'
                                    onChange={this.changeFileType.bind(this)}
                                >
                                   <Option value="1">hdsa</Option>
                                   <Option value="2">hdsa</Option>
                                   <Option value="3">hdsa</Option>
                                   <Option value="4">hdsa</Option>
                                </Select>
                            )}

                        </div> */}
                    {/* {
                        this.props.fatherorginlistReducer == null ? null : <div className={Style.resort}>
                            <Label title='上级菜单' />
                            {getFieldDecorator('pid', { initialValue: this.initialValue() })(
                                <Select
                                    style={{ width: '100%' }}
                                    showSearch
                                    size='large'
                                    onChange={this.changeFileType.bind(this)}
                                >{
                                    [this.state.defaultValue].concat(this.props.fatherorginlistReducer).map((item, index) => {
                                            return <Option value={item.id} key={index}>{item.name}</Option>
                                        })
                                    }

                                </Select>
                            )}

                        </div>
                    } */}
                    <div className={Style.resort}>
                        <Label title='序号' />
                        {getFieldDecorator('sort', {initialValue:this.state.editdetail==null?'':this.state.editdetail.sort })(
                            <InputNumber size='large' min={0} style={{ width: '100%' }} />
                        )}
                    </div>

                    <div className={Style.submit}>
                        {/* <Button size='large' style={{ width: '200px', marginRight: '60px' }} onClick={() => {
                            this.props.history.goBack()
                        }}>返回</Button> */}
                        <Button size='large' style={{ width: '200px' }} type="primary" htmlType="submit">确定</Button>
                    </div>
                </Form>
            </div>
        );
    }
}

export default Form.create()(AddOrganization);