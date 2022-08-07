package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.ActivityDTO;
import com.gsg.commons.model.Activity;
import com.gsg.commons.vo.ActivityVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 抽奖活动表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-07
 */
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {

    /** 查询所有抽奖活动 */
    List<ActivityVO> getActivity(Integer isEnabled, String userId, String prizeUserId);

    Integer getActivityById(Integer activityNum);


}
