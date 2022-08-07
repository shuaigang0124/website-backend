package com.gsg.shuaigang.service;

import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.utils.Page;
import com.gsg.commons.vo.TestVO;
import com.gsg.commons.vo.UserDetailsVO;
import com.gsg.commons.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gaoshenggang
 * @since 2021-08-24
 */
public interface IUserService extends IService<User> {

    int insertUser(UserDTO userDTO);

    /**
     * 校验用户名称是否唯一
     */
    String checkUserNameUnique(UserDTO userDTO);

    /**
     * 校验用户电话号码是否唯一
     */
    String checkPhoneUnique(UserDTO userDTO);

    /**
     * 校验用户邮箱是否唯一
     */
    String checkEmailUnique(UserDTO userDTO);

    /** 查询所有用户（含分页查询） */
    List<UserVO> getAllUser(UserDTO userDTO, Page page);

    /** 通过userId查询用户详情 */
    UserDetailsVO getUserDetails(String userId);

    /** 查询用户总数（若传入sex，则查询性别总数） */
    long getUserListTotal(UserDTO userDTO);

    /** 上传修改头像 */
    void updateAvatar(UserDTO userDTO);

    /** 修改用户 */
    void updateUser(UserDTO userDTO);

    /** 批量删除用户 */
    void deleteUser(UserDTO userDTO);

    /**
     * 校验用户是否允许操作
     */
    void checkUserAllowed(String userId);

    /**
     * 重置用户密码
     */
    int resetPwd(UserDTO userDTO);

    /**
     * 通过用户ID查询用户密码
     */
    String selectUserPasswordById(String userId);

    /**
     * 修改用户密码
     */
    int updatePwd(UserDTO userDTO);

    /**
     * 修改用户密码
     */
    int updatePwdByEmail(UserDTO userDTO);



    /**
     * 以下接口用于内部后台的登录使用
     * @author gaoshenggang
     * @date  2021/11/15 14:02
     */
    User findUserByUserName(String userName);

    User findUserById(String id);

    User findUserByPhone(String phone);

    User findUserByEmail(String email);


}
