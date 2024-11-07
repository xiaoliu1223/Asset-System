import React from 'react'
import Style from './Style.less'
import Acture from '../../static/images/national.png'
class Footer extends React.Component{
    render(){
        return(
            <div className={Style.footer}>
                <p>© 版权所有 XXXXX有限公司</p>
                <p>Copyright 2018 - 无固定期限. xxxx.com. All Rights Reserved.</p>
                <p> 
                    <img src={Acture}/>
                    <span>苏公网安备xxxxx号</span>
                    <span style={{margin:" 0 20px"}}>|</span>
                    <span>苏ICP备xxxxx号</span>
                </p>
            </div>
        )
    }
}
export default Footer;