package com.gsg.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 更新日志表
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log")
@Accessors(chain = true)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作内容
     */
    @TableField("content")
    private String content;

    /**
     * 操作时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 操作人
     */
    @TableField("author")
    private String author;


}
