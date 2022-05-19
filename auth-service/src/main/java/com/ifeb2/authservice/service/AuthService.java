package com.ifeb2.authservice.service;

import com.ifeb2.scdevbase.domain.vo.AuthVo;

import java.util.Map;

public interface AuthService {

    String auth(AuthVo authVo);

    String authForSBA(AuthVo authVo);

    Map<String,Object> getCode();

}
