package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.dto.OrderDTO;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 19:56 2018-05-14
 * @Modified By:
 */
public interface BuyerService {

    /**
     * 查找一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne(String openid,String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid,String orderId);
}
