package com.ifeb2.userservice.service;

import com.ifeb2.userservice.domain.ScLog;
import com.ifeb2.userservice.domain.vo.ScLogVo;
import org.springframework.data.domain.Page;

public interface ScLogService {

    void saveLog(ScLog scLog);

    ScLog getById(Long id);

    Page<ScLog> selectPage(ScLogVo logVo);

}
