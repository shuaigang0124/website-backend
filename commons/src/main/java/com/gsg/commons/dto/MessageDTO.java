package com.gsg.commons.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gsg.commons.utils.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/22 9:52
 */
@Data
@Accessors(chain = true)
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = -7422215581517350255L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 用户表主键
     */
    private String userId;

    /**
     * 被评论人主键
     */
    private String beCommentedUserId;

    /**
     * 评论父级id
     */
    private String parentId;

    /**
     * 留言等级
     */
    private Integer level;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 逻辑删除,0-未删除,1-已删除,默认值0
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /** 分页参数 */
    private Page page;

}
