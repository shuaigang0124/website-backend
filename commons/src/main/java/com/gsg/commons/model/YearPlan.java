package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 年度计划表
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("year_plan")
@Accessors(chain = true)
public class YearPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年份id
     */
    @TableField("year_id")
    private String yearId;

    /**
     * 计划
     */
    @TableField("plan")
    private String plan;

    /**
     * 是否完成；0：是；1：延期；2：否；3：未开始
     */
    @TableField("is_enabled")
    private Integer isEnabled;


}
