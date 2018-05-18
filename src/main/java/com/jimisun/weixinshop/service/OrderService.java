package com.jimisun.weixinshop.service;

import com.jimisun.weixinshop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:41 2018-05-13
 * @Modified By:
 */
public interface OrderService {

    /** 获取某个用户所有订单 **/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) ;

    /** 创建订单 **/
    OrderDTO create(OrderDTO orderDTO) ;

    /** 查询单个订单 **/
    OrderDTO findOne(String orderId);

    /** 取消订单 **/
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结支付 **/
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 **/
    OrderDTO paid(OrderDTO orderDTO);

    /** 获取所有订单 **/
    Page<OrderDTO> findList(Pageable pageable) ;


}
