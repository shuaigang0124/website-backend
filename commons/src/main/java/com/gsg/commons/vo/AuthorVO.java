package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 14:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AuthorVO implements Serializable {
    private static final long serialVersionUID = 5213330465791095800L;

    private Integer id;

    private String content;

    private Integer click;

}
