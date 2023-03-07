package com.hf.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:24
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    private String remark;

    /**
     * ALL：所有的持久化操作
     * PERSIST：只有新增才会执行关联操作
     * MERGE：只有合并才会执行关联操作
     * REMOVE： 只有删除才会执行关联操作
     * 默认查询都走关联操作
     * fetch 设置是否懒加载
     * EAGER：立即模式
     * LAZY：懒加载模式（只有用到对象才会进行查询）
     * orphanRemoval：关联移除
     * mappedBy：双向一对一时使用，将外键约束执行交给另一方维护（通常值 = 另一方关联属性名）
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userEntity")
    @JoinColumn(name = "account_id")
    // 设置逻辑外键字段名 ;
    private AccountEntity accountEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Message> messageList;

    /**
     * 中间表需要通过JoinTable维护外键,
     * name指定中间表名称,
     * joinColumns设置本表的外键名称,
     * inverseJoinColumns设置关联表的外键名称
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role_relation",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roleList;

    @Version
    private int version;

    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime = new Date();

    public UserEntity(String name, LocalDateTime birthday, String remark) {
        this.name = name;
        this.birthday = birthday;
        this.remark = remark;
    }
}
