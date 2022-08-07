package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.AuthorDTO;
import com.gsg.commons.model.Author;
import com.gsg.commons.vo.AuthorVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
public interface IAuthorService extends IService<Author> {

    /* 增加 */
    void insertAuthor(AuthorDTO authorDTO);

    /* 查询 */
    List<AuthorVO> getAuthor();

    /* 修改 */
    int updateAuthor(AuthorDTO authorDTO);

    /* 删除 */
    void deleteAuthor(AuthorDTO authorDTO);


}
