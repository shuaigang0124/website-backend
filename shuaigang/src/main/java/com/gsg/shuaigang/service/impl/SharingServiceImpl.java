package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.SharingDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.*;
import com.gsg.commons.vo.SharingBackendVO;
import com.gsg.commons.vo.SharingByTagVO;
import com.gsg.commons.vo.SharingDetailVO;
import com.gsg.commons.vo.SharingVO;
import com.gsg.shuaigang.mapper.SharingMapper;
import com.gsg.commons.model.Sharing;
import com.gsg.shuaigang.service.ISharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static com.gsg.commons.utils.Constants.USER_KUDOS_URL;


/**
 * <p>
 * 日常分享表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-29
 */
@Service
public class SharingServiceImpl extends ServiceImpl<SharingMapper, Sharing> implements ISharingService {

    @Autowired
    SharingMapper sharingMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 新增日常分享
     * @author gaoshenggang
     * @date  2021/11/29 16:48
     */
    @Override
    public void insertSharing(SharingDTO sharingDTO) {
        String id = "SRG" + PKGenerator.generate();
        if (StringUtils.isEmpty(sharingDTO.getUserId()) || !sharingDTO.getUserId().startsWith("GSG")) {
            throw ServiceException.errorParams("userId不能为空！");
        }
        if (StringUtils.isEmpty(sharingDTO.getTagId())) {
            throw ServiceException.errorParams("tagId不能为空！");
        }
        if (StringUtils.isEmpty(sharingDTO.getTitle())) {
            throw ServiceException.errorParams("标题不能为空！");
        }
        if (StringUtils.isEmpty(sharingDTO.getTitleDescribe())) {
            throw ServiceException.errorParams("标题描述不能为空！");
        }
        if (StringUtils.isEmpty(sharingDTO.getContent())) {
            throw ServiceException.errorParams("内容不能为空！");
        }
        if (StringUtils.isEmpty(sharingDTO.getImg())) {
            throw ServiceException.errorParams("图片不能为空！");
        }
        Sharing sharing = new Sharing();
        sharing.setId(id)
                .setUserId(sharingDTO.getUserId())
                .setTagId(sharingDTO.getTagId())
                .setTitle(sharingDTO.getTitle())
                .setTitleDescribe(sharingDTO.getTitleDescribe())
                .setContent(SubStringUtils.replaceHtmlContentImgPath(sharingDTO.getContent()))
                .setImg(sharingDTO.getImg())
                .setGmtCreate(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int i = sharingMapper.insert(sharing);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 查询所有日常分享（传入tagId、id，则为标签和id条件查询）
     * @author gaoshenggang
     * @date  2021/11/29 17:40
     */
    @Override
    public List<SharingVO> getSharing(SharingDTO sharingDTO, Page page) {
        List<SharingVO> list;
        /*分页查询实体*/
        SearchBean<SharingDTO> searchBean = new SearchBean<>(sharingDTO.getPage(), sharingDTO);
        list = sharingMapper.getSharing(searchBean);
        for (SharingVO sharingVO : list) {
            if (!StringUtils.isEmpty(sharingVO.getImg())) {
                String imgPath = sharingVO.getImg();
                // 20211210 生成WebP图片副本
                imgPath = WebPUtils.changePathToWebp("1", imgPath);
                sharingVO.setImg(imgPath);
            }
        }
        return list;
    }

    @Override
    public List<SharingByTagVO> getSharingByTag(SharingDTO sharingDTO) {
        Integer tagId = sharingDTO.getTagId();
        String sharingId = sharingDTO.getSharingId();
        if (tagId == null || StringUtils.isEmpty(sharingId)) {
            throw ServiceException.errorParams("请求参数有误!");
        }
        return sharingMapper.getSharingByTag(tagId, sharingId);
    }


    /**
     * 通过ID查询日常分享详情（包括评论总条数）
     * @author gaoshenggang
     * @date  2021/11/30 11:47
     */
    @Override
    public SharingDetailVO getSharingDetail(String id) {
        SharingDetailVO sharingDetail = sharingMapper.getSharingDetail(id);
        Integer total = sharingMapper.getCommentListTotal(id);
        sharingDetail.setCommentTotal(total)
                .setContent(SubStringUtils.joinContentImg(sharingDetail.getContent()));
        return sharingDetail;
    }

    /**
     * 查询修改详情
     * @author gaoshenggang
     * @date  2021/12/28 15:27
     */
    @Override
    public SharingDetailVO getUpdateDetail(String id) {
        return sharingMapper.getSharingDetail(id);
    }

    /**
     * 查询日常分享总数
     * @author gaoshenggang
     * @date  2021/11/30 21:23
     */
    @Override
    public long getSharingListTotal(SharingDTO sharingDTO) {
        return sharingMapper.getSharingListTotal(sharingDTO);
    }

    /**
     * 分页查询
     * @author gaoshenggang
     * @date  2021/11/30 21:23
     */
    @Override
    public List<SharingBackendVO> getAllSharing(SharingDTO sharingDTO, Page page) {
        SearchBean<SharingDTO> list = new SearchBean<>(sharingDTO.getPage(), sharingDTO);
        return sharingMapper.getSharingListByPage(list);
    }


    /**
     * 修改日常分享（点赞数，阅读数，删除标识（逻辑删除））
     * @author gaoshenggang
     * @date  2021/11/29 17:47
     */
    @Override
    public int updateSharing(SharingDTO sharingDTO) {

        Integer clickNum = sharingDTO.getClickNum();
        String content = sharingDTO.getContent();
        String userId = sharingDTO.getUserId();
        String sharingId = sharingDTO.getSharingId();
        Integer readNum = sharingDTO.getReadNum();
        Integer deleted = sharingDTO.getDeleted();
        if (sharingId == null) {
            throw ServiceException.errorParams("id必传!");
        }

        Sharing sharing = new Sharing();
        sharing.setId(sharingId)
                .setGmtModified(LocalDateTime.now());

        if (!StringUtils.isEmpty(content)) {
            sharing.setContent(SubStringUtils.replaceHtmlContentImgPath(content));
        }
        if (readNum != null) {
            readNum += 1;
            sharing.setReadNum(readNum);
        }
        if (deleted != null) {
            sharing.setDeleted(deleted);
        }
        if (clickNum != null) {
            clickNum = restTemplate.getForObject(USER_KUDOS_URL, Integer.class, userId, sharingId, clickNum);
            sharing.setClickNum(clickNum);
        }

        int i = sharingMapper.updateById(sharing);
        if (i != 1) {
            throw ServiceException.busy();
        }
        return clickNum == null ? 0 : clickNum;
    }


}
