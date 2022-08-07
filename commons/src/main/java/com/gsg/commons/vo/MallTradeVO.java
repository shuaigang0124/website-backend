package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/12 10:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MallTradeVO implements Serializable {
    private static final long serialVersionUID = -8016587770996617160L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 商品发布人id
     */
    private String userId;

    /**
     * 商品发布人
     */
    private String userName;

    /**
     * 商品类型id；1-6
     */
    private Integer typeId;

    /**
     * 商品名称
     */
    private String tradeName;

    /**
     * 价值积分
     */
    private Integer pointNum;

    /**
     * 商品图片
     */
    private String tradeImg;

    /**
     * 商品剩余数量
     */
    private Integer tradeNum;

}
