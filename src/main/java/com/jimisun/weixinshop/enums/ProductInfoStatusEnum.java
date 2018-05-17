package com.jimisun.weixinshop.enums;

import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductInfoStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架"),
    PRODUCT_NOT_EXIST(10,"商品不存在！")
    ;

    private Integer code;

    private String msg;

    ProductInfoStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
