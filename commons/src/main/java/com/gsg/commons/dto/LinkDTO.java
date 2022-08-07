package com.gsg.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LinkDTO implements Serializable {
    private static final long serialVersionUID = -3241598690812708522L;

    private Integer id;

    private String name;

    private String url;

    private String type;

    private Integer[] ids;

}
