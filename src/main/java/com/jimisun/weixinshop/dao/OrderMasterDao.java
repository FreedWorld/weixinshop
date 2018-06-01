package com.jimisun.weixinshop.dao;

import com.jimisun.weixinshop.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:10 2018-05-13
 * @Modified By:
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster>findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    Page<OrderMaster>findByBuyerOpenidAndOrderStatus(String buyerOpenid, Pageable pageable,Integer status);
}
