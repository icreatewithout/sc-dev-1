package com.ifeb2.fileservice.rest;

import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevbase.response.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("文件服务")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    @Value("${server.port}")
    private String port;

    @ParamIgnore
    @GetMapping("/test/{val}")
    public Result<String> getPort(@PathVariable String val) {
        return Result.success("val is " + val + "and port is " + port);
    }
}
