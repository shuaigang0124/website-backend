package com.gsg.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/10/11 15:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DiaryDTO implements Serializable {
    private static final long serialVersionUID = 4514065432055698589L;

    private String id;

    private String weather;

    private String mood;

    private String content;

    private String[] ids;

}
