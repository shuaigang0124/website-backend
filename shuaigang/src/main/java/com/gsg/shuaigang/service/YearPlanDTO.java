package com.gsg.shuaigang.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/17 13:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YearPlanDTO implements Serializable {

    private static final long serialVersionUID = 3211801353393961909L;

    private Integer id;

    private String yearId;

    private String plan;

    private Integer isEnabled;

    private Integer[] ids;
}
