package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.entity.*;
import com.jimisun.weixinshop.enums.CustomerAddressStatusEnum;
import com.jimisun.weixinshop.enums.OrderStatusEnum;
import com.jimisun.weixinshop.service.CustomerAddressService;
import com.jimisun.weixinshop.service.OrderService;
import com.jimisun.weixinshop.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private OrderService orderService;




    /**
     * 跳转到下单页面
     */
    @RequestMapping({"/order/addorder", "/order/addorder.html"})

    public ModelAndView goestablished(@RequestParam(required = false) String productId,
                                      HttpSession session,
                                      Map<String, Object> map) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        //根据用户查找用户选中的收货地址
        CustomerAddress customerAddress = customerAddressService.findByOpenidAndStatus(customer.getOpenid(),CustomerAddressStatusEnum.FINISHEND.getCode());
        if (customerAddress != null) {
            System.out.println(customerAddress.toString());
            map.put("customerAddress", customerAddress);
        }


        if (productId != null) {
            //通过productId获取商品
            List<CartItem> cartProductList = new ArrayList<>();
            CartItem cartItem = new CartItem();
            ProductInfo productInfo = productInfoService.findOne(productId);
            cartItem.setProductInfo(productInfo);
            cartItem.setCount(1);
            cartProductList.add(cartItem);
            map.put("carts", cartProductList);
            map.put("totalPrice", productInfo.getProductPrice());
            //跳转到下单页面
            return new ModelAndView("before/established", map);
        } else {
            Map<String, Integer> carts = (Map<String, Integer>) session.getAttribute("carts");
            BigDecimal totalPrice = new BigDecimal(0);
            if (carts != null) {
                List<CartItem> cartProductList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : carts.entrySet()) {
                    ProductInfo productInfo = productInfoService.findOne(entry.getKey());
                    totalPrice = productInfo.getProductPrice().add(totalPrice).multiply(new BigDecimal(entry.getValue()));
                    CartItem cartItem = new CartItem();
                    cartItem.setProductInfo(productInfo);
                    cartItem.setCount(entry.getValue());
                    cartProductList.add(cartItem);
                }

                map.put("carts", cartProductList);
                map.put("totalPrice", totalPrice);
                return new ModelAndView("before/established", map);

            }

            List<CartItem> cartProductList = new ArrayList<>();
            map.put("carts", cartProductList);
            map.put("totalPrice", 0);
            map.put("message", "购物车为null");
            return new ModelAndView("before/shopping_cart", map);

        }

    }

    /**
     * 确认订单
     *
     * @param productId
     * @param session
     * @param map
     * @return
     */
    @RequestMapping({"/order/ensureorder", "/order/ensureorder.html"})
    public ModelAndView ensureorder(@RequestParam(required = false) String productId,
                                    HttpSession session,
                                    Map<String, Object> map) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }
        //根据用户查找用户选中的收货地址
        CustomerAddress customerAddress = customerAddressService.findByOpenidAndStatus(customer.getOpenid(),CustomerAddressStatusEnum.FINISHEND.getCode());
        if (customerAddress != null) {
            System.out.println(customerAddress.toString());
            map.put("customerAddress", customerAddress);
        }

        if (productId != null) {
            //通过productId获取商品
            List<CartItem> cartProductList = new ArrayList<>();
            CartItem cartItem = new CartItem();
            ProductInfo productInfo = productInfoService.findOne(productId);
            cartItem.setProductInfo(productInfo);
            cartItem.setCount(1);
            cartProductList.add(cartItem);
            map.put("carts", cartProductList);
            map.put("totalPrice", productInfo.getProductPrice());
            //跳转到下单页面
            return new ModelAndView("before/established_date", map);
        } else {
            Map<String, Integer> carts = (Map<String, Integer>) session.getAttribute("carts");
            BigDecimal totalPrice = new BigDecimal(0);
            if (carts != null) {
                List<CartItem> cartProductList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : carts.entrySet()) {
                    ProductInfo productInfo = productInfoService.findOne(entry.getKey());
                    totalPrice = productInfo.getProductPrice().add(totalPrice).multiply(new BigDecimal(entry.getValue()));
                    CartItem cartItem = new CartItem();
                    cartItem.setProductInfo(productInfo);
                    cartItem.setCount(entry.getValue());
                    cartProductList.add(cartItem);
                }

                map.put("carts", cartProductList);
                map.put("totalPrice", totalPrice);
                return new ModelAndView("before/established_date", map);

            }

            List<CartItem> cartProductList = new ArrayList<>();
            map.put("carts", cartProductList);
            map.put("totalPrice", 0);
            map.put("message", "购物车为null");
            return new ModelAndView("before/shopping_cart", map);

        }


    }


    /**
     * 创建订单
     *
     * @param
     * @param session
     * @param map
     * @return
     */
    @RequestMapping({"/order/ordersuccess"})
    public ModelAndView ordersuccess(String productId,
                                     HttpSession session,
                                     Map<String, Object> map) {

        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        //获取购物车
        Map<String, Integer> carts = (Map<String, Integer>) session.getAttribute("carts");


        //获取用户选中的收件地址
        CustomerAddress customerAddress = customerAddressService.findByOpenidAndStatus(customer.getOpenid(),CustomerAddressStatusEnum.FINISHEND.getCode());

        //创建订单OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setBuyerName(customerAddress.getGetName());
        orderDTO.setBuyerOpenid(customerAddress.getOpenid());
        orderDTO.setBuyerAddress(customerAddress.getGetAddress());
        orderDTO.setBuyerPhone(customerAddress.getGetPhone());
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(Map.Entry<String,Integer>entry:carts.entrySet()){
            OrderDetail orderDetail = new OrderDetail();
            ProductInfo productInfo1 = productInfoService.findOne(entry.getKey());
            BeanUtils.copyProperties(productInfo1,orderDetail);
            orderDetail.setProductQuantity(entry.getValue());
            orderDetails.add(orderDetail);
        }
        orderDTO.setOrderDetailList(orderDetails);

        //调用service
        OrderDTO result = orderService.create(orderDTO);
        map.put("result",result);
        map.put("url","/order/list.html");


        //清空当前用户的session
        carts.clear();
        session.setAttribute("carts",carts);

        return new ModelAndView("before/paysuccess",map);


    }


    /**
     * 查看订单列表
     * @param page
     * @param size
     * @param map
     * @param session
     * @return
     */
    @RequestMapping({"/order/list","/order/list.html"})
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "20") Integer size,
                             Map<String, Object> map,
                             HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(customer.getOpenid(),request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("before/orders", map);


    }

    /**
     * 取消订单
     * @param
     * @param session
     * @return
     */
    @RequestMapping({"/order/cancel"})
    public ModelAndView cancel(String orderId ,
                             Map<String, Object> map,
                             HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }


        //取下订单
        OrderDTO orderDTO  = orderService.findOne(orderId);
        orderService.cancel(orderDTO);

        return new ModelAndView("redirect:/order/list.html", map);


    }


    /**
     * 完结订单
     * @param orderId
     * @param map
     * @param session
     * @return
     */
    @RequestMapping({"/order/finish"})
    public ModelAndView finish(String orderId ,
                             Map<String, Object> map,
                             HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }


        //取下订单
        OrderDTO orderDTO  = orderService.findOne(orderId);
        orderService.finish(orderDTO);

        return new ModelAndView("redirect:/order/list.html", map);


    }


    /**
     * 查看已经取消订单列表
     * @param orderId
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/order/cancellist")
    public ModelAndView cancellist(String orderId ,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size,
                               Map<String, Object> map,
                               HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findListAndStatus(customer.getOpenid(),request,OrderStatusEnum.CANCEL.getCode());
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("before/orders", map);


    }


    /**
     * 新订单列表
     * @param orderId
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/order/newlist")
    public ModelAndView newlist(String orderId ,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size,
                                   Map<String, Object> map,
                                   HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findListAndStatus(customer.getOpenid(),request,OrderStatusEnum.NEW.getCode());
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("before/orders", map);


    }


    /**
     * 完结订单列表
     * @param orderId
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/order/finishlist")
    public ModelAndView finishlist(String orderId ,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "20") Integer size,
                                Map<String, Object> map,
                                HttpSession session) {
        //校验用户状态
        Customer customer = (Customer) session.getAttribute("existUser");
        if (customer == null) {
            map.put("message", "登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html", map);
        }

        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findListAndStatus(customer.getOpenid(),request,OrderStatusEnum.FINISHEND.getCode());
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("before/orders", map);


    }







}
