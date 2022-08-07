package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 个人文档表
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("travel")
@Accessors(chain = true)
public class Travel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 城市id
     */
    @TableField("city_id")
    private String cityId;

    /**
     * 日期
     */
    @TableField("day_time")
    private LocalDate dayTime;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

}
