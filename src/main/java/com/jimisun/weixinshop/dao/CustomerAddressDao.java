package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 10:51 2018-05-30
 * @Modified By:
 */
public interface CustomerAddressDao extends JpaRepository<CustomerAddress,Integer> {

    List<CustomerAddress> findByOpenid(String openid);
    CustomerAddress findByStatus(Integer status);


}
