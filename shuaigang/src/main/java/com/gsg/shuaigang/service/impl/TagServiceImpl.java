package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.TagVO;
import com.gsg.shuaigang.mapper.TagMapper;
import com.gsg.commons.model.Tag;
import com.gsg.shuaigang.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-30
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    TagMapper tagMapper;

    /**
     * 新增标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    @Override
    public void insertTag(Tag tag) {
        if (tag.getName() == null) {
            throw ServiceException.errorParams("name必传");
        }
        int i = tagMapper.insert(tag);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    @Override
    public List<TagVO> getAllTag() {
        return tagMapper.getAllTag();
    }
    /**
     * 后台查询所有标签
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    @Override
    public List<TagVO> getAllBackendTag(FuzzySearchDTO fuzzySearchDTO) {
        return tagMapper.getAllBackendTag(fuzzySearchDTO);
    }


    /**
     * 删除标签（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/30 15:55
     */
    @Override
    public void updateTag(Tag tag) {
        if (tag.getId() == null) {
            throw ServiceException.errorParams("id必传");
        }
        tag.setGmtModified(LocalDateTime.now());
        int i = tagMapper.updateById(tag);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }
}
