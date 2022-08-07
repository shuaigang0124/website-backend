package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.AttendanceRecordDTO;
import com.gsg.commons.model.AttendanceRecord;
import com.gsg.commons.vo.AttendanceRecordVO;

import java.util.List;

/**
 * <p>
 * 积分商城-签到记录表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-24
 */
public interface IAttendanceRecordService extends IService<AttendanceRecord> {

    void insertAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO);

    List<AttendanceRecordVO> getUserAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO);

    AttendanceRecordVO getUserDay(AttendanceRecordDTO attendanceRecordDTO);
}
