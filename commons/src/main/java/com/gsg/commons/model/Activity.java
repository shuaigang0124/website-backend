package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 抽奖活动表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("activity")
@Accessors(chain = true)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发起人id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 参与所需积分
     */
    @TableField("part_point")
    private Integer partPoint;

    /**
     * 参与人id数组
     */
    @TableField("people_num")
    private Integer peopleNum;

    /**
     * 活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 奖品名称
     */
    @TableField("prize_name")
    private String prizeName;

    /**
     * 奖品图片
     */
    @TableField("prize_img")
    private String prizeImg;

    /**
     * 中奖人id
     */
    @TableField("prize_user_id")
    private String prizeUserId;

    /**
     * 活动状态，0：进行中，1：已结束
     */
    @TableField("is_enabled")
    private Integer isEnabled;

    /**
     * 开奖时间
     */
    @TableField("gmt_time")
    private LocalDateTime gmtTime;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;


}
