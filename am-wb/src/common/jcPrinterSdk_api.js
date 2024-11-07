//websocket
var g_websocket;

/* //响应数据 */
var ackJsonData;

//初始化打印服务
//1.1 获取接口实例JCAPI.getInstance（单例模式）
export function getInstance() {
  
  if ('WebSocket' in window) {
    g_websocket = websocketLifePeriod();
  } else {
    console.log('unsupported websocket');
    //return false;
    g_websocket = null;
    return false;
  }

  //保持在线
  setInterval(function () {
    if (g_websocket.readyState === 2 || g_websocket.readyState === 3) {
      getInstance();
    }
  }, 3000);
  console.log("xxxxxxx", g_websocket.readyState)
  return true;
}

export function unInitPrintInstance() {
  g_websocket.close();
}

//2.1 打印机连接成功回调onConnectSuccess
export function onConnectSuccess(printerName) {
  console.log('连接打印机!');
}

//2.2 打印机断开回调onDisConnect
export function onDisConnect(printerName) {
  console.log('打印机断开！');
}

//2.3 打印机上盖变化回调onCoverStatusChange
export function onCoverStatusChange(coverStatus) {
  console.log('打印机盒盖有变化！');
}

//2.4 打印机电量变化回调onElectricityChange()
export function onElectricityChange(powerLever) {
  console.log('打印机电量有变化！');
}

//2.5 打印机纸张状态变化回调onPaperStatusChange
export function onPagerStatusChange(paperStatus) {
  console.log('打印机纸张状态有变化！');
}

//8.1 页打印成功回调onPrintPageCompleted
export function onPrintPageCompleted() {
  console.log('也打印状态有变化！');
}

//8.2 打印进度回调onPrintProgress
export function onPrintProgress() {
  console.log('打印进度有变化！');
}

//8.3 打印异常回调onAbnormalResponse
export function onAbnormalResponse() {
  console.log('打印异常！');
}

//获取所有当前PC上连接的精臣打印机
//3.1 获取打印机列表getAllPrinters()
export function getAllPrinters(callbackFunction) {
  //刷新设备时，关闭设备
  closePrinter();
  var jsonObj = { apiName: 'getAllPrinters' };
  var allDevice = {};

  sendMsg(jsonObj, callbackFunction);
}

//4.图片打印
/*base64Data--图片base64数据
nPrintCount--打印数量
bDenoise--降噪*/
export function picturePrint(base64Data, nPrintCount, bDenoise, callbackFunction) {
  var jsonObj = {
    apiName: 'picturePrint',
    parameter: {
      data: base64Data,
      nPrintCount: nPrintCount,
      bDenoise: bDenoise
    }
  };

  sendMsg(jsonObj, callbackFunction);
}

//5.选择并打开需要使用的打印机名称，及端口号
export function selectPrinter(printerName, port, callbackFunction) {
  var jsonObj = {
    apiName: 'selectPrinter',
    parameter: { printerName: printerName, port: port }
  };
  sendMsg(jsonObj, callbackFunction);
}

//6.停止打印
export function stopPrint(callbackFunction) {
  var jsonObj = { apiName: 'stopPrint' };
  sendMsg(jsonObj, callbackFunction);
}

//7.关闭打印端口

export function closePrinter(callbackFunction) {
  var jsonObj = { apiName: 'closePrinter' };
  sendMsg(jsonObj, callbackFunction);
}

//8.设置打印浓度
//nDensity--范围为getDensityScopeApi查询范围
export function setPrintDensity(nDensity, callbackFunction) {
  var jsonObj = {
    apiName: 'setPrintDensity',
    parameter: { nDensity: nDensity }
  };
  sendMsg(jsonObj, callbackFunction);
}

//9.设置打印速度
//nSpeed--范围为getSpeedScopeApi查询的范围
export function setPrintSpeed(nSpeed, callbackFunction) {
  var jsonObj = { apiName: 'setPrintSpeed', parameter: { nSpeed: nSpeed } };
  sendMsg(jsonObj, callbackFunction);
}

