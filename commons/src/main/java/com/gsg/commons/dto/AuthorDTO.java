package com.gsg.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 14:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = 8596378800245881018L;

    private Integer id;

    private String content;

    private Integer click;

    private String userId;

    private Integer[] ids;
}
