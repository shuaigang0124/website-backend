package com.gsg.shuaigang.controller;

import com.gsg.commons.dto.RequestDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.User;
import com.gsg.commons.utils.*;
import com.gsg.commons.dto.UserDTO;
import com.gsg.commons.vo.PageResponseVO;
import com.gsg.commons.vo.UserDetailsVO;
import com.gsg.shuaigang.config.JwtProperties;
import com.gsg.shuaigang.service.IUserService;
import com.gsg.commons.vo.UserVO;
import com.gsg.shuaigang.utils.JwtTokenUtil;
import com.gsg.shuaigang.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gaoshenggang
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/v1/user")
public class UserController extends BaseController{

    @Autowired
    IUserService userService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private JwtProperties jwtProperties;

    @PostMapping("/insertUser")
    public R<?> insertUser(@RequestBody @Valid Request<UserDTO> request, BindingResult bindingResult){
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }
        try {
            UserDTO userDTO = request.getCustomData();
            /*入参校验*/
            Assert.notNull(userDTO.getUserName(),"参数异常，userName 必传");
            Assert.notNull(userDTO.getPassword(),"参数异常，password 必传");
            Assert.notNull(userDTO.getBirthday(),"参数异常，birthday 必传");
            Assert.notNull(userDTO.getEmail(),"参数异常，email 必传");
            Assert.notNull(userDTO.getPhone(),"参数异常，phone 必传");
            Assert.notNull(userDTO.getSex(),"参数异常，sex 必传");
            Assert.notNull(userDTO.getAddress(),"参数异常，address 必传");
            /*检查待新增的用户相关信息是否存在*/
            if (Constants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userDTO))) {
                return R.failed(new ServiceException("注册用户'" + userDTO.getUserName() + "'失败，用户名已存在！"));
            }
