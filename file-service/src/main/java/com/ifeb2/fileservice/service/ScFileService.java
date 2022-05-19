package com.ifeb2.fileservice.service;

import com.ifeb2.fileservice.domian.ScFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface ScFileService {

    ScFile getById(Long id);

    void load(Long id, HttpServletResponse response);

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    Boolean exists(String fileName);

    /**
     * 多文件上传
     *
     * @param uid
     * @param bizName
     * @param files
     */
    List<Long> upload(Long uid, String bizName, MultipartFile[] files);

    /**
     * 流上传
     *
     * @param uid
     * @param bizName
     * @param inputStream
     * @param fileName
     */
    Long upload(Long uid, String bizName, InputStream inputStream, String fileName, String contentType);

    /**
     * 单文件上传
     *
     * @param uid
     * @param bizName
     * @param file
     * @return
     */
    Long upload(Long uid, String bizName, MultipartFile file);

    /**
     * 文件下载
     *
     * @param id
     * @param request
     * @param response
     */
    void download(Long id, HttpServletRequest request, HttpServletResponse response);

}
