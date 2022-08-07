package com.gsg.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/29 14:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentVO implements Serializable {

    private static final long serialVersionUID = 2266113989193053492L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 用户表主键
     */
    private String userId;

    /**
     * 被回复用户id
     */
    private String beCommentedUserId;

    /**
     * 评论父级id
     */
    private String pId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer clickNum;

    /**
     * 点赞状态
     */
    private Integer clickState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 被回复的用户名
     */
    private String userNameByReply;

    /**
     * 子评论条数
     */
    private Integer total;

    /**
     * 子评论
     */
    private List<CommentVO> children;

}
