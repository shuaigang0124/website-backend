package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.NoticeDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.vo.NoticeBackendVO;
import com.gsg.commons.vo.NoticeVO;
import com.gsg.shuaigang.mapper.NoticeMapper;
import com.gsg.commons.model.Notice;
import com.gsg.shuaigang.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    NoticeMapper noticeMapper;


    @Override
    public void insertNotice(NoticeDTO noticeDTO) {
        if (noticeDTO == null) {
            throw ServiceException.errorParams("传入参数有误！");
        }
        Notice notice = new Notice();
        if (noticeDTO.getSort() == null) {
            throw ServiceException.errorParams("sort必传！");
        }
        if (StringUtils.isEmpty(noticeDTO.getContent())) {
            throw ServiceException.errorParams("content必传！");
        }
        if (noticeDTO.getColorId() != null) {
            notice.setColorId(noticeDTO.getColorId());
        }
        String id = "NOT" + PKGenerator.generate();
        notice.setId(id)
                .setSort(noticeDTO.getSort())
                .setContent(noticeDTO.getContent());
        int i = noticeMapper.insert(notice);
        if (i != 1 ) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<NoticeVO> getNotice() {
        return noticeMapper.getNotice();
    }

    @Override
    public List<NoticeBackendVO> getBackendNotice(FuzzySearchDTO fuzzySearchDTO) {
        return noticeMapper.getBackendNotice(fuzzySearchDTO);
    }

    @Override
    public void updateNotice(NoticeDTO noticeDTO) {
        if (noticeDTO == null) {
            throw ServiceException.errorParams("请传入参数!");
        }
        String id = noticeDTO.getId();

        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("id必传!");
        }

        Integer sort = noticeDTO.getSort();
        String content = noticeDTO.getContent();
        Integer colorId = noticeDTO.getColorId();
        Integer isEnabled = noticeDTO.getIsEnabled();

        Notice notice = new Notice();

        if (sort != null) {
            notice.setSort(sort);
        }
        if (content != null) {
            notice.setContent(content);
        }
        if (colorId != null) {
            notice.setColorId(colorId);
        }
        if (isEnabled != null) {
            notice.setIsEnabled(isEnabled);
        }
        notice.setId(id);
        int i = noticeMapper.updateById(notice);
        if (i != 1) {
            throw ServiceException.busy();
        }

    }

    @Override
    public void deleteNotice(NoticeDTO noticeDTO) {
        if (noticeDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        String[] ids = noticeDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = noticeMapper.deleteNotice(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
