package com.ifeb2.userservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifeb2.userservice.domain.ScMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScDeptVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long value;

    private String label;

    private String name;

    private Boolean disabled = false;

    private List<ScMenu> children;

}
