package com.gsg.commons.dto;

import com.gsg.commons.utils.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 16:11
 */@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LogDTO implements Serializable {
    private static final long serialVersionUID = 3527935764991779177L;

    private Integer id;

    private String content;

    private LocalDateTime gmtCreate;

    private String author;

    private Integer[] ids;

    private Page page;

}
