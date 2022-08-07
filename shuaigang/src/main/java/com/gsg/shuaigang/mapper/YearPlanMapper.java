package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.YearPlan;
import com.gsg.commons.vo.YearPlanVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 年度计划表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Repository
public interface YearPlanMapper extends BaseMapper<YearPlan> {

    List<YearPlanVO> getYearPlanList(FuzzySearchDTO fuzzySearchDTO);

    Integer deleteYearPlan(Integer[] ids);
}
