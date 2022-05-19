package com.ifeb2.apiuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScRoleDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private String code;

    private String status;

    private Integer sort;

    private List<ScMenuDto> permissions = new ArrayList<>();
}
