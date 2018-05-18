package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.WeixinshopApplicationTests;
import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 23:45 2018-05-13
 * @Modified By:
 */
@Slf4j
public class OrderServiceImplTest extends WeixinshopApplicationTests {

    private final String openId = "openid123456";

    private final String orderId = "152622801903736385";

    @Autowired
    private OrderServiceImpl orderService;


    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("jimisun");
        orderDTO.setBuyerAddress("北京市");
        orderDTO.setBuyerPhone("12345678963");
        orderDTO.setBuyerOpenid(openId);

        //购物车
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1234567");
        orderDetail.setProductQuantity(5);
        orderDetails.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO result= orderService.create(orderDTO);
        log.info("创建订单{}",result);

    }

    @Test
    public void findOne()throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderId);
        System.out.println(orderDTO);
    }

    @Test
    public void cancel()throws Exception {
        OrderDTO orderDTO =orderService.findOne(orderId);
        OrderDTO orderDTO1 = orderService.cancel(orderDTO);
        System.out.println(orderDTO1);
    }

    @Test
    public void finish()throws Exception {
        OrderDTO orderDTO =orderService.findOne(orderId);
        OrderDTO orderDTO1 = orderService.finish(orderDTO);
        System.out.println(orderDTO1);
    }

    @Test
    public void paid()throws Exception{
        OrderDTO orderDTO =orderService.findOne(orderId);
        OrderDTO orderDTO1 = orderService.paid(orderDTO);
        System.out.println(orderDTO1);
    }

    @Test
    public void findList()  throws  Exception{
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderDTO>orderDTOPage = orderService.findList(openId,pageRequest);
        log.info("查询结果{}",orderDTOPage);
    }

    @Test
    public void findList1() {
        PageRequest pageRequest = new PageRequest(0,10);
        Page<OrderDTO>orderDTOPage = orderService.findList(pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getContent().size());
    }
}