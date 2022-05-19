package com.ifeb2.apiuser.feign.fallback;

import com.ifeb2.apiuser.feign.FeignUserService;
import com.ifeb2.scdevbase.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignUserServiceFallback implements FallbackFactory<FeignUserService> {
    @Override
    public FeignUserService create(Throwable cause) {
        return new FeignUserService() {
            @Override
            public Result getUserByName(String name) {
                log.debug("getUserByName:{}", cause.getMessage());
                return Result.error(cause.getMessage());
            }

            @Override
            public Result getRolesById(Long id) {
                log.debug(cause.getMessage());
                return Result.error(cause.getMessage());
            }

            @Override
            public Result seataTest(String val) {
                return Result.error(cause.getMessage());
            }
        };
    }
}
