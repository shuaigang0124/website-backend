package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.AttendanceRecordDTO;
import com.gsg.commons.model.AttendanceRecord;
import com.gsg.commons.vo.AttendanceRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 积分商城-签到记录表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-24
 */
@Repository
public interface AttendanceRecordMapper extends BaseMapper<AttendanceRecord> {

    List<AttendanceRecordVO> getUserAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO);

    AttendanceRecordVO getUserDay(AttendanceRecordDTO attendanceRecordDTO);
}
