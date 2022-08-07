package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.LinkDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.LinkVO;
import com.gsg.shuaigang.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 外链接表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/v1/link")
public class LinkController {

    @Autowired
    ILinkService linkService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2021/10/11 15:19
     */
    @PostMapping("/insertLink")
    public R<?> insertLink(@RequestBody Request<LinkDTO> request){
        try {
            LinkDTO linkDTO = request.getCustomData();
            linkService.insertLink(linkDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询
     * @author gaoshenggang
     * @date  2021/10/11 15:21
     */
    @PostMapping("/getLink")
    public R<?> getLink(@RequestBody Request<FuzzySearchDTO> request){
        try {
            List<LinkVO> list = linkService.getLink(request.getCustomData());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改
     * @author gaoshenggang
     * @date  2021/10/11 15:23
     */
    @PostMapping("/updateLink")
    public R<?> updateLink(@RequestBody Request<LinkDTO> request){
        try {
            LinkDTO linkDTO = request.getCustomData();
            linkService.updateLink(linkDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除
     * @author gaoshenggang
     * @date  2021/10/11 15:25
     */
    @PostMapping("/deleteLink")
    public R<?> deleteLink(@RequestBody Request<LinkDTO> request){
        try {
            LinkDTO linkDTO = request.getCustomData();
            linkService.deleteLink(linkDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
