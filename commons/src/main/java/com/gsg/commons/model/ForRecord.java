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
 * 商城-兑换记录表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("for_record")
@Accessors(chain = true)
public class ForRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 商品id
     */
    @TableField("trade_id")
    private String tradeId;

    /**
     * 兑换消耗积分
     */
    @TableField("sub_num")
    private Integer subNum;

    /**
     * 兑换状态；0-待处理；1-；已完成；2-已取消
     */
    @TableField("convert_status")
    private Integer convertStatus;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;


}
