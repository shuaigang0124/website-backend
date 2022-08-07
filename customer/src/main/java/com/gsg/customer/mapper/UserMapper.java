package com.gsg.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.User;
import org.springframework.stereotype.Repository;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/17 17:03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User findByMailWithDeleted(String email,String userId);

    User findUserByMail(String email);

    User findUserByMailContainDeleted(String mail);

    /**
     * 校验email是否唯一
     */
    User checkEmailUnique(String email);

}
