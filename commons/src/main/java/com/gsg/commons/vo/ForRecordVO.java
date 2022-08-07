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
 * @Date 2022/1/13 13:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ForRecordVO implements Serializable {
    private static final long serialVersionUID = 4415558130881027922L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 商品id
     */
    private String tradeId;

    /**
     * 商品名
     */
    private String tradeName;

    /**
     * 兑换消耗积分
     */
    private Integer subNum;

    /**
     * 兑换状态；0-待处理；1-；已完成；2-已取消
     */
    private Integer convertStatus;

    /**
     * 兑换状态；0-待处理；1-；已完成；2-已取消
     */
    private String statusName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtCreate;


}
