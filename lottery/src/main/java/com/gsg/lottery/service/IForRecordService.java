package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.ForRecordDTO;
import com.gsg.commons.model.ForRecord;
import com.gsg.commons.vo.ForRecordVO;

import java.util.List;

/**
 * <p>
 * 商城-兑换记录表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
public interface IForRecordService extends IService<ForRecord> {

    List<ForRecordVO> getForRecordList(ForRecordDTO forRecordDTO);

    void updateRecordStatus(ForRecordDTO forRecordDTO);
}
