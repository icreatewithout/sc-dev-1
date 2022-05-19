package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.exception.BadRequestException;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.userservice.domain.*;
import com.ifeb2.userservice.domain.vo.ScMenuVo;
import com.ifeb2.userservice.domain.vo.ScRoleVo;
import com.ifeb2.userservice.repository.*;
import com.ifeb2.userservice.service.ScRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScRoleServiceImpl extends BaseService<ScRole, ScRoleRepository> implements ScRoleService {

    private final ScRoleMenuRepository roleMenuRepository;
    private final ScUserRoleRepository userRoleRepository;
    private final ScMenuRepository menuRepository;
    private final ScUserRepository userRepository;

    @Override
    public ScRoleVo getRoleById(Long id) {
        ScRole scRole = this.getById(id);

        ScRoleVo roleVo = new ScRoleVo();
        BeanUtils.copyProperties(scRole, roleVo);

        List<ScRoleMenu> roleMenus = roleMenuRepository.findAll(Example.of(ScRoleMenu.builder().roleId(id).build()));
        if (roleMenus.size() > 0) {
            StringBuffer sb = new StringBuffer();
            roleMenus.forEach(item -> {
                sb.append(item.getMenuId()).append(",");
            });
            roleVo.setCheckedKeys(sb.substring(0, sb.length() - 1));
        }
        return roleVo;
    }

    @Override
    public Page<ScRole> findPage(ScRoleVo scRoleVo) {
        ScRole role = new ScRole();
        BeanUtils.copyProperties(scRoleVo, role);
        Page<ScRole> page = this.findPage(role);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(ScRoleVo scRoleVo) {
        ScRole role = new ScRole();
        BeanUtils.copyProperties(scRoleVo, role);
        Long id = this.save(role);
        if (StringUtils.isNoneBlank(scRoleVo.getCheckedKeys())) {
            String[] strings = scRoleVo.getCheckedKeys().split(",");
            saveRoleMenus(id, strings);
        }
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScRoleVo scRoleVo) {
        ScRoleMenu rm = ScRoleMenu.builder().roleId(scRoleVo.getId()).build();
        List<ScRoleMenu> roleMenus = roleMenuRepository.findAll(Example.of(rm));
        if (roleMenus.size() > 0) {
            roleMenuRepository.deleteInBatch(roleMenus);
        }

        if (StringUtils.isNoneBlank(scRoleVo.getCheckedKeys())) {
            String[] strings = scRoleVo.getCheckedKeys().split(",");
            saveRoleMenus(scRoleVo.getId(), strings);
        }

        ScRole role = this.getById(scRoleVo.getId());
        BeanUtils.copyProperties(scRoleVo, role);
        this.save(role);
    }

    private void saveRoleMenus(Long id, String[] strings) {
        List<ScRoleMenu> roleMenus = new ArrayList<>();
        for (String menuId : strings) {
            ScRoleMenu roleMenu = ScRoleMenu.builder().menuId(Long.valueOf(menuId)).roleId(id).build();
            roleMenus.add(roleMenu);
        }
        roleMenuRepository.saveAll(roleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(String ids) {
        String[] strings = ids.split(",");
        ScRoleMenu rm = null;
        for (String id : strings) {
            ScRole role = super.getById(Long.valueOf(id));
            if (role.getCode().equals("ADMIN")) {
                throw new BadRequestException("超级管理员角色不可以删除");
            }
            repository.deleteById(Long.valueOf(id));
            rm = ScRoleMenu.builder().roleId(Long.valueOf(id)).build();
            List<ScRoleMenu> roleMenus = roleMenuRepository.findAll(Example.of(rm));
            if (roleMenus.size() > 0) {
                roleMenuRepository.deleteInBatch(roleMenus);
            }
        }
    }

    @Override
    public List<ScRole> getRoleByUser(Long id) {

        ScUser scUser = userRepository.getOne(id);
        List<ScUserRole> roles;
        if (scUser.getType().equals("0")) {
            roles = userRoleRepository.findAll();
        } else {
            roles = userRoleRepository.findAll(Example.of(ScUserRole.builder().userId(id).build()));
        }

        List<ScRole> roleList = new ArrayList<>();
        for (ScUserRole role : roles) {
            ScRole scRole = super.getById(role.getRoleId());
            List<ScRoleMenu> roleMenus = roleMenuRepository.findAll(Example.of(ScRoleMenu.builder().roleId(role.getRoleId()).build()));
            List<ScMenuVo> menus = new ArrayList<>();
            ScMenuVo menuVo;
            for (ScRoleMenu roleMenu : roleMenus) {
                ScMenu menu = menuRepository.getOne(roleMenu.getMenuId());
                menuVo = new ScMenuVo();
                BeanUtils.copyProperties(menu, menuVo);
                menus.add(menuVo);
            }
            scRole.setPermissions(menus);
            roleList.add(scRole);
        }

        return roleList;
    }

}
