package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.*;
import com.jimisun.weixinshop.service.Ad1Service;
import com.jimisun.weixinshop.service.NoticeService;
import com.jimisun.weixinshop.service.ProductCategoryService;
import com.jimisun.weixinshop.service.ProductInfoService;
import com.jimisun.weixinshop.vo.ProductInfoVo;
import com.jimisun.weixinshop.vo.ProductListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private Ad1Service ad1Service;


    /**
     * GO主页公告
     * 查询分类
     * 查询公告
     * 查询热销商品
     * 猜你喜欢
     * @return
     */
    @GetMapping({"/index","/index.html","/"})
    public ModelAndView index( Map<String, Object> map){

        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //查询类目的属性
        List<ProductCategory>productCategoryList = productCategoryService.findAll();
        /**
         * 封装数据
         */
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
        map.put("categoryResult",productCategoryList);

        //查询最新公告
        Notice notice = noticeService.findNew();
        map.put("notice",notice);

        //查询IndexHotVo
        PageRequest request = new PageRequest(0, 12);
        List<ProductInfo> resultList= productInfoService.findIndexHotVo(request);
        List<List<ProductInfo>> lists = new ArrayList<>();
        List<ProductInfo>list1 = new ArrayList<>();
        List<ProductInfo>list2 = new ArrayList<>();
        List<ProductInfo>list3 = new ArrayList<>();
        for(int i=0;i<resultList.size();i++){
            if(i<4){
               list1.add(resultList.get(i));
            }else if(i>3&&i<8){
                list2.add(resultList.get(i));
            }else if(i>7&&i<12) {
                list3.add(resultList.get(i));
            }
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        map.put("indexhotvo",lists);

        //轮播图大广告位
        List<Ad1>ad1List = ad1Service.findAd1All();
        map.put("ad1list",ad1List);

        //猜你喜欢
        PageRequest request1 = new PageRequest(0, 12);
        List<ProductInfo>youlikeResult= productInfoService.findIndexyoulike(request1);
        map.put("youlike",youlikeResult);


        return new ModelAndView("before/index",map);
    }

    /**
     * GO所有分类页面
     * @return
     */
    @GetMapping({"/allcategory.html","/allcategory"})
    public ModelAndView allCategory(Map<String, Object> map){
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("before/allcategory",map);
    }

    /**
     * GO一个分类页面
     * @return
     */
    @GetMapping({"/category/list.html","/category/list"})
    public ModelAndView categoryList(@RequestParam(value = "categoryType",required = false) Integer categoryType,
                                     @RequestParam(value = "sousuo",required = false) String sousuo,
                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "size",required = false,defaultValue = "12") Integer size,
                                     Map<String, Object> map){
        if(categoryType==null&&StringUtils.isEmpty(sousuo)){
            return new ModelAndView("redirect:/allcategory.html",map);
        }

        if(categoryType!=null){
            //根据categoryType查询分类
            List<ProductInfo> list = productInfoService.findByCategoryType(categoryType);
            map.put("productList",list);

            //查询分类信息
            ProductCategory category = productCategoryService.findOne(categoryType);
            map.put("idxcategory",category);

            //查询所有分类
            List<ProductCategory> categoryList = productCategoryService.findAll();
            map.put("categoryList",categoryList);


            return new ModelAndView("before/categorylist",map);
        }

        if(!StringUtils.isEmpty(sousuo)){
            //通过关键字查询列表
            List<ProductInfo> list= productInfoService.findBySousuo(sousuo);
            map.put("productList",list);


            //查询分类信息
            ProductCategory category = new ProductCategory();
            category.setCategoryName("搜索结果");
            map.put("idxcategory",category);


            //查询所有分类
            List<ProductCategory> categoryList = productCategoryService.findAll();
            map.put("categoryList",categoryList);

            return new ModelAndView("before/categorylist",map);
        }

        return new ModelAndView("redirect:/allcategory.html",map);


    }

    /**
     * 根据productId查询一个商品的详情
     * @param productId
     */
    @GetMapping({"/product/info.html","/product/info"})
    public ModelAndView productInfo(@RequestParam("productId") String productId, Map<String, Object> map,
                                    HttpSession session){
        Integer cartSize = 0;
        Map<Integer,Integer> carts = (Map<Integer,Integer>)session.getAttribute("carts");
        if(carts!=null){
            cartSize=carts.size();
        }
        ProductInfo productInfo = productInfoService.findOne(productId);
        map.put("productInfo",productInfo);
        map.put("cartSize",cartSize);
        return new ModelAndView("/before/product_info",map);
    }

    /**
     * 跳转搜索页面
     * @param map
     * @return
     */
    @GetMapping({"/search.html","/search"})
    public ModelAndView searchProduct(Map<String, Object> map){
        //查询所有分类
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("/before/search",map);
    }

    /**
     * 跳转登录页面
     * @param map
     * @return
     */
    @GetMapping({"/login.html","/login"})
    public ModelAndView gologin(Map<String, Object> map){

        return new ModelAndView("/before/login",map);
    }

    /**
     * 跳转注册页面
     * @param map
     * @return
     */
    @GetMapping({"/register.html","/register"})
    public ModelAndView goregister(Map<String, Object> map){

        return new ModelAndView("/before/register",map);
    }

    /**
     * 跳转个人中心
     * @param session
     * @param map
     * @return
     */
    @GetMapping({"/center.html","/center"})
    public ModelAndView gocenter(HttpSession session,
                                 Map<String, Object> map){
        Customer customer = (Customer)session.getAttribute("existUser");
        if(customer==null){
            return new ModelAndView("redirect:/login.html",map);
        }
        return new ModelAndView("before/center",map);
    }

    /**
     * 跳转订单
     * @param session
     * @param map
     * @return
     */
    @GetMapping({"/orders.html","/orders"})
    public ModelAndView orders(HttpSession session,
                                 Map<String, Object> map){
        Customer customer = (Customer)session.getAttribute("existUser");
        if(customer==null){
            return new ModelAndView("/before/login",map);
        }
        return new ModelAndView("/before/orders",map);
    }






}
