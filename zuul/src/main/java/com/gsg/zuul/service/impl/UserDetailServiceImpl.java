package com.gsg.zuul.service.impl;

import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.User;
import com.gsg.zuul.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.gsg.commons.utils.Constants.DELIMITER_3;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/9/29 14:48
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final String MOBILE = "mobile";
    private static final String ACCOUNT = "account";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameSplit = username.split(DELIMITER_3);
        username = usernameSplit[0];

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw ServiceException.errorParams("程序内部错误, 请联系管理员");
        }

        // 后台登录逻辑
        String url;
        User userDetail;
        String[] stringArr = username.split(" ");

        /** 1、用户ID校验 (暂不做登录使用)*/
        if (username.startsWith("GSG")) {
            url = "http://shuaigang-server/v1/user/getUserById?id={1}";
            userDetail = restTemplate.getForObject(url, User.class, username);
            if (userDetail == null) {
                throw ServiceException.errorParams("未填写有效用户名！");
            }
            return new JwtUserDetails(
                    userDetail.getId(),
                    username,
                    userDetail.getPassword(),
                    userDetail.getRole()
            );
        }
        User phone = null;
        User email = null;
        String type = stringArr[0];
        /** 2、手机号登陆 */
        if (MOBILE.equals(type)) {
            url = "http://shuaigang-server/v1/user/getUserByPhone?phone={1}";
            phone = restTemplate.getForObject(url, User.class, stringArr[1]);
        }
        /** 3、邮箱登录 */
        if (ACCOUNT.equals(type)) {
            url = "http://shuaigang-server/v1/user/getUserByEmail?email={1}";
            email = restTemplate.getForObject(url, User.class, stringArr[1]);
        }

        // 判断登录
        if (phone == null && email == null) {
            throw ServiceException.errorParams("未填写有效用户名！");
        }

        // 手机号登陆
        if (phone != null) {
            String newPhone = type + " " + phone.getPhone();
            return new JwtUserDetails(
                    newPhone,
                    phone.getId(),
                    phone.getPassword(),
                    phone.getRole()
            );
        }

        // 邮箱登录
        String newEmail = type + " " + email.getEmail();
        return new JwtUserDetails(
                newEmail,
                email.getId(),
                email.getPassword(),
                email.getRole()
        );

    }
}
