package com.ifeb2.userservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScMenuVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    private String name;

    private Boolean disabled = false;

    private String permission;

    private String icon;

    private String href;

    private String path;

    private List<ScMenuVo> children;

}
