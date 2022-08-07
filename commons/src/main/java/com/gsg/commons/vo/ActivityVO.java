package com.gsg.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/7 18:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ActivityVO implements Serializable {
    private static final long serialVersionUID = -7492180503564432781L;

    /**
     * 自定义主键
     */
    private Integer id;

    /**
     * 发起人id
     */
    private String userId;

    /**
     * 发起人用户名
     */
    private String reUserName;

    /**
     * 参与人数
     */
    private Integer peopleNum;

    /**
     * 参与所需积分
     */
    private Integer partPoint;

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
     * 中奖人用户名
     */
    private String prizeUserName;

    /**
     * 中奖人头像
     */
    private String reUserAvatar;

    /**
     * 活动状态，0：进行中，1：已结束
     */
    private Integer isEnabled;

    /**
     * 开奖时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtTime;

    /**
     * 参与标识（0：未参与，1：已参与）
     */
    private Integer userJoinNum;

    /**
     * 活动状态；0-进行中；1-；已结束
     */
    private String statusName;
}
