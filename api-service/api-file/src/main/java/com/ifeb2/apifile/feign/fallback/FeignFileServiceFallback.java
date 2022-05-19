package com.ifeb2.apifile.feign.fallback;

import com.ifeb2.apifile.feign.FeignFileService;
import com.ifeb2.scdevbase.response.Result;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FeignFileServiceFallback implements FallbackFactory<FeignFileService> {
    @Override
    public FeignFileService create(Throwable cause) {
        return new FeignFileService() {
            @Override
            public Result upload(MultipartFile multipartFile, String uploadPath) {
                return Result.error();
            }

            @Override
            public Response download() {
                return null;
            }

            @Override
            public Result<String> getPort(String val,String token) {
                return Result.error(cause.getMessage());
            }
        };
    }
}
