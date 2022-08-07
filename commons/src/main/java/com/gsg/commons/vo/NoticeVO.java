package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/22 9:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeVO implements Serializable {

    private static final long serialVersionUID = -4784016089394851196L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 内容
     */
    private String content;

    /**
     * 公告内容底色ID; 0-无，1-绿，2-蓝，3-黄，4-红
     */
    private Integer colorId;

}
