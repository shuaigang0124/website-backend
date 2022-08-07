package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户积分表
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_points")
@Accessors(chain = true)
public class UserPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表主键
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户积分
     */
    @TableField("point")
    private Integer point;


}
