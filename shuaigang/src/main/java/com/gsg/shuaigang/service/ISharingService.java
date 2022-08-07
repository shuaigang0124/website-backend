package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.SharingDTO;
import com.gsg.commons.model.Sharing;
import com.gsg.commons.utils.Page;
import com.gsg.commons.vo.SharingBackendVO;
import com.gsg.commons.vo.SharingByTagVO;
import com.gsg.commons.vo.SharingDetailVO;
import com.gsg.commons.vo.SharingVO;

import java.util.List;

/**
 * <p>
 * 日常分享表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
public interface ISharingService extends IService<Sharing> {

    /**
     * 新增日常分享
     * @author gaoshenggang
     * @date  2021/11/29 16:45
     */
    void insertSharing(SharingDTO sharingDTO);

    /**
     * 查询所有日常分享（传入tagId，则为标签条件查询）
     * @author gaoshenggang
     * @date  2021/11/29 17:41
     */
    List<SharingVO> getSharing(SharingDTO sharingDTO, Page page);

    /**
     * 通过tagId查询除当前分享的其它日常分享（tagId、sharingId）
     * @param sharingDTO
     * @return
     */
    List<SharingByTagVO> getSharingByTag(SharingDTO sharingDTO);

    /**
     * 修改日常分享（点赞数，阅读数，删除标识（逻辑删除））
     * @author gaoshenggang
     * @date  2021/11/29 17:45
     */
    int updateSharing(SharingDTO sharingDTO);

    /**
     * 通过ID查询日常分享详情（包括评论总条数）
     * @author gaoshenggang
     * @date  2021/11/30 11:48
     */
    SharingDetailVO getSharingDetail(String id);

    /**
     * 查询修改详情
     * @author gaoshenggang
     * @date  2021/12/28 14:50
     */
    SharingDetailVO getUpdateDetail(String id);

    /**
     * 查询日常分享总数
     * @author gaoshenggang
     * @date  2021/11/30 21:22
     */
    long getSharingListTotal(SharingDTO sharingDTO);

    /**
     * 分页查询
     * @author gaoshenggang
     * @date  2021/11/30 21:22
     */
    List<SharingBackendVO> getAllSharing(SharingDTO sharingDTO, Page page);


}
