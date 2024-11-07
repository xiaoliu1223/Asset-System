import React, { useState, useEffect } from "react";
import style from "./Login.less";
import { withRouter } from "react-router-dom";
import { Button, Form, Input, Icon, Checkbox } from "antd";
import axios from "axios";
import { connect } from "react-redux";
import { getusernameMethod } from "../../store/actions/loginAction";
import BgcImg from "../../static/images/bgc.jpg";
import LogoImg from "../../static/images/logo.png";

const Base64 = require("js-base64").Base64;

const Login = (props) => {
  const [state, setState] = useState({
    certificate: "",
    password: "",
    remember: false,
    verificationCode: "",
    verificationCodeInput: "",
  });

  useEffect(() => {
    const remember = getCookie("remember") === "true";
    const certificate = getCookie("certificate");
    const password = Base64.decode(getCookie("password"));

    const newCode = generateRandomVerificationCode();// 生成新的随机验证码
    saveVerificationCode(newCode); // 保存验证码到后端
    // 使用 setState 更新状态
    setState(prevState => ({
      ...prevState,
      remember,
      certificate: certificate || "",
      password: password || "",
      verificationCode: newCode // 使用新生成的验证码

    }));
  }, []);


  const getCookie = (key) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${key}=`);
    if (parts.length === 2) return unescape(parts.pop().split(';').shift());
    return "";
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // 阻止表单默认提交行为
    const { certificate, password, verificationCodeInput, verificationCode } = state; // 从状态中解构出证书、密码、用户输入的验证码和实际的验证码
    const isCodeValid = verificationCodeInput === verificationCode; // 比较用户输入的验证码和实际的验证码是否一致
    if (isCodeValid) {// 如果验证码正确，调用传入的 props.getusernameMethod 方法提交登录信息，并传入证书、密码和记住密码的状态
      props.getusernameMethod({ certificate, password }, state.remember);
    } else {
      alert('验证码错误！'); // 如果验证码错误，弹出提示框
    }
  };

  //随机数生成验证码
  const generateRandomVerificationCode = () => {
    // 生成一个 1000 到 9999 之间的随机数并转换为字符串作为验证码
    return Math.floor(1000 + Math.random() * 9000).toString();
  };

  // 保存验证码到后端
  const saveVerificationCode = async (code) => {
    try {
      // 使用 axios 向后端发送 POST 请求，路径为 /verification/save，并携带生成的验证码作为参数
      await axios.post('/verification/save', { code });
      // 打印保存成功的消息和验证码
      console.log('验证码保存成功：', code);
    } catch (error) {
      // 打印保存验证码时出现的错误信息
      console.error('保存验证码错误：', error);
    }
  };

  const verifyVerificationCode = async (enteredCode) => {
    try {
      const response = await axios.post('/verifyVerificationCode', { enteredCode });
      return response.data;
    } catch (error) {
      console.error('验证验证码错误：', error);
      return false;
    }
  };

  return (
    <div className={style.login} style={{ backgroundImage: `url(${BgcImg})`, height: "100vh" }}>
      <div className={style.login_form}>
        <div className={style.login_form_logo}>
          <img src={LogoImg} alt="" />
        </div>
        <div className={style.login_form_title}>
          <span>资产管理系统</span>
        </div>
        <Form className={style.form} onSubmit={handleSubmit}>
          <Form.Item>
            <Input
              placeholder="请输入账号"
              value={state.certificate}
              onChange={e => setState({ ...state, certificate: e.target.value })}
              prefix={<Icon type="user" />}
            />
          </Form.Item>
          <Form.Item>
            <Input.Password
              placeholder="请输入密码"
              value={state.password}
              onChange={e => setState({ ...state, password: e.target.value })}
              prefix={<Icon type="lock" />}
            />
          </Form.Item>
          <Form.Item>
            <Input
              placeholder="请输入验证码"
              value={state.verificationCodeInput}
              onChange={e => setState({ ...state, verificationCodeInput: e.target.value })}
              prefix={<Icon type="lock" />}
              style={{ width: 'calc(100% - 100px)', display: 'inline-block' }} // 调整宽度以适应验证码
            />
            <span
              onClick={saveVerificationCode}
              style={{
                cursor: "pointer",
                marginLeft: "10px",
                display: 'inline-block',
                height: '40px',
                lineHeight: '40px',
                color: "white",
                fontSize: '26px',
                background: 'pink', // 设置背景为粉色
                position: 'relative',
                overflow: 'hidden'
              }}
            >
              {state.verificationCode}

              {/* 添加干扰线条 */}
              {/* 红色干扰线 */}
              <div style={{
                position: 'absolute',
                top: '20%',
                left: '10%',
                width: '80%',
                height: '2px',
                backgroundColor: 'red',
                transform: 'rotate(15deg)',
                pointerEvents: 'none'
              }} />

              {/* 橙色干扰线 */}
              <div style={{
                position: 'absolute',
                top: '40%',
                left: '15%',
                width: '70%',
                height: '2px',
                backgroundColor: 'orange',
                transform: 'rotate(-10deg)',
                pointerEvents: 'none'
              }} />

              {/* 黄色干扰线 */}
              <div style={{
                position: 'absolute',
                top: '60%',
                left: '20%',
                width: '60%',
                height: '2px',
                backgroundColor: 'yellow',
                transform: 'rotate(5deg)',
                pointerEvents: 'none'
              }} />

              {/* 蓝色干扰线 */}
              <div style={{
                position: 'absolute',
                top: '80%',
                left: '25%',
                width: '50%',
                height: '2px',
                backgroundColor: 'blue',
                transform: 'rotate(-15deg)',
                pointerEvents: 'none'
              }} />

              {/* 紫色干扰线 */}
              <div style={{
                position: 'absolute',
                top: '90%',
                left: '30%',
                width: '40%',
                height: '2px',
                backgroundColor: 'purple',
                transform: 'rotate(10deg)',
                pointerEvents: 'none'
              }} />
            </span>

          </Form.Item>
          <Checkbox
            onChange={e => setState({ ...state, remember: e.target.checked })}
            checked={state.remember}
          >
            记住密码
          </Checkbox>
          <Form.Item>
            <Button size="large" type="primary" htmlType="submit" className="button" style={{ borderRadius: '5px', height: '50px', width: '100%', position: 'relative', left: '50%', transform: 'translateX(-50%)', fontSize: '24px', backgroundColor: '#00cedb', marginTop: '20px' }}>
              登录
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default connect(
  state => ({ userinfo: state.getUserInfo }),
  dispatch => ({ getusernameMethod: (data, remember) => dispatch(getusernameMethod(data, remember)) })
)(withRouter(Login));
