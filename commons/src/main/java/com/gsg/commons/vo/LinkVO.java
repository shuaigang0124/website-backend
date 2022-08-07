package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 15:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LinkVO implements Serializable {
    private static final long serialVersionUID = 1634669295782894332L;

    private Integer id;

    private String name;

    private String url;

    private String type;

}
