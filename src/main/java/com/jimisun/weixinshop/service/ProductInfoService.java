package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.dto.CartDTO;
import com.jimisun.weixinshop.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    //通过商品id查询一个商品
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架的商品
     */
    List<ProductInfo> findUpAll();

    //查询所有商品（后台） 需要分页
    Page<ProductInfo> findAll(Pageable pageable);

    //添加商品的方法
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO>cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO>cartDTOList);


}
