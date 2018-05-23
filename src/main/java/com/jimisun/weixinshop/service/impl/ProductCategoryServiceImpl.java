package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.ProductCategoryDao;
import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) throws SellException {

        if(productCategoryDao.getOne(categoryId)==null){
            throw new  SellException(ResultVoCodeEnum.PRODUCT_CATEGORY_NOT_EXIST);
        }

        return productCategoryDao.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }
}
