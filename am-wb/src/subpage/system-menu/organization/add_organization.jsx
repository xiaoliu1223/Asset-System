import React, { Component } from 'react';
import Style from '../../../common/upload.less';
import { Input, Button, message, Form, TreeSelect, Select, Icon } from 'antd';
import { connect } from "react-redux";
import { editOrganizationMethod, addOrganizationMethod, getorganizationListMethod, } from '../../../store/actions/assets/organizationAction'
const { Option } = Select;
const { TreeNode } = TreeSelect;
@connect(
    state => ({
        organizationlist: state.organizationlist
    }),
    dispatch => ({
        addOrganizationMethod: (data) => dispatch(addOrganizationMethod(data)),
        editOrganizationMethod: (id, data) => dispatch(editOrganizationMethod(id, data)),
        getorganizationListMethod: (data) => dispatch(getorganizationListMethod(data)),
    })
)
class AddOrganization extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        defaultValue:[],
        item: {},
        orgvalue: '',
        checkId: '1',
    }
    componentDidMount() {
        this.props.getorganizationListMethod()
        console.log(this.props.location.state)
        if (this.props.location.state !== undefined) {
            this.setState({
                item: this.props.location.state.item,
                defaultValue:[ {
                    name: '无上级部门',
                    pid: 0,
                    id:0,
                    list:[],
                }]
            })
        }

    }
    onSelect = (selectedKeys, info) => {
        this.setState({
            checkId: selectedKeys[0]
        })
    };
    renderTreeNodes = data =>
        data.map((item, index) => {
            if (item.list.length !== 0) {
                return (
                    <TreeNode title={item.name} key={item.id} value={item.id}>
                        {this.renderTreeNodes(item.list)}
                    </TreeNode>
                );
            }
            return <TreeNode title={item.name} key={item.id} value={item.id} />;
        });
    onSubmitType = (e) => {
        e.preventDefault();
        if (Object.keys(this.state.item).length == 0) {
            this.props.form.validateFields((err, values) => {
                if (values.name == '') {
                    message.info('请输入资产类别名称');
                    return;
                }
                this.props.addOrganizationMethod(values)
            });
        } else {
            this.props.form.validateFields((err, values) => {
                if (values.name == '') {
                    message.info('请输入资产类别名称');
                    return;
                }
                this.props.editOrganizationMethod(this.state.item.id, values)
            });
        }
    }
    initialValue() {
        if (Object.keys(this.state.item).length !== 0) {
            if (this.state.item.pid == 0) {
                return this.state.defaultValue[0].id
            } else {
                return this.state.item.pid
            }
        }
        return 1
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
                        <Label title='部门名称' />
                        {getFieldDecorator('name', { initialValue: Object.keys(this.state.item).length == 0 ? "" : this.state.item.name })(
                            <Input size='large' />
                        )}
                    </div>
                    <div className={Style.resort}>
                        <Label title='上级部门' />
                        {/* {getFieldDecorator('pid', { initialValue: Object.keys(this.state.item).length == 0 ? "1" : this.state.item.pid })( */}
                        {getFieldDecorator('pid', { initialValue:this.initialValue() })(
                            <TreeSelect
                                style={{ width: '100%' }}
                                value={this.state.value}
                                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                                placeholder="请选择"
                                treeDefaultExpandAll
                                onChange={this.onChangeTreeNodes}
                            >
                                {
                                    this.renderTreeNodes(this.state.defaultValue.concat(this.props.organizationlist))
                                }
                            </TreeSelect>
                        )}

                    </div>
                    <div className={Style.resort}>
                        <Label title='组织描述' />
                        {getFieldDecorator('description', { initialValue: Object.keys(this.state.item).length == 0 ? "" : this.state.item.description })(
                            <Input size='large' maxLength={30}/>
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