package com.gsg.lottery.controller;

import com.gsg.commons.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.TimeZone;

/**
 * TODO 公共的一些Controller层方法可以写在这里便于使用
 * @author gaoshenggang
 * @date 下午 01:58:040 2021/5/7 0007
 **/
@Slf4j
public abstract class BaseController {

    /**
     * TODO 时区类，后续用于处理时区偏移量
     **/
    protected TimeZone timeZone = TimeZone.getDefault();

    /**
     * TODO 处理page参数，如果传入的page为null, 返回具有初始分页值得page对象
     * @author gaoshenggang
     * @date 下午 02:04:044 2021/5/7 0007
     **/
    protected Page pageDeal (Page page){

        if(StringUtils.isEmpty(page)){
            return new Page();
        }

        if (StringUtils.isEmpty(page.getIndex())){
            page.setIndex(1);
        }

        if (StringUtils.isEmpty(page.getSize())){
            page.setIndex(10);
        }

        return page;
    }

}