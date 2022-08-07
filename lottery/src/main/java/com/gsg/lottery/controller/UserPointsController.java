package com.gsg.lottery.controller;


import com.gsg.commons.dto.UserPointsDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.lottery.service.IUserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户积分表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/v1/userPoints")
public class UserPointsController {

    @Autowired
    IUserPointsService userPointsService;

    /**
     * 查询用户积分
     * @author gaoshenggang
     * @date  2022/1/6 17:18
     */
    @PostMapping("/getUserPiont")
    public R<?> getUserPiont(@RequestBody Request<UserPointsDTO> request) {
        try {
            UserPointsDTO userPointsDTO = request.getCustomData();
            UserPointsVO userPoint = userPointsService.getUserPoint(userPointsDTO);
            return R.ok(userPoint);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改用户积分  增加 或 减少
     * @author gaoshenggang
     * @date  2022/1/6 17:18
     */
    @PostMapping("/updateUserPoint")
    public R<?> updateUserPoint(@RequestBody Request<UserPointsDTO> request) {
        try {
            UserPointsDTO userPointsDTO = request.getCustomData();
            userPointsService.updateUserPoint(userPointsDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
