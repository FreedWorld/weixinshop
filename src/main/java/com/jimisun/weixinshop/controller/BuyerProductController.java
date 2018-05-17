package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.service.ProductCategoryService;
import com.jimisun.weixinshop.service.ProductInfoService;
import com.jimisun.weixinshop.utils.ResultVoUtil;
import com.jimisun.weixinshop.vo.ProductInfoVo;
import com.jimisun.weixinshop.vo.ProductListVo;
import com.jimisun.weixinshop.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jimisun
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVo list() throws Exception{
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询类目（一次性查询）
        //lambda表达式
        List<Integer> categoryTypeList =
                productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //查询类目的属性
        List<ProductCategory>productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        /**
         * 封装数据
         */
        //返回页面总对象
        //商品分类集合
        List<ProductListVo> productListVoList= new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList){
            //商品分类
            ProductListVo productListVo = new ProductListVo();
            productListVo.setCategoryType(productCategory.getCategoryType());
            productListVo.setCategoryName(productCategory.getCategoryName());


            List<ProductInfoVo>productInfoVoList = new ArrayList<>();
            //商品
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    //创建一个商品Vo
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);

                }
            }

            productListVo.setProductInfoVoList(productInfoVoList);
            productListVoList.add(productListVo);
        }

        return ResultVoUtil.sucess(productListVoList);
    }
}
