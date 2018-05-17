package com.jimisun.weixinshop.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jimisun.weixinshop.entity.OrderDetail;
import com.jimisun.weixinshop.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:46 2018-05-13
 * @Modified By:
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id. */
    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus ;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus ;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //给返回的字段附上初始值List<OrderDetail>orderDetailList = new ArrayList<>();

    List<OrderDetail>orderDetailList;


}
