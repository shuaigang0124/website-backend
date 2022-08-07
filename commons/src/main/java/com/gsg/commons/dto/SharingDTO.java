package com.gsg.commons.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.SharingVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/29 16:47
 */
@Data
@Accessors(chain = true)
public class SharingDTO implements Serializable {

    /**
     * 用户表主键
     */
    private String userId;

    /**
     * 日常分享主键
     */
    private String sharingId;

    /**
     * 标签表主键
     */
    private Integer tagId;

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
     * 逻辑删除,0-未删除,1-已删除,默认值0
     */
    private Integer deleted;

    /** 分页参数 */
    private Page page;

    /** zuul网关手动添加信息：是否支持WebP格式图片，1-支持*/
    private String supportWebp;

    /** 模糊查询参数 */
    private String fuzzySearch;

    /** 日常分享id */
    private String id;

}
