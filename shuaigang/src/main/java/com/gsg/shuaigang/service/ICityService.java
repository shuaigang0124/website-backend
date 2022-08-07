package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.CityDTO;
import com.gsg.commons.model.City;
import com.gsg.commons.vo.CityVO;
import com.gsg.commons.vo.MapVO;

import java.util.List;

/**
 * <p>
 * 城市表-省份 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-30
 */
public interface ICityService extends IService<City> {

    List<CityVO> getCityValue();

    List<MapVO> getCityData();

    void updateCityValue(CityDTO cityDTO);

}
