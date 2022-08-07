package com.gsg.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.UserPoints;
import com.gsg.commons.vo.UserPointsVO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户积分表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-06
 */
@Repository
public interface UserPointsMapper extends BaseMapper<UserPoints> {

    /**
     * 查询用户个人积分
     * @author gaoshenggang
     * @date  2022/1/6 16:22
     */
    UserPointsVO getUserPoint(String userId);

    /**
     * 修改用户积分
     * @author gaoshenggang
     * @date  2022/1/6 18:16
     */
    int updateUserPiont(String userId, Integer pointNum);
}
