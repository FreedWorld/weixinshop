package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    /**
     * 根据传入categoryType查询出所有商品
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory>findByCategoryTypeIn(List<Integer>categoryTypeList);

}
