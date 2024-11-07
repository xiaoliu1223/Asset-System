import React, { Component } from 'react';
import Style from '../../common/upload.less';
import { Input, Button, message, Form, Tree, InputNumber, Select } from 'antd';
import { connect } from "react-redux";
const { Option } = Select;
@connect(
    state => ({

    }),
    dispatch => ({

    })
)
class AddAssetsCollection extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        item: {},
        orgvalue: '',
    }
    componentDidMount() {
        if(this.props.location.state !== undefined){
            this.setState({
                item: this.props.location.state.item
            })
        }
    }
    onSubmitType(e) {
        e.preventDefault();
        // this.props.form.validateFields((err, values) => {
        //     if (values.name == undefined) {
        //         message.info('请输入组织名称');
        //         return;
        //     }
        //     this.props.addorganizationMethod(values)
        // });
    }
    initialValue() {
        return this.state.item.org
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
        const { TextArea } = Input;
        const FormItem = Form.Item;
        const { getFieldDecorator } = this.props.form;
        const { errorMsg } = this.props;
        return (
            <div className={Style.content}>
                <Form className={Style.banner_add} onSubmit={this.onSubmitType.bind(this)}>
                    <div className={Style.resort}>
                        <Label title='领用人姓名' />
                        {getFieldDecorator('name', { initialValue: this.state.item.name })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='领用部门' />
                        {getFieldDecorator('pid', { initialValue: this.initialValue() })(
                            <Select
                                style={{ width: '100%' }}
                                showSearch
                                size='large'
                                onChange={this.changeFileType.bind(this)}
                            >
                                <Option value="技术部" >技术部</Option>
                                <Option value="行政部" >行政部</Option>
                                <Option value="总助办公室" >总助办公室</Option>
                                <Option value="工程部" >工程部</Option>
                                <Option value="成本部" >成本部</Option>
                                {/* [this.state.defaultValue].concat(this.props.fatherorginlistReducer).map((item, index) => {
                                             return <Option value={item.id} key={index}>{item.name}</Option>
                                        }) */}


                            </Select>
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='设备名称' />
                        {getFieldDecorator('devicename', { initialValue: this.state.item.devicename  })(
                            <Select
                                style={{ width: '100%' }}
                                showSearch
                                size='large'
                                onChange={this.changeFileType.bind(this)}
                            >
                                <Option value="技术部" >1233</Option>
                                <Option value="行政部" >2233</Option>
                                <Option value="总助办公室" >45565</Option>
                                <Option value="工程部" >678</Option>
                                <Option value="成本部" >8999</Option>
                                {/* [this.state.defaultValue].concat(this.props.fatherorginlistReducer).map((item, index) => {
                                             return <Option value={item.id} key={index}>{item.name}</Option>
                                        }) */}


                            </Select>
                        )}
                       
                    </div>
                    <div className={Style.resort}>
                        <Label title='设备类型' />
                        {getFieldDecorator('devicetype', { initialValue: this.state.item.devicetype })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='领用数量' />
                        {getFieldDecorator('devicetype', { initialValue: this.state.item.devicetype })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='领用描述' />
                        {getFieldDecorator('descri', { initialValue: this.state.item.descri })(
                            <Input size='large' maxLength={30}/>
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='领用日期' />
                        {getFieldDecorator('createTime', { initialValue: this.state.item.createTime })(
                            <Input size='large' />
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

export default Form.create()(AddAssetsCollection);