package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.UserPointsDTO;
import com.gsg.commons.model.UserPoints;
import com.gsg.commons.vo.UserPointsVO;

/**
 * <p>
 * 用户积分表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
public interface IUserPointsService extends IService<UserPoints> {

    /**
     * 通过用户id查询用户积分
     * @author gaoshenggang
     * @date  2022/1/6 16:43
     */
    UserPointsVO getUserPoint(UserPointsDTO userPointsDTO);

    /**
     * 修改用户积分
     * @author gaoshenggang
     * @date  2022/1/6 16:44
     */
    void updateUserPoint(UserPointsDTO userPointsDTO);

}
