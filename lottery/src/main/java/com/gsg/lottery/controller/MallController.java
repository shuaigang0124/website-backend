package com.gsg.lottery.controller;


import com.gsg.commons.dto.MallDTO;
import com.gsg.commons.utils.*;
import com.gsg.commons.vo.MallTradeVO;
import com.gsg.commons.vo.PageResponseVO;
import com.gsg.lottery.service.IMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.gsg.commons.utils.Constants.PREFIX;
import static com.gsg.commons.utils.Constants.TEMP;

/**
 * <p>
 * 商城-商品表 前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/v1/mall")
public class MallController extends BaseController {

    @Autowired
    IMallService mallService;

    /**
     * 新增商品信息
     * @author gaoshenggang
     * @date  2022/1/12 11:38
     */
    @PostMapping("/insertMallTrade")
    public R<?> insertMallTrade(@RequestBody Request<MallDTO> request) {
        try {
            MallDTO mallDTO = request.getCustomData();
            String imgPath = mallDTO.getTradeImg();
            String newPath;
            if (imgPath.contains(TEMP)) {
                imgPath = imgPath.substring(imgPath.lastIndexOf(PREFIX));
                newPath = FileUploadUtils.singleMove(imgPath);
                mallDTO.setTradeImg(newPath);
            }
            mallService.insertMall(mallDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询商品
     * @author gaoshenggang
     * @date  2022/1/12 11:39
     */
    @PostMapping("/getMallTrade")
    public R<?> getMallTrade(@RequestBody Request<MallDTO> request) {
        try {
            MallDTO mallDTO = request.getCustomData();
            List<MallTradeVO> list = new ArrayList<>();
            Page page = mallDTO.getPage();
            /* 如果没传分页参数 */
            if (StringUtils.isEmpty(page)) {
                list = mallService.getMallTrade(mallDTO, null);
                return R.ok(list);
            }
            /* 如果传分页参数，用page初始化参数查询*/
            Page pageAfter = pageDeal(page);
            mallDTO.setPage(pageAfter);
            PageResponseVO<MallTradeVO> pageResponseVO = new PageResponseVO<>();

            /*1、查询数据量总数*/
            long total = mallService.getMallListTotal(mallDTO);
            if (total == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("数据总量为0,未查询到数据!"+pageResponseVO);
            }
            /*2、分页查询当页数据*/
            list = mallService.getMallTrade(mallDTO, pageAfter);
            if (list == null || list.size() == 0) {
                pageResponseVO.setTotalCount(0);
                pageResponseVO.setCount(0);
                pageResponseVO.setCurrentPage(0);
                pageResponseVO.setResultList(list);
                return R.ok("未查询到商品列表!"+pageResponseVO);
            }

            /*3、封装返回分页结果数据*/
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
     * 修改商品信息
     * @author gaoshenggang
     * @date  2022/1/12 11:40
     */
    @PostMapping("/updateMallTrade")
    public R<?> updateMallTrade(@RequestBody Request<MallDTO> request) {
        try {
            MallDTO mallDTO = request.getCustomData();
            mallService.updateMallTrade(mallDTO);
            return R.ok("修改成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 删除商品
     * @author gaoshenggang
     * @date  2022/1/12 11:42
     */
    @PostMapping("/deletedMallTrade")
    public R<?> deletedMallTrade(@RequestBody Request<MallDTO> request) {
        try {
            MallDTO mallDTO = request.getCustomData();
            mallService.deletedMallTrade(mallDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 商品兑换
     * @author gaoshenggang
     * @date  2022/1/12 16:21
     */
    @PostMapping("/tradeConvert")
    public R<?> tradeConvert(@RequestBody Request<MallDTO> request) {
        try {
            MallDTO mallDTO = request.getCustomData();
            mallService.tradeConvert(mallDTO);
            return R.ok("兑换成功，等待商家处理");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
