package com.ifeb2.userservice.rest;

import com.ifeb2.scdevbase.annotation.AuthIgnore;
import com.ifeb2.scdevbase.annotation.ParamIgnore;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.userservice.domain.ScRole;
import com.ifeb2.userservice.service.ScRoleService;
import com.ifeb2.userservice.domain.vo.ScRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("角色资源")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class ScRoleController extends BaseController {

    private final ScRoleService roleService;

    @AuthIgnore
    @ParamIgnore
    @ApiOperation(value = "获取用户角色权限", notes = "获取用户角色权限")
    @GetMapping("/by/{id}")
    public Result<List<ScRole>> getRoleByUser(@PathVariable Long id){
        return Result.success(roleService.getRoleByUser(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    public Result findList(ScRoleVo scRoleVo) {
        Page<ScRole> page = roleService.findPage(scRoleVo);
        return Result.success(page);
    }

    @GetMapping("/all")
    @ApiOperation(value = "用户页面获取角色列表", notes = "用户页面获取角色列表")
    public Result userList(ScRole scRole) {
        List<ScRole> list = roleService.findList(scRole);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取角色信息", notes = "获取角色信息")
    public Result getById(@PathVariable("id") Long id) {
        return Result.success(roleService.getRoleById(id));
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping
    public Result save(@RequestBody ScRoleVo scRoleVo) {
        return Result.success(roleService.save(scRoleVo));
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PutMapping
    public Result update(@RequestBody ScRoleVo scRoleVo) {
        roleService.update(scRoleVo);
        return Result.success();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String ids) {
        roleService.deleteByIds(ids);
        return Result.success();
    }

}
