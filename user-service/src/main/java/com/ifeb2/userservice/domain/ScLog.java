package com.ifeb2.userservice.domain;


import com.ifeb2.scdevbase.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_log")
public class ScLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1;

    @Column(name = "descriptions", length = 255)
    private String descriptions;

    @Column(name = "method")
    private String method;

    @Column(name = "biz_name",length = 45)
    private String bizName;

    @Column(name = "params",columnDefinition = "TEXT")
    private String params;

    @Column(name = "result",columnDefinition = "TEXT")
    private String result;

    @Column(name = "log_type", length = 10)
    private String logType;

    @Column(name = "request_ip", length = 18)
    private String requestIp;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "browser", length = 20)
    private String browser;

    @Column(name = "time")
    private Long time;

    @Column(name = "exception_detail", length = 1024)
    private byte[] exceptionDetail;

    @Transient
    private String userName;
}
