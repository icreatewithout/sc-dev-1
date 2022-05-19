package com.ifeb2.authservice.rest;

import com.ifeb2.authservice.service.AuthService;
import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevbase.domain.vo.AuthVo;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.scdevlog.annotation.RecordLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Api("认证接口服务")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService userService;

    @AuthIgnore
    @ParamIgnore
    @PostMapping("/sba")
    @ApiOperation(value = "admin service 登录认证", notes = "admin service 登录认证")
    @RecordLog(value = "admin service 登录认证", bizName = ServiceConstants.AUTH_SERVICE)
    public Result authForSBA(@RequestBody AuthVo authVo) {
        return Result.success(userService.authForSBA(authVo));
    }

    @AuthIgnore
    @ParamIgnore
    @PostMapping("/token")
    @ApiOperation(value = "登录认证", notes = "登录认证")
    @RecordLog(value = "用户登录", bizName = ServiceConstants.AUTH_SERVICE)
    public Result auth(@RequestBody AuthVo authVo) {
        return Result.success(userService.auth(authVo));
    }


    @AuthIgnore
    @ParamIgnore
    @GetMapping("/code")
    @ApiOperation(value = "图片验证码", notes = "图片验证码")
    @RecordLog(value = "加载图片验证码", bizName = ServiceConstants.AUTH_SERVICE)
    public Result genCode() {
        return Result.success(userService.getCode());
    }


}
