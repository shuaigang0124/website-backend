package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.model.UserKudos;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/14 16:51
 */
public interface IUserKudosService extends IService<UserKudos> {

    int confirmAndCancelKudos(String userId, String serviceId, Integer clickNum);

    /** 查询用户是否已点赞 */
    int findByUserIdAndServiceId(String userId, String serviceId);

}
