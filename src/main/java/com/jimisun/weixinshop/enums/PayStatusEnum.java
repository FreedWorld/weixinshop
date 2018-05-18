package com.jimisun.weixinshop.enums;

import lombok.Getter;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:05 2018-05-13
 * @Modified By:
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
