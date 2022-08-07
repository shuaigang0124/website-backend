package com.gsg.lottery.controller;


import com.gsg.commons.dto.AttendanceRecordDTO;
import com.gsg.commons.dto.MallDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.AttendanceRecordVO;
import com.gsg.commons.vo.MallTradeVO;
import com.gsg.lottery.service.IAttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 积分商城-签到记录表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/v1/attendance")
public class AttendanceRecordController {

    @Autowired
    IAttendanceRecordService attendanceRecordService;

    /**
     * 新增签到数据
     * @author gaoshenggang
     * @date  2022/1/24 21:25
     */
    @PostMapping("/insertAttendance")
    public R<?> insertAttendanceRecord(@RequestBody Request<AttendanceRecordDTO> request) {
        try {
            AttendanceRecordDTO attendanceRecordDTO = request.getCustomData();
            attendanceRecordService.insertAttendanceRecord(attendanceRecordDTO);
            return R.ok("签到成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询用户所有签到数据
     * @author gaoshenggang
     * @date  2022/1/24 21:25
     */
    @PostMapping("/getUserAttendance")
    public R<?> getUserAttendance(@RequestBody Request<AttendanceRecordDTO> request) {
        try {
            AttendanceRecordDTO attendanceRecordDTO = request.getCustomData();
            List<AttendanceRecordVO> list = attendanceRecordService.getUserAttendanceRecord(attendanceRecordDTO);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询用户当日是否签到
     * @author gaoshenggang
     * @date  2022/1/24 21:25
     */
    @PostMapping("/getUserDay")
    public R<?> getUserDay(@RequestBody Request<AttendanceRecordDTO> request) {
        try {
            AttendanceRecordDTO attendanceRecordDTO = request.getCustomData();
            AttendanceRecordVO userDay = attendanceRecordService.getUserDay(attendanceRecordDTO);
            return R.ok(userDay);
        } catch (Exception e) {
            return R.failed(e);
        }
    }
}
