package com.gsg.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.MallDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.ForRecord;
import com.gsg.commons.model.UserPoints;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.utils.WebPUtils;
import com.gsg.commons.vo.MallTradeVO;
import com.gsg.commons.vo.UserPointsVO;
import com.gsg.lottery.mapper.ForRecordMapper;
import com.gsg.lottery.mapper.MallMapper;
import com.gsg.commons.model.Mall;
import com.gsg.lottery.mapper.UserPointsMapper;
import com.gsg.lottery.service.IMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 商城-商品表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Service
public class MallServiceImpl extends ServiceImpl<MallMapper, Mall> implements IMallService {

    @Autowired
    MallMapper mallMapper;

    @Autowired
    UserPointsMapper userPointsMapper;

    @Autowired
    ForRecordMapper forRecordMapper;

    /**
     * 新增商品
     * @author gaoshenggang
     * @date  2022/1/12 11:15
     */
    @Override
    public void insertMall(MallDTO mallDTO) {
        String tradeImg = mallDTO.getTradeImg();
        String tradeName = mallDTO.getTradeName();
        Integer tradeNum = mallDTO.getTradeNum();
        Integer pointNum = mallDTO.getPointNum();
        String userId = mallDTO.getUserId();
        Integer typeId = mallDTO.getTypeId();

        if (StringUtils.isEmpty(tradeName) || StringUtils.isEmpty(userId) || tradeNum == null || pointNum == null || typeId == null) {
            throw ServiceException.errorParams("请求参数有误");
        }
        if (StringUtils.isEmpty(tradeImg)) {
            String[] images = new String[]{"1641954457218-139504783123541.webp", "1641954559303-139606868067398.webp", "1641954569440-139617005244234.webp", "1641954579842-139627407347187.webp"};
            int i = new Random().nextInt(images.length);
            tradeImg = "/gsg/static-resource/formal/2/20220112/" + images[i];
        }
        String id = "TRA" + PKGenerator.generate();
        Mall mall = new Mall();
        mall.setId(id)
                .setTradeImg(tradeImg)
                .setUserId(userId)
                .setTradeName(tradeName)
                .setTradeNum(tradeNum)
                .setPointNum(pointNum)
                .setTypeId(typeId);
        int row = mallMapper.insert(mall);
        if (row != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 查询商品（传入typeId查询相关商品）
     * @author gaoshenggang
     * @date  2022/1/12 11:15
     */
    @Override
    public List<MallTradeVO> getMallTrade(MallDTO mallDTO, Page page) {
        List<MallTradeVO> list;
        if (StringUtils.isEmpty(page)) {
            /* 非分页查询时 */
            list = mallMapper.getMallTrade(mallDTO.getTypeId());
        } else {
            /* 分页查询时 */
            SearchBean<MallDTO> mallList = new SearchBean<>(mallDTO.getPage(), mallDTO);
            list = mallMapper.getMallTradeListByPage(mallList);
        }

        for (MallTradeVO mallTradeVO : list) {
            String tradeImg = mallTradeVO.getTradeImg();
            if (!StringUtils.isEmpty(tradeImg)) {
                tradeImg = WebPUtils.changePathToWebp("1",tradeImg);
                mallTradeVO.setTradeImg(tradeImg);
            }
        }
        return list;
    }

    /**
     * 修改商品信息
     * @author gaoshenggang
     * @date  2022/1/12 11:18
     */
    @Override
    public void updateMallTrade(MallDTO mallDTO) {
        String id = mallDTO.getId();
        String tradeImg = mallDTO.getTradeImg();
        String tradeName = mallDTO.getTradeName();
        Integer tradeNum = mallDTO.getTradeNum();
        Integer pointNum = mallDTO.getPointNum();
        String userId = mallDTO.getUserId();
        Integer typeId = mallDTO.getTypeId();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("商品id必传");
        }
        // 查询该商品是否有为完成的操作记录
        ForRecord result = forRecordMapper.getRecord(id, 0);
        if (result != null) {
            throw ServiceException.errorParams("商品有未处理的记录，不能进行修改");
        }

        Mall mall = new Mall();
        mall.setId(id);
        if (!StringUtils.isEmpty(tradeImg)) {
            mall.setTradeImg(tradeImg);
        }
        if (!StringUtils.isEmpty(tradeName)) {
            mall.setTradeName(tradeName);
        }
        if (!StringUtils.isEmpty(userId)) {
            mall.setUserId(userId);
        }
        if (tradeNum != null) {
            mall.setTradeNum(tradeNum);
        }
        if (pointNum != null) {
            mall.setPointNum(pointNum);
        }
        if (typeId != null) {
            mall.setTypeId(typeId);
        }
        int row = mallMapper.updateById(mall);
        if (row != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 删除商品
     * @author gaoshenggang
     * @date  2022/1/12 15:01
     */
    @Override
    public void deletedMallTrade(MallDTO mallDTO) {
        String id = mallDTO.getId();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("商品id必传");
        }
        // 查询该商品是否有为完成的操作记录
        ForRecord result = forRecordMapper.getRecord(id, 0);
        if (result != null) {
            throw ServiceException.errorParams("商品有未处理的记录，不能进行删除");
        }
        Mall mall = new Mall();
        mall.setId(id)
                .setDeleted(1);
        int row = mallMapper.updateById(mall);
        if (row != 1) {
            throw ServiceException.busy();
        }
    }

    /**
     * 商品兑换
     * @author gaoshenggang
     * @date  2022/1/12 15:01
     */
    @Override
    public void tradeConvert(MallDTO mallDTO) {
        String id = mallDTO.getId();
        String userId = mallDTO.getUserId();
        Integer pointNum = mallDTO.getPointNum();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(userId) || pointNum == null) {
            throw ServiceException.errorParams("请求参数有误!");
        }

        // 商品数量是否足够   (为提升接口速度，由前端检验)
        Integer tradeNumById = mallMapper.getTradeNumById(id);
        if (tradeNumById < 1) {
            throw ServiceException.errorParams("商品已售罄");
        }

        if (pointNum != 0) {
            /* 判断用户积分是否足够 */
            UserPointsVO userPointsVO = userPointsMapper.getUserPoint(userId);
            if (StringUtils.isEmpty(userPointsVO)) {
                UserPoints userPoints = new UserPoints();
                userPoints.setUserId(userId);
                int row = userPointsMapper.insert(userPoints);
                if (row < 1) {
                    throw ServiceException.busy();
                }
            }
            Integer userPoint = userPointsVO.getPoint();
            userPoint -= pointNum;
            if (userPoint < 0) {
                throw ServiceException.errorParams("用户积分不足");
            }
            int i = userPointsMapper.updateUserPiont(userId, userPoint);
            if (i < 1) {
                throw ServiceException.busy();
            }
        }

        /* 更新商品数量信息 */
        tradeNumById -= 1;
        Mall mall = new Mall();
        mall.setId(id)
                .setTradeNum(tradeNumById);
        int row = mallMapper.updateById(mall);
        if (row < 1) {
            throw ServiceException.busy();
        }

        /* 新增用户兑换记录 */
        ForRecord forRecord = new ForRecord();
        forRecord.setId("FRD" + PKGenerator.generate())
                .setUserId(userId)
                .setSubNum(pointNum)
                .setTradeId(id);
        int row2 = forRecordMapper.insert(forRecord);
        if (row2 < 1) {
            throw ServiceException.busy();
        }

    }

    /** 查询商品总数 */
    @Override
    public long getMallListTotal(MallDTO mallDTO) {
        return mallMapper.getMallTradeListTotal(mallDTO);
    }


}
