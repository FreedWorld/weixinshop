package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.Customer;
import com.jimisun.weixinshop.entity.CustomerAddress;
import com.jimisun.weixinshop.enums.CustomerAddressStatusEnum;
import com.jimisun.weixinshop.service.CustomerAddressService;
import com.jimisun.weixinshop.service.CustomerService;
import com.jimisun.weixinshop.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:54 2018-05-29
 * @Modified By:
 */
@Controller
public class GoCustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAddressService customerAddressService;

    /**
     * 用户登陆
     * @param customer
     * @param session
     * @param code
     * @param map
     * @return
     */
    @PostMapping("/customer/login")
    public ModelAndView login(Customer customer,
                              HttpSession session,
                              String code,
                              Map<String,Object>map) {
        //校验验证码
        if (!session.getAttribute("code").toString().equals(code)) {
            map.put("message", "验证码不正确！");
            return new ModelAndView("redirect:/login.html", map);
        }

        //根据用户名查询数据库账户
        Customer result = customerService.findByUsername(customer);
        if(result==null){
            map.put("message", "用户名不存在！");
            return new ModelAndView("redirect:/login.html", map);
        }
        if(!result.getPassword().equals(customer.getPassword())){
            map.put("message", "密码错误！");
            return new ModelAndView("redirect:/login.html", map);
        }

        //清除隐秘信息
        result.setPassword("");

        //检验通过
        session.setAttribute("existUser",result);
        return new ModelAndView("redirect:/center.html");

    }

    /**
     * 用户注册
     * @param customer
     * @param password1
     * @param code
     * @param session
     * @param map
     * @return
     */
    @PostMapping("/customer/register")
    public ModelAndView register(Customer customer,
                                 String password1,
                                 String code,
                                 HttpSession session,
                                 Map<String, Object> map) {

        //检验密码
        if (!customer.getPassword().equals(password1)) {
            map.put("message", "两次密码不正确！");
            return new ModelAndView("before/register", map);
        }

        //校验验证码
        if (!session.getAttribute("code").toString().equals(code)) {
            map.put("message", "验证码不正确！");
            return new ModelAndView("before/register", map);
        }
        //校验用户名重复
        if(customerService.findByUsername(customer)!=null){
            map.put("message", "用户名已存在，请重试！");
            return new ModelAndView("redi", map);
        }


        //补全customer属性
        customer.setId(KeyUtil.genUniqueKey());
        customer.setOpenid(KeyUtil.getUUID());
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());

        //调用service
        customerService.register(customer);
        //响应
        map.put("message", "注册成功,请登陆");
        return new ModelAndView("before/login", map);

    }

    /**
     * 跳转到列表页面
     * @param map
     * @param session
     * @return
     */
    @GetMapping({"/customer/address","/customer/address.html"})
    public ModelAndView goaddress(Map<String,Object>map,
                                  HttpSession session){
        //从HttpSerssion中获取用户的openid
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }

        //查询当前用户的地址列表
        List<CustomerAddress> customerAddressList = customerAddressService.findByOpenid(customer.getOpenid());
        map.put("customerAddressList",customerAddressList);
        return new ModelAndView("before/address",map);
    }

    @GetMapping({"/customer/add_new","/customer/add_new.html"})
    public ModelAndView goaddnew(Map<String,Object>map,
                                  HttpSession session){
        //从HttpSerssion中获取用户的openid
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }


        return new ModelAndView("before/add_new",map);
    }




    /**
     * 新增客户地址
     * 跳转地址列表
     * @param map
     * @return
     */
    @PostMapping("/customer/add_address")
    public ModelAndView addAddress(Map<String,Object>map,
                                   CustomerAddress customerAddress,
                                   HttpSession session){

        //从HttpSerssion中获取用户的用户
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }
        if(customerAddress.getId()==null&&customerAddress.getOpenid()==null){
            customerAddress.setOpenid(customer.getOpenid());
        }

        customerAddress.setStatus(CustomerAddressStatusEnum.NEW.getCode());


        //补全CustomerAddress对象



        //调用service
        customerAddressService.addCustomerAddress(customerAddress);


        return new ModelAndView("redirect:/customer/address.html",map);
    }

    /**
     * 跳转修改地址页面
     * 回显
     * @param map
     * @param id
     * @param session
     * @return
     */
    @GetMapping({"/customer/editaddress.html","/customer/editaddress"})
    public ModelAndView editAddress(Map<String,Object>map,
                                   @RequestParam(value = "addressid") Integer id,
                                   HttpSession session){
        //从HttpSerssion中获取用户的用户
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }

        //通过id查询地址信息
        CustomerAddress customerAddress = customerAddressService.findById(id);
        map.put("customerAddress",customerAddress);

        return new ModelAndView("before/editaddress",map);
    }


    @GetMapping("/customer/delete")
    public ModelAndView deleteAddress(Map<String,Object>map,
                                    @RequestParam(value = "addressid") Integer id,
                                    HttpSession session){
        //从HttpSerssion中获取用户的用户
        Customer customer = (Customer) session.getAttribute("existUser");
        if(customer==null){
            map.put("message","登陆过期，重新登陆");
            return new ModelAndView("redirect:/login.html",map);
        }

        //通过id查询地址信息
        customerAddressService.deleteById(id);

        return new ModelAndView("redirect:/customer/address.html",map);
    }



}
