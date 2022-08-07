package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.ActivityDTO;
import com.gsg.commons.model.Activity;
import com.gsg.commons.vo.ActivityVO;

import java.util.List;

/**
 * <p>
 * 抽奖活动表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-07
 */
public interface IActivityService extends IService<Activity> {

    void insertActivity(ActivityDTO activityDTO);

    List<ActivityVO> getAllActivity(Integer isEnabled,String joinUserId);

    List<ActivityVO> getActivityByCondition(ActivityDTO activityDTO);

}
