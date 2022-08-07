package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.YearPlan;
import com.gsg.commons.vo.YearPlanVO;
import com.gsg.commons.vo.YearVO;

import java.util.List;

/**
 * <p>
 * 年度计划表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
public interface IYearPlanService extends IService<YearPlan> {

    /** 增加 */
    void insertYearPlan(YearPlanDTO yearPlanDTO);

    /** 查询 */
    List<YearPlanVO> getYearPlan(FuzzySearchDTO fuzzySearchDTO);

    /** 修改 */
    void updateYearPlan(YearPlanDTO yearPlanDTO);

    /** 删除 */
    void deleteYearPlan(YearPlanDTO yearPlanDTO);

}
