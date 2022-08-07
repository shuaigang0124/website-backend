package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.AuthorDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.UserKudos;
import com.gsg.commons.utils.SubStringUtils;
import com.gsg.commons.vo.AuthorVO;
import com.gsg.shuaigang.mapper.AuthorMapper;
import com.gsg.commons.model.Author;
import com.gsg.shuaigang.mapper.UserKudosMapper;
import com.gsg.shuaigang.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.gsg.commons.utils.Constants.USER_KUDOS_URL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void insertAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            throw ServiceException.errorParams("请传入参数!");
        }
        Author author = new Author();
        author.setContent(SubStringUtils.replaceHtmlContentImgPath(authorDTO.getContent()))
                .setClick(authorDTO.getClick());

        int i = authorMapper.insert(author);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<AuthorVO> getAuthor() {
        List<AuthorVO> author = authorMapper.getAuthor();
        for (AuthorVO authorVO : author) {
            authorVO.setContent(SubStringUtils.joinContentImg(authorVO.getContent()));
        }
        return author;
    }

    @Override
    public int updateAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer id = authorDTO.getId();
        String userId = authorDTO.getUserId();
        Integer clickNum = authorDTO.getClick();
        String content = authorDTO.getContent();

        Author author = new Author();
        author.setId(id);
        if (!StringUtils.isEmpty(content)) {
            author.setContent(SubStringUtils.replaceHtmlContentImgPath(content));
        }
        if (!StringUtils.isEmpty(userId) && clickNum != null) {
            // 点赞处理
            clickNum = restTemplate.getForObject(USER_KUDOS_URL, Integer.class, userId, id.toString(), clickNum);
            author.setClick(clickNum);
        }
        int i = authorMapper.updateById(author);
        if (i != 1) {
            throw ServiceException.busy();
        }
        return clickNum == null ? 0 : clickNum;
    }

    @Override
    public void deleteAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        Integer[] ids = authorDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = authorMapper.deleteAuthor(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
