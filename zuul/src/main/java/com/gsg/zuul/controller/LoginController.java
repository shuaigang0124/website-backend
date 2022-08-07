package com.gsg.zuul.controller;

import com.gsg.commons.dto.RequestDTO;
import com.gsg.commons.utils.*;
import com.gsg.zuul.config.JwtProperties;
import com.gsg.zuul.model.JwtUserDetails;
import com.gsg.zuul.service.impl.UserDetailServiceImpl;
import com.gsg.zuul.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * @author shuaigang
 * @date 2021/9/29 17:22
 */
@RestController
@RequestMapping("/gsg/authentication")
@Component
public class LoginController {

    /*@Value("${jasypt.encryptor.password}")
    public String encryptorSaltKey;*/

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/form")
    public void login(String username, String password) {
        // TODO 这里面不需要写任何代码，由UserDetailsService去处理
    }

    /**
     * 根据用户id生成新的token令牌
     */
    @PostMapping("/generateToken")
    public R<?> generateToken(@RequestBody @Valid Request<RequestDTO> request, BindingResult bindingResult) {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }
        String userId = request.getCustomData().getUserId();
        JwtUserDetails userDetails = userDetailService.loadUserByUsername(userId);
        redisUtils.set(userId, "");
        String newToken = jwtTokenUtil.generateToken(userId);

        Map<String, Object> map = new HashMap<>(5);

        // base64 加密用户Id，用户角色
        String base64UserId = Base64.getEncoder().encodeToString(userId.getBytes());
        String base64Role = Base64.getEncoder().encodeToString(userDetails.getRole().getBytes());

        //Base64 解密方法
//        String userId = new String(Base64.getDecoder().decode(base64UserId));

        map.put("Authorization", newToken);
        map.put("userId", base64UserId);
        map.put("role", base64Role);
        return R.ok(map);


    }
}
