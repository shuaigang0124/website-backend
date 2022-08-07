package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.Year;
import com.gsg.commons.vo.YearVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 年份表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Repository
public interface YearMapper extends BaseMapper<Year> {

    List<YearVO> getYearList();

    Integer deleteYears(String[] ids);

}
