package com.gsg.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/17 13:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YearDTO implements Serializable {

    private static final long serialVersionUID = 7918939218317305456L;

    private String id;

    private String title;

    private String content;

    private String[] ids;

}
