package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xf
 * @create 2020-03-02-19:28
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {
    public User findByMobile(String mobile);
}
