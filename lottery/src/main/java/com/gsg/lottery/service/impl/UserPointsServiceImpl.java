package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.UserPointsDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.StringUtils;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.lottery.mapper.UserPointsMapper;
import com.gsg.commons.model.UserPoints;
import com.gsg.lottery.service.IUserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户积分表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Service
public class UserPointsServiceImpl extends ServiceImpl<UserPointsMapper, UserPoints> implements IUserPointsService {

    @Autowired
    UserPointsMapper userPointsMapper;

    /**
     * 查询用户积分
     * @author gaoshenggang
     * @date  2022/1/6 17:17
     */
    @Override
    public UserPointsVO getUserPoint(UserPointsDTO userPointsDTO) {
        String userId = userPointsDTO.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("用户id必传！");
        }
        return checkUserPoint(userId);
    }

    /** 校验用户积分数据 */
    public UserPointsVO checkUserPoint(String userId) {
        UserPointsVO userPoint = userPointsMapper.getUserPoint(userId);
        // 如果不存在用户积分数据则添加
        if (userPoint == null) {
            UserPoints userPoints = new UserPoints();
            userPoints.setUserId(userId);
            int i = userPointsMapper.insert(userPoints);
            if (i != 1) {
                throw ServiceException.busy();
            }
            return userPointsMapper.getUserPoint(userId);
        }
        return userPoint;
    }

    /**
     * 修改用户积分 增加 或 减少
     * @author gaoshenggang
     * @date  2022/1/6 17:17
     */
    @Override
    public void updateUserPoint(UserPointsDTO userPointsDTO) {
        Integer ident = userPointsDTO.getIdent();
        String userId = userPointsDTO.getUserId();
        Integer partPoint = userPointsDTO.getPoint();
        if (ident == null) {
            throw ServiceException.errorParams("请求参数有误");
        }
        if (partPoint == null) {
            throw ServiceException.errorParams("参与积分必传");
        }
        if (StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("用户id必传！");
        }
        UserPointsVO userPointsVO = checkUserPoint(userId);
        Integer point = userPointsVO.getPoint();
        if (ident < 0 ) {
            point -= partPoint;
        } else {
            point += partPoint;
        }
        if (point < 0) {
            throw ServiceException.errorParams("积分不足！");
        }
        int i = userPointsMapper.updateUserPiont(userId,point);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }
}





