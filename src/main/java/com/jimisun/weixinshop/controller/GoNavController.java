package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.service.ProductCategoryService;
import com.jimisun.weixinshop.service.ProductInfoService;
import com.jimisun.weixinshop.vo.ProductInfoVo;
import com.jimisun.weixinshop.vo.ProductListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:55 2018-05-17
 * @Modified By:
 */
@Controller
@RequestMapping("")
public class GoNavController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;


    /**
     * GO主页
     * @return
     */
    @GetMapping({"/index.html","/index",""})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();

        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询类目（一次性查询）
        //lambda表达式
        //List<Integer> categoryTypeList =
         //       productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //查询类目的属性
        List<ProductCategory>productCategoryList = productCategoryService.findAll();

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


        modelAndView.addObject("result",productListVoList);

        modelAndView.setViewName("index");

        return modelAndView;
    }

    /**
     * GO所有分类页面
     * @return
     */
    @GetMapping({"/allcategory.html","/allcategory"})
    public ModelAndView allCategory(){
        ModelAndView modelAndView = new ModelAndView();
        List<ProductCategory> categoryList = productCategoryService.findAll();
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("category");
        return modelAndView;
    }

    /**
     * GO一个分类页面
     * @return
     */
    @GetMapping({"/categorylist{categoryType}.html","/category{categoryType}"})
    public ModelAndView categoryList(@PathVariable("categoryType") Integer categoryType){
        ModelAndView modelAndView = new ModelAndView();


        //获取所有分类
        List<ProductCategory> categoryList = productCategoryService.findAll();
        modelAndView.addObject("categoryList",categoryList);


        modelAndView.setViewName("categorylist");
        return modelAndView;
    }

}
