package com.hf.jpa.repo;

import com.hf.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:25
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 参数设置方式
     * 1：索引：？数字
     * 2：具体名称： ：参数名，要结合@Param注解指定参数的名字
     * @param name
     * @return
     */
    @Query("from UserEntity where name = :name")
    UserEntity findByName(@Param("name") String name);


    @Transactional
    @Modifying // 通知jpa这里时增删改操作
    @Query("update UserEntity t set t.name = :name where t.id = :id")
    int updateUser(@Param("name") String name, @Param("id") Long id);


    @Transactional
    @Modifying
    @Query("delete from UserEntity t where t.id = ?1")
    int deleteCustomer(Long id);

    /**
     * JPQL不支持插入,只支持insert into ... select 伪插入的方式
     */
    @Transactional
    @Modifying
    @Query("insert into UserEntity(name, birthday) select t.name, t.birthday from UserEntity t where t.id = ?1")
    int insertUserBySelect(Long id);

    /**
     * 使用原生sql
     * @return
     */
    @Query(value =  "select t.* from user_info t where t.name = ?1", nativeQuery = true)
    UserEntity findByNameBySql(String name);
}
