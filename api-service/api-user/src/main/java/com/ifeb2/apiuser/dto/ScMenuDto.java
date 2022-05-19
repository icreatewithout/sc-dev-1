package com.ifeb2.apiuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScMenuDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    private String name;

    private String href;

    private String permission;

    private String icon;

    private String type;
    private String isShow;

}
