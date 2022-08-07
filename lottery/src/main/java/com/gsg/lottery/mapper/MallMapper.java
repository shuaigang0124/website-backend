package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.dto.MallDTO;
import com.gsg.commons.model.Mall;
import com.gsg.commons.utils.SearchBean;
import com.gsg.commons.vo.MallTradeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商城-商品表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@Repository
public interface MallMapper extends BaseMapper<Mall> {

    List<MallTradeVO> getMallTrade(Integer typeId);

    // 通过id查询商品数量
    Integer getTradeNumById(String id);

    List<MallTradeVO> getMallTradeListByPage(SearchBean<MallDTO> mallList);

    long getMallTradeListTotal(MallDTO mallDTO);
}
