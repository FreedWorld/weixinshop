package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.form.CategoryForm;
import com.jimisun.weixinshop.service.ProductCategoryService;
import com.jimisun.weixinshop.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jimisun
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    /**
     * 跳转更新页面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/update")
    public ModelAndView update(@RequestParam(value = "categoryId", required = true) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }

        return new ModelAndView("category/update", map);
    }

    /**
     * 保存/新增
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        List<ProductInfo>productInfoList = new ArrayList<>();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
                productInfoList = productInfoService.findByCategoryType(productCategory.getCategoryType());
            }
            BeanUtils.copyProperties(form, productCategory);
            //设置分类属性
            categoryService.save(productCategory);
            //设置原来商品的分类为新分类
            for(ProductInfo productInfo :productInfoList){
                productInfo.setCategoryType(form.getCategoryType());
                productInfoService.save(productInfo);
            }
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }


}
