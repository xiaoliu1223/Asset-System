import React, { Component } from 'react';
import Style from '../../common/upload.less';
import { getoptionsMethod } from '../../store/actions/commomAction'
import { Input, Button, message, Form, Tree, InputNumber, Select,DatePicker } from 'antd';
import moment from 'moment';
import { connect } from "react-redux";
import { buyconfirmMethod } from '../../store/actions/assets/buyAction'
const { Option } = Select;
@connect(
    state => ({
        attributesoption: state.attributesoption
    }),
    dispatch => ({
        getoptionsMethod: (data) => dispatch(getoptionsMethod(data)),
        buyconfirmMethod: (id,data) => dispatch(buyconfirmMethod(id,data)),
    })
)
class AddAssetsBuy extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        item: {},
        orgvalue: '',
        username: JSON.parse(localStorage.getItem("userinfotoken")).username

    }
    componentDidMount() {

        this.props.getoptionsMethod({
            current:1,
            limit:100000,
        })
        if(this.props.location.state !== undefined){
            this.setState({
                item: this.props.location.state.item
            })
        } 
    }
        //修改资产类别
        initialassetTypeValue(){
            if(Object.keys(this.state.item).length !== 0){
                 return this.state.item.assetType
            }else{
                 return  1
            }
        }
        //单位
        initialValue() {
            if(Object.keys(this.state.item).length !== 0){
                 return this.state.item.units
            }else{
                 return "个"
            }
        }
    onSubmitType(e) {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
                console.log(new Date(values.purchaseTime).getTime())
                values.purchaseTime = new Date(values.purchaseTime).getTime();
                values.confirmUserId = JSON.parse(localStorage.getItem('userinfotoken')).id;
            // if (values.name == undefined) {
            //     message.info('请输入组织名称');
            //     return;
            // }
           this.props.buyconfirmMethod(this.state.item.id,values)
        });
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
                        <Label title='采购人' />
                        {getFieldDecorator('username', { initialValue: this.state.username })(
                            <Input size='large' readOnly/>
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购部门' />
                        {getFieldDecorator('departmentName', { initialValue: "综合管理部" })(
                            <Input size='large' readOnly />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='资产名称' />
                        {getFieldDecorator('actualAssetName', { initialValue: Object.keys(this.state.item).length == 0 ?"" : this.state.item.assetName })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                    <Label title='资产类别' />
                        {getFieldDecorator('actualAssetType', { initialValue: this.initialassetTypeValue() })(
                            <Select
                                style={{ width: '100%' }}
                                showSearch
                                size='large'
                            >
                                {
                                    this.props.attributesoption.map((ele,index)=>{
                                    return  <Option value={ele.id} key={index}>{ele.name}</Option>
                                    })
                                }
                            </Select>
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购规格型号' />
                        {getFieldDecorator('actualSpecification', { initialValue: Object.keys(this.state.item).length == 0 ?"" : this.state.item.specification })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购数量' />
                        {getFieldDecorator('actualNum',{ initialValue: Object.keys(this.state.item).length == 0 ?"" : this.state.item.budgetNum })(
                           <InputNumber size='large' min={0} style={{ width: '100%' }} />
                        )}
                    </div>
                    
                    <div className={Style.resort}>
                        <Label title='采购单位' />
                        {getFieldDecorator('actualUnits', { 
                            initialValue: this.initialValue() 
                        })(
                            <Select
                                style={{ width: '100%' }}
                                showSearch
                                size='large'
                            >
                                {
                                     ['个',"批","组","张","本","支","台","桶","套"].map((ele,index)=>{
                                     return  <Option value={ele} key={index}>{ele}</Option>
                                     })
                                }
                            </Select>
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购单价' />
                        {getFieldDecorator('actualPrice', {initialValue:Object.keys(this.state.item).length == 0 ?"" :this.state.item.budgetPrice })(
                            <InputNumber size='large' min={0} style={{ width: '100%' }} />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购总额' />
                        {getFieldDecorator('actualTotalMount', {initialValue:Object.keys(this.state.item).length == 0 ?"" :this.state.item.totalAmount })(
                            <InputNumber size='large' min={0} style={{ width: '100%' }} />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购日期' />
                        {getFieldDecorator('purchaseTime',{initialValue: moment(new Date().toLocaleDateString(), 'YYYY/MM/DD')})(
                            <DatePicker placeholder="请选择采购日期" format={"YYYY/MM/DD"} style={{ width: '100%' }} size="large"/>
                            // <InputNumber size='large' min={0} style={{ width: '100%' }} />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='采购描述' />
                        {getFieldDecorator('buyDescription')(
                          <Input size='large' maxLength={30}/>
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

export default Form.create()(AddAssetsBuy);