//10.设置打印标贴类型
//nType--间隙：01，黑标：02，连续纸：03，定位孔：04，透明纸：05，随机打印：06
export function setPrintLabelType(nType, callbackFunction) {
  var jsonObj = { apiName: 'setPrintLabelType', parameter: { nType: nType } };
  sendMsg(jsonObj, callbackFunction);
}

//11.设置关机时间
//nType--1：15分钟，2:30分钟，3:60分钟，4：never
export function setPrinterAutoShutDownTime(nType, callbackFunction) {
  var jsonObj = {
    apiName: 'setPrinterAutoShutDownTime',
    parameter: { nType: nType }
  };
  sendMsg(jsonObj, callbackFunction);
}

//12.复位打印机
export function setPrinterReset(callbackFunction) {
  var jsonObj = { apiName: 'setPrinterReset' };
  sendMsg(jsonObj, callbackFunction);
}

//13.设置打印模式
//nType-1热敏，2碳带
export function setPrintMode(nType, callbackFunction) {
  var jsonObj = { apiName: 'setPrintMode', parameter: { nType: nType } };
  sendMsg(jsonObj, callbackFunction);
}

//14.获取打印速度
export function getPrintSpeed(callbackFunction) {
  var jsonObj = { apiName: 'getPrintSpeed' };
  sendMsg(jsonObj, callbackFunction);
}

export function getPower(callbackFunction) {
  var jsonObj = { apiName: 'getPower' };
  sendMsg(jsonObj, callbackFunction);
}

//15.获取标贴类型
export function getPrintLabelType(callbackFunction) {
  var jsonObj = { apiName: 'getPrintLabelType' };

  sendMsg(jsonObj, callbackFunction);
}

//16.获取打印浓度
export function getPrintDensity(callbackFunction) {
  var jsonObj = { apiName: 'getPrintDensity' };
  sendMsg(jsonObj, callbackFunction);
}

//17.获取打印机语言
export function getPrinterLanguageType(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterLanguageType' };
  sendMsg(jsonObj, callbackFunction);
}

//18.查询关机时间
export function getPrinterAutoShutDownTime(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterAutoShutDownTime' };
  sendMsg(jsonObj, callbackFunction);
}

//19.获取打印机序列号
export function getPrinterSn(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterSn' };
  sendMsg(jsonObj, callbackFunction);
}

//20.获取硬件版本
export function getPrinterHardwareVersion(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterHardwareVersion' };
  var responseData = {};
  sendMsg(jsonObj, callbackFunction);
}

//21.获取软件版本
export function getPrinterSoftwareVersion(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterSoftwareVersion' };
  sendMsg(jsonObj, callbackFunction);
}

//25.获取打印机语言
export function setPrinterLanguageType(nType, callbackFunction) {
  var jsonObj = {
    apiName: 'setPrinterLanguageType',
    parameter: { nType: nType }
  };
  sendMsg(jsonObj, callbackFunction);
}

//26.将图像转换为base64数据，仅供demo测试使用，暂时有问题
export function readFileWithBase64(filePath, callbackFunction) {
  var jsonObj = {
    apiName: 'readFileWithBase64',
    parameter: { filePath: filePath }
  };
  sendMsg(jsonObj, callbackFunction);
}

//27.发送十六进制指令，仅供demo测试使用
export function sendData(data, dataSize, callbackFunction) {
  var jsonObj = {
    apiName: 'sendData',
    parameter: { data: data, dataSize: dataSize }
  };
  sendMsg(jsonObj, callbackFunction);
}

//28.接收指令返回数据，仅供demo测试使用
export function receiveData(callbackFunction) {
  var jsonObj = { apiName: 'receiveData' };
  sendMsg(jsonObj, callbackFunction);
}

