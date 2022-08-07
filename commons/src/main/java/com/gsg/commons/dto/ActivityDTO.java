package com.gsg.commons.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/7 18:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("author")
@Accessors(chain = true)
public class ActivityDTO implements Serializable {
    private static final long serialVersionUID = -3298903771334394640L;

    /**
     * 自定义主键
     */
    private Integer id;

    /**
     * 发起人id
     */
    private String userId;

    /**
     * 参与所需积分
     */
    private Integer partPoint;

    /**
     * 参与人id数组
     */
    private String partUserId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品图片
     */
    private String prizeImg;

    /**
     * 中奖人id
     */
    private String prizeUserId;

    /**
     * 活动状态，0：进行中，1：已结束
     */
    private Integer isEnabled;

    /**
     * 开奖时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtTime;

    /**
     * 参与抽奖用户id
     */
    private String joinUserId;
}
