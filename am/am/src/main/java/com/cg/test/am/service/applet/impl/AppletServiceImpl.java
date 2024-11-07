package com.cg.test.am.service.applet.impl;

import cn.hutool.crypto.SecureUtil;
import com.cg.test.am.service.applet.AppletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Service
public class AppletServiceImpl implements AppletService {


    private final static String token = "choryoung2018";

    private final Logger logger = LoggerFactory.getLogger(AppletServiceImpl.class);

    @Override
    public void check(HttpServletRequest request, HttpServletResponse response) {

        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

            String [] arr = new String[] {token, timestamp, nonce};

            // 排序
            Arrays.sort(arr);
            StringBuffer stringBuffer = new StringBuffer();
            for (String str: arr) {
                stringBuffer.append(str);
            }

            String sign = SecureUtil.sha1(stringBuffer.toString());
            Boolean flag = sign.equals(signature);
            PrintWriter out = response.getWriter();

            if (flag) {
                logger.debug("接入成功，echostr: {}", echostr);
                out.print(echostr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
