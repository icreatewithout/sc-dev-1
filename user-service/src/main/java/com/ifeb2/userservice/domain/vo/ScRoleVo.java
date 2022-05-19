package com.ifeb2.userservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScRoleVo {

    public Integer pageSize = 10;
    public Integer pageNum = 1;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;
    private String code;
    private String status;
    private Integer sort;
    private String checkedKeys;

}
