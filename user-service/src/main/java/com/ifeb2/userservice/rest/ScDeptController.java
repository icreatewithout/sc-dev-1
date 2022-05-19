package com.ifeb2.userservice.rest;

import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.web.BaseController;
import com.ifeb2.userservice.domain.ScDept;
import com.ifeb2.userservice.service.ScDeptService;
import com.ifeb2.userservice.domain.vo.ScDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("菜单资源")
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class ScDeptController extends BaseController {

    private final ScDeptService deptService;

    @GetMapping("/list")
    @ApiOperation(value = "获取部门", notes = "获取部门")
    public Result findList(ScDept scDept) {
        List<ScDept> list = deptService.findDeptList(scDept);
        return Result.success(list);
    }

    @GetMapping("/tree")
    @ApiOperation(value = "树形控件数据", notes = "树形控件数据")
    public Result findTreeList(ScDept scDept) {
        List<ScDeptVo> list = deptService.findTreeList(scDept);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取部门信息", notes = "获取部门信息")
    public Result getById(@PathVariable("id") Long id) {
        return Result.success(deptService.getById(id));
    }


    @ApiOperation(value = "新增部门", notes = "新增部门")
    @PostMapping
    public Result save(@RequestBody ScDept scDept) {
        return Result.success(deptService.saveDept(scDept));
    }

    @ApiOperation(value = "修改部门", notes = "修改部门")
    @PutMapping
    public Result update(@RequestBody ScDept scDept) {
        return Result.success(deptService.saveDept(scDept));
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean b = deptService.deleteDept(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error(false, "存在下级节点，无法删除");
        }
    }

}
