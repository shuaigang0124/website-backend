package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.ActivityDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Lottery;
import com.gsg.commons.utils.WebPUtils;
import com.gsg.commons.vo.ActivityVO;
import com.gsg.commons.vo.MallTradeVO;
import com.gsg.commons.vo.WinnerVO;
import com.gsg.lottery.mapper.ActivityMapper;
import com.gsg.commons.model.Activity;
import com.gsg.lottery.mapper.LotteryMapper;
import com.gsg.lottery.service.IActivityService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;

import static com.gsg.commons.utils.Constants.PRIZE_SEND_EMAIL;
import static com.gsg.commons.utils.Constants.USER_KUDOS_URL;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-07
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    LotteryMapper lotteryMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 新增抽奖活动
     *
     * @author gaoshenggang
     * @date 2022/1/7 18:17
     */
    @Override
    public void insertActivity(ActivityDTO activityDTO) {
        String userId = activityDTO.getUserId();
        Integer partPoint = activityDTO.getPartPoint();
        String activityName = activityDTO.getActivityName();
        String prizeName = activityDTO.getPrizeName();
        LocalDateTime gmtTime = activityDTO.getGmtTime();
        String prizeImg = activityDTO.getPrizeImg();
        if (StringUtils.isEmpty(userId) || partPoint == null || StringUtils.isEmpty(activityName) || StringUtils.isEmpty(prizeName) || StringUtils.isEmpty(gmtTime)) {
            throw ServiceException.errorParams("请求参数有误!");
        }
        Activity activity = new Activity();
        if (!StringUtils.isEmpty(prizeImg)) {
            activity.setPrizeImg(prizeImg);
        }
        activity.setUserId(userId)
                .setPartPoint(partPoint)
                .setActivityName(activityName)
                .setPrizeName(prizeName)
                .setGmtTime(gmtTime);
        int i = activityMapper.insert(activity);
        if (i != 1) {
            throw ServiceException.busy();
        }

        // 设置定时任务自动开奖
        Date date = Date.from(gmtTime.atZone(ZoneId.systemDefault()).toInstant());
        Date nowDate = new Date();
        long delay = date.getTime() - nowDate.getTime();
        setTimer(delay, activity.getId(), prizeName);

    }

    /**
     * TODO 定时任务--开奖
     * @author shuaigang
     * @date  2022/8/7 20:15
     */
    void setTimer(long delay,Integer activityNum, String prizeName) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

        // 在一定延时(delay)之后，运行Runnable任务。
        //此任务只运行一次。
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                List<Lottery> list = lotteryMapper.getAllLottery(activityNum);
                List<String> ids = new ArrayList<>();
                for (Lottery lottery : list) {
                    ids.add(lottery.getId());
                }
                if (com.gsg.commons.utils.StringUtils.isEmpty(ids)) {
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

                String message = restTemplate.getForObject(PRIZE_SEND_EMAIL, String.class, winner.getEmail(), prizeName);
                log.debug(message);

            }
        }, delay,TimeUnit.MILLISECONDS);
    }

    /**
     * 查询所有抽奖活动
     *
     * @author gaoshenggang
     * @date 2022/1/7 18:17
     */
    @Override
    public List<ActivityVO> getAllActivity(Integer isEnabled, String joinUserId) {
        List<ActivityVO> list = activityMapper.getActivity(isEnabled, null, null);
        if (!StringUtils.isEmpty(joinUserId)) {
            for (ActivityVO activityVO : list) {
                // 判断用户是否已参与
                if (lotteryMapper.checkUserJoin(joinUserId, activityVO.getId()) == 1) {
                    activityVO.setUserJoinNum(1);
                } else {
                    activityVO.setUserJoinNum(0);
                }
                // webp访问
                String prizeImg = activityVO.getPrizeImg();
                String reUserAvatar = activityVO.getReUserAvatar();
                if (!StringUtils.isEmpty(prizeImg)) {
                    prizeImg = WebPUtils.changePathToWebp("1", prizeImg);
                    activityVO.setPrizeImg(prizeImg);
                }
                if (!StringUtils.isEmpty(reUserAvatar)) {
                    reUserAvatar = WebPUtils.changePathToWebp("1", reUserAvatar);
                    activityVO.setReUserAvatar(reUserAvatar);
                }
            }
        }
        return list;
    }

    @Override
    public List<ActivityVO> getActivityByCondition(ActivityDTO activityDTO) {
        return activityMapper.getActivity(activityDTO.getIsEnabled(), activityDTO.getUserId(), activityDTO.getPrizeUserId());
    }

}
