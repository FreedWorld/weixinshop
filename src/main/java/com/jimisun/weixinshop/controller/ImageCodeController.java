package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.utils.CodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:04 2018-05-29
 * @Modified By:
 */
@Controller
public class ImageCodeController {

    /**
     * 客户登陆验证码
     * @param session
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/imageCode")
    public void getImageCode(HttpSession session, HttpServletResponse resp) throws IOException {
        resp.setHeader("Cache-Control","no-store");
        resp.setHeader("Pragrma","no-cache");
        resp.setDateHeader("Expires",0);

        String result = CodeUtil.drawImageVerificate(resp);
        session.removeAttribute("code");
        session.setAttribute("code",result);
        OutputStream stream = resp.getOutputStream();
        stream.write(result.getBytes());
        stream.flush();
        stream.close();
    }

}