//            if (!StringUtils.isEmpty(userDTO.getPhone())
//                    && Constants.NOT_UNIQUE.equals(userService.checkPhoneUnique(userDTO))) {
//                return R.failed(new ServiceException("注册用户'" + userDTO.getUserName() + "'失败，手机号码已存在！"));
//            }
            if (!StringUtils.isEmpty(userDTO.getEmail())
                    && Constants.NOT_UNIQUE.equals(userService.checkEmailUnique(userDTO))) {
                return R.failed(new ServiceException("注册用户'" + userDTO.getUserName() + "'失败，邮箱账号已存在！"));
            }

            String userId = "GSG" + PKGenerator.generate();
            userDTO.setId(userId)
                    // 对用户密码加密
                    .setPassword(PasswordUtils.encode(userDTO.getPassword()));
            userService.insertUser(userDTO);
            return R.ok("注册成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    @PostMapping("/getAllUser")
    public R<?> getAllUser(@RequestBody Request<UserDTO> request){
        try {
            UserDTO userDTO = request.getCustomData();
            Page page = userDTO.getPage();
            List<UserVO> list = new ArrayList<>();
            // 如果没传分页参数，就查询全部数据
            if (StringUtils.isEmpty(page)) {
                list = userService.getAllUser(userDTO, null);
                return R.ok(list);
            }
            // 分页查询 ,先格式化传入的分页参数
            Page pageAfter = pageDeal(page);
            userDTO.setPage(pageAfter);
            PageResponseVO<UserVO> pageResponseVO = new PageResponseVO<>();
            // 1、查询数据量总数
            long total = userService.getUserListTotal(userDTO);
            if (total == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("数据总量为0,未查询到数据!"+pageResponseVO);
            }
            // 2、分页查询当页数据
            list = userService.getAllUser(userDTO, pageAfter);
            if (list == null || list.size() == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到国家城市列表!"+pageResponseVO);
            }
            // 封装分页结果数据
            pageResponseVO.setTotalCount(total);
            pageResponseVO.setCount(list.size());
            pageResponseVO.setCurrentPage(pageAfter.getIndex());
            pageResponseVO.setResultList(list);

            return R.ok(pageResponseVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 通过userId查询用户详情
     * @author gaoshenggang
     * @date  2021/11/26 13:31
     */
    @PostMapping("/getUserDetails")
    public R<?> getUserDetails(@RequestBody Request<RequestDTO> request){
        try {
            RequestDTO requestDTO = request.getCustomData();
            UserDetailsVO list = userService.getUserDetails(requestDTO.getUserId());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 上传修改头像
     * @author gaoshenggang
     * @date  2021/12/1 14:34
     */
    @PostMapping("/updateAvatar")
    public R<?> updateAvatar(@RequestBody Request<UserDTO> request) {
        try {
            UserDTO userDTO = request.getCustomData();
            userService.updateAvatar(userDTO);
            return R.ok("保存成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    @PostMapping("/updateUser")
    public R<?> updateUser(@RequestBody Request<UserDTO> request){
        try {
            UserDTO userDTO = request.getCustomData();

            /*检查待新增的用户相关信息是否存在*/
            if (!StringUtils.isEmpty(userDTO.getUserName()) && Constants.NOT_UNIQUE.equals(userService.checkUserNameUnique(userDTO))) {
                return R.failed(new ServiceException("修改用户失败，用户名已存在！"));
            }
            if (!StringUtils.isEmpty(userDTO.getPhone())
                    && Constants.NOT_UNIQUE.equals(userService.checkPhoneUnique(userDTO))) {
                return R.failed(new ServiceException("修改用户失败，手机号码已存在！"));
            }
            if (!StringUtils.isEmpty(userDTO.getEmail())
                    && Constants.NOT_UNIQUE.equals(userService.checkEmailUnique(userDTO))) {
                return R.failed(new ServiceException("修改用户失败，邮箱账号已存在！"));
            }

            userService.updateUser(userDTO);
            return R.ok("修改成功!");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    @PostMapping("/deleteUser")
    public R<?> deleteUser(@RequestBody Request<UserDTO> request){
        try {
            UserDTO userDTO = request.getCustomData();
            userService.deleteUser(userDTO);
            return R.ok("删除成功!");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd")
    public R<?> resetPwd(@RequestBody Request<UserDTO> request) {
        try {
            UserDTO userDTO = request.getCustomData();

            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String authToken = httpServletRequest.getHeader(jwtProperties.getHeader());
            String taker = jwtTokenUtil.getUsernameFromToken(authToken);
            if(StringUtils.isEmpty(taker)){
                throw ServiceException.errorParams("请先进行登录！");
            }

            if (!"GSG1".equals(taker)) {
                throw  ServiceException.errorParams("权限不足！");
            }

            /*入参校验*/
            Assert.notNull(userDTO.getIds(), "传入参数有误！");

            for (String userId : userDTO.getIds()) {
                userService.checkUserAllowed(userId);
            }

            /*对用户密码加密*/
            userDTO.setPassword(PasswordUtils.encode("Aa123456"));
            int resetPwdNum = userService.resetPwd(userDTO);

            return resetPwdNum > 0 ?
                    R.ok("重置成功！") :
                    R.ok("重置失败！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * TODO 通过邮箱修改用户密码（提供忘记密码，邮箱验证通过后使用）
     * @author shuaigang
     * @date  2022/7/28 16:14
     */
    @RequestMapping("/updatePwdByEmail")
    public R<?> updatePwdByEmail(@RequestBody Request<UserDTO> request) {
        try {
            UserDTO userDTO = request.getCustomData();
            String email = userDTO.getEmail();
            String newPassword = userDTO.getPassword();

            /*入参校验*/
            Assert.notNull(email, "请求参数有误！");
            Assert.notNull(newPassword, "请求参数有误！");

            userDTO.setPassword(PasswordUtils.encode(userDTO.getPassword()));

            int resetPwdNum = userService.updatePwdByEmail(userDTO);

            return resetPwdNum > 0 ?
                    R.ok("修改成功！") :
                    R.ok("修改失败！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改用户密码
     */
    @RequestMapping("/updatePwd")
    public R<?> updatePwd(@RequestBody Request<UserDTO> request) {
        try {
            UserDTO userDTO = request.getCustomData();
            String oldPassword = userDTO.getOldPassword();
            String newPassword = userDTO.getNewPassword();

            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String authToken = httpServletRequest.getHeader(jwtProperties.getHeader());
            String userId = jwtTokenUtil.getUsernameFromToken(authToken);
            if(StringUtils.isEmpty(userId)){
                userId = userDTO.getId();
            }

            /*入参校验*/
            Assert.notNull(userId, "请求参数有误！");
            Assert.notNull(oldPassword, "请求参数有误！");
            Assert.notNull(newPassword, "请求参数有误！");

            String password = userService.selectUserPasswordById(userId);

            /*判断旧密码是否正确*/
            if (!PasswordUtils.matches(oldPassword, password)) {
                return R.ok("旧密码输入错误！");
            }
            /*判断新旧密码是否相同*/
            if (PasswordUtils.matches(newPassword, password)) {
                return R.ok("新密码不能与旧密码相同！");
            }

            userDTO.setId(userId)
                    .setPassword(PasswordUtils.encode(userDTO.getNewPassword()));

            int resetPwdNum = userService.updatePwd(userDTO);

            return resetPwdNum > 0 ?
                    R.ok("修改成功！") :
                    R.ok("修改失败！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 校验用户密码正确性
     */
    @RequestMapping("/checkPwd")
    public R<?> checkPwd(@RequestBody Request<UserDTO> request) {
        try {
            UserDTO userDTO = request.getCustomData();
            String inputPassword = userDTO.getPassword();

            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String userId = userDTO.getId();
            if(StringUtils.isEmpty(userId)){
                String authToken = httpServletRequest.getHeader(jwtProperties.getHeader());
                userId = jwtTokenUtil.getUsernameFromToken(authToken);
            }

            /*入参校验*/
            Assert.notNull(userId, "用户id不能为空！");
            Assert.notNull(inputPassword, "输入密码不能为空！");

            String password = userService.selectUserPasswordById(userId);

            /*判断旧密码是否正确*/
            if (!PasswordUtils.matches(inputPassword, password)) {
                return R.ok("原密码输入错误！");
            }

            return R.ok("原密码输入正确！");

        } catch (Exception e) {
            return R.failed(e);
        }
    }


    /* 后台登录使用 */

    @RequestMapping("/getUserByUserName")
    public User getUserByUserName(String userName){
        return userService.findUserByUserName(userName);
    }

    @RequestMapping("/getUserById")
    public User getUserById(String id){
        return userService.findUserById(id);
    }

    @RequestMapping("/getUserByPhone")
    public User getUserByPhone(String phone){
        return userService.findUserByPhone(phone);
    }

    @RequestMapping("/getUserByEmail")
    public User getUserByEmail(String email){
        return userService.findUserByEmail(email);
    }


}
