package com.gsg.shuaigang.service;

import com.gsg.commons.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description: TODO
 * @Author shuaigang
 * @Date 2021/12/1 10:26
 */
public interface IFileUploadService {

    /**
     * 上传/修改头像
     */
    String uploadAvatar(MultipartFile file, String userId, Integer type) throws IOException;

    /**
     * 上传单个图片
     */
    String uploadImg(MultipartFile file, String sharingId, Integer type, String address) throws IOException;

    /**
     * 上传文件
     * @param files
     * @param type
     * @return
     */
    List<FileVO> uploadFile(MultipartFile[] files, Integer type, String address);

    /**
     * 删除文件
     * @param filePath
     */
    void deleteFile(String filePath);

    /**
     * 文件下载
     * @param fileName
     */
    void downloadFile(String fileName) throws IOException;

}
