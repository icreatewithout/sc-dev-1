package com.ifeb2.userservice.rest;

import com.ifeb2.apifile.feign.FeignFileService;
import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.AuthUser;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.domain.vo.UserVo;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.scdevfeign.RequestHeaderHolder;
import com.ifeb2.scdevlog.annotation.RecordLog;
import com.ifeb2.userservice.domain.ScUser;
import com.ifeb2.userservice.domain.vo.RepassVo;
import com.ifeb2.userservice.domain.vo.ScUserVo;
import com.ifeb2.userservice.service.ScUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Api("用户服务")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ScUserController extends BaseController {

    @Value("${minio.http}")
    private String minioHttp;
    private final ScUserService userService;
    private final FeignFileService feignFileService;

    @PostMapping
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public Result create(@RequestBody UserVo userVo) {
        return Result.success(userService.create(userVo));
    }

    @PutMapping
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public Result update(@RequestBody UserVo userVo) {
        userService.update(userVo);
        return Result.success();
    }

    @PutMapping("/{id}/{pass}")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public Result changePasswd(@PathVariable("id") Long id, @PathVariable("pass") String pass) {
        userService.updatePasswd(id, pass);
        return Result.success();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/reset/{id}")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public Result resetPasswd(@PathVariable("id") Long id) {
        userService.updatePasswd(id, Constants.DEFAULT_PASSWD);
        return Result.success("密码已重置！");
    }

    @RecordLog(value = "访问用户列表", bizName = ServiceConstants.USER_SERVICE)
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/list")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public Result list(ScUserVo userVo) {

        Result<String> result = feignFileService.getPort("test", RequestHeaderHolder.getToken());

        System.out.println(result.getCode());
        System.out.println(result.getData());
        System.out.println(result.getMessage());

        Page<ScUser> page = userService.selectPage(userVo);
        return Result.success(page);
    }

    @RecordLog(value = "获取用户信息", bizName = ServiceConstants.USER_SERVICE)
    @GetMapping("/{id}")
    @ApiOperation(value = "获取用户", notes = "获取用户")
    public Result getUser(@PathVariable("id") Long id) {
        return Result.success(userService.getUserById(id));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String ids) {
        userService.deleteByIds(ids);
        return Result.success();
    }

    @ParamIgnore
    @AuthIgnore
    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    @GetMapping("/by/{name}")
    public Result<ScUser> getByName(@PathVariable("name") String name) {
        return Result.success(userService.getOne(ScUser.builder().userName(name).build()));
    }

    @GetMapping("/by/token")
    public Result checkLogin(@AuthUser LoginUser user) {
        if (null == user) {
            return Result.error(HttpStatus.UNAUTHORIZED.value(), "未登录");
        }
        UserVo userVo = user.getUsersVo();
        userVo.setPassword(null);
        if (null != userVo.getAvatarUrl()) {
            userVo.setAvatarUrl(minioHttp + userVo.getAvatarUrl());
        }
        return Result.success(userVo);
    }

    @PutMapping("/update/avatar/{fileId}")
    public Result updateAvatar(@AuthUser LoginUser user, @PathVariable("fileId") String fileId) {
        if (null != user) {
            UserVo userVo = user.getUsersVo();
            userVo.setAvatarUrl(fileId);
            userService.update(userVo);
            return Result.success();
        }
        return Result.error();
    }

    @PutMapping("/change/passwd")
    public Result changePass(RepassVo repassVo) {
        userService.changePass(repassVo);
        return Result.success();
    }

    @ParamIgnore
    @AuthIgnore
    @GetMapping("/seata/test/{val}")
    public Result seataTest(@PathVariable("val") String val) {
        userService.seataTest(val);
        return Result.success();
    }

}
