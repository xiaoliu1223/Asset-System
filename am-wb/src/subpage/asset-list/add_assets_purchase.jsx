import React, { Component } from "react";
import Style from "../../common/upload.less";
import { Input, Button, message, Form, Tree, InputNumber, Select } from "antd";
import { connect } from "react-redux";
import { getoptionsMethod } from "../../store/actions/commomAction";
import {
  addpurchaseMethod,
  editpurchaseMethod,
  detailpurchaseMethod,
} from "../../store/actions/assets/purchaseAction";
import { getUnitssMethod } from "../../store/actions/unitAction";
import { getAssetNamesMethod } from "../../store/actions/assetNamesAction";
const { Option } = Select;
@connect(
  (state) => ({
    attributesoption: state.attributesoption,
    units: state.getUnits,
    assetNames: state.getAssetNames,
  }),
  (dispatch) => ({
    getAssetNames: () => dispatch(getAssetNamesMethod()),
    getUnits: () => dispatch(getUnitssMethod()),
    getoptionsMethod: (data) => dispatch(getoptionsMethod(data)),
    addpurchaseMethod: (data) => dispatch(addpurchaseMethod(data)),
    editpurchaseMethod: (id, data) => dispatch(editpurchaseMethod(id, data)),
    detailpurchaseMethod: (id) => dispatch(detailpurchaseMethod(id)),
  })
)
class AddAssetsPurchase extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    item: {},
    orgvalue: "",
  };
  componentDidMount() {
    this.props.getoptionsMethod({
      current: 1,
      limit: 100000,
    });
    if (this.props.location.state !== undefined) {
      this.setState({
        item: this.props.location.state.item,
      });
    }
    this.props.getUnits();

    this.props.getAssetNames();
  }

  onSubmitType(e) {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      console.log(values);
      values.departmentId = JSON.parse(
        localStorage.getItem("userinfotoken")
      ).departmentId;
      values.userId = JSON.parse(localStorage.getItem("userinfotoken")).id;
      if (this.props.location.state !== undefined) {
        // values.id =  this.state.item.id
        this.props.editpurchaseMethod(this.state.item.id, values);
      } else {
        this.props.addpurchaseMethod(values);
      }
    });
  }
  //修改资产类别
  initialassetTypeValue() {
    if (Object.keys(this.state.item).length !== 0) {
      return this.state.item.assetType;
    } else {
      return null;
    }
  }
  initialValue() {
    if (Object.keys(this.state.item).length !== 0) {
      return this.state.item.units;
    } else {
      return "";
    }
  }

  changeFileType(value) {
    this.setState({
      orgvalue: value,
    });
  }

  render() {
    console.log(this.props.units);

    const Label = (event) => {
      return <span className={Style.sort_size}>{event.title}</span>;
    };
    const { TextArea } = Input;
    const FormItem = Form.Item;
    const { getFieldDecorator } = this.props.form;
    return (
      <div className={Style.content}>
        <Form
          className={Style.banner_add}
          onSubmit={this.onSubmitType.bind(this)}
        >
          <div className={Style.resort}>
            {/* 默认当前登录人，不可编辑 */}
            <Label title="申请人" />
            {getFieldDecorator("username", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? JSON.parse(localStorage.getItem("userinfotoken")).username
                  : this.state.item.username,
              getValueFromEvent: (event) =>
                event.target.value.replace(/\s+/g, ""),
            })(<Input size="large" readOnly />)}
          </div>
          <div className={Style.resort}>
            <Label title="申请单位" />
            {getFieldDecorator("departmentName", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? JSON.parse(localStorage.getItem("userinfotoken"))
                      .departmentName
                  : this.state.item.departmentName,
              getValueFromEvent: (event) =>
                event.target.value.replace(/\s+/g, ""),
            })(<Input size="large" readOnly />)}
          </div>
          <div className={Style.resort}>
            <Label title="资产名称" />
            {getFieldDecorator("assetName", {
              initialValue: "",
            })(
              <Select
                style={{ width: "100%" }}
                showSearch
                size="large"
                // onChange={this.changeFileType.bind(this)}
              >
                {this.props.assetNames.length > 0 &&
                  this.props.assetNames.map((ele, index) => {
                    return (
                      <Option value={ele} key={index}>
                        {ele}
                      </Option>
                    );
                  })}
              </Select>
            )}
          </div>
          <div className={Style.resort}>
            <Label title="资产类别" />
            {getFieldDecorator("assetType", {
              initialValue: this.initialassetTypeValue(),
            })(
              <Select
                style={{ width: "100%" }}
                showSearch
                size="large"
                filterOption={(input, option) =>
                  option.props.children
                    .toLowerCase()
                    .indexOf(input.toLowerCase()) >= 0
                }
              >
                {this.props.attributesoption.map((ele, index) => {
                  return (
                    <Option value={ele.id} key={index}>
                      {ele.name}
                    </Option>
                  );
                })}
              </Select>
            )}
          </div>
          <div className={Style.resort}>
            <Label title="规格型号" />
            {getFieldDecorator("specification", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? ""
                  : this.state.item.specification,
              getValueFromEvent: (event) =>
                event.target.value.replace(/\s+/g, ""),
            })(<Input size="large" maxLength={20} />)}
          </div>
          <div className={Style.resort}>
            <Label title="申请数量" />
            {getFieldDecorator("num", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? ""
                  : this.state.item.num,
            })(
              <Input
                size="large"
                type="number"
                maxLength={10}
                placeholder="0"
              />
            )}
          </div>
          <div className={Style.resort}>
            <Label title="单位" />
            {getFieldDecorator("units", {
              initialValue: this.initialValue(),
            })(
              <Select
                style={{ width: "100%" }}
                showSearch
                size="large"
                // onChange={this.changeFileType.bind(this)}
              >
                {this.props.units.length > 0 &&
                  this.props.units.map((ele, index) => {
                    return (
                      <Option value={ele} key={index}>
                        {ele}
                      </Option>
                    );
                  })}
              </Select>
            )}
          </div>
          <div className={Style.resort}>
            <Label title="预算单价" />
            {getFieldDecorator("budgetPrice", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? ""
                  : this.state.item.budgetPrice,
            })(
              <Input
                size="large"
                type="number"
                suffix="元"
                maxLength={10}
                placeholder="0"
              />
            )}
          </div>
          <div className={Style.resort}>
            <Label title="申请描述" />
            {getFieldDecorator("description", {
              initialValue:
                Object.keys(this.state.item).length == 0
                  ? ""
                  : this.state.item.description,
              getValueFromEvent: (event) =>
                event.target.value.replace(/\s+/g, ""),
            })(<Input size="large" maxLength={30} />)}
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

export default Form.create()(AddAssetsPurchase);
