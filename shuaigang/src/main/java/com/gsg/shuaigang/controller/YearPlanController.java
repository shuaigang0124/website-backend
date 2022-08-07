package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.YearDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.YearPlanVO;
import com.gsg.commons.vo.YearVO;
import com.gsg.shuaigang.service.IYearPlanService;
import com.gsg.shuaigang.service.YearPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 年度计划表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/v1/yearPlan")
public class YearPlanController {

    @Autowired
    IYearPlanService yearPlanService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2022/03/17 15:19
     */
    @PostMapping("/insertYearPlan")
    public R<?> insertYearPlan(@RequestBody Request<YearPlanDTO> request){
        try {
            YearPlanDTO yearPlanDTO = request.getCustomData();
            yearPlanService.insertYearPlan(yearPlanDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询
     * @author gaoshenggang
     * @date  2022/03/17 15:21
     */
    @PostMapping("/getYearPlan")
    public R<?> getYearPlan(@RequestBody Request<FuzzySearchDTO> request){
        try {
            List<YearPlanVO> list = yearPlanService.getYearPlan(request.getCustomData());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改
     * @author gaoshenggang
     * @date  2022/03/17 15:23
     */
    @PostMapping("/updateYearPlan")
    public R<?> updateYearPlan(@RequestBody Request<YearPlanDTO> request){
        try {
            YearPlanDTO yearPlanDTO = request.getCustomData();
            yearPlanService.updateYearPlan(yearPlanDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除
     * @author gaoshenggang
     * @date  2022/03/17 15:25
     */
    @PostMapping("/deleteYearPlan")
    public R<?> deleteYearPlan(@RequestBody Request<YearPlanDTO> request){
        try {
            YearPlanDTO yearPlanDTO = request.getCustomData();
            yearPlanService.deleteYearPlan(yearPlanDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
