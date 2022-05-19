package com.ifeb2.userservice.rest;

import com.ifeb2.scdevbase.web.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("字典资源")
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class ScDictController extends BaseController {



}
