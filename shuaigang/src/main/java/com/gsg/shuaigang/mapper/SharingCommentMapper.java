package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.SharingComment;
import com.gsg.commons.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 日常分享-评论表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@Repository
public interface SharingCommentMapper extends BaseMapper<SharingComment> {

    /**
     * 通过评论id查询留言等级和父级ID
     * @author gaoshenggang
     * @date  2021/11/29 14:30
     */
    SharingComment findPIdByCommentId(String commentId);

    /**
     * 查询全部评论
     * @author gaoshenggang
     * @date  2021/11/29 14:12
     */
    List<CommentVO> findAllComment(String sharingId, Integer level);


}
