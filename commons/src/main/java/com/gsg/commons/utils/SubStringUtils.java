package com.gsg.commons.utils;

import com.gsg.commons.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.gsg.commons.utils.Constants.BLANK;

/**
 * @author IANJIANG
 * @since JDK 11.0.10
 * ClassName: SubStringUtils
 * date: 2021/6/2 14:52
 */

/**
 * 截取字符串工具类
 * @author IANJIANG
 */

@Slf4j
public class SubStringUtils {

    private static final String TAG = "img";

    private static final String PREFIX = "/gsg";

    private static final String ATTR = "src";

    private static final String BLANK_REG = " +";

    /**
     * 清除富文本框内字体格式标签
     * @param content
     * @return
     */
    public static String removeHtmlFormat(String content) {
        if (content == null) {
            return null;
        }

        // 清除字体大小/样式
        String styleRegex = "style=\"(.*?)\"";
        String hRegex = "<h[^>]*>";
        String hRegexEnd = "</h[^>]*>";
        String aRegex = "<a[^>]*>";
        String aRegexEnd = "</a[^>]*>";
        String spanRegex = "<span[^>]*>";
        String spanRegexEnd = "</span[^>]*>";


        content = content
                .replaceAll(styleRegex, BLANK)
                .replaceAll(hRegex, BLANK)
                .replaceAll(hRegexEnd, BLANK)
                .replaceAll(aRegex, BLANK)
                .replaceAll(aRegexEnd, BLANK)
                .replaceAll(spanRegex, BLANK)
                .replaceAll(spanRegexEnd, BLANK);

        return content;

    }

    /**
     * 替换富文本图片路径
     * @param content
     * @return
     */
    public static String replaceHtmlContentImgPath(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        /** 清除富文本框内字体格式标签*/
        String rfContent = removeHtmlFormat(content);

        LinkedList<String> list = new LinkedList<>();
        // 使用Jsoup解析html字符串
        Document document = Jsoup.parse(rfContent);
        // 获取img标签
        Elements elements = document.getElementsByTag(TAG);
        if (elements == null || elements.size() < 1) {
            return content;
        }
        for (Element element : elements) {
            // 获取img标签中的attribute -> src
            String imgPath = element.attr(ATTR);
            list.add(imgPath);
        }

        if (list.size() < 1) {
            return content;
        }

        LinkedList<FileVO> newList = new LinkedList<>();

        for (String imgPath : list) {

            // 去掉前缀以便于文件路径转移和存储
            int index = imgPath.lastIndexOf("gsg");
            if (index != -1) {
                imgPath = imgPath.substring(index);
                imgPath = imgPath.replaceAll(BLANK_REG, BLANK);
            }
            imgPath = File.separator + imgPath;
            newList.add(new FileVO(imgPath));
        }
        // 文件转移
        List<FileVO> newImgList = FileUploadUtils.moveForHtmlContent(newList);

        for (int i = 0; i < newImgList.size(); i++) {
            content = content.replace(list.get(i), newImgList.get(i).getFilePath());
        }
        return content;
    }

    /**
     * 从html中解析出contentImg
     * @param content
     * @return
     */
    public static List<String> getContentImg(String content) {
        List<String> list = getContentImgCommons(content);
        if (list == null || list.size() == 0) {
            return null;
        }
        List<String> newList = new ArrayList<>();
        for (String imgPath : list) {
            if (StringUtils.isEmpty(imgPath)) {
                continue;
            }
            int index = -1;
            // 去掉前缀以便于文件路径转移
            if (imgPath.contains(PREFIX)) {
                index = imgPath.lastIndexOf(PREFIX);
            }
            if (index != -1) {
                imgPath = imgPath.substring(index);
                imgPath = imgPath.replaceAll(BLANK_REG, BLANK);
            }
            newList.add(imgPath);
        }

        return newList;
    }

    /**
     * 从html中获取图片并删除
     * @author ianjiang
     * @date 2021/9/8 11:06
     * @param content
     * @return java.util.List<java.lang.String>
     */
    public static void deleteContentImg(String content) {
        List<String> list = getContentImgCommons(content);
        if (list == null || list.size() == 0) {
            return;
        }

        for (String imgPath : list) {
            FileUploadUtils.deleteFile(imgPath);
        }

    }

