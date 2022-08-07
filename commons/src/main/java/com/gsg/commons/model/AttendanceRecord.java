package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 积分商城-签到记录表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("attendance_record")
@Accessors(chain = true)
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 签到日期
     */
    @TableField("day_time")
    private LocalDate dayTime;

    /**
     * 签到时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;


}
