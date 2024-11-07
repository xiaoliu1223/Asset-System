import React, { Component } from 'react';
import Style from '../../../common/upload.less';
import { Select, Input, Button, Upload, Icon, message, Tree, Modal, Form } from 'antd';
import { connect } from 'react-redux'
import { editmenuMethod } from '../../../store/actions/menuAction'
const { Option } = Select;
const { Dragger } = Upload;
const { TreeNode } = Tree;
const IconList = [
    {
        id: '1',
        name: 'step-backward'
    }, {
        id: '2',
        name: 'step-forward'
    }, , {
        id: '3',
        name: 'fast-backward'
    }, {
        id: '4',
        name: 'fast-forward'
    }, {
        id: '5',
        name: 'shrink'
    }, {
        id: '6',
        name: 'arrows-alt'
    }, {
        id: '7',
        name: 'down'
    }, {
        id: '8',
        name: 'up'
    }, {
        id: '9',
        name: 'left'
    }, {
        id: '10',
        name: 'right'
    }, {
        id: '11',
        name: 'caret-up'
    }, {
        id: '12',
        name: 'caret-down'
    }
]


@connect(
    state => ({
        menulistReducer: state.menulistReducer
    }),
    dispatch => ({

        editmenuMethod: (id,data) => dispatch(editmenuMethod(id,data))
    })
)
class AddMenu extends Component {
    constructor(props) {
        super(props);
        this.onSubmitType = this.onSubmitType.bind(this);
    }
    state = {
        defaultValue: {
            name: '不限',
            id: '0'
        },
        pid: '', //上级菜单
        icon: '',
        editdetail: null,

    }
    componentDidMount() {
        if (this.props.location.state !== undefined) {
            console.log(this.props.location.state.item)
            this.setState({
                editdetail: this.props.location.state.item
            })
        }

    }

    changeFileType(value) {
        this.setState({
            pid: value
        })
    }
    changeFileTypeicon(value) {
        this.setState({
            icon: value
        })
    }
    changeFileTypetype(value) {
        this.setState({
            type: value
        })
    }
    initialValue() {
        if (this.state.editdetail !== null) {
            if (this.state.editdetail.pid == 0) {
                return this.state.defaultValue.id
            } else {
                return this.state.editdetail.pid
            }
        }
    }
    onSubmitType = (e) => {
        e.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (values.name == "") {
                message.info('请输入菜单名称');
                return;
            }
            this.props.editmenuMethod(this.state.editdetail.id,values)
        }
        );
    }
    render() {
        const Label = (event) => {
            return <span className={Style.sort_size}>{event.title}</span>
        }
        const { getFieldDecorator } = this.props.form;
        return (
            <>
                {this.props.menulistReducer == null ? null : <div className={Style.content}>
                    <Form className={Style.banner_add} onSubmit={this.onSubmitType.bind(this)}>
                        <div className={Style.resort}>
                            <Label title='菜单名称' />
                            {getFieldDecorator('name', { initialValue: this.state.editdetail == null ? '' : this.state.editdetail.name })(
                                <Input size='large' />
                            )}
                        </div>
                        <div className={Style.resort}>
                            <Label title='图标' />
                            {getFieldDecorator('icon', { initialValue: this.state.editdetail == null ? IconList[0].name : this.state.editdetail.icon })(
                                <Select
                                    style={{ width: '100%' }}
                                    showSearch
                                    size='large'
                                    onChange={this.changeFileTypeicon.bind(this)}
                                >{
                                        IconList.map((item, index) => {
                                            return <Option value={item.name} key={index}>{item.name}</Option>
                                        })
                                    }

                                </Select>
                            )}
                        </div>
                        <div className={Style.resort}>
                            <Label title='链接' />
                            {getFieldDecorator('path', { initialValue: this.state.editdetail == null ? '' : this.state.editdetail.path })(
                                <Input size='large' placeholder="pages/user/userList.html" readOnly />
                            )}
                        </div>
                        <div className={Style.submit}>
                            <Button size='large' style={{ width: '200px', marginRight: '60px' }} onClick={() => { this.props.history.goBack() }}>返回</Button>
                            <Button size='large' style={{ width: '200px' }} type="primary" htmlType="submit">确定</Button>
                        </div>
                    </Form>
                </div>}
            </>

        );
    }
}

export default Form.create()(AddMenu);