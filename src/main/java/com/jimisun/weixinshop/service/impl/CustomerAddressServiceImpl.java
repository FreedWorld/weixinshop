package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.CustomerAddressDao;
import com.jimisun.weixinshop.entity.CustomerAddress;
import com.jimisun.weixinshop.enums.CustomerAddressStatusEnum;
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

    @Override
    public void ensurecar(Integer addressid,String openid) {

        //循环遍历将他们的选中状态重置为null
        List<CustomerAddress>customerAddressList= customerAddressDao.findByOpenid(openid);
        for(CustomerAddress customerAddress1 : customerAddressList){
            customerAddress1.setStatus(CustomerAddressStatusEnum.NEW.getCode());
            customerAddressDao.save(customerAddress1);
        }

        //设置当前addressid为ture
        CustomerAddress customerAddress = customerAddressDao.getOne(addressid);
        customerAddress.setStatus(CustomerAddressStatusEnum.FINISHEND.getCode());
        customerAddressDao.save(customerAddress);
    }

    @Override
    public CustomerAddress findByOpenidAndStatus(String openid,Integer status) {
        CustomerAddress customerAddress = customerAddressDao.findByOpenidAndStatus(openid,status);
        return customerAddress;
    }
}
