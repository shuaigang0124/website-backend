package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.DiaryDTO;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.DiaryVO;
import com.gsg.shuaigang.service.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 日记表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/v1/diary")
public class DiaryController {

    @Autowired
    IDiaryService diaryService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2021/10/11 15:19
     */
    @PostMapping("/insertDiary")
    public R<?> insertDiary(@RequestBody Request<DiaryDTO> request){
        try {
            DiaryDTO diaryDTO = request.getCustomData();
            diaryService.insertDiary(diaryDTO);
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
    @PostMapping("/getDiary")
    public R<?> getDiary(@RequestBody Request<FuzzySearchDTO> request){
        try {
            FuzzySearchDTO fuzzySearchDTO = request.getCustomData();
            List<DiaryVO> list = diaryService.getDiary(fuzzySearchDTO);
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
    @PostMapping("/updateDiary")
    public R<?> updateDiary(@RequestBody Request<DiaryDTO> request){
        try {
            DiaryDTO diaryDTO = request.getCustomData();
            diaryService.updateDiary(diaryDTO);
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
    @PostMapping("/deleteDiary")
    public R<?> deleteDiary(@RequestBody Request<DiaryDTO> request){
        try {
            DiaryDTO diaryDTO = request.getCustomData();
            diaryService.deleteDiary(diaryDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
