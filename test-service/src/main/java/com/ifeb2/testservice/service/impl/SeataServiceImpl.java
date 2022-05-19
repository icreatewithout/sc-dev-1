package com.ifeb2.testservice.service.impl;

import com.ifeb2.apiuser.feign.FeignUserService;
import com.ifeb2.scdevbase.exception.BadRequestException;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.testservice.domain.ScSeataTest;
import com.ifeb2.testservice.repository.ScSeataTestRepository;
import com.ifeb2.testservice.service.SeataService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeataServiceImpl extends BaseService<ScSeataTest, ScSeataTestRepository> implements SeataService {

    private final FeignUserService feignUserService;

    @Override
    @GlobalTransactional(name = "test-service", rollbackFor = Exception.class)
    @Transactional
    public void test(String name) {
        super.save(ScSeataTest.builder().name(name).val("hello").build());
        Result result = feignUserService.seataTest(name);
        if (result.getCode()==500){
            throw new BadRequestException(result.getMessage());
        }
    }

}
