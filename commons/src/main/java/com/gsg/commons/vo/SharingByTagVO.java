package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/23 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SharingByTagVO implements Serializable {
    private static final long serialVersionUID = 6927024251312803760L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 标题描述
     */
    private String titleDescribe;
}
