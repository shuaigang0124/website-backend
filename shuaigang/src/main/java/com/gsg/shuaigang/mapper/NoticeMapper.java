package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Notice;
import com.gsg.commons.vo.NoticeBackendVO;
import com.gsg.commons.vo.NoticeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-22
 */
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {

    /** 前台查询公告 */
    List<NoticeVO> getNotice();

    /** 后台查询公告 */
    List<NoticeBackendVO> getBackendNotice(FuzzySearchDTO fuzzySearchDTO);

    /** 删除公告 */
    Integer deleteNotice(String[] ids);

}
