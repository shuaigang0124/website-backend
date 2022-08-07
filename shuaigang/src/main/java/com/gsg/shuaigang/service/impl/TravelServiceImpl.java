package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.TravelDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.CityAndTravelVO;
import com.gsg.commons.vo.CityVO;
import com.gsg.commons.vo.TravelVO;
import com.gsg.shuaigang.mapper.CityMapper;
import com.gsg.shuaigang.mapper.TravelMapper;
import com.gsg.commons.model.Travel;
import com.gsg.shuaigang.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 个人文档表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Service
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    TravelMapper travelMapper;

    @Autowired
    CityMapper cityMapper;

    @Override
    public void insertTravel(TravelDTO travelDTO) {
        String cityId = travelDTO.getCityId();
        String content = travelDTO.getContent();

        if (StringUtils.isEmpty(cityId) || StringUtils.isEmpty(content)) {
            throw ServiceException.errorParams("请求参数有误！");
        }
        Travel travel = new Travel();
        travel.setCityId(cityId)
                .setContent(content)
                .setDayTime(LocalDate.now());
        int i = travelMapper.insert(travel);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<TravelVO> getTravel(FuzzySearchDTO fuzzySearchDTO) {
        return travelMapper.getTravel(fuzzySearchDTO);
    }

    @Override
    public List<CityAndTravelVO> getCityAndTravel() {
        List<CityAndTravelVO> cityList = cityMapper.getCityList();
        List<TravelVO> travelList = travelMapper.getTravel(null);
        for (CityAndTravelVO city : cityList) {
            String cityId = city.getId();
            List<TravelVO> tt = new ArrayList<>();
            for (TravelVO travel : travelList) {
                if (cityId.equals(travel.getCityId())) {
                    tt.add(travel);
                }
            }
            if (tt.size() == 0) {
                city.setTravelList(null);
            } else {
                city.setTravelList(tt);
            }
        }
        return cityList;
    }

    @Override
    public void updateTravel(TravelDTO travelDTO) {
        Integer id = travelDTO.getId();
        String cityId = travelDTO.getCityId();
        String content = travelDTO.getContent();
        LocalDate dayTime = travelDTO.getDayTime();
        if (id == null) {
            throw ServiceException.errorParams("请传入id");
        }
        Travel travel = new Travel();
        travel.setId(id);
        if (!StringUtils.isEmpty(cityId)) {
            travel.setCityId(cityId);
        }
        if (!StringUtils.isEmpty(content)) {
            travel.setContent(content);
        }
        if (!StringUtils.isEmpty(dayTime)) {
            travel.setDayTime(dayTime);
        }
        int i = travelMapper.updateById(travel);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public void deleteTravel(TravelDTO travelDTO) {
        Integer[] ids = travelDTO.getIds();
        if (StringUtils.isEmpty(ids)) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = travelMapper.deleteTravel(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
