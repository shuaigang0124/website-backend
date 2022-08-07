package com.gsg.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.model.User;
import com.gsg.commons.utils.Constants;
import com.gsg.customer.mapper.UserMapper;
import com.gsg.customer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/17 17:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    @Override
    public User findByMailWithDeleted(String email ,String userId) {
        return userMapper.findByMailWithDeleted(email ,userId);
    }

    /**
     * 校验email是否唯一
     */
    @Override
    public String checkEmailUnique(UserDTO userDTO)
    {

        String userId = StringUtils.isEmpty(userDTO.getId()) ? "GSG1" : userDTO.getId();
        User user = userMapper.checkEmailUnique(userDTO.getEmail());
        if (!StringUtils.isEmpty(user) && !user.getId().equals(userId)) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }
}
