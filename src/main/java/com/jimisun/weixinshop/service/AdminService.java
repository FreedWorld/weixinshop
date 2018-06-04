package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.entity.Admin;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 14:53 2018-06-04
 * @Modified By:
 */
public interface AdminService {

    Admin findByUsername(String username);
}
