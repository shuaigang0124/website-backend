package com.gsg.commons.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/30 14:06
 */
@Data
@Accessors(chain = true)
public class CityDTO implements Serializable {
    private static final long serialVersionUID = -195805379104688126L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 用户数量标识：（小于0为数量减一；大于等于零为数量加一）
     */
    private Integer subtractOrAdd;


}
