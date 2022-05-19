package com.ifeb2.userservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScUserVo {

    private String status;

    private String userName;

    private String phone;

    private Integer pageNum;
    private Integer pageSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
}
