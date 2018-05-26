package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dao.Ad1Dao;
import com.jimisun.weixinshop.entity.Ad1;
import com.jimisun.weixinshop.service.Ad1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:40 2018-05-25
 * @Modified By:
 */
@Service
@Transactional
public class Ad1ServiceImpl implements Ad1Service {

    @Autowired
    private Ad1Dao ad1Dao;

    @Override
    public List<Ad1> findAd1All() {
        return ad1Dao.findAll();
    }
}
