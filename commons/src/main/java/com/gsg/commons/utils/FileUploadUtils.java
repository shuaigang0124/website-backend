package com.gsg.commons.utils;


import com.gsg.commons.ex.ServiceException;
import com.gsg.commons.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.gsg.commons.utils.Constants.*;

/**
 * 文件上传
 * @author gaoshenggang
 * @date  2021/11/25 16:15
 */
@Slf4j
public class FileUploadUtils {

    /**
     * 上传文件---返回路径 + 文件上传
     * @param baseDir           ——文件上传服务器基础路径-配置文件中设置
     * @param maxFileSize       ——上传文件-文件最大大小
     * @param file              ——需要上传的文件
     * @param type              ——文件类型ID
     * @param typeName          ——临时目录（temp）或者固定目录名（formal）
     * @return
     */
    public static String fileUpload(String baseDir, long maxFileSize, MultipartFile file, Integer type, String typeName) {
        log.debug("开始上传文件...");

        if (file == null) {
            return null;
        }

        // 检查上传的文件大小是否超标
        if (file.getSize() > maxFileSize) {
            throw ServiceException.errorParams("上传失败! 文件大小不允许超过" + maxFileSize / 1024 / 1024 + "MB的文件!");
        }

        // 确定上传的文件保存路径
        String yearAndMonth = DateFormateUtils.formateDate(new Date(), DateFormateUtils.STANDARD_DATE_DAY);
        // 1、路径拼接：temp/1/20211125
        String path = typeName + File.separator + type + File.separator + yearAndMonth;
        // 2、路径拼接：/gsg/staic-resource/temp/1/20211125
        File parent = new File(baseDir, path);
        if (!parent.exists()) {
            // 创建目录
            boolean mkdirs = parent.mkdirs();
            if (!mkdirs) {
                throw ServiceException.errorParams("创建列目录失败!");
            }
        }

        // 生成文件名
        String fileName = System.currentTimeMillis() + "-" + System.nanoTime();
        // 扩展名（保留原扩展名）
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || BLANK.equals(originalFileName)) {
            throw ServiceException.errorParams("无效的文件!");
        }
        int beginIndex = originalFileName.lastIndexOf(".");
        // 完整扩展名 ： .png
        String suffix = originalFileName.substring(beginIndex);

        // 文件全名拼接
        String child = fileName + suffix;

//        if (type != 1) {
//            child = originalFileName;
//        }

        // 创建上传的文件保存时使用的File对象 ： /gsg/staic-resource/temp/1/20211125/***-***.png
        File dest = new File(parent + File.separator + child);
        try {
            // 执行文件上传
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw ServiceException.errorParams("上传文件发生读写错误!请稍后再试!");
        }

        // 日志
        log.debug("上传成功! 文件路径 {}", dest);
        // 拼接完成的全路径 ： /gsg/staic-resource/temp/1/20211125/***-***.png
        String filePath = baseDir + File.separator + path + File.separator + child;
        log.debug("上传后的文件路径 {}", filePath);

        return filePath;
    }

    /**
     * 转移单个文件
     * @param filePath
     * @return
     */
    public static String singleMove(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }

        String newPath;
        // 判断文件路径中是否含有 temp
        if (filePath.contains(TEMP)) {
            // 将文件路径中的 temp 替换为 formal
            newPath = filePath.replaceAll(TEMP, FORMAL);
        } else {
            // 将包含formal目录的文件路径改名
            File oldFormalFile = new File(filePath);
            // 判断是一个文件并且存在
            if (oldFormalFile.isFile() && oldFormalFile.exists()) {
                /**
                 * @param substring() 截取父字符串的某一部分
                 * @param lastIndexOf() 方法可返回一个指定的字符串值最后出现的位置，在一个字符串中的指定位置从后向前搜索。
                 */
                // 截取文件后缀之前的路径 ： 保留后缀名
                String suffix = filePath.substring(filePath.lastIndexOf("."));
                // 截取文件路径不保留文件名 ： 得到父路径
                String parent = filePath.substring(0, filePath.lastIndexOf(File.separator));
                // 重新组装路径 ： 父路径 + / + 重新生成文件名 + 后缀名
                filePath = parent + File.separator + DateFormateUtils.getUtcCurrentTimeMillis() + "-" + System.nanoTime() + suffix;
                File newFormalFile = new File(filePath);
                // 修改旧文件名为重新生成文件名
                boolean result = oldFormalFile.renameTo(newFormalFile);
                if (!result) {
                    log.error("服务器FORMAL文件改名失败!");
                }
            }
            return filePath;
        }
        // 截取除了***-***.png之前的路径，进行目录创建
        File parent = new File(newPath.substring(0, newPath.lastIndexOf(File.separator)));
        if (!parent.exists()) {
            boolean result = parent.mkdirs();
            if (!result) {
                log.error("创建列目录失败!");
            }
        }
        File source = new File(filePath);
        File target = new File(newPath);
        /*只有当原文件路径包含temp时才进行文件转移*/
        if(filePath.contains(TEMP)){
            boolean b = source.renameTo(target);
            log.debug("是否转移成功?{}", b);
            boolean result = source.delete();
            if (!result) {
                log.error("删除原文件失败!");
            }
        }
        return newPath;
    }

    /**
     * 文件路径转移（多个文件路径转移）
     * @param fileUploads
     * @return
     */
    public static List<FileVO> moveForHtmlContent(List<FileVO> fileUploads) {
        if (fileUploads == null || fileUploads.size() < 1) {
            return fileUploads;
        }
        for (FileVO fileVO : fileUploads) {
            String oldPath = fileVO.getFilePath();
            String newPath = singleMoveForHtmlContent(oldPath);
            fileVO.setFilePath(newPath);
        }
        return fileUploads;
    }

    /**
     * 转移单个文件
     * @param imgPath
     * @return
     */
    public static String singleMoveForHtmlContent(String imgPath) {
        if (imgPath == null) {
            return null;
        }

        String newPath;
        if (imgPath.contains(TEMP)) {
            newPath = imgPath.replaceAll(TEMP, FORMAL);
        } else {
            /** 不带 temp 的图片不进行搬移*/
            return imgPath;
        }

        // 截取除了***-***.png之前的路径，进行目录创建
        File parent = new File(newPath.substring(0, newPath.lastIndexOf(File.separator)));
        if (!parent.exists()) {
            boolean result = parent.mkdirs();
            if (!result) {
                log.error("创建列目录失败!");
            }
        }
        File source = new File(imgPath);
        File target = new File(newPath);
        /*只有当原文件路径包含temp时才进行文件转移*/
        if(imgPath.contains(TEMP)){
            boolean b = source.renameTo(target);
            log.debug("是否转移成功?{}", b);
            boolean result = source.delete();
            if (!result) {
                log.error("删除原文件失败!");
            }
        }
        return newPath;
    }

    /**
     * 删除文件
     * @author gaoshenggang
     * @date  2021/11/25 17:31
     */
    public static void deleteFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        // 截取***-***.png
        filePath = filePath.substring(filePath.lastIndexOf(File.separator));
        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        boolean result = file.delete();
        if (!result) {
            log.error("文件删除失败！");
        }
    }

    /**
     * 拼接图片路径
     * @author gaoshenggang
     * @date  2021/11/25 17:32
     */
    public static List<FileVO> spliceFilePath(List<FileVO> list, String serverName) {
        if (list == null || list.size() == 0) {
            return list;
        }

        for (FileVO fileVO : list) {
            String filePath = fileVO.getFilePath();
            filePath = serverName + filePath;
            fileVO.setFilePath(filePath);
        }

        return list;
    }

}
