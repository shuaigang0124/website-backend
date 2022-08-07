package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 抽奖表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lottery")
@Accessors(chain = true)
public class Lottery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户表主键
     */
    @TableField("user_id")
    private String userId;

    /**
     * 活动编号
     */
    @TableField("activity_num")
    private Integer activityNum;

    /**
     * 中奖标识，0:未中奖，1:一等奖，2:二等奖，3:三等奖
     */
    @TableField("winning")
    private Integer winning;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;


}
