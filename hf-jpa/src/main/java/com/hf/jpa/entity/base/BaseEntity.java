package com.hf.jpa.entity.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;
}
