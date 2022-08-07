package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.SharingDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Sharing;
import com.gsg.commons.utils.*;
import com.gsg.commons.vo.*;
import com.gsg.shuaigang.service.ISharingService;
import com.gsg.shuaigang.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.gsg.commons.utils.Constants.PREFIX;
import static com.gsg.commons.utils.Constants.TEMP;

/**
 * <p>
 * 日常分享表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/v1/sharing")
public class SharingController extends BaseController {

    @Autowired
    ISharingService sharingService;

    /**
     * 新增日常分享
     *
     * @author gaoshenggang
     * @date 2021/11/29 17:01
     */
    @PostMapping("/insertSharing")
    public R<?> insertSharing(@RequestBody Request<SharingDTO> request) {
        try {
            SharingDTO sharingDTO = request.getCustomData();
            String imgPath = sharingDTO.getImg();
            if (StringUtils.isEmpty(imgPath)) {
                throw ServiceException.errorParams("请求参数有误");
            }
            String newPath;
            if (imgPath.contains(TEMP)) {
                imgPath = imgPath.substring(imgPath.lastIndexOf(PREFIX));
                newPath = FileUploadUtils.singleMove(imgPath);
                sharingDTO.setImg(newPath);
            }

            sharingService.insertSharing(sharingDTO);
            return R.ok("发布成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询所有日常分享（传入tagId、id，则为标签和id条件查询）
     *
     * @author gaoshenggang
     * @date 2021/11/29 17:44
     */
    @PostMapping("/getSharing")
    public R<?> getSharing(@RequestBody Request<SharingDTO> request) {
        try {
            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String supportWebp = httpServletRequest.getHeader("supportWebp");
            SharingDTO sharingDTO = request.getCustomData();
            sharingDTO.setSupportWebp(supportWebp);
            List<SharingVO> list = new ArrayList<>();
            /* 如果不传分页参数则返回所有数据 */
            Page page = sharingDTO.getPage();
            if (StringUtils.isEmpty(page)) {
                list = sharingService.getSharing(sharingDTO, null);
                return R.ok(list);
            }
            /* 分页查询 ,先格式化传入的分页参数*/
            Page pageAfterProcess = pageDeal(page);
            sharingDTO.setPage(pageAfterProcess);

            PageResponseVO<SharingVO> pageResponseVO = new PageResponseVO();

            /*1、查询数据量总数*/
            long total = sharingService.getSharingListTotal(sharingDTO);
            if (total == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到数据列表");
            }

            /*2、分页查询当页数据*/
            list = sharingService.getSharing(sharingDTO, pageAfterProcess);
            if (list == null || list.size() == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到数据列表");
            }

            /*3、封装返回分页结果数据*/
            pageResponseVO.setTotalCount(total);
            pageResponseVO.setCount(list.size());
            pageResponseVO.setCurrentPage(pageAfterProcess.getIndex());
            pageResponseVO.setResultList(list);

            /* 传入分页参数，则直接对结果数据分页返回*/
//          Integer index = page.getIndex();
//          Integer size = page.getSize();
//          List<SharingVO> subList = list.subList(size * (index - 1), Math.min((index * size), total));
            return R.ok(pageResponseVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 通过tagId查询除当前分享的其它日常分享（tagId、sharingId）
     *
     * @author gaoshenggang
     * @date 2021/11/29 17:44
     */
    @PostMapping("/getSharingByTag")
    public R<?> getSharingByTag(@RequestBody Request<SharingDTO> request) {
        try {
            SharingDTO sharingDTO = request.getCustomData();
            List<SharingByTagVO> list = sharingService.getSharingByTag(sharingDTO);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 后台查询所有-分页查询
     *
     * @author gaoshenggang
     * @date 2021/11/30 21:15
     */
    @PostMapping("/getBackendAllSharing")
    public R<?> getBackendAllSharing(@RequestBody Request<SharingDTO> request) {
        try {
            SharingDTO sharingDTO = request.getCustomData();
            Page page = sharingDTO.getPage();
            List<SharingBackendVO> list = new ArrayList<>();
            // 分页查询 ,先格式化传入的分页参数
            Page pageAfter = pageDeal(page);
            sharingDTO.setPage(pageAfter);
            PageResponseVO<SharingBackendVO> pageResponseVO = new PageResponseVO<>();
            // 1、查询数据量总数
            long total = sharingService.getSharingListTotal(sharingDTO);
            if (total == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("数据总量为0,未查询到数据!" + pageResponseVO);
            }
            // 2、分页查询当页数据
            list = sharingService.getAllSharing(sharingDTO, pageAfter);
            if (list == null || list.size() == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到数据!" + pageResponseVO);
            }
            // 封装分页结果数据
            pageResponseVO.setTotalCount(total);
            pageResponseVO.setCount(list.size());
            pageResponseVO.setCurrentPage(pageAfter.getIndex());
            pageResponseVO.setResultList(list);

            return R.ok(pageResponseVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 通过ID查询日常分享详情（包括评论总条数，webp处理）
     *
     * @author gaoshenggang
     * @date 2021/11/30 11:52
     */
    @PostMapping("/getSharingDetail")
    public R<?> getSharingDetail(@RequestBody Request<Sharing> request) {
        try {
            Sharing sharing = request.getCustomData();
            String id = sharing.getId();
            SharingDetailVO sharingDetail = sharingService.getSharingDetail(id);
            return R.ok(sharingDetail);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询修改的日常分享内容
     * @author gaoshenggang
     * @date  2021/12/28 14:47
     */
    @PostMapping("/getUpdateDetail")
    public R<?> getUpdateDetail(@RequestBody Request<Sharing> request) {
        try {
            Sharing sharing = request.getCustomData();
            String id = sharing.getId();
            SharingDetailVO sharingDetailVO = sharingService.getUpdateDetail(id);
            return R.ok(sharingDetailVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 条件修改
     * 修改日常分享（点赞数，阅读数，删除标识（逻辑删除））
     *
     * @author gaoshenggang
     * @date 2021/11/29 17:50
     */
    @PostMapping("/updateByCondition")
    public R<?> updateByCondition(@RequestBody Request<SharingDTO> request) {
        try {
            SharingDTO sharingDTO = request.getCustomData();
            Integer clickNum = sharingDTO.getClickNum();
            Integer readNum = sharingDTO.getReadNum();
            Integer deleted = sharingDTO.getDeleted();
            // 图片路径转移
            String imgPath = sharingDTO.getImg();
            if (!StringUtils.isEmpty(imgPath) && imgPath.contains(TEMP)) {
                String newPath;
                imgPath = imgPath.substring(imgPath.lastIndexOf(PREFIX));
                newPath = FileUploadUtils.singleMove(imgPath);
                sharingDTO.setImg(newPath);
            }
            int afterClickNum = sharingService.updateSharing(sharingDTO);
            if (clickNum != null && readNum == null && deleted == null) {
                if (clickNum < afterClickNum) {
                    return R.ok("点赞成功");
                }
                return R.ok("取消成功！");
            } else if (clickNum == null && readNum != null && deleted == null) {
                return R.ok("阅读量+1！");
            } else if (clickNum == null && readNum == null && deleted != null) {
                return R.ok("删除成功！");
            } else {
                return R.ok("修改成功！");
            }
        } catch (Exception e) {
            return R.failed(e);
        }
    }


}
