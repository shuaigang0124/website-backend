package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Year;
import com.gsg.commons.vo.YearPlanVO;
import com.gsg.shuaigang.mapper.YearPlanMapper;
import com.gsg.commons.model.YearPlan;
import com.gsg.shuaigang.service.IYearPlanService;
import com.gsg.shuaigang.service.YearPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 年度计划表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Service
public class YearPlanServiceImpl extends ServiceImpl<YearPlanMapper, YearPlan> implements IYearPlanService {

    @Autowired
    YearPlanMapper yearPlanMapper;

    @Override
    public void insertYearPlan(YearPlanDTO yearPlanDTO) {
        String yearId = yearPlanDTO.getYearId();
        String plan = yearPlanDTO.getPlan();

        if (StringUtils.isEmpty(yearId) || StringUtils.isEmpty(plan)) {
            throw ServiceException.errorParams("请求参数有误！");
        }
        YearPlan yearPlan = new YearPlan();
        yearPlan.setYearId(yearId)
                .setPlan(plan);
        int i = yearPlanMapper.insert(yearPlan);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<YearPlanVO> getYearPlan(FuzzySearchDTO fuzzySearchDTO) {
        return yearPlanMapper.getYearPlanList(fuzzySearchDTO);
    }

    @Override
    public void updateYearPlan(YearPlanDTO yearPlanDTO) {
        Integer id = yearPlanDTO.getId();
        String yearId = yearPlanDTO.getYearId();
        String plan = yearPlanDTO.getPlan();
        Integer isEnabled = yearPlanDTO.getIsEnabled();
        if (id == null) {
            throw ServiceException.errorParams("请求参数有误！");
        }
        YearPlan yearPlan = new YearPlan();
        yearPlan.setId(id);
        if (!StringUtils.isEmpty(yearId)) {
            yearPlan.setYearId(yearId);
        }
        if (!StringUtils.isEmpty(plan)) {
            yearPlan.setPlan(plan);
        }
        if (isEnabled != null) {
            yearPlan.setIsEnabled(isEnabled);
        }
        int i = yearPlanMapper.updateById(yearPlan);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public void deleteYearPlan(YearPlanDTO yearPlanDTO) {
        Integer[] ids = yearPlanDTO.getIds();
        if (StringUtils.isEmpty(ids)) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = yearPlanMapper.deleteYearPlan(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
