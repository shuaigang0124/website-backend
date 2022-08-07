package com.gsg.zuul.controller;

import com.gsg.commons.dto.RequestDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.RedisUtils;
import com.gsg.commons.utils.Request;
import com.gsg.zuul.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;



/**
 * @author gaoshenggang
 * @date  2021/9/29 17:43
 */
@RestController
@RequestMapping("/gsg/authentication")
@Slf4j
public class SecurityController {

    @Autowired
    RedisUtils redisUtils;

    @Resource
    private JwtProperties jwtProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @PostMapping("/Login")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public R<?> requireAuthentication (HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl,".vue")) {
                // 跳转登录页
                redirectStrategy.sendRedirect(request, response, "/Login.vue");
            }
        }
        return R.ok("未认证!");
    }

    @PostMapping("/logout")
    public R<?> loginOut(@RequestBody @Valid Request<RequestDTO> request, HttpServletRequest httpServletRequest){
        String userId = request.getCustomData().getUserId();

        try {
            /*TODO token交由redis管理时效*/
//            String authToken = httpServletRequest.getHeader(jwtProperties.getHeader());
//            redisUtils.delete(authToken);
            redisUtils.delete(userId);
            log.debug("[{}]已退出!" + userId);
            return R.ok("退出成功！");
        } catch (Exception e) {
            log.error("未退出!{}", e.getClass().getName());
            e.printStackTrace();
            return R.ok("退出失败！");
        }
    }

}
