package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 15:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DiaryVO implements Serializable {

    private static final long serialVersionUID = 4220559162633772952L;

    private String id;

    private LocalDate day;

    private String weather;

    private String mood;

    private String content;

}
