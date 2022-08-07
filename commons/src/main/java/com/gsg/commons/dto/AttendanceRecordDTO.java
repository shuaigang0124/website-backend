package com.gsg.commons.dto;

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
 * @date 2022/1/24 21:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AttendanceRecordDTO implements Serializable {

    private static final long serialVersionUID = -5446158445065366724L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 签到日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dayTime;

}
