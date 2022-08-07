package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.LinkDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.LinkVO;
import com.gsg.shuaigang.mapper.LinkMapper;
import com.gsg.commons.model.Link;
import com.gsg.shuaigang.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 外链接表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

    @Autowired
    LinkMapper linkMapper;

    @Override
    public void insertLink(LinkDTO linkDTO) {
        if (linkDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Link link = new Link();
        link.setName(linkDTO.getName())
                .setUrl(linkDTO.getUrl())
                .setType(linkDTO.getType());
        int i = linkMapper.insert(link);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<LinkVO> getLink(FuzzySearchDTO fuzzySearchDTO) {
        return linkMapper.getLink(fuzzySearchDTO);
    }

    @Override
    public void updateLink(LinkDTO linkDTO) {
        if (linkDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer id = linkDTO.getId();
        Link link = new Link();
        link.setId(id)
                .setName(linkDTO.getName())
                .setUrl(linkDTO.getUrl())
                .setType(linkDTO.getType());
        int i = linkMapper.updateById(link);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public void deleteLink(LinkDTO linkDTO) {
        if (linkDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer[] ids = linkDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = linkMapper.deletelink(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
