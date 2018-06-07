package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.entity.Ad1;
import com.jimisun.weixinshop.entity.Notice;
import com.jimisun.weixinshop.service.Ad1Service;
import com.jimisun.weixinshop.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 17:25 2018-06-04
 * @Modified By:
 */
@Controller
@RequestMapping("")
public class SellerContentController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private Ad1Service ad1Service;


    /**
     * 跳转notice主页面
     * @param map
     * @return
     */
    @RequestMapping("/notice.html")
    public ModelAndView goEditNotice(Map<String,Object> map){
        //查询最新notice
        Notice notice = noticeService.findNew();
        map.put("notice",notice);
        return new ModelAndView("before/notice",map);
    }

    /**
     * 后台notice主页
     * @param map
     * @return
     */
    @RequestMapping("/notice/index")
    public ModelAndView index(Map<String,Object> map){

        //查询最新notice
        Notice notice = noticeService.findNew();
        map.put("notice",notice);
        return new ModelAndView("content/noticeindex",map);
    }

    /**后台修改notice
     * @param map
     * @return
     */
    @RequestMapping("/notice/editNotice")
    public ModelAndView editNotice(Map<String,Object> map,
                                   String title,
                                   String content){

        //查询最新notice
        Notice notice = noticeService.findNew();
        //设置内容
        notice.setTitle(title);
        notice.setContent(content);
        Notice result = noticeService.save(notice);
        map.put("notice",result);
        return new ModelAndView("content/noticeindex",map);
    }

    /**
     * 跳转轮播图列表
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/seller/ad1/list")
    public ModelAndView ad1List(Map<String,Object> map,
                                   HttpSession session){
        //查看所有List列表
        List<Ad1>ad1List =  ad1Service.findAd1All();
        map.put("ad1List",ad1List);

        //跳转页面
        return new ModelAndView("content/ad1list",map);


    }

    /**
     * 跳转修改页面
     * @param map
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/seller/ad1/edit.html")
    public ModelAndView goAd1Edit(Map<String,Object> map,
                                Integer id,
                                HttpSession session){

        Ad1 ad1 = ad1Service.findById(id);
        map.put("ad1",ad1);

        //跳转页面
        return new ModelAndView("content/ad1edit",map);

    }

    /**
     * 修改操作
     * @param map
     * @param ad1
     * @param session
     * @return
     */
    @RequestMapping("/seller/ad1/edit")
    public ModelAndView ad1Edit(Map<String,Object> map,
                                Ad1 ad1,
                                HttpSession session){


        //查询
        ad1Service.save(ad1);

        //跳转页面
        return new ModelAndView("redirect:/seller/ad1/list",map);

    }


    /**
     * 删除操作
     * @param map
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/seller/ad1/delete")
    public ModelAndView ad1Delete(Map<String,Object> map,
                                Integer id,
                                HttpSession session){
        ad1Service.deleteById(id);

        //跳转页面
        return new ModelAndView("redirect:/seller/ad1/list",map);

    }



}
