package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Tag;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.TagVO;
import com.gsg.shuaigang.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/v1/tag")
public class TagController {

    @Autowired
    ITagService tagService;

    /**新增标签
     * @author gaoshenggang
     * @date  2021/11/30 16:08
     */
    @PostMapping("/insertTag")
    public R<?> insertTag(@RequestBody Request<Tag> request){
        try {
            Tag tag = request.getCustomData();
            tagService.insertTag(tag);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 16:09
     */
    @PostMapping("/getAllTag")
    public R<?> getAllTag(){
        try {
            List<TagVO> list = tagService.getAllTag();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 后台查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 16:09
     */
    @PostMapping("/getAllBackendTag")
    public R<?> getAllBackendTag(@RequestBody Request<FuzzySearchDTO> request){
        try {
            List<TagVO> list = tagService.getAllBackendTag(request.getCustomData());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除标签（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/30 16:10
     */
    @PostMapping("/updateTag")
    public R<?> updateTag(@RequestBody Request<Tag> request){
        try {
            Tag tag = request.getCustomData();
            tagService.updateTag(tag);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
