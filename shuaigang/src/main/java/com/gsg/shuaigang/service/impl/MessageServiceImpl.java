package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.MessageDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.utils.WebPUtils;
import com.gsg.commons.vo.MessageVO;
import com.gsg.shuaigang.mapper.MessageMapper;
import com.gsg.commons.model.Message;
import com.gsg.shuaigang.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.gsg.commons.utils.Constants.*;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-22
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    MessageMapper messageMapper;

    /**
     * 新增留言
     * @author gaoshenggang
     * @date  2021/11/22 11:02
     */
    @Override
    public void insertMessage(MessageDTO messageDTO) {
        Message message = new Message();
        String id = "MSE" + PKGenerator.generate();

        // 父级ID如果不传说明为顶级，直接给默认值
        String parentId = messageDTO.getParentId();
        if (StringUtils.isEmpty(parentId)) {
            message.setPId(TYPE_0);
        } else {
            Message parentDetil = messageMapper.findPIdByMessageId(parentId);
            String pId = parentDetil.getPId();
            Integer level = parentDetil.getLevel();
            String userId = parentDetil.getUserId();
            if (level == 0) {
                message.setPId(parentId)
                        .setLevel(level + 1);
            } else {
                message.setPId(pId)
                        .setBeCommentedUserId(userId)
                        .setLevel(level);
            }
        }
        if (StringUtils.isEmpty(messageDTO.getUserId()) || !messageDTO.getUserId().startsWith("GSG")) {
            throw ServiceException.errorParams("userId不能为空！");
        }
        if (StringUtils.isEmpty(messageDTO.getContent())) {
            throw ServiceException.errorParams("内容不能为空！");
        }
        message.setId(id)
                .setUserId(messageDTO.getUserId())
                .setContent(messageDTO.getContent());
        int row = messageMapper.insert(message);
        if (row != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 查询全部留言
     * @author gaoshenggang
     * @date  2021/11/22 11:03
     */
    @Override
    public List<MessageVO> findAllMessage(MessageDTO messageDTO, Page page) {
        SearchBean<MessageDTO> searchBean = new SearchBean<>(messageDTO.getPage(), messageDTO);
        List<MessageVO> result = messageMapper.findParentMessage(searchBean);
        List<MessageVO> lists = messageMapper.findChildrenMessage();

        for (MessageVO parent : result) {
            // 顶级评论头像处理
            if (!StringUtils.isEmpty(parent.getAvatar())) {
                String avatarPath = parent.getAvatar();
                // 20211210 生成WebP图片副本
                avatarPath = WebPUtils.changePathToWebp("1", avatarPath);
                parent.setAvatar(avatarPath);
            }
            String pId = parent.getId();
            List<MessageVO> messageVOS = new ArrayList<>();

            // 遍历所有数据，为其设置子节点集合
            for (MessageVO child : lists) {
                // 子节点
                if (pId.equals(child.getPId())) {
                    // 处理子节点图片
                    if (!StringUtils.isEmpty(child.getAvatar())) {
                        String avatarPath = child.getAvatar();
                        // 20211210 生成WebP图片副本
                        avatarPath = WebPUtils.changePathToWebp("1", avatarPath);
                        child.setAvatar(avatarPath);
                    }
                    messageVOS.add(child);
                }
                parent.setChildren(messageVOS);
            }
            // 设置顶级的子节点条数
            int total = parent.getChildren().size();
            parent.setTotal(total);
        }
        return result;
    }

    /**
     * 删除留言（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/22 17:08
     */
    @Override
    public void updateMessage(MessageDTO messageDTO) {
        String id = messageDTO.getId();
        Integer deleted = messageDTO.getDeleted();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("请传入留言id！");
        }
        if (deleted == null) {
            deleted = 1;
        }
        Message message = new Message();
        message.setId(id)
                .setDeleted(deleted);
        messageMapper.updateById(message);
    }

}
