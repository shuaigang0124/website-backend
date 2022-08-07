package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.CityDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.CityVO;
import com.gsg.commons.vo.MapVO;
import com.gsg.shuaigang.mapper.CityMapper;
import com.gsg.commons.model.City;
import com.gsg.shuaigang.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 城市表-省份 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-30
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    CityMapper cityMapper;

    /**
     * 查询所有城市用户数据
     * @author gaoshenggang
     * @date  2021/12/30 14:40
     */
    @Override
    public List<CityVO> getCityValue() {
        return cityMapper.getCityValue();
    }

    /**
     * 后台地图可视化数据
     * @author gaoshenggang
     * @date  2021/12/30 16:18
     */
    @Override
    public List<MapVO> getCityData() {
        List<CityVO> cityValue = cityMapper.getCityValue();
        List<MapVO> mapVOS = new ArrayList<>();
        for (CityVO cityVO : cityValue) {
            MapVO mapVO = new MapVO();
            mapVO.setName(cityVO.getCityName())
                    .setValue(cityVO.getUserValue());
            mapVOS.add(mapVO);
        }
        return mapVOS;
    }

    /**
     * 城市用户数量（加一或者减一）
     * @author gaoshenggang
     * @date  2021/12/30 14:40
     */
    @Override
    public void updateCityValue(CityDTO cityDTO) {
        String id = cityDTO.getId();
        Integer subtractOrAdd = cityDTO.getSubtractOrAdd();
        Integer value = cityMapper.findCityValueById(id);
        if (subtractOrAdd < 0) {
            value -= 1;
        } else {
            value += 1;
        }
        City city = new City();
        city.setId(id)
                .setUserValue(value);
        int i = cityMapper.updateById(city);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }
}
