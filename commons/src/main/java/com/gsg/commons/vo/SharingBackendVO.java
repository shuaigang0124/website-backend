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
 * @Date 2021/11/30 21:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SharingBackendVO implements Serializable {
    private static final long serialVersionUID = 2008987412202586788L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 标题描述
     */
    private String titleDescribe;

    /**
     * 预览图
     */
    private String img;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtModified;
}
