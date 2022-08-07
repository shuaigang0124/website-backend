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
 * @Date 2021/11/29 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SharingVO implements Serializable {
    private static final long serialVersionUID = -2023586912045622164L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 作者主键
     */
    private String userId;

    /**
     * 标签主键
     */
    private String tagId;

    /**
     * 作者
     */
    private String userName;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题描述
     */
    private String titleDescribe;

    /**
     * 预览图
     */
    private String img;

    /**
     * 点赞数,默认0
     */
    private Integer clickNum;

    /**
     * 浏览量
     */
    private Integer readNum;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
