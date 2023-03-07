package com.hf.jpa.repo;

import com.hf.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/6 23:04
 */
public interface AccountRepo extends JpaRepository<AccountEntity, Integer> {
}
