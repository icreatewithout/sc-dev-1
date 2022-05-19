package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.domain.vo.UserVo;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.userservice.domain.ScLog;
import com.ifeb2.userservice.domain.vo.ScLogVo;
import com.ifeb2.userservice.repository.ScLogRepository;
import com.ifeb2.userservice.service.ScLogService;
import com.ifeb2.userservice.service.ScUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScLogServiceImpl extends BaseService<ScLog, ScLogRepository> implements ScLogService {

    private final ScUserService scUserService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveLog(ScLog scLog) {
        super.save(scLog);
    }

    @Override
    public Page<ScLog> selectPage(ScLogVo logVo) {
        ScLog scLog = new ScLog();
        BeanUtils.copyProperties(logVo, scLog);
        Page<ScLog> page = super.findPage(scLog);
        page.getContent().forEach(info -> {
            if (null != info.getCreateUser()) {
                UserVo scUser = scUserService.getUserById(info.getCreateUser());
                if (scUser != null) {
                    info.setUserName(scUser.getUserName());
                }
            }
        });
        return page;
    }

}
