package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.CommentDTO;
import com.gsg.commons.model.SharingComment;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.CommentVO;
import com.gsg.shuaigang.service.ISharingCommentService;
import com.gsg.shuaigang.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 日常分享-评论表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/v1/sharingComment")
public class SharingCommentController {

    @Autowired
    ISharingCommentService sharingCommentService;

    /**
     * 新增评论
     * @author gaoshenggang
     * @date  2021/11/29 14:35
     */
    @PostMapping("/releaseComment")
    public R<?> releaseComment(@RequestBody Request<CommentDTO> request) {
        try {
            CommentDTO commentDTO = request.getCustomData();
            sharingCommentService.insertComment(commentDTO);
            if (StringUtils.isEmpty(commentDTO.getParentId())) {
                return R.ok("评论成功！");
            }
            return R.ok("回复成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询所有评论
     * @author gaoshenggang
     * @date  2021/11/29 15:07
     */
    @PostMapping("/findAllComment")
    public R<?> findAllComment(@RequestBody Request<SharingComment> request) {
        try {
            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String supportWebp = httpServletRequest.getHeader("supportWebp");

            SharingComment sharingComment = request.getCustomData();
            List<CommentVO> list = sharingCommentService.findAllComment(sharingComment, supportWebp);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改评论点赞数量
     * @author gaoshenggang
     * @date  2021/11/29 15:20
     */
    @PostMapping("/updateComment")
    public R<?> updateComment(@RequestBody Request<SharingComment> request) {
        try {
            SharingComment sharingComment = request.getCustomData();
            int afterClickNum = sharingCommentService.updateComment(sharingComment);
            if (sharingComment.getClickNum() < afterClickNum) {
                return R.ok("点赞成功！");
            }
            return R.ok("取消成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除评论（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/29 15:20
     */
    @PostMapping("/deleteComment")
    public R<?> deleteComment(@RequestBody Request<SharingComment> request) {
        try {
            SharingComment sharingComment = request.getCustomData();
            sharingCommentService.deleteComment(sharingComment);
            return R.ok("删除成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }


}
