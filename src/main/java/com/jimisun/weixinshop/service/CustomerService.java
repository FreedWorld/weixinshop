package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 13:15 2018-05-29
 * @Modified By:
 */
public interface CustomerService {

    void save(Customer customer);

    Customer findByUsername(Customer customer);

    Page<Customer> finAll(Pageable pageable);
}
