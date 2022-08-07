package com.gsg.customer.controller;

import com.gsg.commons.dto.MailCodeDTO;
import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.User;
import com.gsg.commons.utils.*;
import com.gsg.customer.mapper.UserMapper;
import com.gsg.customer.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * TODO 邮箱验证码功能
 *
 * @author shuaigang
 * @date 2021/12/17 16:55
 */
@RestController
@RequestMapping("/v1/email")
@Slf4j
public class SmsCodeController {

    @Resource
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String mailUserName;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    IUserService userService;

    @Autowired
    MailCodeSendThread mailCodeSendThread;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/sendMailCode")
    public R<?> sendMailCode(@RequestBody @Valid Request<MailCodeDTO> request, BindingResult bindingResult) throws IllegalAccessException {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }

        MailCodeDTO mailCodeDTO = request.getCustomData();

        String email = mailCodeDTO.getEmail();
        String userId = mailCodeDTO.getUserId();
        Integer type = mailCodeDTO.getType();
        /* 1-修改密码调用(校验邮箱存在,且是否是当前用户) */
        if (type == 0) {
            User result = userService.findByMailWithDeleted(email, userId);
            if (ObjectUtils.isEmpty(result)) {
                /* 邮箱不一致 */
                return R.ok("确认登录用户Id不一致，登陆失败");
            }
        }
        /* 2-忘记密码发送邮箱 (校验邮箱是否存在 ,存在即可通过) */
        if (type == 1) {
            User byMail = userMapper.findUserByMail(email);
            if (ObjectUtils.isEmpty(byMail)) {
                return R.ok("邮箱不存在");
            }
        }
        /* 3-换绑邮箱((校验邮箱是否存在 ,存在不可通过)) */
        if (type == 2) {
            User byMail = userMapper.findUserByMailContainDeleted(email);
            if (!ObjectUtils.isEmpty(byMail)) {
                return R.ok("该邮箱已被注册");
            }
        }

        String comment = setComment(email);
        /* 异步发送邮箱验证码 */
        mailCodeSendThread.sendMailThread(email, comment);
        return R.ok("验证码发送成功");
    }

    /**
     * 发送中奖信息到邮箱
     */
    @RequestMapping("/sendMailPrizeInfo")
    public String sendMailPrizeInfo(String email, String prizeName) throws IllegalAccessException {

        /* 校验邮箱是否存在 ,不存在直接返回 */
        User byMail = userMapper.findUserByMailContainDeleted(email);
        if (ObjectUtils.isEmpty(byMail)) {
            log.debug("未查询到该邮箱账号{}", email);
            return "未查询到该邮箱账号";
        }

        /* 异步发送邮箱验证码 */
        mailCodeSendThread.sendMailInfoThread(email, prizeName);
        return "发送成功";
    }

    /**
     * 校验邮箱验证码
     *
     * @return com.caftrade.info.commons.utils.Result<?>
     * @author ianjiang
     * @date 2021/7/16 17:04
     */
    @PostMapping("/checkMailCode")
    public R<?> checkMailCode(@RequestBody @Valid Request<MailCodeDTO> request, BindingResult bindingResult) throws IllegalAccessException {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }

        MailCodeDTO mailCodeDTO = request.getCustomData();
        String mail = mailCodeDTO.getEmail();
        String validCode = mailCodeDTO.getValidCode();

        String listKey = mail + ".list";
        String codeKey = mail + ".code";

        if (!redisUtils.isExist(codeKey)) {
            return R.ok("验证码已过期");
        }

        if (redisUtils.getString(codeKey).equals(validCode)) {
            redisUtils.remove(codeKey);
            redisUtils.remove(listKey);
            return R.ok("验证通过,前往下一步");
        } else {
            return R.ok("验证码错误");
        }

    }

    /**
     * 生成邮箱验证码内容
     */
    public String setComment(String email) {
        String mailCode = setMailCode(email);
        return "您好!\n\n" + "-----您本次的验证码为：" + mailCode + "    -----" + "\n\n" +
                "-----请注意:需要您在收到邮件后5分钟内使用,否则验证码将失效!非本人操作请忽略,谢谢!-----\n\n";
    }

    /**
     * 生成邮箱验证码
     */
    public String setMailCode(String email) {
        String listKey = email + ".list";
        String lockKey = email + ".lock";
        String codeKey = email + ".code";

        Long nowTimeStamp = DateFormateUtils.getUtcCurrentTimeMillis();

        Long size = redisUtils.listSize(listKey);
        // 10分钟超三次锁定
        if (size == 3) {
            Long firstTimeStamp = redisUtils.getTimeStamp(listKey);
            if (nowTimeStamp - firstTimeStamp < 1000 * 60 * 10) {
                redisUtils.remove(listKey);
//                redisUtils.remove(codeKey);
                /* 生成锁 */
                redisUtils.set(lockKey, nowTimeStamp + "", 30);
            }
        }

        if (redisUtils.isExist(lockKey)) {
            log.error("10分钟内发送超过三次，请30分钟后再试！");
            throw ServiceException.errorParams("10分钟内发送超过三次，请30分钟后再试");
        }

        redisUtils.setList(listKey, nowTimeStamp + "");
        // 设置list超时 5分钟
        redisUtils.setExpire(listKey, 5L, TimeUnit.MINUTES);

        String code = String.valueOf(new Random().nextInt(9000) + 1000);

        if (redisUtils.isExist(codeKey)) {
            code = redisUtils.getString(codeKey);
        }

        log.info("验证码:" + code);
        redisUtils.set(codeKey, code, 5);
        return code;
    }

    /**
     * 校验邮箱是否被使用
     */
    @PostMapping("/validMail")
    public R<?> validMail(@RequestBody @Valid Request<UserDTO> request, BindingResult bindingResult) throws IllegalAccessException {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }
        UserDTO userDTO = request.getCustomData();

        if (!StringUtils.isEmpty(userDTO.getEmail())
                && Constants.NOT_UNIQUE.equals(userService.checkEmailUnique(userDTO))) {
            return R.failed(new ServiceException("失败，邮箱账号已存在！"));
        }

        return R.ok("校验邮箱通过!");

    }

}
