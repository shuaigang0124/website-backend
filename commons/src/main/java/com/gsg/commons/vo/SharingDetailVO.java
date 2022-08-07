package com.gsg.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/30 11:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SharingDetailVO implements Serializable {

    private static final long serialVersionUID = 5940056595295800068L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 作者主键id
     */
    private String userId;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 作者
     */
    private String userName;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题描述
     */
    private String titleDescribe;

    /**
     * 正文
     */
    private String content;

    /**
     * 点赞数,默认0
     */
    private Integer clickNum;

    /**
     * 评论条数
     */
    private Integer commentTotal;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
