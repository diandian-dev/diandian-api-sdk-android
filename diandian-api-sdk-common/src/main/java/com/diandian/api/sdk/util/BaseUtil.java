/**
 * 
 */
package com.diandian.api.sdk.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.diandian.api.sdk.exception.DDAPIException;

/**
 * diandian-api-sdk基础功能包
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-10下午5:20:33
 */
public class BaseUtil {

    /**
     * 获取文件内容
     * 目前支持jpg/jpeg, png, bmp, gif, mp3.
     * 
     * @param file
     * @return
     */
    public static byte[] getFileData(File file) {
        if (file == null) {
            throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, "文件不能为空", null);
        }
        String fileName = file.getName();
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")
                && !fileName.endsWith(".bmp") && !fileName.endsWith("gif")
                && !fileName.endsWith("mp3")) {
            throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, "暂不支持此格式文件，请重新选择", null);
        }

        // 获取文件内容字节数组
        byte[] content = fileToByteArray(file);
        if (content == null) {
            throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, "文件内容为空！", null);
        }

        return content;

    }

    /**
     * 工具类，读取文件二进制数据
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fileToByteArray(File file) {
        try {
            return streamToByteArray(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * 工具，将输入流转换成字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] streamToByteArray(InputStream inputStream) {
        byte[] content = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        try {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            content = baos.toByteArray();
            if (content.length == 0) {
                content = null;
            }
            baos.close();
            bis.close();
        } catch (IOException e) {
            throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, e.getMessage(), e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, e.getMessage(),
                            e);
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new DDAPIException(DDAPIException.DEFAULT_EXCEPTION_CODE, e.getMessage(),
                            e);
                }
            }
        }
        return content;
    }

    /**
     * 读取InputStream中数据
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    /**
     * 根据文件名获取其contentType
     * 
     * @param fileName
     * @return
     */
    public static String parseContentType(String fileName) {
        String contentType = "image/jpg";
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(".jpg")) contentType = "image/jpg";
        else if (fileName.endsWith(".png")) contentType = "image/png";
        else if (fileName.endsWith(".jpeg")) contentType = "image/jpeg";
        else if (fileName.endsWith(".gif")) contentType = "image/gif";
        else if (fileName.endsWith(".bmp")) contentType = "image/bmp";
        else if (fileName.endsWith(".mp3")) contentType = "audio/mp3";
        else throw new RuntimeException("不支持的文件类型'" + fileName + "'(或没有文件扩展名)");
        return contentType;
    }
}
