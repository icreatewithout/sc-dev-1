package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.RecurseUtil;
import com.ifeb2.userservice.domain.ScDept;
import com.ifeb2.userservice.repository.ScDeptRepository;
import com.ifeb2.userservice.service.ScDeptService;
import com.ifeb2.userservice.domain.vo.ScDeptVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScDeptServiceImpl extends BaseService<ScDept, ScDeptRepository> implements ScDeptService {

    @Override
    public List<ScDept> findDeptList(ScDept dept) {
        List<ScDept> list = this.findList(dept);
        return RecurseUtil.parents(list);
    }

    @Override
    public List<ScDeptVo> findTreeList(ScDept dept) {
        List<ScDept> list = this.findList(dept);
        List<ScDeptVo> voList = new ArrayList<>();
        list.forEach(m -> {
            ScDeptVo vo = new ScDeptVo();
            BeanUtils.copyProperties(m, vo);
            vo.setValue(m.getId());
            vo.setLabel(m.getName());
            if ("2".equals(m.getStatus())) {
                vo.setDisabled(true);
            }
            voList.add(vo);
        });
        return RecurseUtil.parents(voList);
    }

    @Override
    public Long saveDept(ScDept scDept) {
        //生成code标识
        long code;
        if (scDept.getParentId() == null) {
            ScDept theLast = repository.findTheLast();
            if (null == theLast) {
                code = Constants.DEPT_CODE;
            } else {
                code = Long.parseLong(theLast.getCode()) + 1;
            }
        } else {
            ScDept parent = repository.findTheLastByParentId(scDept.getParentId());
            if (null == parent) {
                parent = super.getOne(new ScDept(scDept.getParentId()));
                code = Long.parseLong(parent.getCode() + Constants.DEPT_CODE);
            } else {
                code = Long.parseLong(parent.getCode()) + 1;
            }
        }
        scDept.setCode(String.valueOf(code));
        return super.save(scDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDept(Long id) {
        List<ScDept> children = this.findList(ScDept.builder().parentId(id).build());
        if (children.size() > 0) {
            return false;
        }
        this.deleteById(id);
        return true;
    }


}
