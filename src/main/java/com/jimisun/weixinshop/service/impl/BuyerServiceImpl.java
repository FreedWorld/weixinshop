package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.service.BuyerService;
import com.jimisun.weixinshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 19:59 2018-05-14
 * @Modified By:
 */
@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId)  {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultVoCodeEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultVoCodeEnum.ORDER_NOT_EXIST);
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultVoCodeEnum.ORDER_OWNER_ERROR);
        }
        return orderService.cancel(orderDTO);
    }
}
