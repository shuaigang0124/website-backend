package com.gsg.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * 发送邮箱验证码
 * @author shuaigang
 */
@RestController
@Slf4j
public class MailCodeSendThread {
    @Resource
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String mailUserName;
    /**
     * 异步发送邮箱验证码
     */
    @Async
    public void sendMailThread(String mail,String comment) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(mailUserName);
            messageHelper.setTo(mail);
            messageHelper.setSubject("【xxxxxxxxx.com】邮箱验证码");
            messageHelper.setText(comment);
            log.debug("发送验证码成功{}", messageHelper);
            this.javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("[{}] 发送邮箱验证码失败", mail);
        }
    }

    /**
     * 异步发送信息到指定邮箱
     */
    @Async
    public void sendMailInfoThread(String mail,String prizeName) {
        String comment = "您好!\n\n" + "-----您在本网站的抽奖活动中抽中了奖品：" + prizeName + "    -----" + "\n\n" +
                "-----请注意:中奖后请前往该网站或小程序联系相关人员在3日内完成兑奖,否则将视为放弃领奖!非本人操作请忽略,谢谢!-----\n\n";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(mailUserName);
            messageHelper.setTo(mail);
            messageHelper.setSubject("【xxxxxxx.com】中奖提醒");
            messageHelper.setText(comment);
            log.debug("消息发送成功{}", messageHelper);
            this.javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("[{}] 发送邮箱信息失败", mail);
        }
    }
}
