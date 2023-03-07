package com.hf.jpa.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/7 22:29
 */
@Data
@Entity
@Table(name = "user_message")
@Accessors(chain = true)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String info;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
