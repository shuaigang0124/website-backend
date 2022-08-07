package com.gsg.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/17 18:49
 */
@Data
@Accessors(chain = true)
public class MailCodeDTO {

    @NotNull(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式错误!")
    private String email;

    private String validCode;

    private Integer type;

    private String userId;

    private String prizeName;
}
