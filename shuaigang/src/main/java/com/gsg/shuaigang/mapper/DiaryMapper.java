package com.gsg.shuaigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gsg.commons.model.Diary;
import com.gsg.commons.vo.DiaryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 日记表 Mapper 接口
 * </p>
 *
 * @author shuaigang
 * @since 2022-03-17
 */
@Repository
public interface DiaryMapper extends BaseMapper<Diary> {

    /** 查询 */
    List<DiaryVO> getDiary(String fuzzySearch, String day);

    /** 删除 */
    Integer deleteDiary(String[] ids);


}
