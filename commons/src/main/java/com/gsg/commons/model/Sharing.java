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
 * 日常分享表
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sharing")
@Accessors(chain = true)
public class Sharing implements Serializable {

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
     * 标签表主键
     */
    @TableField("tag_id")
    private Integer tagId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 标题描述
     */
    @TableField("title_describe")
    private String titleDescribe;

    /**
     * 正文
     */
    @TableField("content")
    private String content;

    /**
     * 预览图
     */
    @TableField("img")
    private String img;

    /**
     * 点赞数,默认0
     */
    @TableField("click_num")
    private Integer clickNum;

    /**
     * 浏览量
     */
    @TableField("read_num")
    private Integer readNum;

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
