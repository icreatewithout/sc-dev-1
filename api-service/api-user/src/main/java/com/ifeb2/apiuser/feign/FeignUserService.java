package com.ifeb2.apiuser.feign;

import com.ifeb2.apiuser.dto.ScRoleDto;
import com.ifeb2.apiuser.dto.ScUserDto;
import com.ifeb2.apiuser.feign.fallback.FeignUserServiceFallback;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = ServiceConstants.USER_SERVICE, fallbackFactory = FeignUserServiceFallback.class)
public interface FeignUserService {

    @GetMapping("/user/by/{name}")
    Result<ScUserDto> getUserByName(@PathVariable("name") String name);

    @GetMapping("/role/by/{id}")
    Result<List<ScRoleDto>> getRolesById(@PathVariable("id") Long id);

    /**
     * 测试seata分布式事务
     * @param val
     * @return
     */
    @GetMapping("/user/seata/test/{val}")
    Result seataTest(@PathVariable("val") String val);
}
