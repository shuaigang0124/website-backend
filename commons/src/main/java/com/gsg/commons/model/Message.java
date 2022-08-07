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
 * 留言表
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("message")
@Accessors(chain = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户表主键
     */
    @TableField("user_id")
    private String userId;

    /**
     * 被评论人主键
     */
    @TableField("be_commented_user_id")
    private String beCommentedUserId;

    /**
     * 评论父级id
     */
    @TableField("p_id")
    private String pId;

    /**
     * 评论等级
     */
    @TableField("level")
    private Integer level;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

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
