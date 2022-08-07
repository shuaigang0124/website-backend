package com.gsg.zuul.security;

import com.gsg.zuul.filter.AvalidatorFilter;
import com.gsg.zuul.filter.JwtAuthenticationTokenFilter;
import com.gsg.zuul.handler.JwtAccessDeniedHandler;
import com.gsg.zuul.handler.JwtAuthenticationEntryPoint;
import com.gsg.zuul.handler.MyAuthenticationFailureHandler;
import com.gsg.zuul.handler.MyAuthenticationSuccessHandler;
import com.gsg.zuul.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/9/29 16:04
 */
@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private AvalidatorFilter avalidatorFilter;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    /**
     * 白名单, 不做权限效验的url
     */
    private static final String[] AUTH_WHITELIST = {
            "/gsg/authentication/login",
            "/gsg/authentication/logout",
            "/gsg/authentication/generateToken",
//            "/**/gsg/shuaigang/**",

            "/gsg/shuaigang/v1/log/getLog",
            "/gsg/shuaigang/v1/log/getLogList",

            "/gsg/shuaigang/v1/author/getAuthor",
            "/gsg/shuaigang/v1/author/updateAuthor",

            "/gsg/shuaigang/v1/link/getLink",
            
            "/gsg/shuaigang/v1/diary/getDiary",

            "/gsg/shuaigang/v1/year/getYearList",

            "/gsg/shuaigang/v1/travel/getCityAndTravel",

            "/gsg/shuaigang/v1/message/findAllMessage",

            "/gsg/shuaigang/v1/yearPlan/getYearPlan",

            "/gsg/shuaigang/v1/travel/getTravel",




            "/gsg/shuaigang/v1/user/getAllUser",
            "/gsg/shuaigang/v1/user/insertUser",
            "/gsg/shuaigang/v1/user/getUserDetails",
            "/gsg/shuaigang/v1/user/updatePwdByEmail",

            "/gsg/shuaigang/v1/sharingComment/findAllComment",

            "/gsg/shuaigang/v1/sharing/insertSharing",
            "/gsg/shuaigang/v1/sharing/getSharing",
            "/gsg/shuaigang/v1/sharing/getSharingDetail",
            "/gsg/shuaigang/v1/sharing/getBackendAllSharing",
            "/gsg/shuaigang/v1/sharing/getSharingByTag",
            "/gsg/shuaigang/v1/sharing/updateByCondition",
            "/gsg/shuaigang/v1/sharing/getUpdateDetail",

            "/gsg/shuaigang/v1/tag/getAllTag",
            "/gsg/shuaigang/v1/tag/getAllBackendTag",

            "/gsg/shuaigang/v1/notice/getNotice",
            "/gsg/shuaigang/v1/notice/getBackendNotice",

            "/gsg/shuaigang/v1/city/getCityData",
            "/gsg/shuaigang/v1/city/updateCityValue",
            "/gsg/shuaigang/v1/city/getBackendCityData",

            "/gsg/shuaigang/v1/upload/uploadImg",
            "/gsg/shuaigang/v1/upload/uploadFile",
            "/gsg/shuaigang/v1/upload/deleteFile",

            "/gsg/shuaigang/v1/userKudos/findByUserIdAndServiceId",

            "/gsg/lottery/v1/userPoints/getUserPiont",
            "/gsg/lottery/v1/userPoints/updateUserPoint",

//            "/gsg/lottery/v1/lottery/insertLottery",
            "/gsg/lottery/v1/lottery/getWinner",
//            "/gsg/lottery/v1/activity/insertActivity",
            "/gsg/lottery/v1/activity/getActivity",

            "/gsg/lottery/v1/mall/getMallTrade",
//            "/gsg/lottery/v1/mall/insertMallTrade",
            "/gsg/lottery/v1/mall/updateMallTrade",
//            "/gsg/lottery/v1/mall/deletedMallTrade",
            "/**/gsg/custom/**"
    };

    @Bean
    @Primary
    UserDetailsService myDetailsService() {
        return new UserDetailServiceImpl();
    }

    /**
     * 注意这个方法是注入的
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myDetailsService());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 整理格式
        http.addFilterBefore(avalidatorFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置自己的JWT验证过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                // 登录页面
                .loginPage("/gsg/authentication/Login")
                // 登录通过页面
                .loginProcessingUrl("/gsg/authentication/form")
                // 自定义认证成功处理器
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                // token的延续恶化那个方式不需要开启csrf防护
                .csrf().disable()
                // 自定义认证失败类
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 自定义权限不足处理类
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // 设置无状态链接，即不创建Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**/*.*").permitAll()
                // 白名单URL
                .antMatchers(AUTH_WHITELIST).permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
//                .antMatchers("/**/release").hasAnyRole("SUPER_ADMIN", "DEVELOP_ADMIN")


                // 配置允许匿名访问的路径
                .anyRequest()
                .authenticated();


        // 禁用缓存
        http.headers().cacheControl();
    }
}
