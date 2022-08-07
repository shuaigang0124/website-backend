package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.City;
import com.gsg.commons.vo.CityAndTravelVO;
import com.gsg.commons.vo.CityVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 城市表-省份 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-30
 */
@Repository
public interface CityMapper extends BaseMapper<City> {

    /**
     * 查询城市用户数据
     * @author gaoshenggang
     * @date  2021/12/30 13:41
     */
    List<CityVO> getCityValue();

    /**
     * 查询当前城市用户数量
     * @author gaoshenggang
     * @date  2021/12/30 14:18
     */
    Integer findCityValueById(String id);

    /**
     * 查询所有城市
     * @author gaoshenggang
     * @date  2022/03/17 19:41
     */
    List<CityAndTravelVO> getCityList();

}
