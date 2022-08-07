package com.gsg.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/6 16:41
 */
@Data
@Accessors(chain = true)
public class UserPointsDTO implements Serializable {
    private static final long serialVersionUID = 5149304688581308114L;

    /**
     * 用户表主键
     */
    private String userId;

    /**
     * 参与积分
     */
    private Integer point;

    /**
     * 积分加减标识
     */
    private Integer ident;

}
