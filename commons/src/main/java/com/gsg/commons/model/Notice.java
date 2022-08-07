package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice")
@Accessors(chain = true)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 公告内容底色ID; 0-无，1-绿，2-蓝，3-黄，4-红
     */
    @TableField("color_id")
    private Integer colorId;

    /**
     * 是否启用,0-否,1-是,默认值1
     */
    @TableField("is_enabled")
    private Integer isEnabled;


}
