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
 * 城市表-省份
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("city")
@Accessors(chain = true)
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 城市名称
     */
    @TableField("city_name")
    private String cityName;

    /**
     * 用户数量
     */
    @TableField("user_value")
    private Integer userValue;

    /**
     * 是否启用,0-禁用,1-启用,默认值1
     */
    @TableField("is_enabled")
    private Integer isEnabled;

    /**
     * 逻辑删除,0-未删除,1-已删除,默认值0
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;


}
