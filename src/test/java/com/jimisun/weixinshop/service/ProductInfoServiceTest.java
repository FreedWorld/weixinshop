package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.ProductInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class ProductInfoServiceTest extends WeixinshopApplicationTests {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    @Transactional
    public void findAll()throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage =  productInfoService.findAll(request);
        System.out.print(productInfoPage.getTotalElements());
        System.out.print(productInfoPage.getTotalPages());


    }
}