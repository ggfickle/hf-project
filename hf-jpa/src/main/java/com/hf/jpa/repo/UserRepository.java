package com.hf.jpa.repo;

import com.hf.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:25
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
