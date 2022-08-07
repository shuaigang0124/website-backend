package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.AttendanceRecordDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.UserPoints;
import com.gsg.commons.vo.AttendanceRecordVO;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.lottery.mapper.AttendanceRecordMapper;
import com.gsg.commons.model.AttendanceRecord;
import com.gsg.lottery.mapper.UserPointsMapper;
import com.gsg.lottery.service.IAttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 积分商城-签到记录表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-24
 */
@Service
public class AttendanceRecordServiceImpl extends ServiceImpl<AttendanceRecordMapper, AttendanceRecord> implements IAttendanceRecordService {

    @Autowired
    AttendanceRecordMapper attendanceRecordMapper;

    @Autowired
    UserPointsMapper userPointsMapper;

    /**
     * 新增签到数据
     * @author gaoshenggang
     * @date  2022/1/24 21:13
     */
    @Override
    public void insertAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO) {
        String userId = attendanceRecordDTO.getUserId();
        LocalDate dayTime = attendanceRecordDTO.getDayTime();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(dayTime)) {
            throw ServiceException.errorParams("请求参数有误!");
        }
        AttendanceRecordVO userDay = attendanceRecordMapper.getUserDay(attendanceRecordDTO);
        if (!StringUtils.isEmpty(userDay)) {
            throw ServiceException.errorParams("当日已签到，请勿重复签到！");
        }
        UserPointsVO userPointsVO = userPointsMapper.getUserPoint(userId);
        if (StringUtils.isEmpty(userPointsVO)) {
            UserPoints userPoints = new UserPoints();
            userPoints.setUserId(userId);
            int row = userPointsMapper.insert(userPoints);
            if (row < 1) {
                throw ServiceException.busy();
            }
        }
        Integer userPoint = userPointsVO.getPoint();
        userPoint += 2;
        int row = userPointsMapper.updateUserPiont(userId, userPoint);
        if (row != 1) {
            throw ServiceException.busy();
        }
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setUserId(userId)
                .setDayTime(dayTime)
                .setGmtCreate(LocalDateTime.now());
        int i = attendanceRecordMapper.insert(attendanceRecord);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 查询用户所有签到数据
     * @author gaoshenggang
     * @date  2022/1/24 21:13
     */
    @Override
    public List<AttendanceRecordVO> getUserAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO) {
        String userId = attendanceRecordDTO.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw ServiceException.errorParams("用户id必传");
        }
        return attendanceRecordMapper.getUserAttendanceRecord(attendanceRecordDTO);
    }

    /**
     * 查询用户当日数据
     * @author gaoshenggang
     * @date  2022/1/25 9:04
     */
    @Override
    public AttendanceRecordVO getUserDay(AttendanceRecordDTO attendanceRecordDTO) {
        String userId = attendanceRecordDTO.getUserId();
        LocalDate dayTime = attendanceRecordDTO.getDayTime();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(dayTime)) {
            throw ServiceException.errorParams("请求参数有误!");
        }
        return attendanceRecordMapper.getUserDay(attendanceRecordDTO);
    }
}
