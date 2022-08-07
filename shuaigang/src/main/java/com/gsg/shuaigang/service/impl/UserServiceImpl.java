package com.gsg.shuaigang.service.impl;

import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.*;
import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.model.User;
import com.gsg.commons.vo.UserDetailsVO;
import com.gsg.shuaigang.mapper.UserMapper;
import com.gsg.shuaigang.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.gsg.commons.utils.Constants.PREFIX;
import static com.gsg.commons.utils.Constants.TEMP;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gaoshenggang
 * @since 2021-08-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 插入用户
     * @author gaoshenggang
     * @date  2021/8/24 15:21
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(UserDTO userDTO) {
        userDTO.setGmtCreate(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now())
                // 默认头像
                .setAvatar("/gsg/static-resource/formal/2/20211218/1639807415900-1622025345981709.jpg");
        return userMapper.insertUser(userDTO);
    }

    /**
     * 校验用户名称是否唯一
     */
    @Override
    public String checkUserNameUnique(UserDTO userDTO)
    {
        String userId = StringUtils.isEmpty(userDTO.getId()) ? "GSG1" : userDTO.getId();
        User user = userMapper.checkUserNameUnique(userDTO.getUserName());
        if (!StringUtils.isEmpty(user) && !user.getId().equals(userId)) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    /**
     * 校验用户手机号是否唯一
     */
    @Override
    public String checkPhoneUnique(UserDTO userDTO)
    {
        String userId = StringUtils.isEmpty(userDTO.getId()) ? "GSG1" : userDTO.getId();
        User user = userMapper.checkPhoneUnique(userDTO.getPhone());
        if (!StringUtils.isEmpty(user) && !user.getId().equals(userId)) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
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

    /** 查询用户（传入page参数，则为分页查询） */
    @Override
    public List<UserVO> getAllUser(UserDTO userDTO, Page page) {
        if (StringUtils.isEmpty(page)) {
            return userMapper.getAllUser(null);
        }
        SearchBean<UserDTO> userList = new SearchBean<>(userDTO.getPage(), userDTO);
        return userMapper.getUserListByPage(userList);
    }

    @Override
    public UserDetailsVO getUserDetails(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("userId不能为空");
        }
        List<UserVO> list = userMapper.getAllUser(userId);
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        for (UserVO userVO : list) {
            if (!StringUtils.isEmpty(userVO.getAvatar())) {
                String avatarPath = userVO.getAvatar();
                // 20211210 生成WebP图片副本
                avatarPath = WebPUtils.changePathToWebp("1", avatarPath);
                userVO.setAvatar(avatarPath);
            }
            userDetailsVO.setId(userVO.getId())
                    .setUserName(userVO.getUserName())
                    .setBirthday(userVO.getBirthday())
                    .setEmail(userVO.getEmail())
                    .setPhone(userVO.getPhone())
                    .setAvatar(userVO.getAvatar())
                    .setSex(userVO.getSex())
                    .setAddress(userVO.getAddress())
                    .setCityName(userVO.getCityName());
        }


        return userDetailsVO;
    }

    /** 查询用户数量（传入sex则查询性别数量） */
    @Override
    public long getUserListTotal(UserDTO userDTO) {
        return userMapper.getUserListTotal(userDTO);
    }

    /** 上传修改用户头像 */
    @Override
    public void updateAvatar(UserDTO userDTO) {
        if (userDTO == null) {
            throw ServiceException.errorParams("请求参数有误");
        }
        String avatarPath = userDTO.getAvatar();
        String userId = userDTO.getId();
        if (StringUtils.isEmpty(avatarPath) || StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("请求参数有误");
        }
        String newPath = "";
        if (avatarPath.contains(TEMP)) {
            avatarPath = avatarPath.substring(avatarPath.lastIndexOf(PREFIX));
            newPath = FileUploadUtils.singleMove(avatarPath);
        }
        int row = userMapper.updateAvatarPath(newPath, userId);
        if (row != 1) {
            throw ServiceException.busy();
        }
    }

    /** 修改用户 */
    @Override
    public void updateUser(UserDTO userDTO) {

        String id = userDTO.getId();
        if (id == null) {
            throw ServiceException.errorParams("请传入用户id");
        }
        User user = new User();
        user.setId(id)
                .setGmtModified(LocalDateTime.now());
        if (!StringUtils.isEmpty(userDTO.getUserName())) {
            user.setUserName(userDTO.getUserName());
        }if (!StringUtils.isEmpty(userDTO.getPassword())) {
            user.setPassword(PasswordUtils.encode(userDTO.getPassword()));
        }if (!StringUtils.isEmpty(userDTO.getBirthday())) {
            user.setBirthday(userDTO.getBirthday());
        }if (!StringUtils.isEmpty(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }if (!StringUtils.isEmpty(userDTO.getPhone())) {
            user.setPhone(userDTO.getPhone());
        }if (!StringUtils.isEmpty(userDTO.getAvatar())) {
            user.setAvatar(userDTO.getAvatar());
        }if (!StringUtils.isEmpty(userDTO.getRole())) {
            user.setRole(userDTO.getRole());
        }if (!StringUtils.isEmpty(userDTO.getSex())) {
            user.setSex(userDTO.getSex());
        }if (!StringUtils.isEmpty(userDTO.getAddress())) {
            user.setAddress(userDTO.getAddress());
        }
        int i = userMapper.updateById(user);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /** 删除用户 */
    @Override
    public void deleteUser(UserDTO userDTO) {
        String[] ids = userDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("ids传入为空！");
        }
        Integer i = userMapper.deleteUser(ids);
        if (i < 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 校验用户是否允许操作
     */
    @Override
    public void checkUserAllowed(String userId)
    {
        if (!StringUtils.isEmpty(userId) && "GSG1".equals(userId)) {
            throw new ServiceException("不允许操作管理员用户");
        }
    }

    /**
     * 重置用户密码
     */
    @Override
    public int resetPwd(UserDTO userDTO)
    {
        return userMapper.resetPwd(userDTO);
    }

    /**
     * 通过用户ID查询用户密码
     */
    @Override
    public String selectUserPasswordById(String userId) {
        return userMapper.selectUserPasswordById(userId);
    }

    /**
     * 修改用户密码
     */
    @Override
    public int updatePwd(UserDTO userDTO)
    {
        String userId = userDTO.getId();
        String password = userDTO.getPassword();
        return userMapper.updateUserPassword(userId, password);
    }

    /**
     * 修改用户密码
     */
    @Override
    public int updatePwdByEmail(UserDTO userDTO)
    {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        return userMapper.updatePwdByEmail(email, password);
    }



    /**
     * 根据条件查询对应的用户信息（内部登录使用）
     * @author gaoshenggang
     * @date  2021/11/19 11:13
     */
    @Override
    public User findUserByUserName(String userName) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        return userMapper.getUserByCondition(userDTO);
    }

    @Override
    public User findUserById(String id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        return userMapper.getUserByCondition(userDTO);
    }

    @Override
    public User findUserByPhone(String phone) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhone(phone);
        return userMapper.getUserByCondition(userDTO);
    }

    @Override
    public User findUserByEmail(String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        return userMapper.getUserByCondition(userDTO);
    }

}
