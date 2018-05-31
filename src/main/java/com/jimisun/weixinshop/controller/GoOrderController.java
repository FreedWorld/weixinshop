package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.CartItem;
import com.jimisun.weixinshop.entity.Customer;
import com.jimisun.weixinshop.entity.CustomerAddress;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.enums.CustomerAddressStatusEnum;
import com.jimisun.weixinshop.service.CustomerAddressService;
import com.jimisun.weixinshop.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:20 2018-05-31
 * @Modified By:
 */
@Controller
public class GoOrderController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CustomerAddressService customerAddressService;


    /**
     * 跳转到下单页面
     */
    @RequestMapping({"/order/addorder","/order/addorder.html"})
    public ModelAndView goestablished(@RequestParam(required = false) String productId,
                                      HttpSession session,
                                      Map<String,Object> map){
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }

        //根据用户查找用户选中的收货地址
        CustomerAddress customerAddress = customerAddressService.findByStatus(CustomerAddressStatusEnum.FINISHEND.getCode());
        if(customerAddress!=null){
            System.out.println(customerAddress.toString());
            map.put("customerAddress",customerAddress);
        }


        if(productId!=null){
            //通过productId获取商品
            List<CartItem> cartProductList = new ArrayList<>();
            CartItem cartItem = new CartItem();
            ProductInfo productInfo = productInfoService.findOne(productId);
            cartItem.setProductInfo(productInfo);
            cartItem.setCount(1);
            cartProductList.add(cartItem);
            map.put("carts",cartProductList);
            map.put("totalPrice",productInfo.getProductPrice());
            //跳转到下单页面
            return new ModelAndView("before/established",map);
        }else{
            Map<String,Integer> carts = (Map<String,Integer>) session.getAttribute("carts");
            BigDecimal totalPrice = new BigDecimal(0);
            if(carts!=null){
                List<CartItem> cartProductList = new ArrayList<>();
                for(Map.Entry<String,Integer> entry :carts.entrySet()){
                    ProductInfo productInfo = productInfoService.findOne(entry.getKey());
                    totalPrice=productInfo.getProductPrice().add(totalPrice).multiply(new BigDecimal(entry.getValue()));
                    CartItem cartItem = new CartItem();
                    cartItem.setProductInfo(productInfo);
                    cartItem.setCount(entry.getValue());
                    cartProductList.add(cartItem);
                }

                map.put("carts",cartProductList);
                map.put("totalPrice",totalPrice);
                return new ModelAndView("before/established",map);

            }

            List<CartItem> cartProductList = new ArrayList<>();
            map.put("carts",cartProductList);
            map.put("totalPrice",0);
            map.put("message","购物车为null");
            return new ModelAndView("before/shopping_cart",map);

        }


    }


    /**
     * 确认订单
     * @param productId
     * @param session
     * @param map
     * @return
     */
    @RequestMapping({"/order/ensureorder","/order/ensureorder.html"})
    public ModelAndView ensureorder(@RequestParam(required = false) String productId,
                                      HttpSession session,
                                      Map<String,Object> map){
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }
        //根据用户查找用户选中的收货地址
        CustomerAddress customerAddress = customerAddressService.findByStatus(CustomerAddressStatusEnum.FINISHEND.getCode());
        if(customerAddress!=null){
            System.out.println(customerAddress.toString());
            map.put("customerAddress",customerAddress);
        }

        if(productId!=null){
            //通过productId获取商品
            List<CartItem> cartProductList = new ArrayList<>();
            CartItem cartItem = new CartItem();
            ProductInfo productInfo = productInfoService.findOne(productId);
            cartItem.setProductInfo(productInfo);
            cartItem.setCount(1);
            cartProductList.add(cartItem);
            map.put("carts",cartProductList);
            map.put("totalPrice",productInfo.getProductPrice());
            //跳转到下单页面
            return new ModelAndView("before/established_date",map);
        }else{
            Map<String,Integer> carts = (Map<String,Integer>) session.getAttribute("carts");
            BigDecimal totalPrice = new BigDecimal(0);
            if(carts!=null){
                List<CartItem> cartProductList = new ArrayList<>();
                for(Map.Entry<String,Integer> entry :carts.entrySet()){
                    ProductInfo productInfo = productInfoService.findOne(entry.getKey());
                    totalPrice=productInfo.getProductPrice().add(totalPrice).multiply(new BigDecimal(entry.getValue()));
                    CartItem cartItem = new CartItem();
                    cartItem.setProductInfo(productInfo);
                    cartItem.setCount(entry.getValue());
                    cartProductList.add(cartItem);
                }

                map.put("carts",cartProductList);
                map.put("totalPrice",totalPrice);
                return new ModelAndView("before/established_date",map);

            }

            List<CartItem> cartProductList = new ArrayList<>();
            map.put("carts",cartProductList);
            map.put("totalPrice",0);
            map.put("message","购物车为null");
            return new ModelAndView("before/shopping_cart",map);

        }


    }



}
