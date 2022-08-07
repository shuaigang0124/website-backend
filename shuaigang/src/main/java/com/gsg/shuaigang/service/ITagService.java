package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.LogDTO;
import com.gsg.commons.model.Tag;
import com.gsg.commons.vo.TagVO;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-30
 */
public interface ITagService extends IService<Tag> {

    /**
     * 新增标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    void insertTag(Tag tag);

    /**
     * 查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    List<TagVO> getAllTag();

    /**
     * 后台查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    List<TagVO> getAllBackendTag(FuzzySearchDTO fuzzySearchDTO);

    /**
     * 删除标签（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    void updateTag(Tag tag);
}
