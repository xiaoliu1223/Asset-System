//公共的方法
import $ from 'jquery'
import Axios from './axios';
export function getWindowsheight(cb){
    const Height = $(window).height();
    return Height+"px";  
}
export function extend() {
    var length = arguments.length;
    if(length === 0)return {};
    if(length === 1)return arguments[0];
    var target = arguments[0] || {};
    for (var i = 1; i < length; i++) {
        var source = arguments[i];
        for (var key in source) {
            if (source.hasOwnProperty(key)) {
                target[key] = source[key];
            }
        }
    }
    return target;
}
export function URL(){
    // return "http://asset.zdhserp.com"
    return Axios.defaults.baseURL  //下载库存表的http
}


