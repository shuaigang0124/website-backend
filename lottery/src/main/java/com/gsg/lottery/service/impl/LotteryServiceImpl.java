package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.LotteryDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Activity;
import com.gsg.commons.model.UserPoints;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.utils.StringUtils;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.commons.vo.WinnerVO;
import com.gsg.lottery.mapper.ActivityMapper;
import com.gsg.lottery.mapper.LotteryMapper;
import com.gsg.commons.model.Lottery;
import com.gsg.lottery.mapper.UserPointsMapper;
import com.gsg.lottery.service.ILotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 抽奖表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery> implements ILotteryService {

    @Autowired
    LotteryMapper lotteryMapper;

    @Autowired
    UserPointsMapper userPointsMapper;

    @Autowired
    ActivityMapper activityMapper;

    /**
     * 新增抽奖数据
     * @author gaoshenggang
     * @date  2022/1/6 18:48
     */
    @Override
    public void insertLottery(LotteryDTO lotteryDTO) {

        String userId = lotteryDTO.getUserId();
        Integer point = lotteryDTO.getPoint();
        Integer activityNum = lotteryDTO.getActivityNum();
        if (StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("用户主键不能为空");
        }
        if (point == null) {
            throw ServiceException.errorParams("参与积分不能为空");
        }
        if (activityNum == null) {
            throw ServiceException.errorParams("活动主键不能为空");
        }
        // 用户积分数据判断（如果免费积分参与即参与积分为0时，跳过积分消耗）
        if (point != 0) {
            UserPointsVO userPoint = userPointsMapper.getUserPoint(userId);
            // 如果不存在用户积分数据则添加
            if (org.springframework.util.StringUtils.isEmpty(userPoint)) {
                UserPoints userPoints = new UserPoints();
                userPoints.setUserId(userId);
                int row = userPointsMapper.insert(userPoints);
                if (row != 1) {
                    throw ServiceException.busy();
                }
            }
            Integer pointNum = userPoint.getPoint();
            pointNum -= point;
            if (pointNum < 0) {
                throw ServiceException.errorParams("用户积分不足！");
            }
            // 用户积分消耗
            int row1 = userPointsMapper.updateUserPiont(userId, pointNum);
            if (row1 != 1) {
                throw ServiceException.busy();
            }
        }

        // 判断用户是否已参与
        if (lotteryMapper.checkUserJoin(userId, activityNum) == 1) {
            throw ServiceException.errorParams("用户已参与，请勿重复参与！");
        }

        // 用户数据添加
        Integer peopleNum = activityMapper.getActivityById(activityNum);
        peopleNum += 1;
        Activity activity = new Activity();
        activity.setId(activityNum)
                .setPeopleNum(peopleNum);
        int row2 = activityMapper.updateById(activity);
        if (row2 != 1) {
            throw ServiceException.busy();
        }
        // 新增抽奖数据
        String id = "LTY" + PKGenerator.generate();

        Lottery lottery = new Lottery();
        lottery.setId(id)
                .setUserId(userId)
                .setActivityNum(activityNum)
                .setGmtCreate(LocalDateTime.now());

        int i = lotteryMapper.insert(lottery);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 随机抽奖
     * @author gaoshenggang
     * @date  2022/1/6 19:52
     */
    @Override
    public WinnerVO getWinnerLotteryId(Integer activityNum) {
        List<Lottery> list = lotteryMapper.getAllLottery(activityNum);
        List<String> ids = new ArrayList<>();
        for (Lottery lottery : list) {
            ids.add(lottery.getId());
        }
        if (StringUtils.isEmpty(ids)) {
            throw ServiceException.errorParams("参与人数异常");
        }
        // 随即一位中奖人id
        String winnerId = ids.get(new Random().nextInt(ids.size()));

        Lottery lottery = new Lottery();
        lottery.setId(winnerId)
                .setWinning(1);

        int row = lotteryMapper.updateById(lottery);
        if (row != 1) {
            throw ServiceException.busy();
        }
        WinnerVO winner = lotteryMapper.getWinnerById(winnerId);
        // 更新活动表获奖人id
        Activity activity = new Activity();
        activity.setId(activityNum)
                .setPrizeUserId(winner.getUserId())
                .setIsEnabled(1);
        int i = activityMapper.updateById(activity);
        if (i < 1) {
            throw ServiceException.busy();
        }

        return winner;
    }


}
