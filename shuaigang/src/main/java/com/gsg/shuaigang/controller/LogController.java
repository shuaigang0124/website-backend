package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.LogDTO;
import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.LogVO;
import com.gsg.commons.vo.PageResponseVO;
import com.gsg.shuaigang.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 更新日志表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/v1/log")
public class LogController extends BaseController {

    @Autowired
    ILogService logService;

    /**
     * 新增
     * @author gaoshenggang
     * @date  2021/10/11 15:19
     */
    @PostMapping("/insertLog")
    public R<?> insertLog(@RequestBody Request<LogDTO> request){
        try {
            LogDTO logDTO = request.getCustomData();
            logService.insertLog(logDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询（倒序）
     * @author gaoshenggang
     * @date  2021/10/11 15:21
     */
    @PostMapping("/getLog")
    public R<?> getLog(){
        try {
            List<LogVO> list = logService.getLog();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询(如果没传分页参数page，就查询全部数据)
     * @author gaoshenggang
     * @date  2021/10/11 15:21
     */
    @PostMapping("/getLogList")
    public R<?> getLogList(@RequestBody Request<LogDTO> request){
        try {
            LogDTO logDTO = request.getCustomData();
            Page page = logDTO.getPage();
            List<LogVO> list = new ArrayList<>();
            // 如果没传分页参数，就查询全部数据
            if (StringUtils.isEmpty(page)) {
                list = logService.getLogList(logDTO, null);
                return R.ok(list);
            }
            // 分页查询 ,先格式化传入的分页参数
            Page pageAfter = pageDeal(page);
            logDTO.setPage(pageAfter);
            PageResponseVO<LogVO> pageResponseVO = new PageResponseVO<>();
            // 1、查询数据量总数
            long total = logService.getLogListTotal(logDTO);
            if (total == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("数据总量为0,未查询到数据!"+pageResponseVO);
            }
            // 分页查询当页数据
            list = logService.getLogList(logDTO, pageAfter);
            if (list == null || list.size() == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到国家城市列表!"+pageResponseVO);
            }
            // 封装分页结果数据
            pageResponseVO.setTotalCount(total);
            pageResponseVO.setCount(list.size());
            pageResponseVO.setCurrentPage(pageAfter.getIndex());
            pageResponseVO.setResultList(list);

            return R.ok(pageResponseVO);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改
     * @author gaoshenggang
     * @date  2021/10/11 15:23
     */
    @PostMapping("/updateLog")
    public R<?> updateLog(@RequestBody Request<LogDTO> request){
        try {
            LogDTO logDTO = request.getCustomData();
            logService.updateLog(logDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除
     * @author gaoshenggang
     * @date  2021/10/11 15:25
     */
    @PostMapping("/deleteLog")
    public R<?> deleteLog(@RequestBody Request<LogDTO> request){
        try {
            LogDTO logDTO = request.getCustomData();
            logService.deleteLog(logDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
