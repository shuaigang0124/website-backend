package com.gsg.shuaigang.controller;


import com.gsg.commons.dto.AuthorDTO;
import com.gsg.commons.utils.R;
import com.gsg.commons.utils.Request;
import com.gsg.commons.vo.AuthorVO;
import com.gsg.shuaigang.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shuaigang
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/v1/author")
public class AuthorController {

    @Autowired
    IAuthorService authorService;

    /**
     * 新增
     * @author gaoshenggang（不做使用）
     * @date  2021/10/11 15:19
     */
    @PostMapping("/insertAuthor")
    public R<?> insertAuthor(@RequestBody Request<AuthorDTO> request){
        try {
            AuthorDTO authorDTO = request.getCustomData();
            authorService.insertAuthor(authorDTO);
            return R.ok("新增成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 查询
     * @author gaoshenggang
     * @date  2021/10/11 15:21
     */
    @PostMapping("/getAuthor")
    public R<?> getAuthor(){
        try {
            List<AuthorVO> list = authorService.getAuthor();
            return R.ok(list);
        } catch (Exception e) {
            return R.failed(e);
        }
    }

    /**
     * 修改
     * @author gaoshenggang
     * @date  2021/10/11 15:23
     */
    @PostMapping("/updateAuthor")
    public R<?> updateAuthor(@RequestBody Request<AuthorDTO> request){
        try {
            AuthorDTO authorDTO = request.getCustomData();
            int clickNum = authorService.updateAuthor(authorDTO);
            if (StringUtils.isEmpty(authorDTO.getContent())) {
                if (authorDTO.getClick() < clickNum) {
                    return R.ok("点赞成功");
                } {
                    return R.ok("取消成功");
                }
            }
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
    @PostMapping("/deleteAuthor")
    public R<?> deleteAuthor(@RequestBody Request<AuthorDTO> request){
        try {
            AuthorDTO authorDTO = request.getCustomData();
            authorService.deleteAuthor(authorDTO);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.failed(e);
        }
    }

}
