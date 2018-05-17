package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.OrderMaster;
import com.jimisun.weixinshop.enums.OrderStatusEnum;
import com.jimisun.weixinshop.enums.PayStatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:16 2018-05-13
 * @Modified By:
 */
public class OrderMasterDaoTest extends WeixinshopApplicationTests {

    @Autowired
    private OrderMasterDao orderMasterDao;

    private static String openid = "openid123456";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("orderid124");
        orderMaster.setBuyerName("jimi");
        orderMaster.setBuyerPhone("1855549456485648");
        orderMaster.setBuyerAddress("河南");
        orderMaster.setBuyerOpenid(openid);
        orderMaster.setOrderAmount(new BigDecimal(3.2));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderMaster> orderMasterPage =  orderMasterDao.findByBuyerOpenid(openid,pageRequest);
        System.out.println(orderMasterPage.getContent().size());
    }
}