package com.gsg.commons.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gsg.commons.utils.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/12 10:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MallDTO implements Serializable {
    private static final long serialVersionUID = -4006843257724611388L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 商品发布人id
     */
    private String userId;

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

    /**
     * 分页参数
     */
    private Page page;

}
