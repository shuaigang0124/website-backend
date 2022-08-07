package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.MessageDTO;
import com.gsg.commons.model.Message;
import com.gsg.commons.utils.Page;
import com.gsg.commons.vo.MessageVO;

import java.util.List;

/**
 * <p>
 * 留言表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-22
 */
public interface IMessageService extends IService<Message> {

    /**
     * 新增留言
     * @author gaoshenggang
     * @date  2021/11/22 10:13
     */
    void insertMessage(MessageDTO messageDTO);

    /**
     * 查询全部留言（前台）
     * @author gaoshenggang
     * @date  2021/11/22 11:02
     */
    List<MessageVO> findAllMessage(MessageDTO messageDTO, Page page);

    /**
     * 删除留言（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/22 17:07
     */
    void updateMessage(MessageDTO messageDTO);
}
