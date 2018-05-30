package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.entity.CustomerAddress;

import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 10:54 2018-05-30
 * @Modified By:
 */
public interface CustomerAddressService {
    void  addCustomerAddress(CustomerAddress customerAddress);
    List<CustomerAddress> findByOpenid(String openid);
    CustomerAddress findById(Integer id);
    void deleteById(Integer id);
}
