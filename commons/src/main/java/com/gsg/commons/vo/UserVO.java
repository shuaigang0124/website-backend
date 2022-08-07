package com.gsg.commons.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/8/24 17:47
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {

    private static final long serialVersionUID = -5501820298875095470L;

    private String id;

    private String userName;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String email;

    private String phone;

    private String avatar;

    private String role;

    private Integer sex;

    private String address;

    private String cityName;
}
