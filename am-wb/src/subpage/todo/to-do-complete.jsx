import React, { Component } from "react";
import Style from "../../common/upload.less";
import Styles from "../../common/styles.less";
import { connect } from "react-redux";
import { Input, Button, message, Form, Tree, InputNumber, Select } from "antd";
import SecondTimeLineContent from "../../components/SecondTimeLine";
import {
  completedetailMethod
} from "../../store/actions/todoAction";

const { Option } = Select;

var styles = {
  'bigTitle': {
      color: '#a0a0a0',
      fontSize: '14px',
      width: '100%',
      padding: '8px 0'
  },
  'title': {
      color: '#a0a0a0',
      fontSize: '10px'
  },
  'content': {
      fontSize: '13px',
  },
  'module': {
      marginBottom: '8px'
  },
  'bigModule': {
      marginBottom: '15px'
  }
}

@connect(
  (state) => ({
    completedetail: state.completedetail
  }),
  (dispatch) => ({
    completedetailMethod: (data) => dispatch(completedetailMethod(data))
  })
)
class ToDoComplete extends Component {
  constructor(props) {
    super(props);
  }  

  state = {
    item: {}
  };

  doPrint=()=>{
    var iframe=document.getElementById("print-iframe");
    if(!iframe){
            var el = document.getElementById("printcontent");
            iframe = document.createElement('IFRAME');
            var doc = null;
            iframe.setAttribute("id", "print-iframe");
            iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
            document.body.appendChild(iframe);
            doc = iframe.contentWindow.document;
            //这里可以自定义样式
            //doc.write("<LINK rel="stylesheet" type="text/css" href="css/print.css">");
            doc.write('<div>' + el.innerHTML + '</div>');
            doc.close();
            iframe.contentWindow.focus();            
    }
    iframe.contentWindow.print();
    if (navigator.userAgent.indexOf("MSIE") > 0){
        document.body.removeChild(iframe);
    }
  }

  componentWillMount(){
    this.props.completedetailMethod(this.props.location.state.item.id);
  };

  render() {
    const Label = (event) => {
      return <span className={Style.sort_size}>{event.title}</span>;
    };

    var createTimeString = new Date(parseInt(this.props.completedetail.createTime) * 1).toLocaleString().replace(/:\d{1,2}$/,' ');

    return (
      <div>
        <div className={Style.content} id={'printcontent'}>
          <Form className={Style.banner_add}>
              <div className={Styles.bigModule}>
                  <div className={Styles.module}>
                      <span className={Styles.title}>申请人：</span>
                      <span className={Styles.content}>{this.props.completedetail.username}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>申请单位：</span>
                      <span className={Styles.content}>{this.props.completedetail.departmentName}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>资产名称：</span>
                      <span className={Styles.content}>{this.props.completedetail.assetName}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>资产类别：</span>
                      <span className={Styles.content}>{this.props.completedetail.assetTypeName}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>规格型号：</span>
                      <span className={Styles.content}>{this.props.completedetail.specification}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>数量：</span>
                      <span className={Styles.content}>{this.props.completedetail.num}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>单位：</span>
                      <span className={Styles.content}>{this.props.completedetail.units}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>预算单价：</span>
                      <span className={Styles.content}>{this.props.completedetail.budgetPrice}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>申购/核销理由：</span>
                      <span className={Styles.content}>{this.props.completedetail.description}</span>
                  </div>
                  <div className={Styles.module}>
                      <span className={Styles.title}>申请时间：</span>
                      <span className={Styles.content}>{createTimeString}</span>
                  </div>
              </div>
              <br/><br/><br/>
          </Form>
          <div style={{width:'100%'}}>
            <SecondTimeLineContent data={this.props.completedetail} />
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
              onClick={this.doPrint.bind(this)}
            >
              打印
            </Button>
        </div>
      </div>
      
      
    );
  }
}

export default ToDoComplete;
