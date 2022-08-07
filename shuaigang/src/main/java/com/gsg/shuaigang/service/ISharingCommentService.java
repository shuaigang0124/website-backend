package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.CommentDTO;
import com.gsg.commons.dto.MessageDTO;
import com.gsg.commons.model.SharingComment;
import com.gsg.commons.vo.CommentVO;

import java.util.List;

/**
 * <p>
 * 日常分享-评论表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
public interface ISharingCommentService extends IService<SharingComment> {

    /**
     * 新增评论
     * @author gaoshenggang
     * @date  2021/11/29 14:12
     */
    void insertComment(CommentDTO commentDTO);

    /**
     * 查询全部评论
     * @author gaoshenggang
     * @date  2021/11/29 15:00
     */
    List<CommentVO> findAllComment(SharingComment sharingComment, String supportWebp);

    /**
     * 修改点赞数量
     * @author gaoshenggang
     * @date  2021/11/29 15:13
     */
    int updateComment(SharingComment sharingComment);

    /**
     * 删除留言（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/29 15:13
     */
    void deleteComment(SharingComment sharingComment);

}
