package com.ifeb2.userservice.rest;

import com.ifeb2.scdevbase.annotation.AuthUser;
import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.ServiceConstants;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.scdevlog.annotation.RecordLog;
import com.ifeb2.userservice.domain.ScMenu;
import com.ifeb2.userservice.service.ScMenuService;
import com.ifeb2.userservice.domain.vo.ScMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("菜单资源")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class ScMenuController extends BaseController {

    private final ScMenuService menuService;

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单", notes = "获取菜单")
    public Result findList(ScMenu scMenu) {
        List<ScMenu> list = menuService.findUserList(scMenu);
        return Result.success(list);
    }

    @GetMapping("/by/router")
    @ApiOperation(value = "加载动态菜单", notes = "加载动态菜单")
    public Result findMenuForRouter(@AuthUser LoginUser loginUser) {
        List<ScMenuVo> list = menuService.findMenuListByRouter(loginUser);
        return Result.success(list);
    }

    @RecordLog(value = "获取用户菜单", bizName = ServiceConstants.USER_SERVICE)
    @GetMapping("/by/user")
    @ApiOperation(value = "加载左侧菜单", notes = "加载左侧菜单")
    public Result findMenuForUser(@AuthUser LoginUser loginUser) {
        List<ScMenuVo> list = menuService.findMenuListByUser(loginUser);
        return Result.success(list);
    }

    @GetMapping("/tree")
    @ApiOperation(value = "树形控件数据",notes = "树形控件数据")
    public Result findTreeList(ScMenu scMenu){
        List<ScMenuVo> list = menuService.findTreeList(scMenu);
        return Result.success(list);
    }

    @GetMapping("/type")
    @ApiOperation(value = "根据类型获取菜单", notes = "根据类型获取菜单")
    public Result findListByType(ScMenu scMenu) {
        List<ScMenu> list = menuService.findList(scMenu);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取菜单信息", notes = "获取菜单信息")
    public Result getById(@PathVariable("id") Long id) {
        return Result.success(menuService.getById(id));
    }


    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @PostMapping
    public Result save(@RequestBody ScMenu scMenu) {
        return Result.success(menuService.save(scMenu));
    }

    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @PutMapping
    public Result update(@RequestBody ScMenu scMenu) {
        return Result.success(menuService.save(scMenu));
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);
        return Result.success();
    }

}
