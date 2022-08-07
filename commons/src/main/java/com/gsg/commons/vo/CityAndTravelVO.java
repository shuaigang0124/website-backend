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
 * @date 2022/3/17 19:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CityAndTravelVO implements Serializable {

    private static final long serialVersionUID = 5303718969893213120L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 城市名称
     */
    private String cityName;


    /**
     * 旅行记录列表
     */
    private List<TravelVO> travelList;

}
