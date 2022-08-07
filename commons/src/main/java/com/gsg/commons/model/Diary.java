package com.gsg.commons.model;

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
 * 日记表
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("diary")
@Accessors(chain = true)
public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 日期
     */
    @TableField("day")
    private LocalDate day;

    /**
     * 天气
     */
    @TableField("weather")
    private String weather;

    /**
     * 心情
     */
    @TableField("mood")
    private String mood;

    /**
     * 内容
     */
    @TableField("content")
    private String content;


}
