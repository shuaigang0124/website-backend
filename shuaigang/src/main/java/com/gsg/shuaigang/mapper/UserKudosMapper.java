package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Tag;
import com.gsg.commons.model.UserKudos;
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
public interface UserKudosMapper extends BaseMapper<UserKudos> {

    /** 查询点赞记录 */
    Integer findByUserIdAndServiceId(String userId, String serviceId);

    /** 逻辑删除点赞记录 */
    int deleteByUserIdAndServiceId(String userId, String serviceId);

    /** 恢复点赞记录 */
    int updateByUserIdAndServiceId(String userId, String serviceId);
}
