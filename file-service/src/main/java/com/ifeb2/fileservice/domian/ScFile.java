package com.ifeb2.fileservice.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifeb2.scdevbase.domain.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sc_file")
public class ScFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8958687729666659389L;

    /**
     * 用户id
     */
    @Column(name = "user_id", length = 22)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 业务名称
     */
    @Column(name = "biz_name", length = 45)
    private String bizName;

    @Column(name = "f_path", length = 45)
    private String fPath;

    /**
     * 原文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 扩展名 例如 .jpg
     */
    @Column(name = "ext_name", length = 45)
    private String extName;

    /**
     * 文件类型
     */
    @Column(name = "content_type", length = 45)
    private String contentType;

    /**
     * 文件 bucket
     */
    @Column(name = "bucket", length = 45)
    private String bucket;

}
