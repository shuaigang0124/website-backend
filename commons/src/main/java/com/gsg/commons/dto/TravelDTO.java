package com.gsg.commons.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TravelDTO implements Serializable {

    private static final long serialVersionUID = -4478242678030081174L;

    private Integer id;

    private String cityId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dayTime;

    private Integer[] ids;

}
