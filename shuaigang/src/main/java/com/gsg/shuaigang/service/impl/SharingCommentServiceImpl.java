package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.CommentDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.utils.WebPUtils;
import com.gsg.commons.vo.CommentVO;
import com.gsg.shuaigang.mapper.SharingCommentMapper;
import com.gsg.commons.model.SharingComment;
import com.gsg.shuaigang.mapper.UserKudosMapper;
import com.gsg.shuaigang.service.ISharingCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.gsg.commons.utils.Constants.TYPE_0;
import static com.gsg.commons.utils.Constants.USER_KUDOS_URL;

/**
 * <p>
 * 日常分享-评论表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@Service
public class SharingCommentServiceImpl extends ServiceImpl<SharingCommentMapper, SharingComment> implements ISharingCommentService {

    @Autowired
    SharingCommentMapper sharingCommentMapper;

    @Autowired
    UserKudosMapper userKudosMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 新增评论
     * @author gaoshenggang
     * @date  2021/11/29 14:12
     */
    @Override
    public void insertComment(CommentDTO commentDTO) {
        SharingComment comment = new SharingComment();
        String id = "CMT" + PKGenerator.generate();

        // 父级ID如果不传说明为顶级，直接给默认值
        String parentId = commentDTO.getParentId();
        if (StringUtils.isEmpty(parentId)) {
            comment.setPId(TYPE_0);
        } else {
            SharingComment parentDetil = sharingCommentMapper.findPIdByCommentId(parentId);
            String pId = parentDetil.getPId();
            Integer level = parentDetil.getLevel();
            String userId = parentDetil.getUserId();
            if (level == 0) {
                comment.setPId(parentId)
                        .setLevel(level + 1);
            } else {
                comment.setPId(pId)
                        .setBeCommentedUserId(userId)
                        .setLevel(level);
            }
        }
        if (StringUtils.isEmpty(commentDTO.getUserId()) || !commentDTO.getUserId().startsWith("GSG")) {
            throw ServiceException.errorParams("userId不能为空！");
        }
        if (StringUtils.isEmpty(commentDTO.getContent())) {
            throw ServiceException.errorParams("内容不能为空！");
        }
        comment.setId(id)
                .setSharingId(commentDTO.getSharingId())
                .setUserId(commentDTO.getUserId())
                .setContent(commentDTO.getContent());
        int row = sharingCommentMapper.insert(comment);
        if (row != 1) {
            throw ServiceException.busy();
        }

    }

    /**
     * 查询全部评论
     * @author gaoshenggang
     * @date  2021/11/29 15:01
     */
    @Override
    public List<CommentVO> findAllComment(SharingComment sharingComment, String supportWebp) {
        if (StringUtils.isEmpty(sharingComment.getSharingId())) {
            throw ServiceException.errorParams("请传入shringId");
        }
        // 顶级评论
        List<CommentVO> result = sharingCommentMapper.findAllComment(sharingComment.getSharingId(), 0);
        // 所有评论
        List<CommentVO> lists = sharingCommentMapper.findAllComment(sharingComment.getSharingId(), null);

        for (CommentVO parent : result) {

            // 顶级评论头像处理
            if (!StringUtils.isEmpty(parent.getAvatar())) {
                String avatarPath = parent.getAvatar();
                // 20211210 生成WebP图片副本
                avatarPath = WebPUtils.changePathToWebp("1", avatarPath);
                parent.setAvatar(avatarPath);
            }
            if (!StringUtils.isEmpty(sharingComment.getUserId())) {
                Integer deleted = userKudosMapper.findByUserIdAndServiceId(sharingComment.getUserId(), parent.getId());
                if (deleted == null || deleted == 1) {
                    parent.setClickState(0);
                } else {
                    parent.setClickState(1);
                }
            } else {
                parent.setClickState(0);
            }

            String pId = parent.getId();
            List<CommentVO> commentVOS = new ArrayList<>();

            // 遍历所有数据，为顶级设置子节点集合
            for (CommentVO child : lists) {
                // 子节点
                if (pId.equals(child.getPId())) {
                    // 处理子节点图片
                    if (!StringUtils.isEmpty(child.getAvatar())) {
                        String avatarPath = child.getAvatar();
                        // 20211210 生成WebP图片副本
                        avatarPath = WebPUtils.changePathToWebp("1", avatarPath);
                        child.setAvatar(avatarPath);
                    }
                    if (!StringUtils.isEmpty(sharingComment.getUserId())) {
                        Integer deleted = userKudosMapper.findByUserIdAndServiceId(sharingComment.getUserId(), child.getId());
                        if (deleted == null || deleted == 1) {
                            child.setClickState(0);
                        } else {
                            child.setClickState(1);
                        }
                    } else {
                        child.setClickState(0);
                    }
                    commentVOS.add(child);
                }
                parent.setChildren(commentVOS);
            }
            // 设置顶级的子节点条数
            int total = parent.getChildren().size();
            parent.setTotal(total);
        }
        return result;
    }

    /**
     * 修改评论点赞数量
     * @author gaoshenggang
     * @date  2021/11/29 15:15
     */
    @Override
    public int updateComment(SharingComment sharingComment) {
        String id = sharingComment.getId();
        Integer clickNum = sharingComment.getClickNum();
        String userId = sharingComment.getUserId();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(userId) || clickNum == null) {
            throw ServiceException.errorParams("请求参数有误");
        }
        clickNum = restTemplate.getForObject(USER_KUDOS_URL, Integer.class, userId, id, clickNum);
        SharingComment comment = new SharingComment();
        comment.setId(id)
                .setClickNum(clickNum);
        int i = sharingCommentMapper.updateById(comment);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
        return clickNum == null ? 0 : clickNum;
    }

    /**
     * 删除评论（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/29 15:15
     */
    @Override
    public void deleteComment(SharingComment sharingComment) {
        String id = sharingComment.getId();
        Integer deleted = sharingComment.getDeleted();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("请传入评论id！");
        }
        if (deleted == null) {
            sharingComment.setDeleted(1);
        }
        sharingCommentMapper.updateById(sharingComment);
    }
}
