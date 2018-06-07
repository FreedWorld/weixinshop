package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.Admin;
import com.jimisun.weixinshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 14:46 2018-06-04
 * @Modified By:
 */
@Controller
@RequestMapping("/admin")
public class GoAdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping({"/login.html"})
    public ModelAndView adminGoLogin(){
        return new ModelAndView("auth/login");
    }

    /**
     * 管理员登陆
     * @param username
     * @param password
     * @param session
     * @param map
     * @return
     */
    @RequestMapping({"/login"})
    public ModelAndView adminLogin(String username,
                                   String password,
                                   HttpSession session,
                                   Map<String,Object> map){

        //查询数据库验证用户名密码
        Admin admin = adminService.findByUsername(username);
        if(admin==null){
            map.put("message","异常");
            return new ModelAndView("auth/login",map);
        }else if (admin.getPassword().equals(password)){
            admin.setPassword("");
            session.setAttribute("existAdmin",admin);
            return new ModelAndView("redirect:/seller/order/list");
        }

        map.put("message","异常");
        return new ModelAndView("auth/login",map);


    }

    /**
     * 退出登陆
     * @param map
     * @param session
     * @return
     */
    @RequestMapping({"/out"})
    public ModelAndView adminOut(Map<String,Object> map,HttpSession session){

        session.invalidate();
        map.put("message","成功退出登陆！");
        return new ModelAndView("auth/login",map);


    }



}
