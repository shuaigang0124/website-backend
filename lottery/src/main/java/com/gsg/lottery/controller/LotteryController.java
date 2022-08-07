package com.gsg.lottery.controller;


import com.gsg.commons.dto.LotteryDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.WinnerVO;
import com.gsg.lottery.service.IActivityService;
import com.gsg.lottery.service.ILotteryService;
import com.gsg.lottery.service.IUserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 抽奖表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/v1/lottery")
public class LotteryController {

    @Autowired
    ILotteryService lotteryService;

    @Autowired
    IUserPointsService userPointsService;

    @Autowired
    IActivityService activityService;

    /**
     * 参与抽奖
     * @author gaoshenggang
     * @date  2022/1/6 19:53
     */
    @PostMapping("/insertLottery")
    public R<?> insertLottery(@RequestBody Request<LotteryDTO> request) {
        try {
            LotteryDTO lotteryDTO = request.getCustomData();
            lotteryService.insertLottery(lotteryDTO);
            return R.ok("参与成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 随机抽奖并返回结果
     * @author gaoshenggang
     * @date  2022/1/6 19:53
     */
    @PostMapping("/getWinner")
    public R<?> getWinner(@RequestBody Request<LotteryDTO> request) {
        try {
            LotteryDTO lotteryDTO = request.getCustomData();
            Integer activityNum = lotteryDTO.getActivityNum();
            WinnerVO winnerVO = lotteryService.getWinnerLotteryId(activityNum);

            return R.ok(winnerVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }


}
