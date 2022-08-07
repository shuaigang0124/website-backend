package com.gsg.shuaigang.controller;

import com.gsg.commons.model.UserKudos;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.shuaigang.service.IUserKudosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/14 16:43
 */
@RestController
@RequestMapping("/v1/userKudos")
public class UserKudosController {

    @Autowired
    IUserKudosService userKudosService;

    @RequestMapping("/confirmAndCancelKudos")
    public int confirmAndCancelKudos(String userId, String serviceId, Integer clickNum) {
        return userKudosService.confirmAndCancelKudos(userId, serviceId, clickNum);
    }

    @PostMapping("/findByUserIdAndServiceId")
    public R<?> findByUserIdAndServiceId(@RequestBody Request<UserKudos> request){
        int num = userKudosService.findByUserIdAndServiceId(request.getCustomData().getUserId(), request.getCustomData().getServiceId());
        return R.ok(num);
    }
}
