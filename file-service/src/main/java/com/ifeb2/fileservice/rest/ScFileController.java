package com.ifeb2.fileservice.rest;

import com.google.common.collect.Maps;
import com.ifeb2.fileservice.service.ScFileService;
import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.AuthUser;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.SecurityUtils;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.scdevlog.annotation.RecordLog;
import com.ifeb2.scdevminio.minio.MinioProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api("文件服务")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ScFileController extends BaseController {

    private final ScFileService fileService;
    private final MinioProperties properties;

    @AuthIgnore
    @ParamIgnore
    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping("/down/{id}")
    @RecordLog(value = "文件下载",bizName = ServiceConstants.FILE_SERVICE)
    public void down(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        fileService.download(id, request, response);
    }

    @AuthIgnore
    @ParamIgnore
    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping("/load/{id}")
    @RecordLog(value = "文件下载",bizName = ServiceConstants.FILE_SERVICE)
    public void load(@PathVariable("id") Long id, HttpServletResponse response) {
        fileService.load(id, response);
    }

    @ParamIgnore
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping("/upload")
    @RecordLog(value = "上传文件",bizName = ServiceConstants.FILE_SERVICE)
    public Result upload(@AuthUser LoginUser loginUser, MultipartFile file, String bizName) {
        try {
            if (loginUser == null) {
                loginUser = SecurityUtils.getLoginUser();
            }
            Long id = fileService.upload(loginUser != null ? loginUser.getUserId() : null, bizName, file);
            return Result.success(handlerRes(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }

    @ParamIgnore
    @RecordLog(value = "上传文件",bizName = ServiceConstants.FILE_SERVICE)
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping("/uploads")
    public Result uploads(@AuthUser LoginUser loginUser, MultipartFile[] files, String bizName) {
        try {
            if (loginUser == null) {
                loginUser = SecurityUtils.getLoginUser();
            }
            List<Long> ids = fileService.upload(loginUser.getUserId(), bizName, files);
            List<Map<String, String>> res = new ArrayList<>();
            ids.forEach(id -> {
                res.add(handlerRes(id));
            });
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }

    private Map<String, String> handlerRes(Long id) {
        Map<String, String> map = Maps.newHashMap();
        map.put("id", id.toString());
        map.put("url", properties.getHttp() + id);
        return map;
    }
}
