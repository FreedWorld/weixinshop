package com.jimisun.weixinshop.enums;

import lombok.Getter;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:03 2018-05-13
 * @Modified By:
 */
@Getter
public enum OrderStatusEnum {


    NEW(0,"新订单"),
    FINISHEND(1,"完结"),
    CANCEL(2,"取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
