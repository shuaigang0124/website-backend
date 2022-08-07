package com.gsg.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/1 13:16
 */
@Data
@Accessors(chain = true)
public class FileDTO implements Serializable {

    private static final long serialVersionUID = -6943640493487799399L;
    /** 文件路径 */
    @NotBlank(message = "文件路径不能为空!")
    private String filePath;

}
