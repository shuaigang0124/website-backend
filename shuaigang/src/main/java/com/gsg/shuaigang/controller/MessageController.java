package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.MessageDTO;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.MessageVO;
import com.gsg.shuaigang.service.IMessageService;
import com.gsg.shuaigang.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-11-22
 */
@RestController
@RequestMapping("/v1/message")
@Slf4j
public class MessageController extends BaseController {

    @Autowired
    IMessageService messageService;

    /**
     * 新增留言
     * @author gaoshenggang
     * @date  2021/11/22 10:54
     */
    @PostMapping("/releaseMessage")
    public R<?> releaseMessage(@RequestBody Request<MessageDTO> request) {
        try {
            MessageDTO messageDTO = request.getCustomData();
            messageService.insertMessage(messageDTO);
            if (StringUtils.isEmpty(messageDTO.getParentId())) {
                return R.ok("留言成功！");
            }
            return R.ok("回复成功！");
        } catch (Exception e) {
            log.error("留言失败!{}", e.getClass().getName());
            return R.failed(e);
        }
    }

    /**
     * 查询所有留言（前台）
     * @author gaoshenggang
     * @date  2021/11/22 10:54
     */
    @PostMapping("/findAllMessage")
    public R<?> findAllMessage(@RequestBody Request<MessageDTO> request) {
        try {
            HttpServletRequest httpServletRequest = ServletUtils.getRequest();
            String supportWebp = httpServletRequest.getHeader("supportWebp");
            MessageDTO messageDTO = request.getCustomData();
            Page page = messageDTO.getPage();
            if (StringUtils.isEmpty(page)) {
                List<MessageVO> list = messageService.findAllMessage(messageDTO, null);
                return R.ok(list);
            }
            /** 分页查询 ,先格式化传入的分页参数*/
            Page pageAfterProcess = pageDeal(page);
            messageDTO.setPage(pageAfterProcess);
            List<MessageVO> list = messageService.findAllMessage(messageDTO, pageAfterProcess);
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除留言（逻辑删除）
     * @author gaoshenggang
     * @date  2021/11/22 17:14
     */
    @PostMapping("/deletedMessage")
    public R<?> deletedMessage(@RequestBody Request<MessageDTO> request) {
        try {
            MessageDTO messageDTO = request.getCustomData();
            messageService.updateMessage(messageDTO);
            return R.ok("删除成功！");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
