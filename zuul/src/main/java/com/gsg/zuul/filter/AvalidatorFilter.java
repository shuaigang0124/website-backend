package com.gsg.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gsg.commons.utils.ParseJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/9/29 16:15
 */
@Component
@Slf4j
public class AvalidatorFilter extends OncePerRequestFilter {
    public static final String CUSTOM_DATA = "customData";

    private static final String LOGIN_DATA = "loginData";

    private static final String TYPE = "type";

    private static final String MOBILE = "mobile";

    private static final String ACCOUNT = "account";

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("AvalidatorFilter过滤器1登录执行开始");

        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")){
            String serverName = request.getHeader("serverName");
            if (ObjectUtils.isEmpty(serverName)) {
                serverName = request.getServerName() + ":" + request.getServerPort();
            }
            if(!ObjectUtils.isEmpty(request.getScheme())){
                serverName = request.getScheme() + "://" + serverName;
            }
            request.setAttribute("serverName", serverName);
            chain.doFilter(request, response);

            log.debug("请求目标地址{}", serverName);
            log.info("文件上传接口，跳过过滤器1 逻辑");
            return;
        }

        MyHttpServletRequestWrapper requestWrapper = new MyHttpServletRequestWrapper(request);
        String body = requestWrapper.getBody();
        if (body == null || "".equals(body)) {
            chain.doFilter(request, response);
        } else {
            JSONObject bodyJson = JSON.parseObject(body);
            try {
                String customData = bodyJson.getString(CUSTOM_DATA);
                log.debug("body", body);
                JSONObject jsonObject = JSON.parseObject(customData);


                MyHttpServletRequestWrapper myHttpServletRequestWrapper = new MyHttpServletRequestWrapper(request, body.getBytes());
                Map map = ParseJsonUtil.stringToCollect(body);
                System.out.println(map);

                // 将JSON请求参数放入本次HttpServletRequest
                myHttpServletRequestWrapper.setParameterMap(map);

                //判断是否为登陆数据
                if (!StringUtils.isEmpty(jsonObject) && !jsonObject.isEmpty()) {
                    String type = jsonObject.getString(TYPE);
                    String loginData = jsonObject.getString(LOGIN_DATA);
                    if (loginData != null && type != null) {
                        JSONObject loginDataJson = JSON.parseObject(loginData);
                        String password = loginDataJson.getString(PASSWORD);
                        String typeAndUserName = null;
                        /* 登录类型为手机号*/
                        if (MOBILE.equals(type)) {
                            //15922811864
                            typeAndUserName = type + " " + loginDataJson.getString(USERNAME);
                        }
                        /* 登录类型为邮箱*/
                        if (ACCOUNT.equals(type)) {
                            //18888888888@qq.com
                            typeAndUserName = type + " " + loginDataJson.getString(USERNAME);
                        }
                        myHttpServletRequestWrapper.setParameter(USERNAME, typeAndUserName);
                        myHttpServletRequestWrapper.setParameter(PASSWORD, password);
                    }
                }

                log.info("过滤器1执行结束！");
                /* 放行，把我们的requestWrapper1放到方法当中*/
                chain.doFilter(myHttpServletRequestWrapper, response);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("解析失败{}", e.getClass().getName());
            }
        }
    }
}

