package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.AuthorDTO;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.dto.LinkDTO;
import com.gsg.commons.model.Link;
import com.gsg.commons.vo.AuthorVO;
import com.gsg.commons.vo.LinkVO;

import java.util.List;

/**
 * <p>
 * 外链接表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
public interface ILinkService extends IService<Link> {

    /* 增加 */
    void insertLink(LinkDTO linkDTO);

    /* 查询 */
    List<LinkVO> getLink(FuzzySearchDTO fuzzySearchDTO);

    /* 修改 */
    void updateLink(LinkDTO linkDTO);

    /* 删除 */
    void deleteLink(LinkDTO linkDTO);

}
