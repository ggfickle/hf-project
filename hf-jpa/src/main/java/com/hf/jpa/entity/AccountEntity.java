package com.hf.jpa.entity;

import com.hf.jpa.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/6 22:42
 */
@Entity
@Data
@Table(name = "user_account")
public class AccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private UserEntity userEntity;
}
