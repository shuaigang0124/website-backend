package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.Author;
import com.gsg.commons.vo.AuthorVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Repository
public interface AuthorMapper extends BaseMapper<Author> {

    /* 查询 */
    List<AuthorVO> getAuthor();

    /* 删除 */
    Integer deleteAuthor(Integer[] ids);
}
