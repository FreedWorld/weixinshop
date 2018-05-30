package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.CartItem;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:20 2018-05-28
 * @Modified By:
 */
@Controller
public class GoCarController {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping({"/cart/list","/cart/list.html"})
    public ModelAndView cartList(Map<String,Object> map,
                                 HttpSession session){
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
            return new ModelAndView("before/shopping_cart",map);
        }

        List<CartItem> cartProductList = new ArrayList<>();
        map.put("carts",cartProductList);
        map.put("totalPrice",0);
        return new ModelAndView("before/shopping_cart",map);

    }

    @RequestMapping("/cart/addcart")
    public ModelAndView addCart(@RequestParam(value = "productId",required = true) String productId,
                                HttpSession session){
        Map<String,Integer>carts = (Map<String,Integer>) session.getAttribute("carts");
        if(carts!=null){
            if(carts.containsKey(productId)){
                carts.put(productId,carts.get(productId)+1);
                session.setAttribute("carts",carts);
                return new ModelAndView("redirect:/cart/list");
            }else {
                carts.put(productId,1);
                session.setAttribute("carts",carts);
                return new ModelAndView("redirect:/cart/list");
            }
        }else {
            session.setAttribute("carts",new HashMap<>());
            Map<String,Integer>newCarts = (Map<String,Integer>) session.getAttribute("carts");
            newCarts.put(productId,1);
            session.setAttribute("carts",newCarts);
            return new ModelAndView("redirect:/cart/list");
        }

    }

    @RequestMapping("/cart/subtract")
    public ModelAndView subtractCart(@RequestParam(value = "productId") String productId,
                                     HttpSession session){
        Map<String,Integer>carts = (Map<String,Integer>) session.getAttribute("carts");
        if(carts.containsKey(productId)){
            carts.put(productId,carts.get(productId)-1);
            if(carts.get(productId)<=0){
                carts.remove(productId);
            }
        }
        return new ModelAndView("redirect:/cart/list");
    }





}
