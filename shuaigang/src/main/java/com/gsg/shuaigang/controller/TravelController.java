package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.TravelDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.CityAndTravelVO;
import com.gsg.commons.vo.TravelVO;
import com.gsg.shuaigang.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 个人文档表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/v1/travel")
public class TravelController {

    @Autowired
    ITravelService travelService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2021/10/11 15:19
     */
    @PostMapping("/insertTravel")
    public R<?> insertTravel(@RequestBody Request<TravelDTO> request){
        try {
            TravelDTO travelDTO = request.getCustomData();
            travelService.insertTravel(travelDTO);
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
    @PostMapping("/getTravel")
    public R<?> getTravel(@RequestBody Request<FuzzySearchDTO> request){
        try {
            List<TravelVO> list = travelService.getTravel(request.getCustomData());
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询
     * @author gaoshenggang
     * @date  2021/10/11 15:21
     */
    @PostMapping("/getCityAndTravel")
    public R<?> getCityAndTravel(){
        try {
            List<CityAndTravelVO> list = travelService.getCityAndTravel();
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
    @PostMapping("/updateTravel")
    public R<?> updateTravel(@RequestBody Request<TravelDTO> request){
        try {
            TravelDTO travelDTO = request.getCustomData();
            travelService.updateTravel(travelDTO);
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
    @PostMapping("/deleteTravel")
    public R<?> deleteTravel(@RequestBody Request<TravelDTO> request){
        try {
            TravelDTO travelDTO = request.getCustomData();
            travelService.deleteTravel(travelDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
