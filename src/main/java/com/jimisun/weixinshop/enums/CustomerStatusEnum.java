package com.jimisun.weixinshop.enums;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 15:16 2018-06-01
 * @Modified By:
 */
public enum  CustomerStatusEnum {

    NEW(1,"禁止使用"),
    FINISHEND(0,"正常使用"),
    ;

    private Integer code;

    private String message;

    CustomerStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
