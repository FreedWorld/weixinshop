package com.jimisun.weixinshop.interceptor;

import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author:jimisun
 * @Description:后台权限验证
 * @Date:Created in 23:57 2018-06-07
 * @Modified By:
 */
@Component
public class LonginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是登录页面则放行
        if(request.getRequestURI().indexOf("login.html")>=0){
            return true;
        }
        HttpSession session = request.getSession();
        //如果用户已登录也放行
        if(session.getAttribute("existAdmin")!=null){
            return true;
        }

        //用户没有登录挑战到登录页面
        request.setAttribute("message",ResultVoCodeEnum.ADMIN_AUTH_MISS.getMsg());
        request.getRequestDispatcher("/admin/login.html").forward(request, response);
        return false;

    }

    /**
     * 用于记录日志 handler已经执行 ModelAndView没有返回
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 用于释放资源  handler已经执行  ModlerAndView已经返回
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }
}
