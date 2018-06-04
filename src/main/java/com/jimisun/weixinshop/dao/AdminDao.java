package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 14:52 2018-06-04
 * @Modified By:
 */
public interface AdminDao  extends JpaRepository<Admin,Integer> {

    Admin findByUsername(String username);
}
