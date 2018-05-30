package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.CustomerAddressDao;
import com.jimisun.weixinshop.entity.CustomerAddress;
import com.jimisun.weixinshop.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 10:55 2018-05-30
 * @Modified By:
 */

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    private CustomerAddressDao customerAddressDao;


    @Override
    public void addCustomerAddress(CustomerAddress customerAddress) {
        customerAddressDao.save(customerAddress);
    }

    @Override
    public List<CustomerAddress> findByOpenid(String openid) {
        return customerAddressDao.findByOpenid(openid);
    }

    @Override
    public CustomerAddress findById(Integer id) {
        return customerAddressDao.getOne(id);
    }

    @Override
    public void deleteById(Integer id) {
        customerAddressDao.deleteById(id);
    }
}
