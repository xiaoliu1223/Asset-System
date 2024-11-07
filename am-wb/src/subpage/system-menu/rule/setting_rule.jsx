import React, { Component } from "react";
import Style from "../../../common/upload.less";
import { Select, Input, Button, Upload, Icon, message, Tree } from "antd";
import { connect } from "react-redux";
import { menumanferMethod } from "../../../store/actions/menuAction";
import { getruleeditMethod } from "../../../store/actions/assets/ruleAction";
const { Option } = Select;
const { Dragger } = Upload;
const { TreeNode } = Tree;
@connect(
  (state) => ({
    // nemuReducer: state.nemuReducer,
    menulistReducer: state.menulistReducer,
  }),
  (dispatch) => ({
    menumanferMethod: (data) => dispatch(menumanferMethod(data)),
    getruleeditMethod: (id, data) => dispatch(getruleeditMethod(id, data)),
  })
)
class AddRule extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    roleId: "",
    //树状结构
    checkedKeys: [],
    checkedKeysPosted: [],
  };
  componentDidMount() {
    this.props.menumanferMethod();
    this.setState({
      roleId: this.props.location.state.item.id,
    });
    if (this.props.location.state.item.permissionIds !== null) {
      this.setState({
        expandedKeys: this.props.location.state.item.permissionIds,
      });
    }
  }
  onCheck = (checkedKeys, info) => {
    const checkedKeysPosted = JSON.parse(JSON.stringify(checkedKeys));
    if (info.halfCheckedKeys && info.halfCheckedKeys.length > 0) {
      for (let i = 0; i < info.halfCheckedKeys.length; i++) {
        checkedKeysPosted.push(info.halfCheckedKeys[i]);
      }
    }
    this.setState({
      checkedKeys: checkedKeys,
      checkedKeysPosted: checkedKeysPosted,
    });
  };
  renderTreeNodes = (data) =>
    data.map((item, index) => {
      if (item.children) {
        return (
          <TreeNode title={item.name} key={item.id} dataRef={item}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        );
      }
      return <TreeNode title={item.name} key={item.id} />;
    });
  //点击保存
  onSubmitType() {
    this.props.getruleeditMethod(
      this.state.roleId,
      this.state.checkedKeysPosted
    );
  }
  render() {
    const Label = (event) => {
      return <span className={Style.sort_size}>{event.title}</span>;
    };
    const { TextArea } = Input;
    console.log("this.props.menulistReducer", this.props.menulistReducer);
    return (
      <div className={Style.content}>
        <div className={Style.banner_add}>
          <div className={Style.resort}>
            <Label title="菜单" />
            <div className={Style.resort_tree}>
              <div className={Style.resort_tree_title}>
                <span>{this.props.location.state.item.name}</span>
              </div>
              <div style={{ paddingLeft: "30px" }}>
                {this.props.menulistReducer.length == 0 ? null : (
                  <Tree
                    checkable
                    defaultExpandAll={true}
                    onCheck={this.onCheck}
                    checkedKeys={this.state.checkedKeys}
                  >
                    {this.renderTreeNodes(this.props.menulistReducer)}
                  </Tree>
                )}
              </div>
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
              onClick={this.onSubmitType.bind(this)}
            >
              确定
            </Button>
          </div>
        </div>
      </div>
    );
  }
}

export default AddRule;
