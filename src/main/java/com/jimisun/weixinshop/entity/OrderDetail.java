package com.jimisun.weixinshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:08 2018-05-13
 * @Modified By:
 */
@Data
@Entity
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;
    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;


}
