package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.LotteryDTO;
import com.gsg.commons.model.Lottery;
import com.gsg.commons.vo.WinnerVO;

/**
 * <p>
 * 抽奖表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
public interface ILotteryService extends IService<Lottery> {

    void insertLottery(LotteryDTO lotteryDTO);

    WinnerVO getWinnerLotteryId(Integer activityNum);
}
