package com.gsg.commons.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/6 16:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserPointsVO implements Serializable {
    private static final long serialVersionUID = 8256098623638186480L;

    /**
     * 用户表主键
     */
    private String userName;

    /**
     * 用户积分
     */
    private Integer point;

}
