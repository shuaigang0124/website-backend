package com.gsg.shuaigang.mapper;

import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gaoshenggang
 * @since 2021-08-24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /** 增加 */
    int insertUser(UserDTO userDTO);

    /**
     * 校验用户名称是否唯一
     */
    User checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     */
    User checkPhoneUnique(String phone);

    /**
     * 校验email是否唯一
     */
    User checkEmailUnique(String email);

    /** 查询 */
    List<UserVO> getAllUser(String userId);

    /** 查询所有用户总数 */
    long getUserListTotal(UserDTO userDTO);

    /** 分页查询用户 */
    List<UserVO> getUserListByPage(SearchBean<UserDTO> searchBean);

    /** 删除 */
    Integer deleteUser(String[] ids);

    /**
     * 查询用户头像路径
     * @author gaoshenggang
     * @date  2021/12/1 10:38
     */
    User selectUserAvatarPath(String userId);

    /**
     * 上传修改头像
     * @author gaoshenggang
     * @date  2021/12/1 14:41
     */
    int updateAvatarPath(String avatarPath, String userId);

    /**
     * 重置密码
     */
    int resetPwd(UserDTO userDTO);

    /**
     * 通过用户ID查询用户密码
     */
    String selectUserPasswordById(String userId);

    /**
     * 修改用户信息
     */
    int updateUserPassword(String userId, String password);

    /**
     * 修改用户密码
     */
    int updatePwdByEmail(String email, String password);

    /**
     * 根据条件查询对应的用户信息（内部使用）
     * @author gaoshenggang
     * @date  2021/11/19 11:05
     */
    User getUserByCondition(UserDTO userDTO);

}
