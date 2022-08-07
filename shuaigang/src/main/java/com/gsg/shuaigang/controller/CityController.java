package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.CityDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.CityVO;
import com.gsg.commons.vo.MapVO;
import com.gsg.shuaigang.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 城市表-省份-用户数量 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-12-30
 */
@RestController
@RequestMapping("/v1/city")
@Slf4j
public class CityController {

    @Autowired
    ICityService cityService;

    /**
     * 查询
     * @author gaoshenggang
     * @date  2021/12/30 14:36
     */
    @PostMapping("/getCityData")
    public R<?> getCityData(){
        try {
            List<CityVO> list = cityService.getCityValue();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 后台查询
     * @author gaoshenggang
     * @date  2021/12/30 16:08
     */
    @PostMapping("/getBackendCityData")
    public R<?> getBackendCityData(){
        try {
            List<MapVO> list = cityService.getCityData();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改（城市用户数量加一或者减一）
     * @author gaoshenggang
     * @date  2021/12/30 14:37
     */
    @PostMapping("/updateCityValue")
    public R<?> updateCityValue(@RequestBody Request<CityDTO> request){
        try {
            CityDTO cityDTO = request.getCustomData();
            cityService.updateCityValue(cityDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
