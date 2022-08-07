package com.gsg.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gaoshenggang
 * @date  2021/11/25 16:23
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileVO implements Serializable {
    private static final long serialVersionUID = -2736590241591395358L;

    /** 顺序 */
    private Integer sort;

    /** 文件路径 */
    private String filePath;

    public FileVO(String filePath) {
        this.filePath = filePath;
    }
}
