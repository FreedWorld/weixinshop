package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class ProductInfoDaoTest extends WeixinshopApplicationTests {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456788");
        productInfo.setProductName("辣条");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("卫龙辣条");
        productInfo.setProductIcon("xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(4);
        productInfoDao.save(productInfo);
    }

    @Test
    public void findByProductStatus() {

        List<ProductInfo> productInfoList=  productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());

    }
}