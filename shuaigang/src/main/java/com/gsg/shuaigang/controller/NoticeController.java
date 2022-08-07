package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.NoticeDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.NoticeBackendVO;
import com.gsg.commons.vo.NoticeVO;
import com.gsg.shuaigang.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/v1/notice")
public class NoticeController {

    @Autowired
    INoticeService noticeService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2021/12/22 15:19
     */
    @PostMapping("/insertNotice")
    public R<?> insertNotice(@RequestBody Request<NoticeDTO> request){
        try {
            NoticeDTO noticeDTO = request.getCustomData();
            noticeService.insertNotice(noticeDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 前台查询
     * @author gaoshenggang
     * @date  2021/12/22 15:21
     */
    @PostMapping("/getNotice")
    public R<?> getNotice(){
        try {
            List<NoticeVO> list = noticeService.getNotice();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 后台查询
     * @author gaoshenggang
     * @date  2021/12/22 15:21
     */
    @PostMapping("/getBackendNotice")
    public R<?> getBackendNotice(@RequestBody Request<FuzzySearchDTO> request){
        try {
            List<NoticeBackendVO> list = noticeService.getBackendNotice(request.getCustomData());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改
     * @author gaoshenggang
     * @date  2021/12/22 15:23
     */
    @PostMapping("/updateNotice")
    public R<?> updateNotice(@RequestBody Request<NoticeDTO> request){
        try {
            NoticeDTO noticeDTO = request.getCustomData();
            noticeService.updateNotice(noticeDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除
     * @author gaoshenggang
     * @date  2021/12/22 15:25
     */
    @PostMapping("/deleteNotice")
    public R<?> deleteNotice(@RequestBody Request<NoticeDTO> request){
        try {
            NoticeDTO noticeDTO = request.getCustomData();
            noticeService.deleteNotice(noticeDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }


}
