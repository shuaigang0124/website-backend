package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.NoticeDTO;
import com.gsg.commons.model.Notice;
import com.gsg.commons.vo.NoticeBackendVO;
import com.gsg.commons.vo.NoticeVO;

import java.util.List;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-22
 */
public interface INoticeService extends IService<Notice> {

    /** 增加 */
    void insertNotice(NoticeDTO noticeDTO);

    /** 前台查询 */
    List<NoticeVO> getNotice();

    /** 后台查询 */
    List<NoticeBackendVO> getBackendNotice(FuzzySearchDTO fuzzySearchDTO);

    /** 修改 */
    void updateNotice(NoticeDTO noticeDTO);

    /** 删除 */
    void deleteNotice(NoticeDTO noticeDTO);

}
