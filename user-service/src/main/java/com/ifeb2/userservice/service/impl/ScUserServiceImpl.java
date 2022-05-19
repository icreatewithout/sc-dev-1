package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.domain.vo.UserVo;
import com.ifeb2.scdevbase.exception.BadRequestException;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.SecurityUtils;
import com.ifeb2.scdevcore.jjwt.properties.JJwtProperties;
import com.ifeb2.userservice.domain.ScUser;
import com.ifeb2.userservice.domain.ScUserRole;
import com.ifeb2.userservice.domain.vo.RepassVo;
import com.ifeb2.userservice.domain.vo.ScUserVo;
import com.ifeb2.userservice.repository.ScUserRepository;
import com.ifeb2.userservice.repository.ScUserRoleRepository;
import com.ifeb2.userservice.service.ScUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScUserServiceImpl extends BaseService<ScUser, ScUserRepository> implements ScUserService {

    private final PasswordEncoder passwordEncoder;
    private final ScUserRoleRepository userRoleRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JJwtProperties properties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScUser create(UserVo userVo) {
        ScUser scUser = new ScUser();
        BeanUtils.copyProperties(userVo, scUser);
        if (StringUtils.isBlank(scUser.getType())) {
            scUser.setType("1");// 默认系统用户
        }
        scUser.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWD));
        Long id = super.save(scUser);
        saveRoles(userVo, id);
        return scUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserVo userVo) {

        ScUser scUser = this.getById(userVo.getId());
        List<ScUserRole> roles = userRoleRepository.findAll(Example.of(ScUserRole.builder().userId(userVo.getId()).build()));
        if (roles.size() > 0) {
            userRoleRepository.deleteAll(roles);
        }
        userVo.setPassword(scUser.getPassword());
        BeanUtils.copyProperties(userVo, scUser);
        repository.save(scUser);
        //更新缓存
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (null != loginUser && Objects.equals(scUser.getId(), loginUser.getUserId())) {
            loginUser.setUsersVo(userVo);
            redisTemplate.opsForValue().set(properties.getOnlineKey() + loginUser.getId(), loginUser, properties.getExpired() * 60, TimeUnit.SECONDS);
        }
        //更新角色
        saveRoles(userVo, scUser.getId());
    }

    private void saveRoles(UserVo userVo, Long id) {
        if (StringUtils.isNotBlank(userVo.getRoleIds())) {
            String[] ids = userVo.getRoleIds().split(",");
            List<ScUserRole> roles = new ArrayList<>();
            ScUserRole userRole = null;
            for (String roleId : ids) {
                userRole = new ScUserRole();
                userRole.setUserId(id);
                userRole.setRoleId(Long.valueOf(roleId));
                roles.add(userRole);
            }
            userRoleRepository.saveAll(roles);
        }
    }


    @Override
    public Page<ScUser> selectPage(ScUserVo userVo) {
        ScUser user = new ScUser();
        BeanUtils.copyProperties(userVo, user);
        return super.findPage(user);
    }

    @Override
    public UserVo getUserById(Long id) {
        ScUser scUser = super.getById(id);
        List<ScUserRole> roles = userRoleRepository.findAll(Example.of(ScUserRole.builder().userId(scUser.getId()).build()));
        List<String> roleIds = new ArrayList<>();
        roles.forEach(info -> {
            roleIds.add(info.getRoleId().toString());
        });
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(scUser, userVo);
        userVo.setRoleIds(StringUtils.join(roleIds, ","));
        userVo.setPassword(null);
        return userVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(String ids) {
        String[] strings = ids.split(",");
        ScUserRole userRole = null;
        for (String id : strings) {
            ScUser scUser = super.getById(Long.valueOf(id));
            if ("0".equals(scUser.getType())) {
                throw new BadRequestException("不能删除超级管理员");
            }
            super.deleteById(Long.valueOf(id));
            userRole = ScUserRole.builder().userId(Long.valueOf(id)).build();
            List<ScUserRole> roles = userRoleRepository.findAll(Example.of(userRole));
            if (roles.size() > 0) {
                userRoleRepository.deleteAll(roles);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePasswd(Long id, String pass) {
        ScUser scUser = super.getById(id);
        scUser.setPassword(passwordEncoder.encode(pass));
        super.save(scUser);
    }

    @Override
    public void changePass(RepassVo repassVo) {
        Long uid = SecurityUtils.getUserId();
        if (null != uid) {
            ScUser scUser = super.getById(uid);
            if (null != scUser && passwordEncoder.matches(repassVo.getPassword(), scUser.getPassword())) {
                scUser.setPassword(passwordEncoder.encode(repassVo.getPassword1()));
                super.save(scUser);
            } else {
                throw new BadRequestException("原密码错误");
            }
        } else {
            throw new BadRequestException("服务异常请重试！");
        }
    }

    @Override
    @Transactional
    public void seataTest(String val) {
        ScUser scUser = this.getById(970372310752034816L);
        scUser.setRemark(val);
        this.save(scUser);
        Integer i = Integer.parseInt(val);
    }

}
