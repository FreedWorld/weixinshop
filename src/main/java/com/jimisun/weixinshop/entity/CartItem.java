package com.jimisun.weixinshop.entity;

import java.math.BigDecimal;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:39 2018-05-28
 * @Modified By:
 */
public class CartItem {

    //购物车数量

    //购物车项总价

    //购物车商品

    private ProductInfo productInfo;

    private Integer count;

    private BigDecimal totalPrice;


    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return productInfo.getProductPrice().multiply(new BigDecimal(count));
    }


}
