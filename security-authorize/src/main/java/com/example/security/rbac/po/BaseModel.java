package com.example.security.rbac.po;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@MappedSuperclass
@Data
public class BaseModel implements Serializable{
    /**
     * 数据库主键
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 审计日志，记录条目创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;
    /**
     * 审计日志，记录条目创建人
     */
    @CreatedBy
    private String createdBy;
    /**
     * 审计日志，记录条目更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedTime;
    /**
     * 审计日志，记录条目更新人
     */
    @LastModifiedBy
    private String lastModifiedBy;
}
