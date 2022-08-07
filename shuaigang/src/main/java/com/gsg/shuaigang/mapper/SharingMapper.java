package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.SharingDTO;
import com.gsg.commons.model.Sharing;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.SharingBackendVO;
import com.gsg.commons.vo.SharingByTagVO;
import com.gsg.commons.vo.SharingDetailVO;
import com.gsg.commons.vo.SharingVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 日常分享表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@Repository
public interface SharingMapper extends BaseMapper<Sharing> {

    /**
     * 查询所有日常分享（传入tagId，则为标签条件查询）
     * @author gaoshenggang
     * @date  2021/11/29 17:06
     */
    List<SharingVO> getSharing(SearchBean<SharingDTO> searchBean);

    List<SharingByTagVO> getSharingByTag(Integer tagId, String sharingId);

    /**
     * 通过id查询分享详情
     * @author gaoshenggang
     * @date  2021/11/30 11:36
     */
    SharingDetailVO getSharingDetail(String id);

    /**
     * 查询分享详情评论总条数
     * @author gaoshenggang
     * @date  2021/11/30 11:43
     */
    Integer getCommentListTotal(String sharingId);

    Sharing selectSharingImgPath(String sharingId);

    long getSharingListTotal(SharingDTO sharingDTO);

    List<SharingBackendVO> getSharingListByPage(SearchBean<SharingDTO> searchBean);

}
