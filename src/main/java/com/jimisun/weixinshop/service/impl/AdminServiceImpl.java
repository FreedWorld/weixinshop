package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.AdminDao;
import com.jimisun.weixinshop.entity.Admin;
import com.jimisun.weixinshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 14:54 2018-06-04
 * @Modified By:
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }
}
