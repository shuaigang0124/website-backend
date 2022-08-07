package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.AuthorDTO;
import com.gsg.commons.dto.LogDTO;
import com.gsg.commons.model.Log;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.AuthorVO;
import com.gsg.commons.vo.LogVO;

import java.util.List;

/**
 * <p>
 * 更新日志表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
public interface ILogService extends IService<Log> {

    /** 增加 */
    void insertLog(LogDTO logDTO);

    /** 查询（倒序） */
    List<LogVO> getLog();

    /** 查询(传入page，则分页查询) */
    List<LogVO> getLogList(LogDTO logDTO, Page page);

    /** 查询日志总数 */
    long getLogListTotal(LogDTO logDTO);

    /** 修改 */
    void updateLog(LogDTO logDTO);

    /** 删除 */
    void deleteLog(LogDTO logDTO);

}
