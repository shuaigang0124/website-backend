package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.YearDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.YearPlanVO;
import com.gsg.commons.vo.YearVO;
import com.gsg.shuaigang.mapper.YearMapper;
import com.gsg.commons.model.Year;
import com.gsg.shuaigang.mapper.YearPlanMapper;
import com.gsg.shuaigang.service.IYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 年份表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Service
public class YearServiceImpl extends ServiceImpl<YearMapper, Year> implements IYearService {

    @Autowired
    YearMapper yearMapper;

    @Autowired
    YearPlanMapper yearPlanMapper;

    @Override
    public void insertYear(YearDTO yearDTO) {
        String id = yearDTO.getId();
        String title = yearDTO.getTitle();
        String content = yearDTO.getContent();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            throw ServiceException.errorParams("请求参数有误！");
        }
        Year year = new Year();
        year.setId(id)
                .setTitle(title)
                .setContent(content);
        int i = yearMapper.insert(year);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<YearVO> getYearList() {
        List<YearVO> yearList = yearMapper.getYearList();
        List<YearPlanVO> yearPlanList = yearPlanMapper.getYearPlanList(null);
        if (yearPlanList.size() > 0) {
            for (YearVO yearVO : yearList) {
                String parentId = yearVO.getId();
                List<YearPlanVO> child = new ArrayList<>();
                for (YearPlanVO yearPlanVO : yearPlanList) {
                    String childId = yearPlanVO.getYearId();
                    if (parentId.equals(childId)) {
                        child.add(yearPlanVO);
                    }
                }
                yearVO.setListingData(child);
            }
        }
        return yearList;
    }

    @Override
    public void updateYear(YearDTO yearDTO) {
        String id = yearDTO.getId();
        String title = yearDTO.getTitle();
        String content = yearDTO.getContent();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("请求参数有误！");
        }
        Year year = new Year();
        year.setId(id);
        if (!StringUtils.isEmpty(title)) {
            year.setTitle(title);
        }
        if (!StringUtils.isEmpty(content)) {
            year.setContent(content);
        }
        int i = yearMapper.updateById(year);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public void deleteYear(YearDTO yearDTO) {
        String[] ids = yearDTO.getIds();
        if (StringUtils.isEmpty(ids)) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = yearMapper.deleteYears(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
