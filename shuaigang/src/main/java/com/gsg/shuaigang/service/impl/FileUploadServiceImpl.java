package com.gsg.shuaigang.service.impl;

import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.model.Sharing;
import com.gsg.commons.model.User;
import com.gsg.commons.utils.FileUploadUtils;
import com.gsg.commons.vo.FileVO;
import com.gsg.shuaigang.mapper.SharingMapper;
import com.gsg.shuaigang.mapper.UserMapper;
import com.gsg.shuaigang.service.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.gsg.commons.utils.Constants.TEMP;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/1 10:27
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Value("${project.fileUpload.baseDir}")
    private String baseDir;

    @Value("${project.fileUpload.maxAvatarFileSize}")
    private Long maxAvatarFileSize;

    @Value("${project.fileUpload.maxPicFileSize}")
    private Long maxPicFileSize;

    @Value("${project.fileUpload.maxFileSize}")
    private Long maxFileSize;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SharingMapper sharingMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 上传头像
     * @author gaoshenggang
     * @date  2021/12/1 10:28
     */
    @Override
    public String uploadAvatar(MultipartFile multipartFile, String userId, Integer type) throws IOException {
        if (multipartFile == null) {
            throw ServiceException.errorParams("请选择至少一张图片!");
        }

        if (userId == null || type == null) {
            throw ServiceException.errorParams("4004!");
        }

        User userInfo = userMapper.selectUserAvatarPath(userId);
        if (userInfo == null) {
            throw ServiceException.errorParams("4004！");
        }
        String filePath = userInfo.getAvatar();
        if (filePath != null) {
            File file = new File(filePath);
            // 判断是否存在，是否是文件
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
        String avatarPath = FileUploadUtils.fileUpload(baseDir, maxPicFileSize, multipartFile, type, TEMP);
        if ("".equals(avatarPath)) {
            throw ServiceException.errorParams("文件路径出错!");
        }

//        return address.trim() + avatarPath;
        return avatarPath;
    }

    /**
     * 上传/修改单个图片
     * @author gaoshenggang
     * @date  2021/12/1 10:28
     */
    @Override
    public String uploadImg(MultipartFile multipartFile, String sharingId, Integer type, String address) throws IOException {
        if (multipartFile == null) {
            throw ServiceException.errorParams("请选择至少一张图片!");
        }

        if (!StringUtils.isEmpty(sharingId)) {
            Sharing sharingInfo = sharingMapper.selectSharingImgPath(sharingId);
            if (sharingInfo == null) {
                throw ServiceException.errorParams("4004！");
            }
            String filePath = sharingInfo.getImg();
            if (filePath != null) {
                File file = new File(filePath);
                // 判断是否存在，是否是文件
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
        }

        String imgPath = FileUploadUtils.fileUpload(baseDir, maxAvatarFileSize, multipartFile, type, TEMP);
        if ("".equals(imgPath)) {
            throw ServiceException.errorParams("文件路径出错!");
        }

        return address.trim() + imgPath;
//        return imgPath;
    }

    /**
     * 上传文件
     * @author gaoshenggang
     * @date  2021/12/1 10:29
     */
    @Override
    public List<FileVO> uploadFile(MultipartFile[] files, Integer type, String address) {
        if (files == null || files.length == 0) {
            throw ServiceException.errorParams("4004!");
        }

        if (type == null) {
            throw ServiceException.errorParams("4004!");
        }

        List<FileVO> list = new ArrayList<>();

        Long fileSize;

        if (type == 1) {
            fileSize = maxFileSize;
        } else {
            fileSize = maxPicFileSize;
        }

        for (int i = 0; i < files.length; i++) {
            String filePath =address.trim() + FileUploadUtils.fileUpload(baseDir, fileSize, files[i], type, TEMP);
            list.add(new FileVO(i + 1, filePath));
        }
        return list;
    }

    /**
     * 删除文件
     * @author gaoshenggang
     * @date  2021/12/1 10:29
     */
    @Override
    public void deleteFile(String filePath) {
        try {
            FileUploadUtils.deleteFile(filePath);
        } catch (Exception e) {
            throw ServiceException.errorParams("删除失败！");
        }
    }

    /**
     * 文件下载
     * @author gaoshenggang
     * @date  2021/12/1 10:29
     */
    @Override
    public void downloadFile(String filePath) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw ServiceException.errorParams("请求失败！");
        }
        HttpServletResponse resp = requestAttributes.getResponse();

        if (resp == null) {
            throw ServiceException.errorParams("请求失败！");
        }

        File file = new File(filePath);

        if (!file.isFile() && !file.exists()) {
            throw ServiceException.errorParams("文件已删除！");
        }

        resp.setContentType("application/x-msdownload");
        resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filePath.getBytes(), StandardCharsets.UTF_8));
        FileInputStream in = new FileInputStream(file);
        ServletOutputStream out = resp.getOutputStream();
        byte[] bytes = new byte[1024];
        int n;
        while ((n = in.read(bytes)) != -1) {
            out.write(bytes, 0, n);
        }
        // 关闭流
        in.close();
        out.close();
    }

}
