package com.ifeb2.apifile.feign;

import com.ifeb2.apifile.feign.fallback.FeignFileServiceFallback;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = ServiceConstants.FILE_SERVICE, fallbackFactory = FeignFileServiceFallback.class)
public interface FeignFileService {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
    Result<Long> upload(@RequestPart(value = "file") MultipartFile multipartFile, @RequestParam("bizName") String bizName);

    @GetMapping(value = "/download", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    Response download();

    @GetMapping(value = "/test/{val}")
    Result<String> getPort(@PathVariable("val") String val, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token);
}
