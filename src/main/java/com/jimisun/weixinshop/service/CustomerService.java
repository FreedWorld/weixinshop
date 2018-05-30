package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.entity.Customer;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 13:15 2018-05-29
 * @Modified By:
 */
public interface CustomerService {

    void register(Customer customer);

    Customer findByUsername(Customer customer);
}
