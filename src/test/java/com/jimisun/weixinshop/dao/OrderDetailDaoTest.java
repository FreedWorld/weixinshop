package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.entity.OrderDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:29 2018-05-13
 * @Modified By:
 */
public class OrderDetailDaoTest extends WeixinshopApplicationTests {


    @Autowired
    private OrderDetailDao orderDetailDao;

    private static String orderId= "orderid123";

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setDetailId("detailid124");
        orderDetail.setProductId("1234568");
        orderDetail.setProductName("水果粥");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductIcon("http://xxx.com");

        orderDetailDao.save(orderDetail);

    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        System.out.print(orderDetailList.size());
    }
}