package com.jimisun.weixinshop.enums;

import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductInfoStatusEnum implements CodeEnum{
    UP(0,"在架"),
    DOWN(1,"下架"),
    PRODUCT_NOT_EXIST(10,"商品不存在！")
    ;

    private Integer code;

    private String message;

    ProductInfoStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
