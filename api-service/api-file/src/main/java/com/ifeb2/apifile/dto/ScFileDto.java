package com.ifeb2.apifile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScFileDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    private String bizName;

    private String fPath;

    private String fileName;

    private String extName;

    private String contentType;

    private String bucket;

}
