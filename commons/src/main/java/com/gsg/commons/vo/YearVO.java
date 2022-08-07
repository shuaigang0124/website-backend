package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/17 13:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YearVO implements Serializable {

    private static final long serialVersionUID = 9011370549297493607L;

    private String id;

    private String title;

    private String content;

    private List<YearPlanVO> listingData;
}
