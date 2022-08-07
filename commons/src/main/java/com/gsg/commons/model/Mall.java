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
 * 商城-商品表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mall")
@Accessors(chain = true)
public class Mall implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 商品发布人id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 商品类型id；1-6
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 商品名称
     */
    @TableField("trade_name")
    private String tradeName;

    /**
     * 价值积分
     */
    @TableField("point_num")
    private Integer pointNum;

    /**
     * 商品图片
     */
    @TableField("trade_img")
    private String tradeImg;

    /**
     * 商品剩余数量
     */
    @TableField("trade_num")
    private Integer tradeNum;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 是否删除；0-否；1-是；默认为0
     */
    @TableField("deleted")
    private Integer deleted;

}
