package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.exception.SellException;

import java.util.List;

public interface ProductCategoryService {

    //查询一个商品分类
    ProductCategory findOne(Integer categoryId) throws SellException;

    //查询所有商品分类
    List<ProductCategory> findAll() ;

    //通过指定的商品分类id获取商品分类
    List<ProductCategory>findByCategoryTypeIn(List<Integer>categoryTypeList);

    //新增商品分类
    ProductCategory save(ProductCategory productCategory);



}
