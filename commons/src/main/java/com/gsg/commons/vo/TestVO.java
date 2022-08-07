package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/11/1 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TestVO implements Serializable {
    private static final long serialVersionUID = -4922468106716691086L;

    /** 当日工单完成数 */
    private Integer theDayRecordComplete;

    /** 当月工单完成数 */
    private Integer theMonthRecordComplete;

}
