package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.UserKudos;
import com.gsg.shuaigang.mapper.UserKudosMapper;
import com.gsg.shuaigang.service.IUserKudosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/3/14 16:52
 */
@Service
public class UserKudosServiceImpl extends ServiceImpl<UserKudosMapper, UserKudos> implements IUserKudosService {

    @Autowired
    UserKudosMapper userKudosMapper;

    @Override
    public int confirmAndCancelKudos(String userId, String serviceId, Integer clickNum) {

        Integer deleted = userKudosMapper.findByUserIdAndServiceId(userId, serviceId);
        int row;
        if (deleted == null) {
            UserKudos userKudos = new UserKudos();
            userKudos.setUserId(userId)
                    .setServiceId(serviceId);
            row = userKudosMapper.insert(userKudos);
            clickNum += 1;
        } else if (deleted == 1){
            row = userKudosMapper.updateByUserIdAndServiceId(userId, serviceId);
            clickNum += 1;
        } else {
            row = userKudosMapper.deleteByUserIdAndServiceId(userId, serviceId);
            clickNum -= 1;
        }
        if (row != 1) {
            throw ServiceException.busy();
        }
        return clickNum;
    }

    @Override
    public int findByUserIdAndServiceId(String userId, String serviceId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(serviceId)) {
            throw ServiceException.errorParams("请求参数有误");
        }
        Integer deleted = userKudosMapper.findByUserIdAndServiceId(userId, serviceId);
        if (deleted == null || deleted == 1) {
            return 0;
        }
        return 1;
    }
}
