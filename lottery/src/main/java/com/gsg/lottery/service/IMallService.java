package com.gsg.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.MallDTO;
import com.gsg.commons.model.Mall;
import com.gsg.commons.utils.Page;
import com.gsg.commons.vo.MallTradeVO;

import java.util.List;

/**
 * <p>
 * 商城-商品表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
public interface IMallService extends IService<Mall> {

    void insertMall(MallDTO mallDTO);

    List<MallTradeVO> getMallTrade(MallDTO mallDTO, Page page);

    void updateMallTrade(MallDTO mallDTO);

    void deletedMallTrade(MallDTO mallDTO);

    void tradeConvert(MallDTO mallDTO);

    long getMallListTotal(MallDTO mallDTO);
}
