package com.gsg.shuaigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsg.commons.dto.DiaryDTO;
import com.gsg.commons.dto.FuzzySearchDTO;
import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Diary;
import com.gsg.commons.utils.PKGenerator;
import com.gsg.commons.vo.DiaryVO;
import com.gsg.shuaigang.mapper.DiaryMapper;
import com.gsg.shuaigang.service.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 待办事项表 服务实现类
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements IDiaryService {

    @Autowired
    DiaryMapper diaryMapper;

    @Override
    public void insertDiary(DiaryDTO diaryDTO) {
        String weather = diaryDTO.getWeather();
        String mood = diaryDTO.getMood();
        String content = diaryDTO.getContent();
        if (StringUtils.isEmpty(weather) || StringUtils.isEmpty(mood) || StringUtils.isEmpty(content)) {
            throw ServiceException.errorParams("请求参数有误");
        }
        LocalDate date = LocalDate.now();
        List<DiaryVO> list = diaryMapper.getDiary(null, date.toString());
        if (list.size() != 0) {
            throw ServiceException.errorParams("已存在当天日记记录");
        }
        String id = "DAY" + PKGenerator.generate();
        Diary diary = new Diary();
        diary.setId(id)
                .setDay(date)
                .setWeather(weather)
                .setMood(mood)
                .setContent(content);
        int i = diaryMapper.insert(diary);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public List<DiaryVO> getDiary(FuzzySearchDTO fuzzySearchDTO) {
        return diaryMapper.getDiary(fuzzySearchDTO.getFuzzySearch(), null);
    }

    @Override
    public void updateDiary(DiaryDTO diaryDTO) {
        String id = diaryDTO.getId();
        String weather = diaryDTO.getWeather();
        String mood = diaryDTO.getMood();
        String content = diaryDTO.getContent();
        if (StringUtils.isEmpty(id)) {
            throw ServiceException.errorParams("请求参数有误");
        }
        Diary diary = new Diary();
        diary.setId(id);
        if (!StringUtils.isEmpty(weather)) {
            diary.setWeather(weather);
        }
        if (!StringUtils.isEmpty(mood)) {
            diary.setMood(mood);
        }
        if (!StringUtils.isEmpty(content)) {
            diary.setContent(content);
        }

        int i = diaryMapper.updateById(diary);
        if (i != 1) {
            throw ServiceException.busy();
        }
    }

    @Override
    public void deleteDiary(DiaryDTO diaryDTO) {
        if (diaryDTO == null) {
            throw ServiceException.errorParams("请传入参数！");
        }
        String[] ids = diaryDTO.getIds();
        if (ids == null || ids.length == 0) {
            throw ServiceException.errorParams("请求参数异常");
        }
        Integer i = diaryMapper.deleteDiary(ids);
        if (i < 1 ) {
            throw ServiceException.busy();
        }
    }
}