    /**
     * 从html获取文件公共
     * @author ianjiang
     * @date 2021/9/8 11:13
     * @param content
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getContentImgCommons(String content) {
        Document document = Jsoup.parse(content);

        if (StringUtils.isEmpty(content)) {
            return null;
        }

        // 获取img标签
        Elements elements = document.getElementsByTag(TAG);

        List<String> list = new ArrayList<>();

        if (elements == null || elements.size() < 1) {
            return null;
        }

        for (Element element : elements) {
            // 获取img标签中的attribute -> src
            String imgPath = element.attr(ATTR);
            list.add(imgPath);
        }

        if (list.size() < 1) {
            return null;
        }

        return list;
    }

    /**
     * 拼接富文本框图片路径
     * @param content
     * @param
     * @return
     */
    public static String joinContentImg(String content) {
        List<String> contentImg = getContentImg(content);
        LinkedList<String> newList = new LinkedList<>();
        if (contentImg == null) {
            return content;
        }
        for (String imgPath : contentImg) {
            /** 20211210 生成WebP图片副本*/
            imgPath = WebPUtils.changePathToWebp("1", imgPath);
            newList.add(imgPath);
        }

        for (int i = 0; i < newList.size(); i++) {
            content = content.replace(contentImg.get(i), newList.get(i));
        }
        return content;
    }

    /**
     * 获取html中的纯文本
     * @param content
     * @return
     */
    public static String getContentText(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";

        // 过滤script标签
        content = content.replaceAll(scriptRegex, "");
        // 过滤style标签
        content = content.replaceAll(styleRegex, "");
        // 过滤html标签
        content = content.replaceAll(htmlRegex, "");
        // 过滤空格等
        content = content.replaceAll(spaceRegex, "");
        // 返回文本字符串
        return content.trim();
    }

    /**
     * 获取HTML代码里的内容
     * @return
     */
    public static String getTextFromHtml(String content) {
        if (content == null || "".equals(content)) {
            return null;
        }

        //去除html标签
        content = getContentText(content);
        //去除空格" "
        content = content.replaceAll(" ","");
        return content;
    }


    /**
     * 将content中的图片路径空格全部去除
     * @param content
     * @return
     */
    public static String trimContentImgPath(String orgTxt, String content) {
        if (content == null) {
            return null;
        }

        /** 清除富文本框内字体格式标签*/
        content = removeHtmlFormat(content);

        LinkedList<String> contentImgPathList = getContentImgPathList(content);
        LinkedList<String> orgTxtImgPathList = getContentImgPathList(orgTxt);
        if(ObjectUtils.isEmpty(contentImgPathList) || ObjectUtils.isEmpty(orgTxtImgPathList)){
            return content;
        }

        LinkedList<FileVO> newList = new LinkedList<>();

        for (String imgPath : orgTxtImgPathList) {
            imgPath = imgPath.replaceAll(BLANK_REG, BLANK);
            newList.add(new FileVO(imgPath));
        }

        for (int i = 0; i < newList.size(); i++) {
            content = content.replace(contentImgPathList.get(i), newList.get(i).getFilePath());
        }

        return content;

    }

    /** 从入参（HTML字符串中获取所有图片地址 List）*/
    private static LinkedList<String> getContentImgPathList(String htmlText) {

        LinkedList<String> list = new LinkedList<>();
        // 使用Jsoup解析html字符串
        Document document = Jsoup.parse(htmlText);
        // 获取img标签
        Elements elements = document.getElementsByTag(TAG);
        if (elements == null || elements.size() < 1) {
            return null;
        }
        for (Element element : elements) {
            // 获取img标签中的attribute -> src
            String imgPath = element.attr(ATTR);
            list.add(imgPath);
        }

        return list;
    }

//    public static void main(String[] args) {
//        String content = "<p>" +
//                "<img src=\"http://192.168.50.211:8099/caftrade/static-resource/temp/cn/1/20210609/1623229177619-108707971259990.png\" alt=\"\" /></p>\n" +
//                "<p>" +
//                "<img src=\"http://192.168.50.211:8099/caftrade/static-resource/temp/cn/1/20210609/1623229177619-108707971654840.png\" alt=\"\" />" +
//                "<img src=\"http://192.168.50.211:8099/caftrade/static-resource/temp/cn/1/20210609/1623229219756-108750108201394.png\" alt=\"\" /></p>\n" +
//                "<p>" +
//                "<img src=\"http://192.168.50.211:8099/caftrade/static-resource/temp/cn/1/20210609/1623229239792-108770144264193.png\" alt=\"\" /></p>\n" +
//                "<h1>测试测试</h1>\n" +
//                "<p>测试</p>\n" +
//                "<ol>\n" +
//                "<li>测试</li>\n" +
//                "<li>测试</li>\n" +
//                "</ol>\n";
//
//        String textContent = getTextFromHtml(content);
//        System.err.println(textContent);
//
//        String replaceImgPath = replaceImgPath(content);
//        System.err.println(replaceImgPath);
//
//
//        String contentImg = joinContentImg(replaceImgPath, "http://192.168.1.1:8099");
//        System.err.println(contentImg);
//    }

}
