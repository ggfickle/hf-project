package com.hf.jpa.entity;

import com.hf.jpa.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/7 23:10
 */
@Data
@Entity
@Table(name = "user_role")
@Accessors(chain = true)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
