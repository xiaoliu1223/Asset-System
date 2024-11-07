package com.cg.test.am.utils;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/***
 * 微信小程序工具类
 */
public class AppletUtils {

    /** 小程序唯一凭证**/
    private static final String APP_ID = "";

    /** 小程序唯一凭证秘钥 **/
    private static final String SECRET = "";

    /** 获取小程序全局唯一后台接口调用凭据（access_token）**/
    private static final String URL_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;

    /** 下发小程序和公众号统一的服务消息**/
    private static final String URL_UNIFORM_MESSAGE_SEND = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=";


    private static final String URL_SUBSCRIBE_MESSAGE_SEND = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";


    /**
     * http请求处理方法
     * @param requestUrl
     * @param output
     * @return
     */
    public static String httpRequest(String requestUrl, String output) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Connection", "Keep-Alive");

            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("utf-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }




    /**
     * 获取access_token
     * @return
     */
    public static String getAccessToken() {
//        String result = HttpUtil.get(URL_GET_ACCESS_TOKEN);
        String result = httpRequest(URL_GET_ACCESS_TOKEN, null);
        JSONObject jsonObject = JSON.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        return accessToken;
    }


    /**
     * 统一服务消息
     * @param accessToken
     * @param strReq
     */
    public static String uniformMessageSend(String accessToken, String strReq) {
        String url = URL_UNIFORM_MESSAGE_SEND + accessToken;
        return httpRequest(url, strReq);
    }

    /**
     * 发送订阅消息
     * @param accessToken
     * @param strReq
     * @return
     */
    public static String subscribeMessageSend(String accessToken, String strReq) {

        String url = URL_SUBSCRIBE_MESSAGE_SEND + accessToken;
        return httpRequest(url, strReq);
    }

    /**
     * 获取小程序用户openid
     * @param jsCode 登录时获取的 code
     * @return openid
     */
    public static String getOpenid(String jsCode) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + jsCode + "&grant_type=authorization_code";
        String result = httpRequest(url, null);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getString("openid");

    }
}
