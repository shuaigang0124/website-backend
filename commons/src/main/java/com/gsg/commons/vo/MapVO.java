package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/30 16:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MapVO implements Serializable {

    private static final long serialVersionUID = 6329595274166816821L;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 用户数量
     */
    private Integer value;


}
