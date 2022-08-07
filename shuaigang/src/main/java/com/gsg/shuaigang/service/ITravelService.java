package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.TravelDTO;
import com.gsg.commons.model.Travel;
import com.gsg.commons.vo.CityAndTravelVO;
import com.gsg.commons.vo.TravelVO;

import java.util.List;

/**
 * <p>
 * 个人文档表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
public interface ITravelService extends IService<Travel> {

    /** 增加 */
    void insertTravel(TravelDTO insertTravel);

    /** 查询 */
    List<TravelVO> getTravel(FuzzySearchDTO fuzzySearchDTO);

    /** 查询城市记录 */
    List<CityAndTravelVO> getCityAndTravel();

    /** 修改 */
    void updateTravel(TravelDTO insertTravel);

    /** 删除 */
    void deleteTravel(TravelDTO insertTravel);

}
