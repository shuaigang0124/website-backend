package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.YearDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.YearVO;
import com.gsg.shuaigang.service.IYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 年份表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/v1/year")
public class YearController {

    @Autowired
    IYearService yearService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2022/03/17 15:19
     */
    @PostMapping("/insertYear")
    public R<?> insertYear(@RequestBody Request<YearDTO> request){
        try {
            YearDTO yearDTO = request.getCustomData();
            yearService.insertYear(yearDTO);
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
    @PostMapping("/getYearList")
    public R<?> getYearList(){
        try {
            List<YearVO> list = yearService.getYearList();
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
    @PostMapping("/updateYear")
    public R<?> updateYear(@RequestBody Request<YearDTO> request){
        try {
            YearDTO yearDTO = request.getCustomData();
            yearService.updateYear(yearDTO);
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
    @PostMapping("/deleteYear")
    public R<?> deleteYear(@RequestBody Request<YearDTO> request){
        try {
            YearDTO yearDTO = request.getCustomData();
            yearService.deleteYear(yearDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
