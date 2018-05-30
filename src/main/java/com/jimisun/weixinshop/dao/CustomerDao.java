package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:30 2018-05-29
 * @Modified By:
 */
public interface CustomerDao extends JpaRepository<Customer,String> {

    Customer findByUsername(String username);
}
