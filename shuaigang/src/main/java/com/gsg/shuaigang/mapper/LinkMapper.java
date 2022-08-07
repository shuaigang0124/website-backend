package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Link;
import com.gsg.commons.vo.LinkVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 外链接表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

    /* 查询 */
    List<LinkVO> getLink(FuzzySearchDTO fuzzySearchDTO);

    /* 删除 */
    Integer deletelink(Integer[] ids);


}
