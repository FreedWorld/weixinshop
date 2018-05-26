package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.dto.CartDTO;
import com.jimisun.weixinshop.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    /**通过id查询一个商品**/
    ProductInfo findOne(String productId);


    /**通过type查询商品**/
    List <ProductInfo> findByCategoryType(Integer categoryType);
    /**
     * 查询所有在架的商品
     */
    List<ProductInfo> findUpAll();

    /**查询所有商品需要分页**/
    Page<ProductInfo> findAll(Pageable pageable);

    /**添加商品**/
    ProductInfo save(ProductInfo productInfo);

    /**增加库存**/
    void increaseStock(List<CartDTO>cartDTOList);

    /**减少库存**/
    void decreaseStock(List<CartDTO>cartDTOList);

    /**上架商品**/
    ProductInfo onSale(String producId);

    /**下架商品**/
    ProductInfo offSale(String productId);

    /**查询首页indexHotVo**/
    List<ProductInfo> findIndexHotVo(Pageable pageable);

    /**查询首页indexHotVo**/
    List<ProductInfo> findIndexyoulike(Pageable pageable);


}
