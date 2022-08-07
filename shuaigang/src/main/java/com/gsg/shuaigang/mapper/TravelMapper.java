package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Travel;
import com.gsg.commons.vo.TravelVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 个人文档表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Repository
public interface TravelMapper extends BaseMapper<Travel> {

    /* 查询 */
    List<TravelVO> getTravel(FuzzySearchDTO fuzzySearchDTO);

    /* 删除 */
    Integer deleteTravel(Integer[] ids);
}
