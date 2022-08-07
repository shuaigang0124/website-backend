package com.gsg.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/13 13:36
 */
@Data
@Accessors(chain = true)
public class ForRecordDTO implements Serializable {
    private static final long serialVersionUID = 7746415239415292570L;

    // 用户id
    private String userId;

    // 商家id
    private String tradeUserId;

    // 商品id
    private String tradeId;

    // 兑换记录id
    private String id;

    // 兑换消耗积分
    private Integer subNum;

    // 状态
    private Integer convertStatus;

}
