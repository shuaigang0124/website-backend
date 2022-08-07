package com.gsg.lottery.controller;


import com.gsg.commons.dto.ActivityDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.FileUploadUtils;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.ActivityVO;
import com.gsg.lottery.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gsg.commons.utils.Constants.PREFIX;
import static com.gsg.commons.utils.Constants.TEMP;

/**
 * <p>
 * 抽奖活动表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Autowired
    IActivityService activityService;

    /**
     * 新增抽奖活动
     *
     * @author gaoshenggang
     * @date 2022/1/10 10:09
     */
    @PostMapping("/insertActivity")
    public R<?> insertActivity(@RequestBody Request<ActivityDTO> request) {
        try {
            ActivityDTO activityDTO = request.getCustomData();
            String imgPath = activityDTO.getPrizeImg();
            if (!StringUtils.isEmpty(imgPath)) {
                String newPath;
                if (imgPath.contains(TEMP)) {
                    imgPath = imgPath.substring(imgPath.lastIndexOf(PREFIX));
                    newPath = FileUploadUtils.singleMove(imgPath);
                    activityDTO.setPrizeImg(newPath);
                }
            }
            activityService.insertActivity(activityDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询抽奖活动
     *
     * @author gaoshenggang
     * @date 2022/1/10 10:09
     */
    @PostMapping("/getActivity")
    public R<?> getActivity(@RequestBody Request<ActivityDTO> request) {
        try {
            ActivityDTO activityDTO = request.getCustomData();
            Integer isEnabled = activityDTO.getIsEnabled();
            String joinUserId = activityDTO.getJoinUserId();
            List<ActivityVO> list = activityService.getAllActivity(isEnabled, joinUserId);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 条件查询抽奖活动
     *
     * @author gaoshenggang
     * @date 2022/1/10 10:09
     */
    @PostMapping("/getActivityByCondition")
    public R<?> getActivityByCondition(@RequestBody Request<ActivityDTO> request) {
        try {
            ActivityDTO activityDTO = request.getCustomData();
            List<ActivityVO> list = activityService.getActivityByCondition(activityDTO);
            for (ActivityVO activityVO : list) {
                Integer isEnabled = activityVO.getIsEnabled();
                if (isEnabled == 0) {
                    activityVO.setStatusName("进行中");
                } else {
                    activityVO.setStatusName("已结束");
                }
            }
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }


}
