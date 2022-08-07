package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/22 10:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeBackendVO implements Serializable {
    private static final long serialVersionUID = 2234599203841959533L;

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

    /**
     * 是否启用,0-否,1-是,默认值1
     */
    private Integer isEnabled;

}
