package com.ifeb2.userservice.domain.vo;

import lombok.Data;

@Data
public class ScLogVo {

    private String bizName;

    private String logType;

    private String requestIp;

    private Integer pageNum;
    private Integer pageSize;

}
