import React from "react";
import { Form, Icon, Input, Button, message } from "antd";
import Axios from "../../common/axios";

class ChangePasswordWrap extends React.Component {
  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log("Received values of form: ", values);
        if (values.password === values.newPassword) {
          message.warn("原密码与新密码不能一致");
          return;
        }
        Axios({
          method: "PUT",
          url: "/sysUser/changePassword",
          data: {
            id: JSON.parse(localStorage.getItem("userinfotoken")).id,
            newPassword: values.newPassword,
            oldPassword: values.password
          }
        }).then(res => {
          if (res.data.code === "000000") {
            message.success("修改成功");
            window.location.reload();
          }
        });
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <div
        style={{
          width: "700px",
          textAlign: "center",
          margin: "0 auto"
        }}
      >
        <Form onSubmit={this.handleSubmit} className="login-form">
          <Form.Item>
            {getFieldDecorator("password", {
              rules: [{ required: true, message: "旧密码不能为空" }]
            })(
              <Input
                prefix={
                  <Icon type="lock" style={{ color: "rgba(0,0,0,.25)" }} />
                }
                placeholder="旧密码"
              />
            )}
          </Form.Item>
          <Form.Item>
            {getFieldDecorator("newPassword", {
              rules: [{ required: true, message: "新密码不能为空" }]
            })(
              <Input
                prefix={
                  <Icon type="lock" style={{ color: "rgba(0,0,0,.25)" }} />
                }
                type="password"
                placeholder="新密码"
              />
            )}
          </Form.Item>
          <div
            style={{
              width: "50%",
              marginLeft: "25%",
              display: "flex",
              justifyContent: "space-around"
            }}
          >
            <Button
              onClick={() => {
                this.props.form.setFieldsValue({
                  password: "",
                  newPassword: ""
                });
              }}
              size="large"
              type="default"
              className="login-form-button"
            >
              取消
            </Button>
            <Button
              size="large"
              type="primary"
              htmlType="submit"
              className="login-form-button"
            >
              确认
            </Button>
          </div>
        </Form>
      </div>
    );
  }
}

const ChangePassword = Form.create({ name: "normal_login" })(
  ChangePasswordWrap
);

export default ChangePassword;
