package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.ProductInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
    List<ProductInfo> findByCategoryType(Integer categoryType);
    List<ProductInfo> findByCategoryType(Integer categoryType, Pageable pageable);
}
