package com.ifeb2.userservice.service;

import com.ifeb2.userservice.domain.ScRole;
import com.ifeb2.userservice.domain.vo.ScRoleVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScRoleService {

    List<ScRole> findList(ScRole scRole);

    ScRoleVo getRoleById(Long id);

    Page<ScRole> findPage(ScRoleVo scRoleVo);

    Long save(ScRoleVo scRoleVo);

    void update(ScRoleVo scRoleVo);

    void deleteByIds(String ids);

    List<ScRole> getRoleByUser(Long id);
}
