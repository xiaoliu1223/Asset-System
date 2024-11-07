import React, { Component } from 'react';
import Style from '../../../common/upload.less';
import { Input, Button, message, Form, Tree } from 'antd';
import {connect} from "react-redux"
import {
    getruleeditMethod,
    ruleaddMethod
} from '../../../store/actions/assets/ruleAction'
@connect(
    state =>state,
    dispatch => ({
        getruleeditMethod: (id,data) => dispatch(getruleeditMethod(id,data)),
        ruleaddMethod: (id,data) => dispatch(ruleaddMethod(id,data)),
    })
  )
class AddRule extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        editdetail:null
    }
    componentDidMount(){
        if(this.props.location.state !== undefined){
            this.setState({
                editdetail : this.props.location.state.item
            })
        }
    }
    onSubmitType = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            // if(values.name == undefined){
            //     message.info('请输入角色名称');
            //     return;
            // }
            // if(values.description == undefined){
            //     message.info('请输入角色备注');
            //     return;
            // }
            if(this.props.location.state == undefined){
                this.props.ruleaddMethod(values)
            }else{
                this.props.getruleeditMethod(this.state.editdetail.id,values)
            }
            
        });
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
                        <Label title='角色名称' />
                        {getFieldDecorator('name',{ initialValue: this.state.editdetail == null? "":this.state.editdetail.name })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='角色备注' />
                        {getFieldDecorator('description',{ initialValue: this.state.editdetail == null? "":this.state.editdetail.description })(
                            <TextArea size='large' style={{ minHeight: '120px' }} />
                        )}

                    </div>

                    <div className={Style.submit}>
                        <Button size='large' style={{ width: '200px', marginRight: '60px' }} onClick={()=>{
                            this.props.history.goBack()
                        }}>返回</Button>
                        <Button size='large' style={{ width: '200px' }} type="primary" htmlType="submit">确定</Button>
                    </div>
                </Form>
            </div>
        );
    }
}

export default Form.create()(AddRule);