//29.获取高级参数,心跳使用
export function getPrinterHighLevelInfo(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterHighLevelInfo' };
  sendMsg(jsonObj, callbackFunction);
}

//30.获取打速度印范围
export function getSpeedScope(callbackFunction) {
  var jsonObj = { apiName: 'getSpeedScope' };
  sendMsg(jsonObj, callbackFunction);
  return getResult(5, 'getSpeedScope', 'set printer language timeout!');
}

export function getConnectPrinter(callbackFunction) {
  var jsonObj = { apiName: 'getConnectPrinterApi' };
  sendMsg(jsonObj, callbackFunction);
  return getResult(5, 'getConnectPrinterApi', 'set printer language timeout!');
}

export function getPrinterType(callbackFunction) {
  var jsonObj = { apiName: 'getPrinterType' };
  sendMsg(jsonObj, callbackFunction);
}

//31.获取浓度范围
export function getDensityScope(callbackFunction) {
  var jsonObj = { apiName: 'getDensityScope' };
  sendMsg(jsonObj, callbackFunction);
}

var MessageList = {};
export function sendMsg(msg, callback) {
  console.log('sendMsg', msg.apiName);
  MessageList[msg.apiName] = callback;

  var data = JSON.stringify(msg);
  var tryTimes = 10;

  for (var i = 0; i < tryTimes; i++) {  
    if (g_websocket.readyState === 1) {
      g_websocket.send(data);
      return;
    }
  }
}

export function getPrinterMode(callbackFunction) {
  var jsonObj = { apiName: 'getPrintMode' };
  sendMsg(jsonObj, callbackFunction);
}

//32.连接回调
export function connectCallback(e) {
  ackJsonData = '';
  console.log('success');
}

//33.关闭连接回调
export function closeCallback(e) {
  console.log(
    'websocket closed: ' + e.code + ' ' + e.reason + ' ' + e.wasClean
  );
  // globalwebsocket = websocket;
  g_websocket.close();
  //websocketLifePeriod();
  if (e.code == 1005) {
    // globalwebsocket = websocket;
  }
  console.log('closed');
  ackJsonData = '';
}

//34.读回调
export function readCallback(e) {
  console.log('readCallback', e.data);
  ackJsonData = e.data;

  var callbackName;

  if (isJSON(ackJsonData)) {
    var arrParse = JSON.parse(ackJsonData);

    //接口回调
    if (!!MessageList[arrParse.apiName]) {
      MessageList[arrParse.apiName](arrParse);
    }

    //回调分发
    if (arrParse['resultAck']['callback'] != undefined) {
      callbackName = arrParse['resultAck']['callback']['name'];

      if (callbackName == 'onConnectSuccess') {
        var printerName = arrParse['resultAck']['callback']['printerName'];

        onConnectSuccess(printerName);
      } else if (callbackName == 'onDisConnect') {
        var printerName = arrParse['resultAck']['callback']['printerName'];
        onDisConnect(printerName);
      } else if (callbackName == 'onCoverStatusChange') {
        var coverStatus = arrParse['resultAck']['callback']['coverStatus'];
        onCoverStatusChange(coverStatus);
      } else if (callbackName == 'onElectricityChange') {
        var powerLever = arrParse['resultAck']['callback']['powerLever'];
        onElectricityChange(powerLever);
      } else if (callbackName == 'onPagerStatusChange') {
        var paperStatus = arrParse['resultAck']['callback']['paperStatus'];
        onPagerStatusChange(paperStatus);
      } else if (callbackName == 'onPrintPageCompleted') {
        onPrintPageCompleted();
      } else if (callbackName == 'onPrintProgress') {
        onPrintProgress();
      } else if (callbackName == 'onAbnormalResponse') {
        onAbnormalResponse();
      } else {
        console.log('unknow callback api!');
      }
    }

    ackJsonData = '';
  }
}
//35.错误回调
export function errorCallback(e) {
  //如果出现连接、处理、接收、发送数据失败的时候触发onerror事件
  console.log(e.data);
}

