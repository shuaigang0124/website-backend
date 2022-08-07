package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.LogDTO;
import com.gsg.commons.model.Log;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.LogVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 更新日志表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {

    /** 查询（倒序） */
    List<LogVO> getLog();

    /** 查询 */
    List<LogVO> getLogList();

    /** 查询日志总数 */
    long getLogListTotal(LogDTO logDTO);

    /** 分页查询日志 */
    List<LogVO> getLogListByPage(SearchBean<LogDTO> searchBean);

    /** 删除 */
    Integer deleteLog(Integer[] ids);

}
