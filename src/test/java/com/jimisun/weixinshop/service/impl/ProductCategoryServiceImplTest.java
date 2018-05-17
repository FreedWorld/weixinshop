package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.service.ProductCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ProductCategoryServiceImplTest extends WeixinshopApplicationTests {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    @Transactional
    public void findOne() throws Exception{
        ProductCategory productCategory = productCategoryService.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void findAll()throws Exception {
    }

    @Test
    public void findByCategoryTypeIn()throws Exception {
    }

    @Test
    public void save()throws Exception {
    }
}