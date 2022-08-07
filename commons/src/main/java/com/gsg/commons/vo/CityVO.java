package com.gsg.commons.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/30 13:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CityVO implements Serializable {
    private static final long serialVersionUID = 2431254279510342641L;

    /**
     * 自定义主键
     */
    private String id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 用户数量
     */
    private Integer userValue;

}
