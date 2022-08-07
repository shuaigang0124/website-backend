package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.YearDTO;
import com.gsg.commons.model.Year;
import com.gsg.commons.vo.YearVO;

import java.util.List;

/**
 * <p>
 * 年份表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
public interface IYearService extends IService<Year> {

    /** 新增年份 */
    void insertYear(YearDTO yearDTO);

    /** 查询年份 */
    List<YearVO> getYearList();

    /** 修改年份 */
    void updateYear(YearDTO yearDTO);

    /** 删除年份 */
    void deleteYear(YearDTO yearDTO);

}
