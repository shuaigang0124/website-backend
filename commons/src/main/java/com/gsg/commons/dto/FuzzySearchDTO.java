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
 * @date 2022/3/14 14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FuzzySearchDTO implements Serializable {

    private static final long serialVersionUID = 7447974392189559278L;

    /**
     * 模糊查询字段
     */
    private String fuzzySearch;

}
