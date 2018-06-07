package com.jimisun.weixinshop;

import com.jimisun.weixinshop.interceptor.LonginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@SpringBootConfiguration
public class MyWebConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String > patterns = new ArrayList<>();
        patterns.add("/seller/product/list");
        patterns.add("/seller/product/on_sale");
        patterns.add("/seller/product/off_sale");
        patterns.add("/seller/product/index");
        patterns.add("/seller/product/update");
        patterns.add("/seller/order/list");
        patterns.add("/seller/order/cancel");
        patterns.add("/seller/order/detail");
        patterns.add("/seller/order/finish");
        patterns.add("/seller/customer/list");
        patterns.add("/seller/customer/forbid");
        patterns.add("/seller/customer/normal");
        patterns.add("/seller/category/list");
        patterns.add("/seller/category/index");
        patterns.add("/seller/category/update");
        patterns.add("/seller/category/save");
        patterns.add("/notice/index");
        patterns.add("/notice/editNotice");
        patterns.add("/seller/ad1/list");
        patterns.add("/seller/ad1/edit.html");
        patterns.add("/seller/ad1/edit");
        patterns.add("/seller/ad1/delete");



        registry.addInterceptor(new LonginInterceptor()).addPathPatterns(patterns);
    }

}