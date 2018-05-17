package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 测试ProductCategoryDao
 */
public class ProductCategoryDaoTest extends WeixinshopApplicationTests {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    @Transactional
    public void getOneTest(){

        ProductCategory productCategory = productCategoryDao.getOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生");
        productCategory.setCategoryType(4);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(6);
        productCategory.setCategoryName("男生");
        productCategory.setCategoryType(5);
        productCategoryDao.save(productCategory);
    }


    @Test
    public void findByCategoryTypeIn() {
        List<Integer> typeList = Arrays.asList(2, 3);
        List<ProductCategory> productCategoryList = productCategoryDao.findByCategoryTypeIn(typeList);
        Assert.assertNotEquals(0, productCategoryList.size());
    }
}