package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.ForRecordDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Mall;
import com.gsg.commons.vo.ForRecordVO;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.lottery.mapper.ForRecordMapper;
import com.gsg.commons.model.ForRecord;
import com.gsg.lottery.mapper.MallMapper;
import com.gsg.lottery.mapper.UserPointsMapper;
import com.gsg.lottery.service.IForRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商城-兑换记录表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Service
public class ForRecordServiceImpl extends ServiceImpl<ForRecordMapper, ForRecord> implements IForRecordService {

    @Autowired
    ForRecordMapper forRecordMapper;

    @Autowired
    UserPointsMapper userPointsMapper;

    @Autowired
    MallMapper mallMapper;

    /**
     * 查询记录（用户、商家、所有）
     * @author gaoshenggang
     * @date  2022/1/13 13:44
     */
    @Override
    public List<ForRecordVO> getForRecordList(ForRecordDTO forRecordDTO) {
        List<ForRecordVO> list = forRecordMapper.getForRecrdList(forRecordDTO.getUserId(), forRecordDTO.getTradeUserId());
        for (ForRecordVO forRecordVO : list) {
            if (forRecordVO.getConvertStatus() == 0) {
                forRecordVO.setStatusName("待处理");
            } else if (forRecordVO.getConvertStatus() == 1) {
                forRecordVO.setStatusName("已完成");
            } else {
                forRecordVO.setStatusName("已退回");
            }
        }
        return list;
    }

    /**
     * 修改兑换记录状态
     * @author gaoshenggang
     * @date  2022/1/13 13:45
     */
    @Override
    public void updateRecordStatus(ForRecordDTO forRecordDTO) {
        String id = forRecordDTO.getId();
        String userId = forRecordDTO.getUserId();
        String tradeId = forRecordDTO.getTradeId();
        Integer subNum = forRecordDTO.getSubNum();
        Integer convertStatus = forRecordDTO.getConvertStatus();
        if (StringUtils.isEmpty(id) && convertStatus == null) {
            throw ServiceException.errorParams("请求参数有误");
        }
        if (convertStatus == 2) {
            if (forRecordMapper.getStatusById(id) == 1) {
                throw ServiceException.errorParams("该订单已完成不能退回，请联系管理员！");
            }
            if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(tradeId) && subNum == null) {
                throw ServiceException.errorParams("请求参数有误");
            }
            UserPointsVO userPoints = userPointsMapper.getUserPoint(userId);
            Integer point = userPoints.getPoint();
            point += subNum;
            if (subNum != 0) {
                int row = userPointsMapper.updateUserPiont(userId, point);
                if (row < 1) {
                    throw ServiceException.busy();
                }
            }
            Integer tradeNum = mallMapper.getTradeNumById(tradeId);
            if (tradeNum == null) {
                throw ServiceException.busy();
            }
            Mall mall = new Mall();
            mall.setId(tradeId)
                    .setTradeNum(tradeNum + 1);
            int i = mallMapper.updateById(mall);
            if (i < 1) {
                throw ServiceException.busy();
            }
        }
        ForRecord forRecord = new ForRecord();
        forRecord.setId(id)
                .setConvertStatus(convertStatus);
        int row1 = forRecordMapper.updateById(forRecord);
        if (row1 < 1) {
            throw ServiceException.busy();
        }

    }


}
