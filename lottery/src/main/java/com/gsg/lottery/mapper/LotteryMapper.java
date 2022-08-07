package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.Lottery;
import com.gsg.commons.vo.WinnerVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 抽奖表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Repository
public interface LotteryMapper extends BaseMapper<Lottery> {

    List<Lottery> getAllLottery(Integer activityNum);

    WinnerVO getWinnerById(String winnerId);

    Integer checkUserJoin(String userId, Integer activityNum);
}
