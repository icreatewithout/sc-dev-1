package com.ifeb2.userservice.rest;

import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.scdevlog.annotation.RecordLog;
import com.ifeb2.userservice.domain.ScLog;
import com.ifeb2.userservice.domain.vo.ScLogVo;
import com.ifeb2.userservice.service.ScLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("用户服务")
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class ScLogController extends BaseController {

    private final ScLogService logService;

    @RecordLog(value = "查看日志列表", bizName = ServiceConstants.USER_SERVICE)
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/list")
    @ApiOperation(value = "日志列表", notes = "日志列表")
    public Result list(ScLogVo logVo) {
        Page<ScLog> page = logService.selectPage(logVo);
        return Result.success(page);
    }

    @RecordLog(value = "获取日志信息", bizName = ServiceConstants.USER_SERVICE)
    @GetMapping("/{id}")
    @ApiOperation(value = "日志信息", notes = "日志信息")
    public Result getLog(@PathVariable("id") Long id) {
        return Result.success(logService.getById(id));
    }

}
