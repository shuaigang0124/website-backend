package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Tag;
import com.gsg.commons.vo.TagVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-30
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<TagVO> getAllTag();

    List<TagVO> getAllBackendTag(FuzzySearchDTO fuzzySearchDTO);
}
