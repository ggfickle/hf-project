package com.hf.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    private String remark;


    public UserEntity(String name, LocalDateTime birthday, String remark) {
        this.name = name;
        this.birthday = birthday;
        this.remark = remark;
    }
}
