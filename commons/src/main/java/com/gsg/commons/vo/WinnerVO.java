package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2022/1/6 20:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class WinnerVO implements Serializable {
    private static final long serialVersionUID = -4882569330331960175L;

    private String id;

    private String userId;

    private String userName;

    private String email;

}
