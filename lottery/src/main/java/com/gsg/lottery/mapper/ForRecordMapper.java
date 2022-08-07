package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.ForRecord;
import com.gsg.commons.vo.ForRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商城-兑换记录表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Repository
public interface ForRecordMapper extends BaseMapper<ForRecord> {

    ForRecord getRecord(String tradeId, Integer convertStatus);

    List<ForRecordVO> getForRecrdList(String userId, String tradeUserId);

    int getStatusById(String id);
}
