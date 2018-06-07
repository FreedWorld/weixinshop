package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.ProductCategory;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.form.ProductForm;
import com.jimisun.weixinshop.service.ProductCategoryService;
import com.jimisun.weixinshop.service.ProductInfoService;
import com.jimisun.weixinshop.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:14 2018-05-19
 * @Modified By:
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map, HttpSession session) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);

    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map,HttpSession session) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }



    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map,HttpSession session) {
        try {
            productInfoService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map,HttpSession session) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
       List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    @GetMapping("/update")
    public ModelAndView update(@RequestParam(value = "productId", required = true) String productId,
                              Map<String, Object> map,HttpSession session) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/update", map);
    }

    /**
     * 保存更新商品操作
     * @param form  表单提交对象
     * @param bindingResult  校验结果
     * @param map  返回对象
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object>map,HttpSession session){
        //判断校验结果
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            if(!StringUtils.isEmpty(form.getProductId())){
                //数据的拷贝
                //一定要先进行查询，否则会在数据拷贝的时候copynull值
                productInfo = productInfoService.findOne(form.getProductId());
            }else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form,productInfo);

        //调用service方法
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        //返回结果
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);

    }



}
