package com.gsg.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.model.User;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/17 17:00
 */
public interface IUserService extends IService<User> {

    /**
     * 根据邮箱查询用户(包含已注销) -->不包含已注销
     * @param email
     * @return
     */
    User findByMailWithDeleted(String email ,String userId);

    /**
     * 校验用户邮箱是否唯一
     */
    String checkEmailUnique(UserDTO userDTO);

}
