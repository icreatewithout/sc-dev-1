package com.ifeb2.authservice.service.impl;

import com.ifeb2.apiuser.dto.ScMenuDto;
import com.ifeb2.apiuser.dto.ScRoleDto;
import com.ifeb2.apiuser.dto.ScUserDto;
import com.ifeb2.apiuser.feign.FeignUserService;
import com.ifeb2.authservice.service.AuthService;
import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.domain.Permissions;
import com.ifeb2.scdevbase.domain.vo.AuthVo;
import com.ifeb2.scdevbase.domain.vo.UserVo;
import com.ifeb2.scdevbase.exception.BadRequestException;
import com.ifeb2.scdevbase.exception.RequestException;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.IDUtil;
import com.ifeb2.scdevbase.utils.RequestHolder;
import com.ifeb2.scdevcore.captcha.CaptchaProperties;
import com.ifeb2.scdevcore.captcha.CaptchaService;
import com.ifeb2.scdevcore.captcha.enums.CaptchaType;
import com.ifeb2.scdevcore.jjwt.JJwtService;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final CaptchaService captchaService;
    private final CaptchaProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JJwtService jJwtService;
    private final AuthenticationManagerBuilder managerBuilder;
    private final FeignUserService feignUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ScUserDto scUserDto = null;
        Result<ScUserDto> result = feignUserService.getUserByName(s);
        if (result != null && result.getCode() == 200) {
            scUserDto = result.getData();
        }

        if (null == scUserDto) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        if (!scUserDto.getStatus().equals(Constants.NORMAL1)) {
            throw new RequestException("账号状态异常");
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(scUserDto, userVo);

        Result<List<ScRoleDto>> dtoResult = feignUserService.getRolesById(scUserDto.getId());

        Set<Permissions> permissions = new HashSet<>();

        if (dtoResult.getCode() == 200) {
            Permissions permission = null;

            List<ScRoleDto> roleDtoList = dtoResult.getData();

            for (ScRoleDto scRoleDto : roleDtoList) {
                permission = new Permissions();
                permission.setPermission(Constants.ROLE + scRoleDto.getCode());
                permissions.add(permission);
                List<ScMenuDto> menuDtos = scRoleDto.getPermissions();
                for (ScMenuDto menuDto : menuDtos) {
                    if (StringUtils.isBlank(menuDto.getPermission())) {
                        continue;
                    }
                    permission = new Permissions();
                    permission.setPermission(menuDto.getPermission());
                    permissions.add(permission);
                }
            }
        }

        return new LoginUser(IDUtil.swId(), userVo, permissions, "0".equals(scUserDto.getType()));
    }


    @Override
    public String auth(AuthVo authVo) throws BadRequestException {
        String code = (String) redisTemplate.opsForValue().get(authVo.getImgId());
        redisTemplate.delete(authVo.getImgId());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码错误");
        }
        if (!authVo.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        return authentication(authVo);
    }

    @Override
    public String authForSBA(AuthVo authVo) {
       return authentication(authVo);
    }

    String authentication(AuthVo authVo){
        Authentication authentication = managerBuilder.getObject()
                .authenticate(new UsernamePasswordAuthenticationToken(authVo.getUsername(), authVo.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginUser users = (LoginUser) authentication.getPrincipal();
        String token = jJwtService.createToken(users);
        cacheUser(users);
        return token;
    }

    private void cacheUser(LoginUser loginUser) {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        loginUser.setIp(Constants.getIp(request));
        loginUser.setBrowser(Constants.getBrowser(request));
        loginUser.setAddress(Constants.getCityInfoByIp(Constants.getIp(request)));
        jJwtService.cacheToken(loginUser);
    }

    @Override
    public Map<String, Object> getCode() {
        Captcha captcha = captchaService.getCaptcha();
        String id = IDUtil.uuid();
        String value = captcha.text();
        if (captcha.getCharType() - 1 == CaptchaType.arithmetic.ordinal() && value.contains(".")) {
            value = value.split("\\.")[0];
        }
        redisTemplate.opsForValue().set(id, value, properties.getExpiration(), TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("img", captcha.toBase64());
        map.put("value", value);
        return map;
    }
}
