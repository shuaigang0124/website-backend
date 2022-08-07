package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.LogDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.LogVO;
import com.gsg.shuaigang.mapper.LogMapper;
import com.gsg.commons.model.Log;
import com.gsg.shuaigang.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 更新日志表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    LogMapper logMapper;

    /** 新增日志 */
    @Override
    public void insertLog(LogDTO logDTO) {
        if (logDTO == null) {
            throw ServiceException.errorParams("请传入参数!");
        }
        Log log = new Log();
        log.setAuthor(logDTO.getAuthor())
                .setContent(logDTO.getContent())
                .setGmtCreate(LocalDateTime.now());
        int i = logMapper.insert(log);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /** 查询日志（倒序） */
    @Override
    public List<LogVO> getLog() {
        return logMapper.getLog();
    }

    /** 查询日志（传入page参数，则为分页查询） */
    @Override
    public List<LogVO> getLogList(LogDTO logDTO, Page page) {
        if (StringUtils.isEmpty(page)) {
            return logMapper.getLogList();
        }
        SearchBean<LogDTO> logList = new SearchBean<>(logDTO.getPage(), logDTO);
        return logMapper.getLogListByPage(logList);
    }

    /** 查询日志数量*/
    @Override
    public long getLogListTotal(LogDTO logDTO) {
        return logMapper.getLogListTotal(logDTO);
    }

    @Override
    public void updateLog(LogDTO logDTO) {
        if (logDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer id = logDTO.getId();
        Log log = new Log();
        log.setId(id)
                .setAuthor(logDTO.getAuthor())
                .setContent(logDTO.getContent());
        int i = logMapper.updateById(log);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /** 删除日志 */
    @Override
    public void deleteLog(LogDTO logDTO) {
        if (logDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer[] ids = logDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = logMapper.deleteLog(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
