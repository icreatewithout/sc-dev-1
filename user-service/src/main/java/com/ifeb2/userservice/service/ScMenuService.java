package com.ifeb2.userservice.service;

import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.userservice.domain.ScMenu;
import com.ifeb2.userservice.domain.vo.ScMenuVo;

import java.util.List;

public interface ScMenuService {

    List<ScMenu> findUserList(ScMenu scMenu);

    List<ScMenuVo> findTreeList(ScMenu scMenu);

    List<ScMenuVo> findMenuListByUser(LoginUser loginUser);

    List<ScMenuVo> findMenuListByRouter(LoginUser loginUser);

    Long save(ScMenu scMenu);

    ScMenu getById(Long id);

    List<ScMenu> findList(ScMenu scMenu);

    void deleteMenu(Long id);
}
