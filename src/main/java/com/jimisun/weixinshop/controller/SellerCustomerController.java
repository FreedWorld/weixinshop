package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.Customer;
import com.jimisun.weixinshop.enums.CustomerStatusEnum;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:41 2018-06-02
 * @Modified By:
 */
@Controller
@Slf4j
@RequestMapping("/seller/customer")
public class SellerCustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 客户列表
     *
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView customerList(Map<String, Object> map,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     HttpSession session) {


        PageRequest request = new PageRequest(page - 1, size);
        Page<Customer> customerList = customerService.finAll(request);
        map.put("customerList", customerList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("customer/list", map);
    }


    /**
     * 客户拉黑
     *
     * @param map
     * @param
     * @param
     * @return
     */
    @RequestMapping("/forbid")
    public ModelAndView customerforbid(Map<String, Object> map,
                                       String username) {

        try {
            Customer customer = new Customer();
            customer.setUsername(username);
            Customer result = customerService.findByUsername(customer);
            result.setStatus(CustomerStatusEnum.NEW.getCode());
            customerService.save(result);
        } catch (SellException e) {
            log.error("【卖家端拉黑用户】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/customer/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultVoCodeEnum.CUSTOMER_STATUS_NORMAL.getMsg());
        map.put("url", "/seller/customer/list");
        return new ModelAndView("common/success");


    }

    /**
     * 设置客户状态为正常
     *
     * @param map
     * @param username
     * @param session
     * @return
     */
    @RequestMapping("/normal")
    public ModelAndView customerNormal(Map<String, Object> map,
                                       String username, HttpSession session) {


        try {
            Customer customer = new Customer();
            customer.setUsername(username);
            Customer result = customerService.findByUsername(customer);
            result.setStatus(CustomerStatusEnum.FINISHEND.getCode());
            customerService.save(result);
        } catch (SellException e) {
            log.error("【卖家端解锁用户】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/customer/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultVoCodeEnum.CUSTOMER_STATUS_NORMAL.getMsg());
        map.put("url", "/seller/customer/list");
        return new ModelAndView("common/success");


    }


}
