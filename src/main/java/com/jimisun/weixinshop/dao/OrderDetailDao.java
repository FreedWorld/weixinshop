package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:13 2018-05-13
 * @Modified By:
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {

    List<OrderDetail>findByOrderId(String orderId);

}
