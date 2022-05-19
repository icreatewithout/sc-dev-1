package com.ifeb2.testservice.rest;

import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.testservice.service.SeataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seata")
public class SeataTestController {

    private final SeataService seataService;

    @ParamIgnore
    @AuthIgnore
    @GetMapping("/test")
    public void test(@RequestParam("name") String name) {
        seataService.test(name);
    }

}
