package com.gsg.shuaigang.controller;

import com.gsg.commons.dto.FileDTO;
import com.gsg.commons.utils.*;
import com.gsg.commons.vo.FileVO;
import com.gsg.shuaigang.service.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.gsg.commons.utils.Constants.*;

/**
 * @author shuaigang
 * @date 2021/12/1 10:24
 */
@RestController
@RequestMapping("/v1/upload")
@Slf4j
public class FileUploadController {

    @Autowired
    IFileUploadService fileUploadService;

    /**
     * 上传/修改头像
     */
    @PostMapping("/updateAvatar")
    public R<?> updateAvatar(MultipartFile multipartFile, String userId, Integer type) { //HttpServletRequest request
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
//            String address = "https://xxxxxx.com";
//            String avatarPath = fileUploadService.uploadAvatar(multipartFile, userId, type, address);
            String avatarPath = fileUploadService.uploadAvatar(multipartFile, userId, type);

            Map<String, String> map = new HashMap<>();
            map.put(AVATAR_PATH, avatarPath);

            return R.ok(map);
        } catch (Exception e) {
            log.error("上传头像失败!");
            e.printStackTrace();
            return R.failed(e);
        }
    }

    /**
     * 上传/修改头像
     */
    @PostMapping("/updateVxAvatar")
    public R<?> updateVxAvatar(@RequestParam("file") MultipartFile multipartFile, String userId, Integer type) { //HttpServletRequest request
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
//            String address = "https://xxxxxxx.com";
//            String avatarPath = fileUploadService.uploadAvatar(multipartFile, userId, type, address);
            String avatarPath = fileUploadService.uploadAvatar(multipartFile, userId, type);

            Map<String, String> map = new HashMap<>();
            map.put(AVATAR_PATH, avatarPath);

            return R.ok(map);
        } catch (Exception e) {
            log.error("上传头像失败!");
            e.printStackTrace();
            return R.failed(e);
        }
    }

    /**
     * 上传/修改单个分享页预览图
     */
    @PostMapping("/uploadImg")
    public R<?> uploadImg(MultipartFile multipartFile, String sharingId, Integer type) {
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
            String address = "https://xxx.com";
//            String address = "http://127.0.0.1:8090";
            String imgPath = fileUploadService.uploadImg(multipartFile, sharingId, type, address);
            Map<String, Object> map = new HashMap<>();
            map.put("imgPath", imgPath);
            return R.ok(map);
        } catch (Exception e) {
            log.error("上传图片失败!");
            // 异常抛出，交给全局异常处理类 ExceptionControllerAdvice 去处理
            return R.failed(e);
        }
    }

    /**
     * 上传/修改单个分享页预览图(微信小程序使用)
     */
    @PostMapping("/uploadVxImg")
    public R<?> uploadVxImg(@RequestParam("file") MultipartFile multipartFile, String sharingId, Integer type) {
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
            String address = "https://xxxx.com";
//            String address = "http://127.0.0.1:8090";
            String imgPath = fileUploadService.uploadImg(multipartFile, sharingId, type, address);
            Map<String, Object> map = new HashMap<>();
            map.put("imgPath", imgPath);
            return R.ok(map);
        } catch (Exception e) {
            log.error("上传图片失败!");
            // 异常抛出，交给全局异常处理类 ExceptionControllerAdvice 去处理
            return R.failed(e);
        }
    }

    /**
     * 上传文件
     * http参数(, HttpServletRequest request)
     */
    @PostMapping("/uploadFile")
    public R<?> uploadFile(MultipartFile[] multipartFile, Integer type) {
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
            String address = "https://xxxx.com";
//            String address = "http://127.0.0.1:8090";
//            log.debug("目标地址{}", address);
            List<FileVO> fileUploads = fileUploadService.uploadFile(multipartFile, type, address);
            return R.ok(fileUploads);
        } catch (Exception e) {
            log.error("上传图片失败!");
            // 异常抛出，交给全局异常处理类 ExceptionControllerAdvice 去处理
            throw e;
        }
    }

    /**
     * 上传文件(微信小程序使用)
     * http参数(, HttpServletRequest request)
     */
    @PostMapping("/uploadVxFile")
    public R<?> uploadVxFile(@RequestParam("file") MultipartFile[] multipartFile, Integer type) {
        try {
//            String serverName = request.getHeader(SERVER_NAME);
//
//            if (serverName == null) {
//                serverName = request.getServerName() + ":" + request.getServerPort();
//            }
//
//            String address = request.getScheme() + "://" + serverName;
            String address = "https://xxxxx.com";
//            String address = "http://127.0.0.1:8090";
//            log.debug("目标地址{}", address);
            List<FileVO> fileUploads = fileUploadService.uploadFile(multipartFile, type, address);
            return R.ok(fileUploads);
        } catch (Exception e) {
            log.error("上传图片失败!");
            // 异常抛出，交给全局异常处理类 ExceptionControllerAdvice 去处理
            throw e;
        }
    }


    /**
     * 转移多个文件
     *
     * @author gaoshenggang
     * @date 2021/12/13 10:06
     */
    @PostMapping("/moveFiles")
    public R<?> moveFiles(List<FileVO> fileUploads) {
        try {
            List<FileVO> list = FileUploadUtils.moveForHtmlContent(fileUploads);
            return R.ok(list);
        } catch (Exception e) {
            log.error("转移失败!");
            // 异常抛出，交给全局异常处理类 ExceptionControllerAdvice 去处理
            throw e;
        }
    }


    /**
     * 删除文件
     */
    @PostMapping("/deleteFile")
    public R<?> deleteFile(@RequestBody @Valid Request<FileDTO> request, BindingResult bindingResult) {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }

        FileDTO fileDTO = request.getCustomData();
        fileUploadService.deleteFile(fileDTO.getFilePath());
        return R.ok("删除成功!");
    }

    /**
     * 文件下载
     */
    @PostMapping("/downloadFile")
    public R<?> downloadFile(@RequestBody @Valid Request<FileDTO> request, BindingResult bindingResult) {
        if (ValidErrorUtil.hasError(bindingResult) != null) {
            return ValidErrorUtil.hasError(bindingResult);
        }
        FileDTO fileDTO = request.getCustomData();

        try {
            String filePath = fileDTO.getFilePath();
            fileUploadService.downloadFile(filePath);
            return R.ok("下载成功！");
        } catch (IOException e) {
            return R.failed(e);
        }
    }


}
