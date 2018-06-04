package com.jimisun.weixinshop.enums;

import lombok.Getter;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 15:16 2018-06-01
 * @Modified By:
 */
@Getter
public enum  CustomerStatusEnum implements CodeEnum {

    NEW(1,"禁止使用"),
    FINISHEND(0,"正常使用"),
    ;

    private Integer code;

    private String message;

    CustomerStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
