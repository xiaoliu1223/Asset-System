import React, { Component } from "react";
import Style from "../../../common/upload.less";
import {
  Input,
  Button,
  Icon,
  Form,
  TreeSelect,
  message,
  Upload,
  Avatar,
} from "antd";
import { connect } from "react-redux";
import {
  useraddMethod,
  usereditMethod,
} from "./../../../store/actions/assets/userAction";
import { getorganizationListMethod } from "../../../store/actions/assets/organizationAction";
import Axios from "../../../common/axios";
const { TreeNode } = TreeSelect;
@connect(
  (state) => ({
    organizationlist: state.organizationlist,
  }),
  (dispatch) => ({
    getorganizationListMethod: () => dispatch(getorganizationListMethod()),
    useraddMethod: (data) => dispatch(useraddMethod(data)),
    usereditMethod: (id, data) => dispatch(usereditMethod(id, data)),
  })
)
class AddRule extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    createTime: 0,
    departName: "",
    departmentId: NaN,
    // avatar
    icon: null,
    id: NaN,
    index: NaN,
    nickname: null,
    tel: "",
    username: "",
    //树状结构
    expandedKeys: ["1"],
    autoExpandParent: true,
    checkId: "1",
    departmentId: 1,
    // 岗位id array
    postList: [],
    postId: "",
    // 角色ID array
    roleIds: [],
    roleList: [],
    // avatar
    fileList: [],
    uploading: false,
  };
  componentDidMount() {
    const That = this;
    this.props.getorganizationListMethod();
    Axios({
      url: "/sysPost/tree",
      method: "get",
    }).then((res) => {
      if (res.data.code == "000000") {
        That.setState({
          postList: res.data.data,
        });
      } else {
        alert("请求出错了");
      }
    });
    // request for roleList
    Axios({
      url: "/sysRole/allList",
      method: "get",
    }).then((res) => {
      if (res.data.code == "000000") {
        That.setState({
          roleList: res.data.data,
        });
      } else {
        alert("请求出错了");
      }
    });
    if (this.props.location.state !== undefined) {
      const {
        createTime,
        departName,
        departmentId,
        icon,
        id,
        index,
        nickname,
        tel,
        username,
        roleIds,
        postId,
      } = this.props.location.state.item;
      this.setState({
        createTime,
        departName,
        departmentId,
        roleIds,
        postId,
        icon,
        id,
        index,
        nickname,
        tel,
        username,
        editdetail: this.props.location.state.item,
        checkId: String(this.props.location.state.item.departmentId),
        expandedKeys: [String(this.props.location.state.item.departmentId)],
      });
    }
  }
  onSubmitType = (e) => {
    e.preventDefault();

    const telStr = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;

    this.props.form.validateFields((err, values) => {
      if (values.username == "") {
        message.info("请输入角色名称");
        return;
      }
      if (values.nickname == "") {
        message.info("请输入昵称");
        return;
      }
      if (values.tel == "") {
        message.info("请输入联系方式");
        return;
      }

      if (!telStr.test(values.tel)) {
        message.warn("手机号码输入不规范");
        return;
      }
      values.departmentId = this.state.departmentId;
      values.roleIds = this.state.roleIds;
      values.postId = this.state.postId;
      values.icon = this.state.icon;
      if (this.props.location.state == undefined) {
        this.props.useraddMethod(values);
      } else {
        this.props.usereditMethod(this.state.editdetail.id, values);
      }
    });
  };
  onExpand = (expandedKeys) => {
    this.setState({
      expandedKeys,
      autoExpandParent: false,
    });
  };
  onSelect = (selectedKeys, info) => {
    this.setState({
      checkId: selectedKeys[0],
    });
  };
  renderTreeNodes = (data) =>
    data.map((item, index) => {
      if (item.list && item.list.length !== 0) {
        return (
          <TreeNode title={item.name} key={item.id} value={item.id}>
            {this.renderTreeNodes(item.list)}
          </TreeNode>
        );
      }
      return <TreeNode title={item.name} key={item.id} value={item.id} />;
    });

  handleUpload = () => {
    const That = this;
    const { fileList } = this.state;
    const formData = new FormData();
    if (fileList.length > 1) {
      return message.warn("最多上传一张头像");
    }
    fileList.forEach((file) => {
      formData.append("file", file);
    });

    this.setState({
      uploading: true,
    });

    Axios({
      url: "/resource/upload",
      method: "post",
      data: formData,
    }).then((res) => {
      if (res.data.code == "000000") {
        That.setState({
          icon: res.data.data.url,
          fileList: [],
          uploading: false,
        });
        message.success("上传成功");
      } else {
        alert("请求出错了");
      }
    });
  };
  render() {
    const { uploading, fileList } = this.state;
    const props = {
      onRemove: (file) => {
        this.setState((state) => {
          const index = state.fileList.indexOf(file);
          const newFileList = state.fileList.slice();
          newFileList.splice(index, 1);
          return {
            fileList: newFileList,
          };
        });
      },
      beforeUpload: (file) => {
        this.setState((state) => ({
          fileList: [...state.fileList, file],
        }));
        return false;
      },
      fileList,
    };
    const Label = (event) => {
      return <span className={Style.sort_size}>{event.title}</span>;
    };
    const { getFieldDecorator } = this.props.form;
    const { errorMsg } = this.props;
    return (
      <div className={Style.content}>
        <Form
          className={Style.banner_add}
          onSubmit={this.onSubmitType.bind(this)}
        >
          <div className={Style.resort}>
            <Label title="用户名" />
            {getFieldDecorator("username", {
              initialValue: this.state.username,
            })(<Input />)}
          </div>
          <div className={Style.resort}>
            <Label title="用户昵称" />
            {getFieldDecorator("nickname", {
              initialValue: this.state.nickname,
            })(<Input />)}
          </div>
          <div className={Style.resort}>
            <Label title="联系方式" />
            {getFieldDecorator("tel", {
              initialValue: this.state.tel,
            })(<Input />)}
          </div>
          <div className={Style.resort}>
            <Label title="所属部门" />
            <div className={Style.resort_tree}>
              {/* <div className={Style.resort_tree_title}>
                <span>部门结构</span>
              </div> */}
              <div>
                {this.props.organizationlist.length == 0 ? null : (
                  <TreeSelect
                    value={this.state.departmentId}
                    dropdownStyle={{ maxHeight: 400 }}
                    placeholder="选择所属部门"
                    allowClear
                    // size='large'
                    treeDefaultExpandAll
                    onChange={(value) => this.setState({ departmentId: value })}
                  >
                    {this.renderTreeNodes(this.props.organizationlist)}
                  </TreeSelect>
                )}
              </div>
            </div>
          </div>
          <div className={Style.resort}>
            <Label title="所属岗位" />
            <div className={Style.resort_tree}>
              <div>
                {this.props.organizationlist.length == 0 ? null : (
                  <TreeSelect
                    value={this.state.postId}
                    dropdownStyle={{ maxHeight: 400 }}
                    placeholder="选择所属岗位"
                    allowClear
                    // size='large'
                    treeDefaultExpandAll
                    onChange={(value) => this.setState({ postId: value })}
                  >
                    {this.renderTreeNodes(this.state.postList)}
                  </TreeSelect>
                )}
              </div>
            </div>
          </div>
          <div className={Style.resort}>
            <Label title="角色（可多选）" />
            <div className={Style.resort_tree}>
              {/* <div className={Style.resort_tree_title}>
                <span>部门结构</span>
              </div> */}
              <div>
                {this.props.organizationlist.length == 0 ? null : (
                  <TreeSelect
                    value={this.state.roleIds}
                    dropdownStyle={{ maxHeight: 400 }}
                    placeholder="选择所属岗位"
                    allowClear
                    // size='large'
                    multiple
                    treeDefaultExpandAll
                    onChange={(value) => this.setState({ roleIds: value })}
                  >
                    {this.renderTreeNodes(this.state.roleList)}
                  </TreeSelect>
                )}
              </div>
            </div>
          </div>

          <div style={{ display: "flex", alignItems: "flex-start" }}>
            <Label title="角色头像" />
            <div>
              <Upload {...props}>
                <Button>
                  <Icon type="upload" /> 选择头像
                </Button>
              </Upload>
              <Button
                type="primary"
                onClick={this.handleUpload}
                disabled={fileList.length === 0}
                loading={uploading}
                style={{ marginTop: 16 }}
              >
                {uploading ? "上传中" : "开始上传"}
              </Button>
            </div>
            <div style={{ marginLeft: 52 }}>
              <Avatar size={100} src={this.state.icon} shape="square" />
            </div>
          </div>
          <div className={Style.submit}>
            <Button
              size="large"
              style={{ width: "200px", marginRight: "60px" }}
              onClick={() => {
                this.props.history.goBack();
              }}
            >
              返回
            </Button>
            <Button
              size="large"
              style={{ width: "200px" }}
              type="primary"
              htmlType="submit"
            >
              确定
            </Button>
          </div>
        </Form>
      </div>
    );
  }
}

export default Form.create()(AddRule);
