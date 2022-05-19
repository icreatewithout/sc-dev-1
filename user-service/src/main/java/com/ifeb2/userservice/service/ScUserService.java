package com.ifeb2.userservice.service;

import com.ifeb2.scdevbase.domain.vo.UserVo;
import com.ifeb2.userservice.domain.ScUser;
import com.ifeb2.userservice.domain.vo.RepassVo;
import com.ifeb2.userservice.domain.vo.ScUserVo;
import org.springframework.data.domain.Page;

public interface ScUserService {

    ScUser getById(Long id);

    ScUser getOne(ScUser scUser);

    ScUser create(UserVo userVo);

    void update(UserVo userVo);

    Page<ScUser> selectPage(ScUserVo userVo);

    UserVo getUserById(Long id);

    void deleteByIds(String ids);

    void updatePasswd(Long id, String pass);

    void changePass(RepassVo repassVo);

    void seataTest(String val);
}
