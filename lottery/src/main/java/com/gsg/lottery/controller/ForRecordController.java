package com.gsg.lottery.controller;


import com.gsg.commons.dto.ForRecordDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.ForRecordVO;
import com.gsg.lottery.service.IForRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商城-兑换记录表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/v1/forRecord")
public class ForRecordController {

    @Autowired
    IForRecordService forRecordService;

    /**
     * 查询所有商品兑换记录
     * @author gaoshenggang
     * @date  2022/1/24 21:02
     */
    @PostMapping("/getForRecordList")
    public R<?> getForRecordList(@RequestBody Request<ForRecordDTO> request) {
        try {
            ForRecordDTO recordDTO = request.getCustomData();
            List<ForRecordVO> list = forRecordService.getForRecordList(recordDTO);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 商品处理状态
     * @author gaoshenggang
     * @date  2022/1/24 16:19
     */
    @PostMapping("/updateRecordStatus")
    public R<?> updateRecordStatus(@RequestBody Request<ForRecordDTO> request) {
        try {
            ForRecordDTO recordDTO = request.getCustomData();
            forRecordService.updateRecordStatus(recordDTO);
            return R.ok("状态更新成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
