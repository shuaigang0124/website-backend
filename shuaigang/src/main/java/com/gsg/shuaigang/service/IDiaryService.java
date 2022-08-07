package com.gsg.shuaigang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsg.commons.dto.DiaryDTO;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.model.Diary;
import com.gsg.commons.vo.DiaryVO;

import java.util.List;

/**
 * <p>
 * 待办事项表 服务类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
public interface IDiaryService extends IService<Diary> {

    /* 增加 */
    void insertDiary(DiaryDTO backlogDTO);

    /* 查询 */
    List<DiaryVO> getDiary(FuzzySearchDTO fuzzySearchDTO);

    /* 修改 */
    void updateDiary(DiaryDTO backlogDTO);

    /* 删除 */
    void deleteDiary(DiaryDTO backlogDTO);

}
