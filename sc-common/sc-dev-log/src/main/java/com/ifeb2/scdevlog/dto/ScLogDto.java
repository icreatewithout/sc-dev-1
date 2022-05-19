package com.ifeb2.scdevlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScLogDto {

    private String descriptions;

    private String bizName;

    private String method;

    private String params;

    private String result;

    private String logType;

    private String requestIp;

    private String address;

    private String browser;

    private Long time;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUser;

    private byte[] exceptionDetail;


}
