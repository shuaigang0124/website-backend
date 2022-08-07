package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.MessageDTO;
import com.gsg.commons.model.Message;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.MessageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 留言表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-22
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 通过留言id查询留言等级和父级ID
     * @author gaoshenggang
     * @date  2021/11/22 10:18
     */
    Message findPIdByMessageId(String messageId);

    /**
     * 查询一级留言（倒序）
     * @author gaoshenggang
     * @date  2021/11/25 11:33
     */
    List<MessageVO> findParentMessage(SearchBean<MessageDTO> searchBean);

    /**
     * 查询全部留言（顺序）
     * @author gaoshenggang
     * @date  2021/11/22 10:55
     */
    List<MessageVO> findChildrenMessage();

}
