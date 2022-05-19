package com.ifeb2.scdevbase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifeb2.scdevbase.annotation.EsFields;
import com.ifeb2.scdevbase.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "iDUtil")
    @GenericGenerator(name = "iDUtil", strategy = "com.ifeb2.scdevcore.component.AutoIdentityGenerator")
    @Column(name = "id", length = 22)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @EsFields
    public Long id;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    @CreatedBy
    public Long createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @EsFields
    public Date createTime;


    /**
     * 更新人
     */
    @Column(name = "update_by")
    @LastModifiedBy
    public Long updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @EsFields
    public Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    public String remark;

    /**
     * 删除标记
     */
    @Column(name = "del", length = 2, nullable = false)
    @JsonIgnore
    public String del = Constants.NOT_DEl;

    /**
     * 分页参数
     *
     * @return
     */
    @Transient
    @JsonIgnore
    public Integer pageSize = 10;

    @Transient
    @JsonIgnore
    public Integer pageNum = 1;

}