//36.6.1 开启打印任务startJob
//初始化状态(页数，份数)，发送总开始
export function startJob(width, height, orientation, count) {
  var jsonObj = {
    apiName: 'startJob',
    parameter: { width: width, height: height, orientation: orientation,count:count }
  };
  sendMsg(jsonObj, null);
  //return getResult(5, 'startJob', 'set printer language timeout!');
}

//37.6.2 开始页绘制startPage
//发送页开始，统计页数，份数
export function startPage() {
  var jsonObj = { apiName: 'startPage' };
  sendMsg(jsonObj, null);
  //return getResult(5, 'startPage', 'set printer language timeout!');
}

//38.6.3 结束页绘制endPage
//发送页结束

export function endPage() {
  var jsonObj = { apiName: 'endPage' };
  sendMsg(jsonObj, null);
}

//39.6.4 提交打印任务commitJob
//打印开始

export function commitJob(printQuantity, printDensity, printLabelType,base64, x, y, width, height, rotate,top, left, bottom, right,callbackFunction) {
  var jsonObj = {
    apiName: 'commitJob',
    parameter: {
      printQuantity: printQuantity,
      printDensity: printDensity,
      printLabelType: printLabelType,
	  data: base64,
      x: x,
      y: y,
      width: width,
      height: height,
      orientation: rotate,
	  top:top,
	  left:left,
	  bottom:bottom,
	  right:right,
    }
  };
  sendMsg(jsonObj, callbackFunction);
}

//40.6.5 结束打印任务endJob
//发送打印总结束

export function endJob(callbackFunction) {
  var jsonObj = { apiName: 'endJob' };
  sendMsg(jsonObj, callbackFunction);
  //return getResult(5, 'endJob', 'set printer language timeout!');
}

//41.6.6 取消打印任务cancleJob
//发送取消打印指令

export function cancleJob(callbackFunction) {
  var jsonObj = { apiName: 'stopPrint' };
  sendMsg(jsonObj, callbackFunction);
  //return getResult(5, 'cancleJob', 'set printer language timeout!');
}

//42.7.1 图像绘制drawImage
//处理打印图像
export function drawImage(base64, x, y, width, height, rotate) {
  var jsonObj = {
    apiName: 'drawImage',
    parameter: {
      data: base64,
      x: x,
      y: y,
      width: width,
      height: height,
      orientation: rotate
    }
  };
  sendMsg(jsonObj, null);
  //return getResult(5, 'drawImage', 'set printer language timeout!');
}

//打开链接及回调
export function websocketLifePeriod() {

  var websocket;
  
  websocket = new WebSocket('ws://127.0.0.1:37989');

  websocket.onopen = connectCallback;

  websocket.onclose = closeCallback;

  websocket.onmessage = readCallback;

  websocket.onerror = errorCallback;

  console.log(websocket);

  return websocket;
}

// 调用结果
export function getResult(tryTime, apiName, errInfo) {
  var tryTimes = tryTime;

  let result = {};
  while (tryTimes--) {
    if (!isJSON(ackJsonData)) continue;

    var arrParse = JSON.parse(ackJsonData);
    if (arrParse['apiName'] === apiName) {
      result = arrParse['resultAck'];
      break;
    }
  }

  if (tryTimes <= 0) {
    result['result'] = false;
    result['errorCode'] = 0x12;
    result['info'] = errInfo;
  }
  return result;
}

export function isJSON(str) {
  if (typeof str == 'string') {
    try {
      var obj = JSON.parse(str);
      if (typeof obj == 'object' && obj) {
        return true;
      } else {
        return false;
      }

    } catch (e) {
      //console.log('error：'+str+'!!!'+e);
      return false;
    }

  }

  console.log('It is not a string!');
}

