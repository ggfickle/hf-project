package com.hf.jpa.repo;

import com.hf.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:25
 */
public interface UserMethodNameRepository extends JpaRepository<UserEntity, Long> {
    // --------------使用规定方法名查询--------------
    boolean existsByName(String name);

   List<UserEntity> findByName(String name);

   int countByName(String name);

   List<UserEntity> findByIdBetween(Long id1, Long id2);

   List<UserEntity> findByIdGreaterThanAndIdLessThan(Long id1, Long id2);

   List<UserEntity> findByIdIn(List<Long> idList);

    List<UserEntity> findByBirthdayAfterAndBirthdayBefore(LocalDateTime date1, LocalDateTime date2);
}
