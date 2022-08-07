package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/17 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YearPlanVO implements Serializable {

    private static final long serialVersionUID = -6172027038162508742L;

    private Integer id;

    private String yearId;

    private String plan;

    private Integer isEnabled;

}
