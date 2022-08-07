package com.gsg.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/29 14:19
 */
@Data
@Accessors(chain = true)
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = 6938041373927520801L;

    /**
     * 用户表主键
     */
    private String userId;

    /**
     * 日常分享表表主键
     */
    private String sharingId;

    /**
     * 评论父级id
     */
    private String parentId;

    /**
     * 评论内容
     */
    private String content;


    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
