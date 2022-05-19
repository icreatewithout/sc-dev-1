package com.ifeb2.userservice.service;

import com.ifeb2.userservice.domain.ScDept;
import com.ifeb2.userservice.domain.vo.ScDeptVo;

import java.util.List;

public interface ScDeptService {

    List<ScDept> findDeptList(ScDept dept);

    List<ScDeptVo> findTreeList(ScDept dept);

    Long saveDept(ScDept scDept);

    ScDept getById(Long id);

    boolean deleteDept(Long id);
}
