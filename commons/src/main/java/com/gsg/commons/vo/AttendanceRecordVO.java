package com.gsg.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/1/24 21:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AttendanceRecordVO implements Serializable {
    private static final long serialVersionUID = -167567519342120567L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayTime;

}
