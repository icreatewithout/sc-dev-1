package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.userservice.domain.ScMenu;
import com.ifeb2.userservice.repository.ScMenuRepository;
import com.ifeb2.userservice.service.ScMenuService;
import com.ifeb2.scdevbase.utils.RecurseUtil;
import com.ifeb2.userservice.domain.vo.ScMenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScMenuServiceImpl extends BaseService<ScMenu, ScMenuRepository> implements ScMenuService {

    @Override
    public List<ScMenu> findUserList(ScMenu scMenu) {
        List<ScMenu> list = this.findList(scMenu);
        return RecurseUtil.parents(list);
    }

    @Override
    public List<ScMenuVo> findTreeList(ScMenu scMenu) {
        List<ScMenu> list = this.findList(scMenu);
        List<ScMenuVo> voList = new ArrayList<>();
        list.forEach(m -> {
            ScMenuVo vo = new ScMenuVo();
            BeanUtils.copyProperties(m, vo);
            if ("2".equals(m.getIsShow())) {
                vo.setDisabled(true);
            }
            voList.add(vo);
        });
        return RecurseUtil.parents(voList);
    }

    @Override
    public List<ScMenuVo> findMenuListByUser(LoginUser loginUser) {
        List<ScMenu> list;
        if (loginUser.getIsSuper()) {
            list = this.findList(new ScMenu());
        } else {
            list = repository.findListForUser(loginUser.getUserId());
        }

        List<ScMenuVo> voList = new ArrayList<>();
        list.forEach(m -> {
            ScMenuVo vo = new ScMenuVo();
            BeanUtils.copyProperties(m, vo);
            voList.add(vo);
        });
        return RecurseUtil.parents(voList);
    }

    @Override
    public List<ScMenuVo> findMenuListByRouter(LoginUser loginUser) {

        List<ScMenu> list;
        if (loginUser.getIsSuper()) {
            list = this.findList(new ScMenu());
        } else {
            list = repository.findListForUser(loginUser.getUserId());
        }

        List<ScMenuVo> voList = new ArrayList<>();
        list.forEach(m -> {
            if (StringUtils.isNotBlank(m.getPath())&& StringUtils.isNotBlank(m.getHref())){
                ScMenuVo vo = new ScMenuVo();
                BeanUtils.copyProperties(m, vo);
                voList.add(vo);
            }
        });

        return voList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long id) {
        ScMenu scMenu = this.getById(id);

        List<ScMenu> nextList = this.findList(ScMenu.builder().parentId(scMenu.getId()).build());
        if (nextList.size() > 0) {
            for (ScMenu menu : nextList) {
                List<ScMenu> menuList = this.findList(ScMenu.builder().parentId(menu.getId()).build());
                if (menuList.size() > 0) {
                    menuList.forEach(info -> this.deleteById(info.getId()));
                }
            }
            nextList.forEach(info -> this.deleteById(info.getId()));
        }
        this.deleteById(id);
    }
}
